<%-- 
    Document   : verifyemail
    Created on : Mar 14, 2021, 4:27:53 PM
    Author     : IT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Verify Email</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    </head>
    <body>
        <h1 class="mx-auto p-1 bg-success text-white text-center">Verify Email</h1>
        <form action="CreateAccount" method="post">
            <input type="hidden" name="txtEmail" value="${requestScope.txtEmail}" />
            <input type="hidden" name="txtPassword" value="${requestScope.txtPassword}" />
            <input type="hidden" name="txtName" value="${requestScope.txtName}" />
            <input type="hidden" name="txtPhone" value="${requestScope.txtPhone}" />
            <input type="hidden" name="txtAddress" value="${requestScope.txtAddress}" />
            Code <input type="text" name="code" value="" required=""/>
            <input type="submit" value="Verify" />
        </form>
        <font color="red">
        ${requestScope.NOTIFICATION}
        </font>
    </body>
</html>
