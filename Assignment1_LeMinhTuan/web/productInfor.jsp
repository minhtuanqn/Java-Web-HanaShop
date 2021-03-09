<%-- 
    Document   : productInfor
    Created on : Jan 11, 2021, 7:40:20 AM
    Author     : MINH TUAN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/home.css" />
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <c:if test="${param.btnAction ne null && sessionScope.WELLCOMENAME ne null}">
            <div class="container">
                <c:if test="${sessionScope.ROLE ne null && sessionScope.ROLE eq 'admin'}">
                    <div class="productCreation">
                        <c:set var="error" value="${requestScope.ERROR}"/>
                        <form action="DispatchServlet" name="txtImage" enctype="multipart/form-data" method="POST">
                            <h1>Product Details</h1>
                            <div class="warningAndComponent" >
                                <div class="createComponent">
                                    <label class="lableProduct">Category: </lable>
                                        <select name="cbbCategory">
                                            <c:forEach var="category" items="${requestScope.CATEGORIES}">
                                                <c:if test="${category.id eq param.cbbCategory}">
                                                    <option label="${category.name}" selected="selected" value="${category.id}"></option>
                                                </c:if>
                                                <c:if test="${category.id ne param.cbbCategory}">
                                                    <option label="${category.name}" value="${category.id}"></option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                </div>
                            </div>


                            <div class="warningAndComponent" >
                                <div class="createComponent">
                                    <label class="inputAndLable lableProduct">Product id: </label>
                                    <input class="inputAndLable" type="text" name="txtProductId" value="${param.txtProductId}" />
                                </div>
                                <c:if test="${error.blankProductId ne null}">
                                    <label class="warning">${error.blankProductId}</label>
                                </c:if>
                                <c:if test="${error.duplicateProductId ne null}">
                                    <label class="warning">${error.duplicateProductId}</label>
                                </c:if>
                            </div>

                            <div class="warningAndComponent" >
                                <div class="createComponent">
                                    <label class="inputAndLable lableProduct">Product name: </label>
                                    <input type="text" name="txtProductName" value="${param.txtProductName}" />
                                </div>
                                <c:if test="${error.blankName ne null}">
                                    <label class="warning">${error.blankName}</label>
                                </c:if>
                            </div>

                            <div class="warningAndComponent" >
                                <div class="createComponent">
                                    <label class="inputAndLable lableProduct">Remain quantity: </label>
                                    <input type="text" name="txtQuantity" value="${param.txtQuantity}" />
                                </div>
                                <c:if test="${error.blankQuantity ne null}">
                                    <label class="warning">${error.blankQuantity}</label>
                                </c:if>
                                <c:if test="${error.errorFormatQuantity ne null}">
                                    <label  class="warning">${error.errorFormatQuantity}</label>
                                </c:if>
                            </div>

                            <div class="warningAndComponent" >
                                <div class="createComponent">
                                    <label class="inputAndLable lableProduct">Description: </label>
                                    <input type="text" name="txtDesciption" value="${param.txtDesciption}" />
                                </div>
                                <c:if test="${error.blankDescription ne null}">
                                    <label class="warning">${error.blankDescription}</label>
                                </c:if>
                            </div>

                            <div class="warningAndComponent" >
                                <div class="createComponent">
                                    <label class="lableProduct" >Product price: </label>
                                    <input type="text" name="txtPrice" value="${param.txtPrice}" />
                                </div>
                                <c:if test="${error.blankPrice ne null}">
                                    <label class="warning">${error.blankPrice}</label>
                                </c:if>
                                <c:if test="${error.errorFormatPrice ne null}">
                                    <label class="warning">${error.errorFormatPrice}</label>
                                </c:if>
                            </div>

                            <div class="warningAndComponent" >
                                <div class="createComponent">
                                    <label class="lableProduct">Image: </label>
                                    <input type="file" required name = "txtImage" accept="image/*"/>
                                </div>
                                <c:if test="${error.emptyResourceImage ne null}">
                                    <label class="warning">${error.emptyResourceImage}</label>
                                </c:if>
                            </div>
                            <div class="warningAndComponent" >
                                <c:if test="${param.status eq 'Create'}">
                                    <div class="createComponent">
                                        <input class="buttonCotroller" type="submit" value="Create" name="btnAction" />
                                    </div>
                                </c:if>
                                <c:if test="${param.status eq 'Update'}">
                                    <div class="createComponent">
                                        <input class="buttonCotroller" name="btnAction" type="submit" value="Update Product" />
                                    </div>
                                </c:if>
                                <label class="warning">${requestScope.STATUS}</label>
                            </div>
                        </form>
                    </div>
                </c:if>
            </div>
        </c:if>
        <c:if test="${param.btnAction eq null}">
            <div style="margin-top: 200px; margin-left: 33%; font-family: fantasy">
                <h1>Sorry,You can not access resource directly</h1>
            </div>
        </c:if>

    </body>
</html>
