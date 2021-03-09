/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.servlet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import tuanlm.dao.AccountsDAO;
import tuanlm.dto.AccountsDTO;
import tuanlm.utilities.GoogleUtils;

/**
 *
 * @author MINH TUAN
 */
@WebServlet(name = "GoogleLoginServlet", urlPatterns = {"/GoogleLoginServlet"})
public class GoogleLoginServlet extends HttpServlet {

    private final String DISPATCHSERVLET = "DispatchServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String code = request.getParameter("code");
        String url = DISPATCHSERVLET + "?btnAction=Login_JSP";
        try {
           if(code != null && !code.equals("")) {
               String accessToken = GoogleUtils.getToken(code);
               String email = GoogleUtils.getUserInfor(accessToken);
                if(email != null) {
                    AccountsDAO dao = new AccountsDAO();
                    AccountsDTO dto = dao.checkLoginGoogle(email);
                    if(dto != null) {
                        HttpSession session = request.getSession(true);
                        if(session != null) {
                            session.setAttribute("WELLCOMENAME", dto.getFullname());
                            session.setAttribute("USERNAME", dto.getUsername());
                            session.setAttribute("ROLE", dto.getRole());
                            url = DISPATCHSERVLET;
                        }
                    }
                    else {
                        url += "&GOOGLE_STATUS=" + "This email has not been registered";
                    }
                }
               
               
           }
        }
        catch (MalformedURLException ex) {
            Logger.getLogger(GoogleLoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (JSONException ex) {
            Logger.getLogger(GoogleLoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GoogleLoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(GoogleLoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
