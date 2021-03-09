/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author MINH TUAN
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
  maxFileSize = 1024 * 1024 * 5, 
  maxRequestSize = 1024 * 1024 * 5 * 5)
public class DispatchServlet extends HttpServlet {

    private final String LOGIN_SERVLET = "LoginServlet";
    private final String LOGIN_PAGE = "login.html";
    private final String HOME_PAGE = "home.jsp";
    private final String SEARCH_SERVLET = "SearchServlet"; 
    private final String LOGOUT_SERVLET = "LogoutServlet";
    private final String DELETE_SERVLET = "DeleteServlet";
    private final String ONLOAD_SERVLET = "OnloadServlet";
    private final String CREATE_NEW_PRODUCT_SERVLET = "CreateNewProductServlet";
    private final String LOAD_INFOR_UPDATE_SERVLET = "LoadInformationForUpdateServlet";
    private final String UPDATE_SERVLET = "UpdateServlet";
    private final String REDIRECT_INFOR_PAGE = "RedirectInforPageForCreateServlet";
    private final String ADD_TO_CART_SERVLET = "AddToCartServlet";
    private final String DELETE_ITEM_FROM_CART = "DeleteItemFromCartServlet";
    private final String UPDATE_QUANTITY_OF_ITEM_IN_CART_SERVLET = "UpdateQuantityOfItemInCartServlet";
    private final String CHECKOUT_SERVLET = "CheckoutServlet";
    private final String CART_PAGE = "cart.jsp";
    private final String GOOGLE_LOGIN_SERVLET = "GoogleLoginServlet";
    private final String SEARCH_ORDER_SERVLET = "SearchOrderServlet";
    private final String HISTORY_PAGE = "historyOrder.jsp";
    private final String LOGIN_JSP_PAGE = "login.jsp";
    private final String NOTFOUND_PAGE = "notfound.jsp";
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        String url = HOME_PAGE;
        String action = request.getParameter("btnAction");
        try  {
            if(null == action) {
                url = ONLOAD_SERVLET;
            }
            else if(action.equals("Login")) {
                url = LOGIN_SERVLET;
            }
            else if(action.equals("Search")) {
                url = SEARCH_SERVLET;
            }
            else if(action.equals("Add to cart")) {
                url = ADD_TO_CART_SERVLET;
            }
            else if(action.equals("Logout")) {
               url = LOGOUT_SERVLET;
            }
            else if(action.equals("Delete Product")) {
                url = DELETE_SERVLET;
            }
            else if(action.equals("Create")) {
                url = CREATE_NEW_PRODUCT_SERVLET;
            }
            else if(action.equals("Update details")) {
                url = LOAD_INFOR_UPDATE_SERVLET;
            }
            else if(action.equals("Update Product")) {
                url = UPDATE_SERVLET;
            }
            else if(action.equals("Create_New_Product")) {
                url = REDIRECT_INFOR_PAGE;
            }
            else if(action.equals("Delete Item From Cart")) {
                url = DELETE_ITEM_FROM_CART;
            }
            else if(action.equals("Update Quantity")) {
                url = UPDATE_QUANTITY_OF_ITEM_IN_CART_SERVLET;
            }
            else if(action.equals("Check out by Cash")) {
                url = CHECKOUT_SERVLET;
            }
            else if(action.equals("Login now")) {
                url = LOGIN_PAGE;
            }
            else if(action.equals("Cart")) {
                url = CART_PAGE;
            }
            else if(action.equals("GoogleLogin")) {
                url = GOOGLE_LOGIN_SERVLET;
            }
            else if(action.equals("Search Order")) {
                url = SEARCH_ORDER_SERVLET;
            }
            else if(action.equals("Order History")) {
                url = HISTORY_PAGE;
            }
            else if(action.equals("Login_JSP")) {
                url = LOGIN_JSP_PAGE;
            }
            else {
                url = NOTFOUND_PAGE;
            }
        }
        finally {
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
