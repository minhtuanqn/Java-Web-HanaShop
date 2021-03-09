<%-- 
    Document   : header.jsp
    Created on : Jan 6, 2021, 12:38:53 AM
    Author     : MINH TUAN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/home.css" />
    </head>

    <body>
        <font color="red">Welcome, ${sessionScope.WELLCOMENAME}</font>
        <c:if test="${sessionScope.WELLCOMENAME ne null}">
            <c:url var="url" value="/DispatchServlet">
                <c:param name="btnAction" value="Logout"></c:param>
            </c:url>
            <a href="${url}">Logout</a>
        </c:if>
        <c:if test="${sessionScope.WELLCOMENAME eq null}">
            <c:url var="url" value="/DispatchServlet">
                <c:param name="btnAction" value="Login now"></c:param>
            </c:url>
            <a href="${url}">Login now</a>
        </c:if>

        <div class="menu">
            <div class="up">
                <ul>
                    <li>
                        <c:url value="/DispatchServlet" var="url">
                        </c:url>
                        <a href="${url}">
                            <img src="icon/icon1.PNG">
                            Products
                        </a>
                    </li>
                    <c:if test="${sessionScope.ROLE ne null && sessionScope.ROLE ne 'admin'}">
                        <li>
                            <c:url value="/DispatchServlet" var="url">
                                <c:param name="btnAction" value="Cart"></c:param>
                            </c:url>
                            <a href="${url}">
                                <img src="icon/icon2.PNG">
                                Your cart
                            </a>
                        </li>
                        <li>
                            <c:url value="/DispatchServlet" var="url">
                                <c:param name="btnAction" value="Search Order"></c:param>
                            </c:url>
                            <a href="${url}">
                                <img src="icon/icon5.PNG">
                                Order history
                            </a>
                        </li>
                    </c:if>

                </ul>
            </div>

        </div>
    </body>
</html>
