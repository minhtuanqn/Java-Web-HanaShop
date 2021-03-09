/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
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
import tuanlm.dao.OrderDetailsDAO;
import tuanlm.dao.OrdersDAO;
import tuanlm.dao.ProductsDAO;
import tuanlm.dto.ProductsDTO;

/**
 *
 * @author MINH TUAN
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {

    private final String CART = "cart.jsp";
    private final String DISPATCH_SERVLET = "DispatchServlet";

    private float getTotalPrice(Map<String, Integer> items, List<ProductsDTO> productList) {
        float totalPrice = 0;
        if (items != null && items.size() > 0) {
            for (String itemKey : items.keySet()) {
                if (productList != null && productList.size() > 0) {
                    for (ProductsDTO productsDTO : productList) {
                        if (productsDTO.getId().equals(itemKey)) {
                            totalPrice += productsDTO.getPrice() * items.get(itemKey);
                            break;
                        }
                    }
                }

            }
        }
        return totalPrice;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = CART;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                Map<String, Integer> remainAfterCheckout = new HashMap<>();
                ProductsDAO daoProduct = new ProductsDAO();
                CartObject cart = (CartObject) session.getAttribute("CUSTCART");
                List<ProductsDTO> productList = (List<ProductsDTO>) session.getAttribute("PRODUCTINFOR");
                String username = (String) session.getAttribute("USERNAME");
                if (cart != null) {
                    String checkRemainQuantity = "";
                    Map<String, Integer> items = cart.getItems();

                    List<ProductsDTO> listCheckQuantity = daoProduct.getListProductByCart(items);
                    for (ProductsDTO productsDTO : listCheckQuantity) {
                        String tmpId = productsDTO.getId();
                        if (items.get(tmpId) > productsDTO.getQuantityRemain()) {
                            checkRemainQuantity = tmpId;
                            break;
                        }
                        else {
                            remainAfterCheckout.put(tmpId, productsDTO.getQuantityRemain() - items.get(tmpId));
                        }
                    }
                    if (checkRemainQuantity.equals("")) {
                        boolean checkUpdateQuantity = daoProduct.updateQuantityAfterCheckout(remainAfterCheckout);
                        if (checkUpdateQuantity) {
                            Float totalPrice = getTotalPrice(items, productList);
                            OrdersDAO ordersDAO = new OrdersDAO();
                            boolean checkInsertOrder = ordersDAO.insertOrder(totalPrice, username);
                            if (checkInsertOrder) {
                                int curOrderId = ordersDAO.getCurOrderId();
                                OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
                                boolean checkInsertDetail = orderDetailsDAO.insertOrderDetails(items, totalPrice, username, curOrderId);
                                if (checkInsertDetail) {
                                    request.setAttribute("STATUS", "Check out successfuly");
                                    session.removeAttribute("CUSTCART");
                                    session.removeAttribute("PRODUCTINFOR");
                                } else {
                                    request.setAttribute("STATUS", "Check out fail");
                                }
                            }
                        }

                    } else {
                        url = DISPATCH_SERVLET + "?btnAction=Cart&remainStatus=" + "Product "
                                + checkRemainQuantity + " is out of stock";
                    }

                }
            }
        } catch (NamingException ex) {
            Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CheckoutServlet.class.getName()).log(Level.SEVERE, null, ex);
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
