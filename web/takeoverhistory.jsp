<%-- 
    Document   : takeoverhistory
    Created on : Mar 12, 2021, 9:07:19 AM
    Author     : IT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Take Over History</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    </head>
    <body>
        <h1 class="mx-auto p-1 bg-success text-white text-center">Take Over History</h1>
        <c:set var="listBills" value="${requestScope.LISTBILLS}"/>
        <a href="TakeOver">Refresh</a><br/>
        <c:if test="${not empty listBills}">
            <form action="TakeOver">
                Search Name <input type="text" name="searchName" value="${param.searchName}" />
                Date <input type="date" name="searchDate" value="${param.searchDate}" />
                <input type="submit" value="Search" /><br/>
                
            </form>
            <c:set var="mapDetailBill" value="${requestScope.MAPDETAILBILL}"/>
            <c:set var="mapDiscount" value="${requestScope.MAPDISCOUNT}"/>
            <c:forEach var="bill" items="${listBills}">
                <b>Bill ID: </b>${bill.idBill} &ensp; <b>Date Booking: </b>${bill.date} &ensp; <a href="DeleteOrder?idBill=${bill.idBill}">Delete</a>
                <table border="1" class="table table-striped">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Rental Start Date</th>
                            <th>Rental End Date</th>
                            <th>Amount</th>
                            <th>Price</th>
                            <th>Feed Back</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${mapDetailBill[bill.idBill]}">
                            <tr>
                                <td>${dto.carID}</td>
                                <td>${dto.startDate}</td>
                                <td>${dto.endDate}</td>
                                <td>${dto.amount}</td>
                                <td>${dto.total}</td>
                                <td><a href="ViewFeedBack?detailBillID=${dto.detailID}&name=${dto.carID}&searchName=${param.searchName}&searchDate=${param.searchDate}">Feed back</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <b>Total:</b>${bill.total} &ensp; <b>Discount Code:</b> 
                <c:if test="${not empty bill.discountID}">
                    ${mapDiscount[bill.discountID].discountCode}
                    &ensp; <b>Percent:</b>
                    ${mapDiscount[bill.discountID].percent_Discount}%
                </c:if>
                <c:if test="${empty bill.discountID}">
                    None
                </c:if>
                <br/>
                <br/>
            </c:forEach>
            <font color="red">
            ${requestScope.NOTIFICATION}
            </font>
        </c:if>
        <c:if test="${empty listBills}">
            There is no history
        </c:if>
        <a href="/J3.L.P0015/">Back</a>
    </body>
</html>
