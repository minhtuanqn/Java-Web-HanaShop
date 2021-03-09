<%-- 
    Document   : home.jsp
    Created on : Jan 6, 2021, 12:36:31 AM
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
        <c:import url="header.jsp"></c:import>
            <div class="container">
            <c:set var="curPage" value="${param.curPage}"/>
            <div class="search">
                <form action="DispatchServlet">
                    <div class="searchComponent">
                        <label class="lable">Categories:</label>
                        <select name="cbbCategorySearch" value="${param.cbbCategorySearch}">
                            <option label="Choose category" value=""></option>
                            <c:forEach var="category" items="${requestScope.CATEGORIES}">
                                <c:if test="${category.id eq param.cbbCategorySearch}">
                                    <option label="${category.name}" selected="selected" value="${category.id}"></option>
                                </c:if>
                                <c:if  test="${category.id ne param.cbbCategorySearch}">
                                    <option label="${category.name}" value="${category.id}"></option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="searchComponent">
                        <label class="lable">Price:</lable>
                            <c:if test="${param.txtMinPriceSearch ne null}">
                                <input placeholder="Min price" 
                                       type="text" name="txtMinPriceSearch" value="${param.txtMinPriceSearch}" /> - <input 
                                       type="text" name="txtMaxPriceSearch" placeholder="Max price" value="${param.txtMaxPriceSearch}" />
                            </c:if>
                            <c:if test="${param.txtMinPriceSearch eq null}">
                                <input placeholder="Min price" 
                                       type="text" name="txtMinPriceSearch" value="" /> - <input 
                                       type="text" name="txtMaxPriceSearch" placeholder="Max price" value="" />
                            </c:if>

                    </div>

                    <div class="searchComponent">
                        <label class="lable">Product:</lable>
                            <input  type="text" value="${param.txtProductNameSearch}" 
                                    name="txtProductNameSearch" placeholder="Product name">
                            <button name="btnAction" value="Search" >
                                <img src="icon/search.png">
                            </button>
                    </div>
                </form>
            </div>

            <form onsubmit="return confirmDelete()" action="DispatchServlet">
                <div style="width: 100%; height: 45px">
                    <c:if test="${requestScope.PRODUCTS ne null && requestScope.PRODUCTS.size() > 0
                                  && sessionScope.ROLE ne null && sessionScope.ROLE eq 'admin'}">
                          <div class="controllPanel" style="margin-right: 12.5%;">
                              <input type="hidden" name="curPage" value="${curPage}" />
                              <input class="buttonCotroller" name="btnAction" type="submit" value="Delete Product" />
                          </div> 
                    </c:if>
                    <c:if test="${sessionScope.ROLE ne null && sessionScope.ROLE eq 'admin'}">
                        <div class="controllPanel" >
                            <input class="buttonCotroller" type="button" value="Create New Product" onclick="window.location.replace('DispatchServlet?btnAction=Create_New_Product')"/>
                        </div>   
                    </c:if>
                </div>

                <div class="content" style="width: 11% ; background:  #cecece; " >
                    <c:set var="adsLeft" value="${requestScope.ADSLEFT}"/>
                    <c:if test="${adsLeft ne null}">
                        <div class="block-ads">
                            <c:forEach items="${adsLeft}" var="item">
                                <img class="adsImage" src="${item.imgSource}">
                                <label>${item.productName} - ${item.price}</label><br/><br/><br/><br/>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>

                <div class="content" style="margin-left: 0.5%; width: 75%; margin-right: 0.5%; ">
                    <c:set var="products" value="${requestScope.PRODUCTS}"/>
                    <c:if test="${null ne products && products.size() > 0}">
                        <c:forEach var="product" items="${products}" varStatus="counter">
                            <div class="item" style="border-radius: 3px; border: groove; margin-top: 5px; padding-left: 15px; padding-right: 15px; margin-left: 2.7%;">
                                <c:if  test="${sessionScope.ROLE ne null && sessionScope.ROLE eq 'admin'}">
                                    Selected: <input class="checkSelect" type="checkbox" name="checkSelected" value="${product.id}" />
                                </c:if>
                                <c:if test="${sessionScope.ROLE eq null || sessionScope.ROLE ne 'admin'}">
                                    <c:url var="urlAdd" value="/DispatchServlet">
                                        <c:param name="btnAction" value="Add to cart"></c:param>
                                        <c:param name="txtProductId" value="${product.id}"></c:param>
                                        <c:param name="txtMinPriceSearch" value="${param.txtMinPriceSearch}"></c:param>
                                        <c:param name="txtMaxPriceSearch" value="${param.txtMaxPriceSearch}"></c:param>
                                        <c:param name="cbbCategorySearch" value="${param.cbbCategorySearch}"></c:param>
                                        <c:param name="remainQuantityRemain" value="${product.quantityRemain}"></c:param>
                                        <c:param name="curPage" value="${curPage}"></c:param>
                                    </c:url>
                                    <div style="text-align: center">
                                        <c:if test="${product.quantitySatus eq null || product.quantitySatus eq ''}">
                                            <a href="${urlAdd}">Add to cart</a>
                                        </c:if>
                                        <c:if test="${product.quantitySatus ne null && product.quantitySatus ne ''}">
                                            <font color="red">
                                                ${product.quantitySatus}
                                            </font>
                                        </c:if>
                                    </div>

                                </c:if><br/>
                                <input type="hidden" name="txtMinPriceSearch" value="${param.txtMinPriceSearch}"/>
                                <input type="hidden" name="txtMaxPriceSearch" value="${param.txtMaxPriceSearch}"/>
                                <input type="hidden" name="txtProductNameSearch" value="${param.txtProductNameSearch}"/>
                                <input type="hidden" name="cbbCategorySearch" value="${param.cbbCategorySearch}"/>
                                <img src="${product.imageSource}"><br/>
                                <div class="underProduct" style="text-align:  left; color: black;">
                                    <label>Name: ${product.name} - Price : ${product.price}  </label><br/>
                                    <p>Category: ${product.categoryName} - Create date:  ${product.createDate}</p>
                                    <p>Description: ${product.description}</p>
                                    <p>Remain quantity: ${product.quantityRemain}</p>




                                    <c:if test="${sessionScope.ROLE ne null && sessionScope.ROLE eq 'admin'}">
                                        <c:url value="/DispatchServlet" var="url">
                                            <c:param name="btnAction" value="Update details"></c:param>
                                            <c:param name="txtProductId" value="${product.id}"></c:param>
                                        </c:url>
                                        <a href="${url}">Update details</a>
                                    </c:if>
                                </div>
                            </div>  
                        </c:forEach>
                    </c:if>
                    <c:if test="${null eq products || products.size() == 0}">
                        <h1 style="font-size: 150%;">Can not found any result</h1>
                    </c:if>
                </div>

                <div class="content" style="width: 11%; background:  #cecece">
                    <c:if test="${sessionScope.USERNAME ne null}">
                        <c:set var="adsDetails" value="${requestScope.ADSRIGHT}"/>
                    </c:if>
                    <c:if test="${sessionScope.USERNAME eq null}">
                        <c:set var="adsDetails" value="${requestScope.ADSLEFT}"/>
                    </c:if>
                    <c:if test="${adsLeft ne null}">
                        <div class="block-ads">
                            <c:forEach items="${adsDetails}" var="item">
                                <img class="adsImage" src="${item.imgSource}">
                                <label>${item.productName} - ${item.price}</label><br/><br/><br/><br/>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>


            </form>

            <div class="paging" >   
                <c:set var="totalPage" value="${requestScope.TOTALPAGE}"/>
                <c:if test="${totalPage gt 1}">
                    <c:forEach begin="${1}" end="${totalPage}" var="page" step="${1}">
                        <c:url value="/DispatchServlet" var="url">
                            <c:param name="btnAction" value="Search"></c:param>
                            <c:param name="txtMinPriceSearch" value="${param.txtMinPriceSearch}"></c:param>
                            <c:param name="txtMaxPriceSearch" value="${param.txtMaxPriceSearch}"></c:param>
                            <c:param name="txtProductNameSearch" value="${param.txtProductNameSearch}"></c:param>
                            <c:param name="cbbCategorySearch" value="${param.cbbCategorySearch}"></c:param>
                            <c:param name="curPage" value="${page}"></c:param>
                        </c:url>
                        <c:if test="${page eq curPage}">
                            <div  style="display: inline; padding: 3px; margin-left: 5px;  border: groove; background: #e6e6e6">
                                <a href="${url}">${page}</a>
                            </div>
                        </c:if>
                        <c:if test="${page ne curPage}">
                            <div style="display: inline;  border: groove; padding: 3px; margin-left: 5px; ">
                                <a href="${url}">${page}</a>
                            </div>
                        </c:if>
                    </c:forEach>
                </c:if>
            </div>    
        </div>

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
