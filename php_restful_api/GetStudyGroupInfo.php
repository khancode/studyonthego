<?php
/**
 * Created by PhpStorm.
 * User: khancode
 * Date: 4/4/2015
 * Time: 1:16 PM
 */

class GetStudyGroupInfo {

    private $groupID;

    // constructor
    function __construct($data)
    {
        $this->groupID = $data->get('groupID');
    }

    /**
     * query() returns an array of JSON objects which contain all columns of the StudyGroup table:
     *      If user entered courseID and/or building, then get study groups based on those conditions.
     *      Otherwise, return all study groups.
     *
     * return [ {StudyGroup_1}, {StudyGroup_2}, ... ]
     */


    public function query()
    {
        $studyGroup = $this->get_study_group_info();

        if ($studyGroup == NULL)
        {
            $result = array('groupExists' => false);
        }
        else
        {
            $status = array('groupExists' => true);
            $result = array_merge($status, $studyGroup);
        }

        return json_encode($result);
    }

    private function get_study_group_info()
    {
        $query = "SELECT S.GroupID, S.GroupName, S.Admin, S.CourseID, C.Subject, C.CourseNumber, C.Section,
                         S.Description, S.Building, S.StartDate, S.EndDate,
                         S.StartTime, S.EndTime, S.MembersCount, S.MembersLimit
                  FROM StudyGroup AS S
                  INNER JOIN Course AS C
                  ON GroupID='$this->groupID' AND S.CourseID=C.CourseID";

        if ($query_run = mysql_query($query))
        {
            if (mysql_num_rows($query_run) == NULL)
                return NULL;

            $row = mysql_fetch_assoc($query_run);

            $output = $row;

            return $output;
        }
    }

}