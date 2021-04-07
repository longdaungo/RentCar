<%-- 
    Document   : feedback
    Created on : Mar 14, 2021, 7:40:48 AM
    Author     : IT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Feed Back</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    </head>
    <body>
        <h1 class="mx-auto p-1 bg-success text-white text-center">Feed Back ${param.name}</h1>
        <form action="FeedBack" method="post">
            <input type="hidden" name="detailBillID" value="${param.detailBillID}" />
            Comment <textarea name="comment" rows="4" cols="20">
            </textarea>
            <br/>
            <br/>
            Rating<select name="rating">
                <option>1</option>
                <option>2</option>
                <option>3</option>
                <option>4</option>
                <option>5</option>
                <option>6</option>
                <option>7</option>
                <option>8</option>
                <option>9</option>
                <option>10</option>
            </select>
            <input type="submit" value="Feed Back" />
        </form>
            <a href="TakeOver?searchName=${param.searchName}&searchDate=${param.searchDate}">Back</a>
    </body>
</html>
