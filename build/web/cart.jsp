<%-- 
    Document   : cart
    Created on : Mar 6, 2021, 8:30:16 PM
    Author     : IT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Cart</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    </head>
    <body>
        <h1 class="mx-auto p-1 bg-success text-white text-center">Your Cart</h1>
        ${sessionScope.Ahihih}
        <c:if test="${not empty sessionScope.CART}">
            <table border="1" class="table table-striped">
                <thead>
                    <tr>
                        <th>Car Name</th>
                        <th>Car Type</th>
                        <th>Amount</th>
                        <th>Price</th>
                        <th>Time Start</th>
                        <th>Time End</th>
                        <th>Update Amount</th>
                        <th>Remove</th>
                    </tr>
                </thead>

                <c:set var="cart" value="${sessionScope.CART}"/>
                <tbody>
                    <c:set var="total" value="0"/>
                    <c:forEach var="dto" items="${cart.map}">
                    <form action="UpdateItem" method="post">
                        <tr>
                            <td>
                                ${dto.value.name}
                                <input type="hidden" name="id" value="${dto.key}" />
                            </td>
                            <td>
                                ${dto.value.type}
                            </td>
                            <td>
                                <input type="number" min="1" max="100" name="amount" value="${dto.value.amount}" />
                            </td>
                            <td>
                                ${dto.value.price}
                                <c:set var="total" value="${total + dto.value.price}"/>       
                            </td>
                            <td>
                                ${dto.value.datestart} 
                            </td>
                            <td>
                                ${dto.value.dateend} 
                            </td>
                            <td><input type="submit" value="Update" /></td>
                            <td><a href="RemoveItem?id=${dto.key}" onclick="return confirmRemoveItem()">Remove</a></td>
                        </tr>
                    </form>
                </c:forEach>
                <tr>
                    <td colspan="5">Total</td>
                    <c:if test="${requestScope.DISCOUNT == 0 or empty requestScope.DISCOUNT}">
                        <td colspan="3">${total}</td>
                    </c:if>
                    <c:if test="${requestScope.DISCOUNT > 0}" >
                        <c:set var="total" value="${total - (total*requestScope.DISCOUNT/100)}"/>
                        <td colspan="3">${total}</td>
                    </c:if>
                </tr>
            </tbody>
        </table>
        <form action="InputDiscount">
            Discount Code(If you have) <input type="text" name="discount" value="${param.discount}" />
            <font color="red">
            ${requestScope.NOTIFICATION}
            </font>
            <input type="submit" value="Apply" />
        </form>
        <form action="StoreData" method="post">
            <input type="hidden" name="total" value="${total}" />
            <input type="hidden" name="discoutID" value="${requestScope.DISCOUNTID}" />
            <input type="submit" value="Confirm" />
        </form>

    </c:if>
    <c:if test="${empty sessionScope.CART}">
        There is no product
    </c:if>
    <font color="red">
    ${requestScope.ERROR}
    </font>
    <a href="/J3.L.P0015/">Back</a>
    <script src="JavaScript" type="text/javascript"></script>
</body>
</html>
