/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function isnotempty(){
    if(document.getElementById('user').value==''||document.getElementById('pass').value==''){
        return false;
    }else{
        return true;
    }
}

window.onload=function(){
    document.getElementById('loginbutton').onclick=function(){
        if(!isnotempty()){
            alert("Please check again!");
        }else{
            document.getElementById("loginform").submit();
        }
    }
}

