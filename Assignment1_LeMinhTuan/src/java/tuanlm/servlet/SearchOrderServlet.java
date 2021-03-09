/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import tuanlm.dao.OrdersDAO;
import tuanlm.dao.ProductsDAO;
import tuanlm.dto.OrderDetailsDTO;
import tuanlm.dto.OrdersDTO;

/**
 *
 * @author MINH TUAN
 */
@WebServlet(name = "SearchOrderServlet", urlPatterns = {"/SearchOrderServlet"})
public class SearchOrderServlet extends HttpServlet {

    private final String HISTORY_PAGE = "historyOrder.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String orderId = request.getParameter("txtOrderID");
        Integer orderIdNumber = null;
        String orderDate = request.getParameter("txtCheckoutDate");
        String url = HISTORY_PAGE;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Timestamp timestamp = null;

        try {
            if(orderId != null) {
                if(orderId.length() == 0){
                   orderIdNumber = null; 
                }
                else {
                    if(orderId.matches("[0-9]{1,}")) {
                        orderIdNumber = Integer.parseInt(orderId);
                    }
                    else {
                        orderIdNumber = -1;
                    }
                }
            }
            if (orderDate != null && !orderDate.equals("")) {
                timestamp = new Timestamp(sdf.parse(orderDate).getTime());
            }
            HttpSession session = request.getSession(false);
            if (session != null) {
                String username = (String) session.getAttribute("USERNAME");
                if (username != null) {
                    OrdersDAO dao = new OrdersDAO();
                    ProductsDAO productDAO = new ProductsDAO();
                    Map<OrdersDTO, List<OrderDetailsDTO>> historyMap = dao.searchOrder(orderIdNumber, timestamp, username);
                    historyMap = productDAO.getPriceOfProduct(historyMap);
                    request.setAttribute("HISTORY", historyMap);
                }
            }
            

        } catch (SQLException ex) {
            Logger.getLogger(SearchOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(SearchOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(SearchOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
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
