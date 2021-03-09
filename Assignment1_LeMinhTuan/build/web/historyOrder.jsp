<%-- 
    Document   : historyOrder
    Created on : Jan 14, 2021, 6:16:54 PM
    Author     : MINH TUAN
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/historyOrder.css" />
    </head>
    <body>
        <c:import url="header.jsp"></c:import>
        <c:if test="${param.btnAction ne null && sessionScope.WELLCOMENAME ne null}">

            <div class="container" style="margin-bottom: 100px; border: hidden">
                <h1 style="text-align: center">Order History</h1>
                <div class="content" style="width: 100%; border: hidden">
                    <div class="search">
                        <form action="DispatchServlet">

                            <div class="searchComponent">
                                <label class="lable">Order ID:</lable>
                                    <input placeholder="Order name" 
                                           type="text" name="txtOrderID" value="${param.txtOrderID}" />

                            </div>

                            <div class="searchComponent">
                                <label class="lable">Checkout date:</lable>
                                    <input  type="date" value="${param.txtCheckoutDate}" 
                                            name="txtCheckoutDate">
                                    <button name="btnAction" value="Search Order" >
                                        <img src="icon/search.png">
                                    </button>
                            </div>
                        </form>
                    </div>

                    <form action="DispatchServlet">
                        <div style="width: 100%;  margin: auto; border: hidden">
                            <c:set var="histories" value="${requestScope.HISTORY}"/>
                            <c:if test="${null ne histories}">
                                <div style="border: hidden" class="historyOrderContainer">
                                    <c:forEach var="history" items="${histories}" varStatus="counter">
                                        <div class="orderHeader">
                                            <div class="leftHeader">
                                                Order ID: ${history.key.id}
                                            </div>
                                            <div class="rightHeader">
                                                Checkout date: ${history.key.dateString}
                                            </div>
                                            Total Price: ${history.key.total} 
                                        </div>
                                        <div class="orderDetail" style="width: 100%; margin-bottom: 100px;">
                                            <table border="1">
                                                <thead>
                                                <colgroup>
                                                    <col width="150" span="2">
                                                    <col width="250" span="1">
                                                    <col width=230 span="2">
                                                    <col width="30" span="1">
                                                </colgroup>
                                                <tr>
                                                    <th>No.</th>
                                                    <th>Product id</th>
                                                    <th>Product name</th>
                                                    <th>Price</th>
                                                    <th>Quantity</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="items" items="${history.value}" varStatus="counter">
                                                    <colgroup>
                                                        <col width="150" span="2">
                                                        <col width="250" span="1">
                                                        <col width=230 span="2">
                                                        <col width="30" span="1">
                                                    </colgroup>
                                                    <tr>
                                                        <td>${counter.count}</td>
                                                        <td>${items.productId}</td>
                                                        <td>${items.productName}</td>
                                                        <td>${items.price}</td>
                                                        <td>${items.quantity}</td>

                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>


                                        </div><br/>

                                    </c:forEach>
                                </div>
                            </c:if>
                            <c:if test="${requestScope.HISTORY eq null}">
                                <h1 style="font-size: 150%;">Can not found any history</h1>
                            </c:if>

                    </form>

                </div>
            </c:if>
            <c:if test="${param.btnAction eq null}">
                <div style="margin-top: 200px; margin-left: 33%; font-family: fantasy">
                    <h1>Sorry,You can not access resource directly</h1>
                </div>
            </c:if>
        </div>

    </body>
</html>
