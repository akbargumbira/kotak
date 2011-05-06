<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

error_reporting(0);

function userErrorHandler($errno, $errmsg, $filename, $linenum, $vars) {
    $user_errors = array(E_USER_ERROR, E_USER_WARNING, E_USER_NOTICE);
    if (!in_array($errno, $user_errors)) {//abaikan error diatas
        //echo "Error nomor " . $errno . "<br/>pesan: " . $errmsg . "<br/> di file: " . $filename . " line: " . $linenum . "<br/> vars " . $vars;
        return;
    }
}

?>
