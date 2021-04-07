/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function checkConfirmPassword(){
    var password = document.getElementById("password").value;
    var confirmpassword = document.getElementById("confirmpassword").value;
    if(password != confirmpassword){
        alert("Confirm and password is not match");
        return false;
    }
    return true;
}

function checkDateStartDateEnd(){
    var stringdateStart = document.getElementById("dateStart").value;
    var stringdateEnd = document.getElementById("dateEnd").value;
    var dateStart = Date.parse(stringdateStart);
    var dateEnd = Date.parse(stringdateEnd);
    if(dateStart >= dateEnd){
        alert("Date Start must more than date end");
        return false;
    }
    var today = new Date();
    var monthCurrent = today.getMonth() + 1; 
    var stringtoday = today.getFullYear()+"-"+monthCurrent+"-"+today.getDate();
    today = Date.parse(stringtoday);
    if(dateStart < today){
        alert("Date Start must more than current day");
        return false;
    }
    return true;
}

function confirmRemoveItem(){
    var result = confirm("Do you want to remove item");
    if(result == true){
        return true;
    }
    else{
        return false;
    }
}

function confirmToDeleteSession(){
    var notification = document.getElementById("wrongdate").value;
    if(notification != ""){
        var result = confirm("Date start rent and date end rent are not synchronized. Do you want to remove your cart before add new car");
        if(result == true){
            var xhttp = new XMLHttpRequest();
            xhttp.open("POST","RemoveCart", true);
            xhttp.send();
        }
    }
}

