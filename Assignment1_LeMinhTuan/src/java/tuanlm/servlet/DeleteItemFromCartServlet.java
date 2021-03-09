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
@WebServlet(name = "DeleteItemFromCartServlet", urlPatterns = {"/DeleteItemFromCartServlet"})
public class DeleteItemFromCartServlet extends HttpServlet {

    private final String DISPATCH_SERVLET = "DispatchServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String productId = request.getParameter("productId");
        String url = DISPATCH_SERVLET + "?btnAction=Cart";
        try {
            HttpSession session = request.getSession(false);
            if(session != null) {
                CartObject cart = (CartObject) session.getAttribute("CUSTCART");
                if(cart != null) {
                    cart.deleteFromCart(productId);
                    ProductsDAO dao = new ProductsDAO();
                    List<ProductsDTO> listProduct = dao.getListProductByCart(cart.getItems());
                    cart.caculateTotal(listProduct);
                    session.setAttribute("PRODUCTINFOR", listProduct);
                }
            }
            
        }
        catch (NamingException ex) {
            Logger.getLogger(DeleteItemFromCartServlet.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (SQLException ex) {
            Logger.getLogger(DeleteItemFromCartServlet.class.getName()).log(Level.SEVERE, null, ex);
        }        
        finally {
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
