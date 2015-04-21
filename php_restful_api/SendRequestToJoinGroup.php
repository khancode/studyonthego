<?php
/**
 * Created by PhpStorm.
 * User: khancode
 * Date: 4/4/2015
 * Time: 4:38 PM
 */

class SendRequestToJoinGroup {

    private $groupID;
    private $username;

    // constructor
    function __construct($data) {
        $this->groupID = $data->post('groupID');
        $this->username = $data->post('username');
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
        $result = $this->send_request_to_join();

        return json_encode($result);
    }

    private function send_request_to_join()
    {
        $query = "INSERT INTO RequestToJoin (GroupID, Username) VALUES('$this->groupID', '$this->username')";

        if ($query_run = mysql_query($query))
            return array('insertRequestToJoinError' => false);
        else
            return array('insertRequestToJoinError' => true);
    }
}