<?php
/**
 * Created by PhpStorm.
 * User: khancode
 * Date: 4/4/2015
 * Time: 7:00 PM
 */

class RespondToRequest {

    private $groupID;
    private $username;

    // constructor
    function __construct($data) {
        $this->groupID = $data->post('groupID');
        $this->username = $data->post('username');
        $this->acceptRequest = $data->post('acceptRequest');
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
        $queryResult1 = $this->delete_request();
        if ($queryResult1['deleteRequestToJoinError'])
            return json_encode($queryResult1);

        if ($this->acceptRequest == 'yes')
        {
            $queryResult2 = $this->insert_into_member();
            if ($queryResult2['insertMemberError'])
                return json_encode(array_merge($queryResult1, $queryResult2));

            $queryResult3 = $this->update_member_count();

            $result = array_merge($queryResult1, $queryResult2, $queryResult3);
        }
        else if ($this->acceptRequest == 'no')
        {
            $result = $queryResult1;
        }
        else // This should not happen
            die("ERROR: acceptRequest must be 'yes' or 'no'");

        return json_encode($result);
    }

    private function delete_request()
    {
        $query = "DELETE FROM RequestToJoin WHERE GroupID='$this->groupID' AND Username='$this->username'";

        if ($query_run = mysql_query($query))
            return array('deleteRequestToJoinError' => false);
        else
            return array('deleteRequestToJoinError' => true);
    }

    private function insert_into_member()
    {
        $query = "INSERT INTO Member (GroupID, Username) VALUES('$this->groupID', '$this->username')";

        if ($query_run = mysql_query($query))
            return array('insertMemberError' => false);
        else
            return array('insertMemberError' => true, 'updateStudyGroupError' => true);
    }

    private function update_member_count()
    {
        $query = "UPDATE StudyGroup
                  SET MembersCount = MembersCount + 1
                  WHERE GroupID='$this->groupID';";

        if ($query_run = mysql_query($query))
            return array('updateStudyGroupError' => false);
        else
            return array('updateStudyGroupError' => true);
    }

}