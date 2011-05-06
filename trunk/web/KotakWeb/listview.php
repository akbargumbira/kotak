<?php
include 'listing.php';
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <title><?php echo $_SESSION["email"] ?> files</title>
        <link rel="stylesheet" type="text/css" href="style/style.css"/>
    </head>
    <body>
        <div id="main">
            <div id="header">
                <div id="imageheader"><img title="Kotak" alt=""  /></div>
                <div id="link">[ <a href='logout.php'>Logout</a> ]</div>
            </div>
            <div id="container">
                <h1><?php echo $_SESSION["email"] ?> files</h1>
                <div id="breadcrumbs"> <a href="<?php echo strip_tags($_SERVER['PHP_SELF']); ?>">root</a>
                    <?php
                    $breadcrumbs = split('/', str_replace($startdir, '', $leadon));
                    if (($bsize = sizeof($breadcrumbs)) > 0) {
                        $sofar = '';
                        for ($bi = 0; $bi < ($bsize - 1); $bi++) {
                            $sofar = $sofar . $breadcrumbs[$bi] . '/';
                            echo ' &gt; <a href="' . strip_tags($_SERVER['PHP_SELF']) . '?dir=' . urlencode($sofar) . '">' . $breadcrumbs[$bi] . '</a>';
                        }
                    }
                    $baseurl = strip_tags($_SERVER['PHP_SELF']) . '?dir=' . strip_tags($_GET['dir']) . '&amp;';
                    $fileurl = 'sort=name&amp;order=asc';
                    $sizeurl = 'sort=size&amp;order=asc';
                    $dateurl = 'sort=date&amp;order=asc';

                    switch ($_GET['sort']) {
                        case 'name':
                            if ($_GET['order'] == 'asc')
                                $fileurl = 'sort=name&amp;order=desc';
                            break;
                        case 'size':
                            if ($_GET['order'] == 'asc')
                                $sizeurl = 'sort=size&amp;order=desc';
                            break;
                        case 'date':
                            if ($_GET['order'] == 'asc')
                                $dateurl = 'sort=date&amp;order=desc';
                            break;
                        default:
                            $fileurl = 'sort=name&amp;order=desc';
                            break;
                    }
                    ?>
                </div>
                <div id="listingcontainer">
                    <div id="listingheader">
                        <div id="headerfile"><a href="<?php echo $baseurl . $fileurl; ?>">File</a></div>
                        <div id="headersize"><a href="<?php echo $baseurl . $sizeurl; ?>">Size</a></div>
                        <div id="headermodified"><a href="<?php echo $baseurl . $dateurl; ?>">Last Modified</a></div>
                    </div>
                    <div id="listing">
                        <?php
                        //background color per line
                        $class = 'b';
                        if ($dirok) {
                        ?>  <!--directory up-->
                            <div><a href="<?php echo strip_tags($_SERVER['PHP_SELF']) . '?dir=' . urlencode($dotdotdir); ?>" class="<?php echo $class; ?>"><img src="image/dirup.png" alt="Folder" /><strong>..</strong> <em>&nbsp;</em>&nbsp;</a></div>
                        <?php
                            if ($class == 'b')
                                $class = 'w'; else
                                $class = 'b';
                        }
                        $arsize = sizeof($dirs);
                        for ($i = 0; $i < $arsize; $i++) {
                        ?>  <!--list folder-->
                            <div><a href="<?php echo strip_tags($_SERVER['PHP_SELF']) . '?dir=' . urlencode(str_replace($startdir, '', $leadon) . $dirs[$i]); ?>" class="<?php echo $class; ?>"><img src="image/folder.png" alt="<?php echo $dirs[$i]; ?>" /><strong><?php echo $dirs[$i]; ?></strong> <em>-</em> <?php echo date("M d Y h:i:s A", filemtime($includeurl . $leadon . $dirs[$i])); ?></a></div>
                        <?php
                            if ($class == 'b')
                                $class = 'w'; else
                                $class = 'b';
                        }
                        $arsize = sizeof($files);
                        for ($i = 0; $i < $arsize; $i++) {
                            $icon = 'dbicon.png';
                            $filename = $files[$i];
                            if (strlen($filename) > 43) {
                                $filename = substr($files[$i], 0, 40) . '...';
                            }
                            $fileurl = $leadon . $files[$i];
                            $fileurl = $_SESSION['PHP_SELF'] . '?dir=' . urlencode(str_replace($startdir, '', $leadon)) . '&download=' . urlencode($files[$i]);
                        ?>
                            <!--list file-->
                            <div><a href="<?php echo $fileurl; ?>" class="<?php echo $class; ?>">
                                    <img src="image/<?php echo $icon; ?>" alt="<?php echo $files[$i]; ?>" /><strong>
                                    <?php echo $filename; ?></strong> <em><?php echo round(filesize($leadon . $files[$i]) / 1024);
                                    ?>KB</em> <?php echo date("M d Y h:i:s A", filemtime($leadon . $files[$i])); ?></a></div>
                        <?php
                                    if ($class == 'b')
                                        $class = 'w'; else
                                        $class = 'b';
                                }
                        ?>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
