<?php

require_once 'base.php';
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
session_start();

if (!isset($_SESSION['email'])) {
    echo "<link rel='stylesheet' type='text/css' href='style/style.css'/>";
    echo "<div id='main'>";
    echo "<h1>Sorry you are not logged in</h1>";
    echo "<p> Please either <a href='index.php'> login</a> , or <a href='registerform.php'>click here to register</a>";
    echo "</div>";
    exit;
}
?>
