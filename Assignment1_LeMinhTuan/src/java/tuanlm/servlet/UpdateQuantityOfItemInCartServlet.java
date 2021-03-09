/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tuanlm.cart.CartObject;
import tuanlm.dao.ProductsDAO;
import tuanlm.dto.ProductsDTO;

/**
 *
 * @author MINH TUAN
 */
@WebServlet(name = "UpdateQuantityOfItemInCartServlet", urlPatterns = {"/UpdateQuantityOfItemInCartServlet"})
public class UpdateQuantityOfItemInCartServlet extends HttpServlet {

    private final String DISPATCHSERVLET = "DispatchServlet";

    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String productId = request.getParameter("txtProductId");
        String updatedQuantity = request.getParameter("txtQuantity");
        Integer quantityNumber = null;

        String url = DISPATCHSERVLET + "?btnAction=Cart";

        try {
            ProductsDAO dao = new ProductsDAO();
            Integer remainquantity = dao.getQuantityByProductId(productId);
            if (updatedQuantity.matches("[0-9]{1,}")) {
                quantityNumber = Integer.parseInt(updatedQuantity);
                if (remainquantity >= quantityNumber) {
                    HttpSession session = request.getSession(false);

                    if (session != null) {
                        CartObject cart = (CartObject) session.getAttribute("CUSTCART");

                        if (cart != null) {
                            cart.updateAmount(productId, quantityNumber);
                            List<ProductsDTO> productList = dao.getListProductByCart(cart.getItems());
                            cart.caculateTotal(productList);
                            session.setAttribute("CUSTCART", cart);
                            session.setAttribute("PRODUCTINFOR", productList);
                        }
                    }
                }
                else {
                    url += "&remainStatus=" + "Product " + productId + " is out of stock";
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(UpdateQuantityOfItemInCartServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(UpdateQuantityOfItemInCartServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            response.sendRedirect(url);
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
