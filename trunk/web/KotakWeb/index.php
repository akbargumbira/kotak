<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Login</title>
        <script type="text/javascript" src="script/login.js"></script>
        <link rel="stylesheet" type="text/css" href="style/style.css"/>
    </head>
    <body>
        <div id="main">
            <h1><span id='kotaks'>Kotak</span><div id='piclog'><CENTER><img src= 'image/kotakpic.png' alt='Folder' /></CENTER></div><span id='judul'>Login&nbsp;&nbsp;&nbsp;&nbsp;</span></h1>

            <div class='line'><h1> _________________________________________ </h1></div>
            <p>Thanks for visiting! Please either login below, or <a href="registerform.php">click here to register</a>.</p>
            <br/>
            <form method="post" action="login.php" name="loginform" id="loginform">
                <fieldset>
                    <label for="email">Email:</label><input type="text" name="email" id="email" /><br />
                    <label for="password">Password:</label><input type="password" name="password" id="password" /><br />
                    <input type="submit" name="login" id="login" value="Login" />
                </fieldset>
            </form>

            <div class='line'><h2> ________________________________________________________ </h2></div>
            <div id='footer'> created by Akbar Belagu, Rezan, Dimas </div>
        </div>
    </body>
</html>