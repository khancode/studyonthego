<?php
/**
 * Created by PhpStorm.
 * User: rockg_000
 * Date: 2/23/2015
 * Time: 3:41 PM
 */

class GetCurrentStudyGroups {

    private $courses;
    private $building;

    private $currentDate;
    private $currentTime;

    // constructor
    function __construct($data) {
        $this->courses = $data->get('courses');
        $this->building = mysql_real_escape_string($data->get('building'));

        date_default_timezone_set("America/New_York");
        $this->currentDate = date('Y-m-d', time());
        $this->currentTime = date('H:i', time());
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
        $studyGroups = $this->get_study_groups();

//        if (sizeof($studyGroups) != 0)
//        {
//            $studyGroups = $this->get_members($studyGroups);
//        }

        $result = $studyGroups;

        return json_encode($result);
    }

    private function get_study_groups()
    {
        if (!empty($this->courses))
        {
            $arr = explode("_", $this->courses);

            $courseCondition = [];

            for ($i = 0; $i < sizeof($arr); $i++) {
                $crs = $arr[$i];
                preg_match("/[a-zA-Z]+/", $crs, $matches);
                $subject = $matches[0];

                preg_match("/\d+/", $crs, $matches);
                $courseNumber = $matches[0];


                $courseCondition[] = "(C.Subject='$subject' AND C.CourseNumber='$courseNumber' AND S.CourseID=C.CourseID)";
            }
        }

        if (empty($this->courses) && empty($this->building))
            $query = "SELECT S.GroupID, S.GroupName, S.Admin, S.CourseID, C.Subject, C.CourseNumber, C.Section,
                             S.Description, S.Building, S.StartDate, S.EndDate, S.StartTime, S.EndTime,
                             S.MembersCount, S.MembersLimit FROM StudyGroup AS S
                      INNER JOIN
                      Course AS C ON S.CourseID=C.CourseID
                                     AND
                                     ((S.EndDate > '$this->currentDate') OR (S.EndDate='$this->currentDate' AND S.EndTime > '$this->currentTime'))";
        else
        {
            if (!empty($this->courses) && empty($this->building))
            {
                $query = "SELECT S.GroupID, S.GroupName, S.Admin, S.CourseID, C.Subject, C.CourseNumber, C.Section,
                             S.Description, S.Building, S.StartDate, S.EndDate, S.StartTime, S.EndTime,
                             S.MembersCount, S.MembersLimit FROM StudyGroup AS S
                          INNER JOIN
                          Course AS C ON ((S.EndDate > '$this->currentDate') OR (S.EndDate='$this->currentDate' AND S.EndTime > '$this->currentTime'))
                                         AND (".implode(' OR ', $courseCondition).")";
            }
            else if (!empty($this->building) && empty($this->courses))
            {
                $query = "SELECT S.GroupID, S.GroupName, S.Admin, S.CourseID, C.Subject, C.CourseNumber, C.Section,
                             S.Description, S.Building, S.StartDate, S.EndDate, S.StartTime, S.EndTime,
                             S.MembersCount, S.MembersLimit FROM StudyGroup AS S
                          INNER JOIN
                          Course AS C ON Building='$this->building' AND S.CourseID=C.CourseID
                                         AND
                                         ((S.EndDate > '$this->currentDate') OR (S.EndDate='$this->currentDate' AND S.EndTime > '$this->currentTime'))";
            }
            else
            {
                $query = "SELECT S.GroupID, S.GroupName, S.Admin, S.CourseID, C.Subject, C.CourseNumber, C.Section,
                             S.Description, S.Building, S.StartDate, S.EndDate, S.StartTime, S.EndTime,
                             S.MembersCount, S.MembersLimit FROM StudyGroup AS S
                          INNER JOIN
                          Course AS C ON S.Building='".$this->building."'
                                         AND
                                         ((S.EndDate > '$this->currentDate') OR (S.EndDate='$this->currentDate' AND S.EndTime > '$this->currentTime'))
                                         AND (".implode(' OR ', $courseCondition).")";

//                echo 'query: '.$query;
            }
        }

        if ($query_run = mysql_query($query))
        {
            if (mysql_num_rows($query_run) == NULL)
                return [];

            while($row = mysql_fetch_assoc($query_run)) {
//                $row['members'] = []; // add empty 'members' array
                $output[] = $row;
            }

            return $output;
        }
//        else
//        {
//            #        Gotta handle if the MySQL query was successful
//            die('[ { "mysqlError":true } ]');
//        }
    }

//    private function get_members($studyGroups)
//    {
//        // Dynamically construct query
//        for ($i = 0; $i < sizeof($studyGroups); $i++) {
//            $group = $studyGroups[$i];
//            $groupId = $group['GroupID'];
//            $memberCondition[] = "(M.GroupID='$groupId' AND M.Username=P.Username)";
//        }
//
//        $query = "SELECT M.GroupID, M.Username, P.FirstName, P.LastName, P.Major, P.Year, P.Skills FROM Member AS M
//                  INNER JOIN
//                  Profile AS P ON " . implode(' OR ', $memberCondition);
//
//        if ($query_run = mysql_query($query))
//        {
//            // This condition shouldn't happen
////            if (mysql_num_rows($query_run) == NULL) {
////                $output = [];
////            }
//
//            $membersWithoutCourses = array();
//            while ($row = mysql_fetch_assoc($query_run))
//                $membersWithoutCourses[] = $row;
//
//            $membersWithCourses = $this->get_courses_of_members($membersWithoutCourses);
//
//            for ($i = 0; $i < sizeof($membersWithCourses); $i++)
//            {
//                $profile = $membersWithCourses[$i];
//                $groupId = $profile['GroupID'];
//                // find studygroup with groupId in studygroups
//                for ($j = 0; $j < sizeof($studyGroups); $j++)
//                {
//                    $studyGroup = $studyGroups[$j];
//                    if ($studyGroup['GroupID'] == $groupId)
//                    {
//                        unset($profile['GroupID']); // remove GroupID from profile
//                        if (array_key_exists('members', $studyGroup))
//                            $studyGroup['members'][] = $profile; // add profile to 'Members' array
//
//                        $studyGroups[$j] = $studyGroup;
//                        break;
//                    }
//                }
//            }
//
//            return $studyGroups;
//        }
//
//    }
//
//
//    private function get_courses_of_members($profiles)
//    {
//        for ($i = 0; $i < sizeof($profiles); $i++) {
//            $profile = $profiles[$i];
//            $username = $profile['Username'];
//            $courseCondition[] = "Username='$username'";
//        }
//
//        $query = "SELECT CU.Username, CU.CourseID, C.Subject, C.CourseNumber, C.Section FROM CoursesOfUser AS CU
//                  INNER JOIN
//                  Course AS C ON C.CourseID=CU.CourseID AND (".implode(' OR ', $courseCondition).")";
//
//        if ($query_run = mysql_query($query))
//        {
//            // This condition shouldn't happen
////            if (mysql_num_rows($query_run) == NULL) {
////                $output = [];
////            }
//
//            $coursesOfUsers = array();
//            while($row = mysql_fetch_assoc($query_run)) {
//                $key = $row['Username'];
//                $courseId = $row['CourseID'];
//                $subject = $row['Subject'];
//                $courseNumber = $row['CourseNumber'];
//                $section = $row['Section'];
//                $package = array('CourseID' => $courseId, 'Subject' => $subject, 'CourseNumber' => $courseNumber,
//                    'Section' => $section);
//
//                if (array_key_exists($key, $coursesOfUsers))
//                    $coursesOfUsers[$key][] = $package;
//                else
//                    $coursesOfUsers[$key] = array($package);
//            }
//
//            for ($i = 0; $i < sizeof($profiles); $i++) {
//                $profile = $profiles[$i];
//                $key = $profile['Username'];
//                if (array_key_exists($key, $coursesOfUsers)) {
//                    $profile['Courses'] = $coursesOfUsers[$key];
//                    $profiles[$i] = $profile;
//                }
//            }
//
//            return $profiles;
//        }
//
//    }
}