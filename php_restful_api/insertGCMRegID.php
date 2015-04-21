<?php
/**
 * Created by PhpStorm.
 * User: khancode
 * Date: 4/6/2015
 * Time: 4:47 PM
 */
    require 'connect.inc.php';

    $json = $_SERVER['HTTP_JSON'];

    $data = json_decode($json);

    $username = $data->username;
    $regID = $data->regID;


    $query = "INSERT INTO GCM_RegIDs (Username, RegID) VALUES ('$username', '$regID') ON DUPLICATE KEY UPDATE RegID='$regID'";

    if ($query_run = mysql_query($query))
        echo '{ "insertError":false }';
    else
        die('{ "insertError":true }');