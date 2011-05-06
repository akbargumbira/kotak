<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Register</title>
        <script type="text/javascript" src="script/register.js"></script>
        <link rel="stylesheet" type="text/css" href="style/style.css"/>
    </head>
    <body>
        <div id="main">
            <h1><span id='kotaks'>Kotak</span><div id='piclog'><CENTER><img src= 'image/kotakpic.png' alt='Folder' /></CENTER></div><span id='judul'>Register</span></h1>

         <div class='line'><h1> _________________________________________ </h1></div>
         
            <p>Please enter your details below to register.</p>
            <br/>
            <form method="post" action="register.php" name="registerform" id="registerform">
                    <label for="email">Email:</label><input type="text" name="email" id="email"  onblur="cekEmail(this)" />
                <span id="valid1"></span><br />
                <label for="password">Password:</label><input type="password" name="pass" id="pass"  onblur="cekPassword(this)" />
                <span id="valid2"></span><br />
                <div><input class="button" id="button_register" name="button_register" type="button" value="Register" /></div>
            </form>
            <br/>
            <p> If you already have an account, please <a href="index.php"> click here to login </a> </p>

            <div class='line'><h2> ________________________________________________________ </h2></div>
            <div id='footer'> created by Akbar Belagu, Rezan, Dimas </div>
        </div>
    </body>
</html>

