<?php

include 'base.php';
include 'errorhandler.php';

$old_error_handler = set_error_handler("userErrorHandler");

// membaca username dan password dari form login
$email      = mysql_real_escape_string($_POST['email']);
$password   = mysql_real_escape_string($_POST['password']);

if (!$email == '' && !$password == '') {
// mencari data user yang login tujuannya untuk mendapatkan password dari database
    $query = "SELECT * FROM user WHERE email = '$email'";
    $hasil = mysql_query($query);
    $data = mysql_fetch_array($hasil);


// mencocokkan password user dari database dengan password dari form
    if ($password == $data['password']) {
        // jika kedua password sama, maka login berhasil
        // simpan username ke dalam session

        session_start();
        $_SESSION['email'] = $email;
        header("Location: ../KotakWeb/GSONread.php");
    } else {
        // jika kedua password tidak cocok, maka login gagal
        echo "<link rel='stylesheet' type='text/css' href='style/style.css'/>";
        echo "<div id='main'>";
        echo "<h1>Login failed</h1>";
        echo "<p> Please either <a href='index.php'> login</a> , or <a href='registerform.php'>click here to register</a>";
        echo "</div>";
        header("Location: {$_SERVER['HTTP_REFERER']}");
    }
} else {
    header("Location: {$_SERVER['HTTP_REFERER']}");
}
?>