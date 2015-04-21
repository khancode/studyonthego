<?php
/**
 * Created by PhpStorm.
 * User: khancode
 * Date: 2/21/2015
 * Time: 8:32 PM
 */

class CreateStudyGroup {

    private $groupName;
    private $admin;
    private $courseID;
    private $description;
    private $building;
    private $location;
    private $startDate;
    private $endDate;
    private $startTime;
    private $endTime;
    private $membersCount;
    private $membersLimit;

    // constructor
    function __construct($data) {
        $this->groupName = mysql_real_escape_string($data->post('groupName'));
        $this->admin = $data->post('admin');
        $this->courseID = $data->post('courseID');
        $this->description = mysql_real_escape_string($data->post('description'));
        $this->building = mysql_real_escape_string($data->post('building'));
        $this->location = mysql_real_escape_string($data->post('location'));
        $this->startDate = $data->post('startDate');
        $this->endDate = $data->post('endDate');
        $this->startTime = $data->post('startTime');
        $this->endTime = $data->post('endTime');
        $this->membersCount = 1; // By default includes admin.
        $this->membersLimit = $data->post('membersLimit');
    }

    /**
     * query() returns a JSON object with two boolean values:
     *      groupNameExists: true if groupName already exists, false otherwise
     *      insertError: true if MySQL insert query is successful, false otherwise
     *
     * return {'groupNameExists':boolean, 'insertError':boolean}
     */

    public function query()
    {
        $queryResult_1 = $this->check_if_groupname_exists();
        if ($queryResult_1['groupNameExists'])
            return json_encode($queryResult_1);

        $queryResult_2 = $this->insert_into_studygroup();
        if ($queryResult_2['insertStudyGroupError'])
            return json_encode(array_merge($queryResult_1, array('insertMemberError' => true)));
        $queryResult_1_2 = array_merge($queryResult_1, $queryResult_2);

        $queryResult_3 = $this->get_group_info_after_insert();
        $queryResult_1_2_3 = array_merge($queryResult_1_2, $queryResult_3);

        $queryResult_4 = $this->insert_into_member($queryResult_1_2_3['GroupID']);
        $result = array_merge($queryResult_1_2_3, $queryResult_4);

        return json_encode($result);
    }

    private function check_if_groupname_exists()
    {
        $query = "SELECT GroupName FROM StudyGroup WHERE GroupName='$this->groupName'";

        if ($query_run = mysql_query($query))
        {
            if (mysql_num_rows($query_run) == NULL)
                return array('groupNameExists' => false);
            else
                return array('groupNameExists' => true, 'insertStudyGroupError' => true, 'insertMemberError' => true);
        }
    }

    private function insert_into_studygroup()
    {
        $query = "INSERT INTO StudyGroup (GroupName, Admin, CourseID, Description, Building, Location,
                                          StartDate, EndDate, StartTime, EndTime, MembersCount, MembersLimit)
                  VALUES ('$this->groupName', '$this->admin', '$this->courseID', '$this->description', '$this->building', '$this->location',
                          '$this->startDate', '$this->endDate', '$this->startTime', '$this->endTime', '$this->membersCount', '$this->membersLimit')";

        if ($query_run = mysql_query($query))
            return array('insertStudyGroupError' => false);
        else
            return array('insertStudyGroupError' => true);
    }

    private function get_group_info_after_insert()
    {
        $query = "SELECT GroupID FROM StudyGroup WHERE Admin='$this->admin' AND StartDate='$this->startDate' AND StartTime='$this->startTime'";

        $query = "SELECT S.GroupID FROM StudyGroup AS S
                  INNER JOIN Course AS C
                  ON Admin='$this->admin' AND StartDate='$this->startDate' AND StartTime='$this->startTime' AND S.CourseID=C.CourseID";

        if ($query_run = mysql_query($query))
        {
            $row = mysql_fetch_assoc($query_run);
            return array('GroupID' => $row["GroupID"]);
        }
    }

    private function insert_into_member($groupId)
    {
        $query = "INSERT INTO Member (GroupID, Username)
                  VALUES ('$groupId', '$this->admin')";

        if ($query_run = mysql_query($query))
            return array('insertMemberError' => false);
        else
            return array('insertMemberError' => true);
    }
}