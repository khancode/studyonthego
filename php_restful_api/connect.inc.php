<?php
/**
 * Created by PhpStorm.
 * User: khancode
 * Date: 2/21/2015
 * Time: 9:33 PM
 */

// To allow Ionic app to call this script from localhost server browser.
header("Access-Control-Allow-Origin: *");
header("Access-Control-Allow-Methods: GET, POST, OPTIONS, PUT, DELETE");
header("Access-Control-Allow-Headers: X-API-KEY, Origin, X-Requested-With, Content-Type, Accept, Access-Control-Request-Method");

$conn_error = 'Could not connect.';

$mysql_host = 'localhost';
$mysql_user = 'studyont_dbadmin';
$mysql_pass = 'aaosMAS2015';

$mysql_db = 'studyont_db';

if (!@mysql_connect($mysql_host, $mysql_user, $mysql_pass) || !@mysql_select_db($mysql_db))
{
    die($conn_error);
}
