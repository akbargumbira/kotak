<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
session_start();

// menghapus session
session_destroy();
header("Location: ../KotakWeb/index.php");

?>
