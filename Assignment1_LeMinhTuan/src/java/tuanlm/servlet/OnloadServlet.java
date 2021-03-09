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
@WebServlet(name = "OnloadServlet", urlPatterns = {"/OnloadServlet"})
public class OnloadServlet extends HttpServlet {

    private final String HOME = "home.jsp";

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

    private List<OrderDetailsDTO> getListOrderDetailsForLeftAds() {
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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = HOME + "?curPage=1";
        try {
            CategoriesDAO daoCategory = new CategoriesDAO();
            List<CategoriesDTO> categories = daoCategory.getAllCategory();
            request.setAttribute("CATEGORIES", categories);

            ProductsDAO daoProduct = new ProductsDAO();
            List<ProductsDTO> listProduct = daoProduct.getAllProduct();

            ProductPagingModel pagingModel = new ProductPagingModel();

            List<ProductsDTO> pagingList = pagingModel.loadPaging(listProduct, 1);
            int totalPage = pagingModel.getTotalPage(listProduct);

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
            if (session != null && session.getAttribute("USERNAME") != null) {
                String username = "";
                username = (String) session.getAttribute("USERNAME");
                List<OrderDetailsDTO> detailsListRight = getListOrderDetailsForRightAds(username);
                request.setAttribute("ADSRIGHT", detailsListRight);
            }
            List<OrderDetailsDTO> detailsListLeftAds = getListOrderDetailsForLeftAds();
            request.setAttribute("ADSLEFT", detailsListLeftAds);

            for (ProductsDTO productsDTO : pagingList) {
                if (productsDTO.getQuantityRemain() <= 0) {
                    productsDTO.setQuantitySatus("This product is out of stock");
                } 
                else {
                    productsDTO.setQuantitySatus("");
                }
            }
            request.setAttribute("PRODUCTS", pagingList);
        } catch (NamingException ex) {
            Logger.getLogger(OnloadServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(OnloadServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
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
