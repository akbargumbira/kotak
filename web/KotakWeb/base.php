<?php

// database
$dbname = "Kotak";
$dbhost = "localhost";
$dbuser = "root";
$dbpass = "";
define("BASE_PATH", "http://localhost");
$path = "Kotak/";
$dir_file="repository/";
$urlsite = 'http://localhost/Kotak/';

mysql_connect($dbhost, $dbuser, $dbpass) or die ("Error :" .  mysql_error());
mysql_select_db($dbname) or die ("Error :" .  mysql_error());

?>
