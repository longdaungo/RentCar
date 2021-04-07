<%-- 
    Document   : searchcarbeforelogin
    Created on : Mar 1, 2021, 9:34:04 PM
    Author     : IT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Car</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    </head>
    <body onload="confirmToDeleteSession()">
        <h1 class="mx-auto p-1 bg-success text-white text-center" >Search Car</h1>
        <c:if test="${not empty sessionScope.NAME}">
            <font color="red">
            ${sessionScope.NAME}
            </font>
            <a href="Logout">Log out</a><br/>
            <a href="ViewCart">View your cart</a><br/>
            <a href="TakeOver">Take over history</a>
        </c:if>
        <c:if test="${empty sessionScope.NAME}">
            <a href="ViewLogin">Log in</a>
        </c:if>
        <form action="/J3.L.P0015/" onsubmit="return checkDateStartDateEnd()">
            Name <input type="text" name="txtSearchName" value="${param.txtSearchName}" />
            Category <select name="txtCategory">
                <option></option>
                <c:forEach var="dto" items="${requestScope.LISTCATEGORY}">
                    <c:if test="${dto == param.txtCategory}">
                        <option selected>${dto}</option>
                    </c:if>
                    <c:if test="${dto != param.txtCategory}">
                        <option>${dto}</option>
                    </c:if>
                </c:forEach>        
            </select>
            Rental Start Date <input type="date" name="dateStart" id="dateStart" value="${requestScope.DATESTART}" />
            Rental Start Date <input type="date" name="dateEnd" id="dateEnd" value="${requestScope.DATEEND}" />
            <input type="submit" value="Search"/>
        </form>
        <c:if test="${not empty requestScope.LISTREQUEST}">
            <table border="1" class="table table-striped">
                <thead>
                    <tr>
                        <th>NO.</th>
                        <th>Car Name</th>
                        <th>Color</th>
                        <th>Year</th>
                        <th>Category</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Add to Cart</th>
                        <th>View Feed Back</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dto" items="${requestScope.LISTREQUEST}" varStatus="counter">
                        <tr>
                            <td>${requestScope.pageIndex* 3 - 3 +counter.count}</td>
                            <td>${dto.carName}</td>
                            <td>${dto.color}</td>
                            <td>${dto.year}</td>
                            <td>${dto.category}</td>
                            <td>${dto.price}</td>
                            <td><c:if test="${dto.quantity == 0}">
                                    car is out of stock
                                </c:if>
                                <c:if test="${dto.quantity > 0}">
                                    ${dto.quantity}
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${dto.quantity == 0}">

                                </c:if>
                                <c:if test="${dto.quantity > 0}">
                                    <a href="AddCart?idCar=${dto.carID}&name=${dto.carName}&type=${dto.category}&price=${dto.price}&dateStart=${requestScope.DATESTART}&dateEnd=${requestScope.DATEEND}">Add</a>
                                </c:if>
                            </td>
                            <td>
                                <a href="GetFeedBack?carID=${dto.carID}&name=${dto.carName}">View Feed Back</a>
                            </td>
                        </tr>
                    </c:forEach>
                <br/>
            </tbody>
        </table>
        <c:forEach var="dto" step="1" begin="1" end="${requestScope.PAGENUMBER}">
            <a href="/J3.L.P0015/?pageIndex=${dto}&txtSearchName=${param.txtSearchName}&txtCategory=${param.txtCategory}&dateStart=${requestScope.DATESTART}&dateEnd=${requestScope.DATEEND}">${dto}</a>
        </c:forEach>
    </c:if>
        <c:if test="${empty requestScope.LISTREQUEST}">
            There is no car
        </c:if>
            <font color="red">
            ${requestScope.NOTIFICATION}
            </font>
            <input type="hidden" name="WrongDate" id="wrongdate" value="${requestScope.WRONGDATE}" />
    <script src="JavaScript" type="text/javascript"></script>
</body>
</html>
