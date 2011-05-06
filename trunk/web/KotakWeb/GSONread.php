<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

include("dhtmlgoodies_tree.class.php");
include 'base.php';
include 'authority.php';
include 'errorhandler.php';


$old_error_handler = set_error_handler("userErrorHandler");

$email = $_SESSION['email'];
//get the structure

$data   = "SELECT id FROM user WHERE email = '$email'";
$id     = mysql_fetch_array(mysql_query($data));

$data   = "SELECT * FROM revision_repo WHERE user_id = '$id[id]' order by rev_num desc";
$rev    = mysql_fetch_array(mysql_query($data));

$json  = $rev[structure];
$obj    = json_decode($json, true);


echo '<link rel="stylesheet" type="text/css" href="style/style.css"/>';
echo "<body>";
echo "<div id='main'>";

echo "<div id='logout'> <a href='logout.php'>&nbsp;Logout&nbsp; </a> </div> ";

echo "<h1><span id='kotaks'>Kotak</span><div id='piclog'><CENTER><img src= 'image/kotakpic.png' alt='Folder' /></CENTER></div><span id='judul'>List Files </span></h1>";


//create tree object
$tree   = new dhtmlgoodies_tree();

//lets try make a tree
$parent = "0";

$email = $obj["name"];
$max_rev_num = $rev[rev_num];
$tempPath = "";
printObj($obj, $tempPath, $tree, $parent);


function printObj($_obj, $tempPath, $_tree, $_parent) {
    
    $names  = $_obj["name"] . " -- <span style='font-size:8pt;color:brown;'><I>" . $_obj["lastModified"] . "</I></span>";
    $name   = $_obj["name"];

    if($_obj["isFile"]) {
       $_tree->addToArray($name, $names , $_parent, getLink($email, $max_rev_num, $tempPath)  ,"", "image/dhtmlgoodies_sheet.gif");
    }
    else {
       $_tree->addToArray($name, $names , $_parent, "");
    }
    
    $files  = $_obj["files"];
    $_parent = $_obj["name"];

    foreach($files as $f) {
        printObj($f, $tempPath . "/" . $f["name"], $_tree, $_parent);
    }
}

function getLink($email, $max_rev_num, $path) {

    $filePath = "kotak/" . $GLOBALS['email'];
        for ($i = $GLOBALS['max_rev_num']; $i > 0; --$i) {
            $tempPaths = $filePath . "/r" . $i  . $path;
            if (file_exists($tempPaths)) {
                return ($tempPaths);
            }
        }
        return null;

}

echo "<div class='line'><h1> _________________________________________ </h1></div>";
echo "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

echo "<span class='optionnode' ><a href='#' onclick='expandAll();return false'>Expand all nodes</a></span>&nbsp;&nbsp;";
echo "<span class='optionnode' ><a href='#' onclick='collapseAll();return false'>Collapse all nodes</a></span><br>";

echo "<div id='maincon'>";
$tree->writeCSS();
$tree->writeJavascript();
$tree->drawTree();


echo "</div>";
echo "<div class='line'><h2> ________________________________________________________ </h2></div>";
echo "<div id='footer'> created by Akbar Belagu, Rezan, Dimas </div>";
echo "</div>";
echo "</body>"
?>
