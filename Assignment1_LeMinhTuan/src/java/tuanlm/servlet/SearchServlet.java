/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tuanlm.cart.CartObject;
import tuanlm.dao.CategoriesDAO;
import tuanlm.dao.OrdersDAO;
import tuanlm.dao.ProductsDAO;
import tuanlm.dto.CategoriesDTO;
import tuanlm.dto.OrderDetailsDTO;
import tuanlm.dto.OrdersDTO;
import tuanlm.dto.ProductsDTO;
import tuanlm.utilities.ProductPagingModel;

/**
 *
 * @author MINH TUAN
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    private final String HOME_PAGE = "home.jsp";

    private List<ProductsDTO> synProductQuantity(Map<String, Integer> items, List<ProductsDTO> listProduct) {
        if (items != null && items.size() > 0) {
            for (String keyItem : items.keySet()) {
                if (listProduct != null && listProduct.size() > 0) {
                    for (ProductsDTO productsDTO : listProduct) {
                        if (keyItem.equals(productsDTO.getId())) {
                            int tmpQuantity = items.get(keyItem);
                            int totalQuantity = productsDTO.getQuantityRemain();
                            productsDTO.setQuantityRemain(totalQuantity - tmpQuantity);
                            break;
                        }
                    }
                }
            }
        }

        return listProduct;
    }

    private List<OrderDetailsDTO> getListOrderDetails() {
        try {
            OrdersDAO ordersDAO = new OrdersDAO();
            List<OrderDetailsDTO> detailsListAds = new ArrayList<>();
            Map<OrdersDTO, List<OrderDetailsDTO>> historyMap = ordersDAO.searchAdsLeft();
            if (historyMap != null && historyMap.size() > 0) {
                int count = 0;
                for (OrdersDTO ordersDTO : historyMap.keySet()) {
                    List<OrderDetailsDTO> details = historyMap.get(ordersDTO);
                    for (OrderDetailsDTO detail : details) {
                        count++;
                        if (count <= 5) {
                            detailsListAds.add(detail);
                        } else {
                            break;
                        }
                    }
                }
            }
            return detailsListAds;
        } catch (SQLException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private List<OrderDetailsDTO> getListOrderDetailsForRightAds(String username) {
        try {
            OrdersDAO ordersDAO = new OrdersDAO();
            List<OrderDetailsDTO> detailsListAds = new ArrayList<>();
            Map<OrdersDTO, List<OrderDetailsDTO>> historyMap = ordersDAO.searchAdsRight(username);
            if (historyMap != null && historyMap.size() > 0) {
                int count = 0;
                for (OrdersDTO ordersDTO : historyMap.keySet()) {
                    List<OrderDetailsDTO> details = historyMap.get(ordersDTO);
                    for (OrderDetailsDTO detail : details) {
                        count++;
                        if (count <= 5) {
                            detailsListAds.add(detail);
                        } 
                        else {
                            break;
                        }
                    }
                }
            }
            return detailsListAds;
        } catch (SQLException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String urlRewriting = HOME_PAGE;

        String minPrice = request.getParameter("txtMinPriceSearch");
        String maxPrice = request.getParameter("txtMaxPriceSearch");
        String productName = request.getParameter("txtProductNameSearch");
        String category = request.getParameter("cbbCategorySearch");
        String curPage = request.getParameter("curPage");
        int curPageNumber = 1;
        Float maxPriceNumber = null;
        Float minPriceNumber = null;
        boolean checkSearch = false;

        String tmpCategory = category;
        if (category != null && category.equals("")) {
            tmpCategory = null;
        }

        String tmpProductName = productName;
        if (productName == null || productName.equals("")) {
            tmpProductName = null;
        }
        

        try {
            CategoriesDAO daoCategory = new CategoriesDAO();
            List<CategoriesDTO> categories = daoCategory.getAllCategory();
            request.setAttribute("CATEGORIES", categories);

            if (curPage != null) {
                curPageNumber = Integer.parseInt(curPage);
            }

            if (minPrice == null && maxPrice == null && productName == null && category == null) {
                checkSearch = true;
            } 
            else {
                if(minPrice != null) {
                    if(minPrice.equals("")) {
                        minPriceNumber = null;
                    }
                    else if(minPrice.matches("-?\\d+(\\.\\d+)?")){
                        minPriceNumber = Float.parseFloat(minPrice);
                    }
                    else {
                        minPriceNumber = -1f;
                    }
                }
                if(maxPrice != null) {
                    if(maxPrice.equals("")) {
                        maxPriceNumber = null;
                    }
                    else if(maxPrice.matches("-?\\d+(\\.\\d+)?")){
                        maxPriceNumber = Float.parseFloat(maxPrice);
                    }
                    else {
                        maxPriceNumber = -2f;
                    }
                }
                checkSearch = true;
            }

            if (checkSearch) {
                ProductsDAO daoProduct = new ProductsDAO();
                List<ProductsDTO> products = daoProduct.searchProductByOr(minPriceNumber, maxPriceNumber, tmpProductName, tmpCategory);
                ProductPagingModel pagingModel = new ProductPagingModel();

                int totalPage = pagingModel.getTotalPage(products);
                if (curPageNumber > totalPage) {
                    curPageNumber = totalPage;
                }

                List<ProductsDTO> pagingList = pagingModel.loadPaging(products, curPageNumber);
                request.setAttribute("TOTALPAGE", totalPage);

                HttpSession session = request.getSession(false);
                if (session != null && session.getAttribute("WELLCOMENAME") != null) {
                    CartObject cart = (CartObject) session.getAttribute("CUSTCART");
                    if (cart != null) {
                        Map<String, Integer> items = cart.getItems();
                        if (items != null) {
                            pagingList = synProductQuantity(items, pagingList);
                        }
                    }
                }

                if (pagingList != null) {
                    for (ProductsDTO productsDTO : pagingList) {
                        if (productsDTO.getQuantityRemain() <= 0) {
                            productsDTO.setQuantitySatus("This product is out of stock");
                        } else {
                            productsDTO.setQuantitySatus("");
                        }
                    }
                }

                request.setAttribute("PRODUCTS", pagingList);

            }
            List<OrderDetailsDTO> detailsListAds = getListOrderDetails();
            request.setAttribute("ADSLEFT", detailsListAds);

            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("USERNAME") != null) {
                String username = "";
                username = (String) session.getAttribute("USERNAME");
                List<OrderDetailsDTO> detailsListRight = getListOrderDetailsForRightAds(username);
                request.setAttribute("ADSRIGHT", detailsListRight);
            }

            urlRewriting = HOME_PAGE + "?txtMinPriceSearch=" + minPrice
                    + "&txtMaxPriceSearch=" + maxPrice
                    + "&txtProductNameSearch=" + productName
                    + "&cbbCategorySearch=" + category
                    + "&curPage=" + curPageNumber;
        } catch (SQLException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(urlRewriting);
            rd.forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
