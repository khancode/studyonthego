<?php
/**
 * Created by PhpStorm.
 * User: rockg_000
 * Date: 3/18/2015
 * Time: 4:48 PM
 */

class GetAllStudyGroups {

    private $course;
    private $building;

    // constructor
    function __construct($data) {
        $this->course = $data->get('course');
        $this->building = mysql_real_escape_string($data->get('building'));
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
//        return json_encode($this->get_study_groups());

        $result = $this->get_study_groups();

        return json_encode($result);
    }

    private function get_study_groups()
    {
        if (!empty($this->course)) {

            preg_match("/[a-zA-Z]+/", $this->course, $matches);
//            echo 'subject: '.count($matches);
//            if (count($matches) == 0) {
//                $subject = null;
//                if (empty($subject))
//                    'mamacita';
//            }
//            else
            $subject = $matches[0];

            preg_match("/\d+/", $this->course, $matches);
//            echo 'courseNumber: '.count($matches);
//            if (count($matches) == 0)
//                $courseNumber = null;
//            else
            $courseNumber = $matches[0];
        }

        if (empty($this->course) && empty($this->building))
            $query = "SELECT S.GroupID, S.GroupName, S.Admin, S.CourseID, C.Subject, C.CourseNumber, C.Section,
                             S.Description, S.Building, S.Location, S.StartDate, S.EndDate, S.StartTime, S.EndTime,
                             S.MembersCount, S.MembersLimit FROM StudyGroup AS S
                      INNER JOIN
                      Course AS C ON S.CourseID=C.CourseID";
        else
        {
            if (!empty($this->course) && empty($this->building))
            {
                $query = "SELECT * FROM StudyGroup AS S
                          INNER JOIN
                          Course AS C ON C.Subject='$subject' AND C.CourseNumber='$courseNumber' AND S.CourseID=C.CourseID";
            }
            else if (!empty($this->building) && empty($this->course))
            {
                $query = "SELECT * FROM StudyGroup AS S
                          INNER JOIN
                          Course AS C ON Building='$this->building' AND S.CourseID=C.CourseID";
            }
            else
            {
                $query = "SELECT * FROM StudyGroup AS S
                          INNER JOIN
                          Course AS C ON C.Subject='$subject' AND C.CourseNumber='$courseNumber' AND S.CourseID=C.CourseID AND S.Building='$this->building'";
            }
        }

        if ($query_run = mysql_query($query))
        {
            if (mysql_num_rows($query_run) == NULL)
                return [];

            while($row = mysql_fetch_assoc($query_run))
                $output[]=$row;

            return $output;
        }
//        else
//        {
//            #        Gotta handle if the MySQL query was successful
//            die('[ { "mysqlError":true } ]');
//        }
    }

}