/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var valid = new Array( false, false);
function isValid(isTrue) {
    if (isTrue) {
        return "#cccccc";
    }
    else {
        return "#ff0000";
    }

}
function cekEmail(y){
    var x = y.parentNode.id;

    var regex =  /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-z0-9]{2,3}$/;
    isTrue = regex.test(document.getElementById(y.id).value);
    document.getElementById("email").style.backgroundColor = isValid(isTrue);
    if(isTrue){
        var element = document.getElementById("valid1");
        while (element.firstChild) {
            element.removeChild(element.firstChild);
        }
        valid[1] = true;
    }
    else {
        document.getElementById("valid1").innerHTML = "not valid email";
        valid[1] = false;
    }
    validasiRegistrasi();
}
function cekPassword(y){
    var x = y.parentNode.id;

    var pass = document.getElementById("pass").value;
    if(pass.length < 6) {
        isTrue=false;
        document.getElementById("pass").style.backgroundColor = isValid(isTrue);
        document.getElementById("valid2").innerHTML = "char length must > 6";
        valid[0] = false;
    } else {
        isTrue=true;
        document.getElementById("pass").style.backgroundColor = isValid(isTrue);
        var element = document.getElementById("valid2");
        while (element.firstChild) {
            element.removeChild(element.firstChild);
        }
        valid[0] = true;
    }
    validasiRegistrasi();
}

function validasiRegistrasi(){
    kurang = false;
    for (i = 0; i < 1; ++i) {
        if (!valid[i]) {
            kurang = true;
            i = 2;
        }
    }
    if(!kurang){
        return true;
    }else{
        return false;
    }
}
window.onload=function(){
    document.getElementById('button_register').onclick=function(){
        if(!validasiRegistrasi()){
            alert("Please check again!");
        }else{
            document.getElementById("registerform").submit();
        }
    }
}
    