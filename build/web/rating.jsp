<%-- 
    Document   : rating
    Created on : Mar 19, 2021, 9:42:38 PM
    Author     : IT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Rating</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    </head>
    <body>
        <c:if test="${not empty requestScope.LISTFEEDBACK}">

            <h1 class="mx-auto p-1 bg-success text-white text-center">Rating ${param.name}</h1>
            <h2>Average Rating: ${requestScope.AVARAGE}/10</h2>
            <c:forEach var="dto" items="${requestScope.LISTFEEDBACK}">
                -------------------------------------------<br/>
                <strong>Name: ${dto.name}</strong><br/>
                Comment: ${dto.comment}<br/>
                Rating: ${dto.rating}/10
                <br/>
            </c:forEach>
        </c:if>
        <c:if test="${empty requestScope.LISTFEEDBACK}">
            <h1>There is no feedback</h1>
        </c:if>
        <a href="/J3.L.P0015/">Back</a>
    </body>
</html>
