<?php
include 'base.php';

// membaca email dan password dari form register
$password = mysql_real_escape_string($_POST['pass']);
$email = mysql_real_escape_string($_POST['email']);

// cek email
$query  = "SELECT * FROM user WHERE email = '$email'";
$result  = mysql_query($query);
$data   = mysql_num_rows($result);


// cek email di databse
if ($data > 0) {
    echo 'Email already register!';
} else {
    $query2 = "INSERT INTO user (email, password) VALUES ('$email', '$password')";
    mysql_query($query2);
    $lastid = mysql_insert_id();
    $revnum = 1;

    //Create structure file
    $today  = getdate();

    if($today[hours] >= 12) {
        if($today[hours]==12){
            $hour = $today[hours];
        }
        else {
            $hour   = $today[hours] - 12;
        }
        $ampm   = "PM";
    }
    else {
        $hour   = $today[hours];
        $ampm   = "AM";
    }

    $date   = "" . $today[month] . " " . $today[mday] . ", " . $today[year] . " " . $hour . ":" . $today[minutes] . ":" . $today[seconds]. " ". $ampm;
    $struct = '{"name":"'.$email.'","lastModified":"'.$date.'","isFile":false,"files":[]}';
    

    $query3 = "INSERT INTO revision_repo (user_id, rev_num, structure) VALUES ('$lastid', '$revnum', '$struct')";
    mysql_query($query3);

    //$createdir = "INSERT INTO folder (Name, ID_Owner) VALUES ('$username', '$userid')";
    //mysql_query($createdir
    mkdir("kotak/".$email);
    header("Location: ../KotakWeb/index.php");
}
?>