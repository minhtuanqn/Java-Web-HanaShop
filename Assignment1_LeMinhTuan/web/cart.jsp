<%-- 
    Document   : cart.jsp
    Created on : Jan 6, 2021, 2:08:15 AM
    Author     : MINH TUAN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/cart.css" />
    </head>
    <body>
        <c:if test="${sessionScope.ROLE ne null && sessionScope.ROLE ne 'admin'}">
            <h1>Your cart</h1>
            <div style="text-align: center">
                <font color='red'>${requestScope.STATUS}</font>
            </div>
            <c:set var="cart" value="${sessionScope.CUSTCART}"/>
            <c:if test="${cart ne null}">
                <c:set var="items" value="${cart.items}"/>
                <c:if test="${items ne null && items.size() > 0}">
                    <div>
                        <h3>Total Price: ${cart.totalPrice}</h3>
                        <form class="btnCheckout" action="DispatchServlet">
                            <input type="submit" name="btnAction" value="Check out by Cash" />
                        </form>

                        <div style="text-align: center">
                            <font color='red'>${param.remainStatus}</font>
                        </div>
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>No.</th>
                                    <th>Product ID</th>
                                    <th>Product name</th>
                                    <th>Quantity</th>
                                    <th>Price</th>
                                    <th>Action</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="itemKey" items="${items}" varStatus="counter">
                                <form action="DispatchServlet">
                                    <tr>
                                        <td>${counter.count}</td>
                                        <td>${itemKey.key}</td>

                                        <c:set var="products" value="${sessionScope.PRODUCTINFOR}"/>
                                        <c:forEach var="product" items="${products}">
                                            <c:if test="${product.id eq itemKey.key}">
                                                <td>${product.name}</td>
                                                <td>${itemKey.value}</td>
                                                <td>${product.price}</td>
                                            </c:if>
                                        </c:forEach>
                                        <td>
                                            <input type="hidden" name="txtProductId" value="${itemKey.key}" />
                                            <input type="text" name="txtQuantity" value="${itemKey.value}" />
                                            <input type="submit" value="Update Quantity" name="btnAction" />
                                        </td>
                                        <td>
                                            <c:url var="url" value="/DispatchServlet">
                                                <c:param name="btnAction" value="Delete Item From Cart"></c:param>
                                                <c:param name="productId" value="${itemKey.key}"></c:param>
                                                <c:param name="txtQuantity" value="${itemKey.value}"></c:param>
                                            </c:url>
                                            <a href="${url}">Delete</a>
                                        </td>
                                    </tr>
                                </form>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>
                <c:if test="${items eq null || items.size() == 0}">
                    <div style="margin-top: 200px; text-align: center; font-size: 200%; font-family: fantasy">
                        <label>Your cart is empty, let's go shopping</label>
                    </div>
                </c:if>
            </c:if>
            <c:if test="${cart eq null}">
                <div style="margin-top: 200px; text-align: center; font-size: 200%; font-family: fantasy">
                    <label>Your cart is empty, let's go shopping</label>
                </div>
            </c:if>
        </c:if>
        <c:if test="${sessionScope.WELLCOMENAME eq null}">
            <div style="margin-top: 200px; font-family: fantasy">
                <h1>Sorry,You can not access resource directly without login</h1>
            </div>
        </c:if>
        <c:if test="${sessionScope.ROLE ne null && sessionScope.ROLE eq 'admin'}">
            <div style="margin-top: 200px; font-family: fantasy">
                <h1>Sorry, Admin does not have cart to access this page</h1>
            </div>
        </c:if>

        <script>
            function confirmDelete() {
                var checkConfirm = confirm("Do you sure for deleting these product");
                if (checkConfirm == true) {
                    return (true);
                } else {
                    return (false);
                }
            }
        </script>


    </body>
</html>
