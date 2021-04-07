<%-- 
    Document   : registeraccount
    Created on : Mar 1, 2021, 11:08:22 AM
    Author     : IT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register account</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    </head>
    <body>
        <h1 class="mx-auto p-1 bg-success text-white text-center">Register account</h1>
        <form action="VerifyEmail" onsubmit="return checkConfirmPassword()" method="post">
            Email <input type="email" name="txtEmail" value="${param.txtEmail}" required/><br/>
            Password <input type="password" name="txtPassword" value="${param.txtPassword}" id="password" required/><br/>
            Confirm Password <input type="password" name="txtConfirmPassword" value="${param.txtConfirmPassword}" id="confirmpassword" required/> <br/>
            Name <input type="text" name="txtName" value="${param.txtName}" required/>
            Phone <input type="text" name="txtPhone" value="${param.txtPhone}" required/> <br/>
            Address <input type="text" name="txtAddress" value="${param.txtAddress}" required/> <br/>
            <input type="submit" value="Register" />         
        </form>
        <font color="red">
        ${requestScope.NOTIFICATION}
        </font>
        <script src="JavaScript" type="text/javascript"></script>
        <a href="/J3.L.P0015/">Back</a>
    </body>
</html>
