<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
include 'base.php';
include 'authority.php';
include 'errorhandler.php';

$old_error_handler = set_error_handler("userErrorHandler");

$email = $_SESSION['email'];

$startdir = 'repository/' . $email;
$showdirs = true;

error_reporting(0);
if ($startdir)
    $startdir = preg_replace("/^\//", "${1}", $startdir);
$leadon = $startdir;
if ($leadon == '.')
    $leadon = '';
if ((substr($leadon, -1, 1) != '/') && $leadon != '')
    $leadon = $leadon . '/';
$startdir = $leadon;

if ($_GET['dir']) {
    if (substr($_GET['dir'], -1, 1) != '/') {
        $_GET['dir'] = strip_tags($_GET['dir']) . '/';
    }
    $dirok = true;
    $dirnames = split('/', strip_tags($_GET['dir']));
    for ($di = 0; $di < sizeof($dirnames); $di++) {
        if ($di < (sizeof($dirnames) - 2)) {
            $dotdotdir = $dotdotdir . $dirnames[$di] . '/';
        }
        if ($dirnames[$di] == '..') {
            $dirok = false;
        }
    }
    if (substr($_GET['dir'], 0, 1) == '/') {
        $dirok = false;
    }
    if ($dirok) {
        $leadon = $leadon . strip_tags($_GET['dir']);
    }
}

if ($_GET['download']) {
    $file = str_replace('/', '', $_GET['download']);
    $file = str_replace('..', '', $file);

    if (file_exists($leadon . $file)) {
        header('Content-Description: File Transfer');
        header('Content-Type: application/force-download');
        header('Content-Disposition: attachment; filename="' . $file . '"');
        header('Content-Transfer-Encoding: binary');
        header('Expires: 0');
        header('Cache-Control: must-revalidate, post-check=0, pre-check=0');
        header('Pragma: public');
        header('Content-Length: ' . filesize($leadon . $file));
        ob_clean();
        flush();
        readfile($leadon . $file);
        exit;
    }
    die("File not found!");
}

$opendir = $leadon;
if (!$leadon)
    $opendir = '.';

if (!file_exists($opendir)) {
    $opendir = '.';
    $leadon = $startdir;
    die("Your repository doesn't exists");
}

clearstatcache();
$handle = opendir($opendir);
if ($handle) {
    while (false !== ($file = readdir($handle))) {
        if ($file == "." || $file == "..")
            continue;
        if (@filetype($leadon . $file) == "dir") {
            if (!$showdirs)
                continue;

            $n++;
            if ($_GET['sort'] == "date") {
                $key = @filemtime($leadon . $file) . ".$n";
            } else {
                $key = $n;
            }
            $dirs[$key] = $file . "/";
        } else {
            $n++;
            if ($_GET['sort'] == "date") {
                $key = @filemtime($leadon . $file) . ".$n";
            } elseif ($_GET['sort'] == "size") {
                $key = @filesize($leadon . $file) . ".$n";
            } else {
                $key = $n;
            }
            if ($showtypes && !in_array(substr($file, strpos($file, '.') + 1, strlen($file)), $showtypes))
                unset($file);
            if ($file)
                $files[$key] = $file;
        }
    }
    closedir($handle);
}
//sort our files
if ($_GET['sort'] == "date") {
    @ksort($dirs, SORT_NUMERIC);
    @ksort($files, SORT_NUMERIC);
} elseif ($_GET['sort'] == "size") {
    @natcasesort($dirs);
    @ksort($files, SORT_NUMERIC);
} else {
    @natcasesort($dirs);
    @natcasesort($files);
}
//order correctly
if ($_GET['order'] == "desc" && $_GET['sort'] != "size") {
    $dirs = @array_reverse($dirs);
}
if ($_GET['order'] == "desc") {
    $files = @array_reverse($files);
}
$dirs = @array_values($dirs);
$files = @array_values($files);
?>

