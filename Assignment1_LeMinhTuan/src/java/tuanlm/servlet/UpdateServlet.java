/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import tuanlm.dao.CategoriesDAO;
import tuanlm.dao.ProductsDAO;
import tuanlm.dto.CategoriesDTO;
import tuanlm.dto.ProductsDTO;
import tuanlm.utilities.ProductCreattionErrorObject;

/**
 *
 * @author MINH TUAN
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
  maxFileSize = 1024 * 1024 * 5, 
  maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(name = "UpdateServlet", urlPatterns = {"/UpdateServlet"})
public class UpdateServlet extends HttpServlet {
    private final String INFOR = "productInfor.jsp";
    

    
    public String getUploadFile(HttpServletRequest request) throws Exception {
        String fileName = "";
        for (Part part : request.getParts()) {
            String contentDisp = part.getHeader("content-disposition");
            String[] items = contentDisp.split(";");
            for (String s : items) {
                if (s.trim().startsWith("filename")) {
                    fileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                }
            }

            if (!fileName.isEmpty()) {

                if (!fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).equals("png")
                        && !fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).equals("jpg")) {
                    return "fail";
                }
                String uploadPath = getServletContext().getRealPath("/") + File.separator + "image";
                StringTokenizer stk = new StringTokenizer(uploadPath, "\\");
                String realPath = "";
                while(stk.hasMoreTokens() ) {
                    String tmp = stk.nextToken();
                    if(!tmp.equals("build")) {
                        realPath += "\\" + tmp;
                    }
                }
                File folderUpload = new File(realPath.substring(1));
                if (!folderUpload.exists()) {
                    folderUpload.mkdirs();
                }
                part.write(folderUpload.getAbsolutePath() + File.separator + fileName);
                String src = File.separator + fileName;
                return src;
            }
        }
        return "";
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = INFOR + "?status=Update";
        try{
            ProductCreattionErrorObject error = new ProductCreattionErrorObject();
        
            String categoryId = request.getParameter("cbbCategory");
            String productId = request.getParameter("txtProductId");
            String productName = request.getParameter("txtProductName");
            String quantity = request.getParameter("txtQuantity");
            String description = request.getParameter("txtDesciption");
            String price = request.getParameter("txtPrice");

            Integer quantityNumber = null;
            Float priceNumber = null;


            boolean checkErr = false;
            try {
                if(null == productId || productId.trim().length() == 0) {
                    error.setBlankProductId("Product id can not be blank");
                    checkErr = true;
                }
                if(null == productName || productName.trim().length() == 0 ) {
                    error.setBlankName("Product name can not be empty");
                    checkErr = true;
                }

                String imageSrc = getUploadFile(request);
                if(imageSrc.equals("") || imageSrc.equals("false")) {
                    error.setEmptyResourceImage("Image resource can nto be blank");
                    checkErr = true;
                }

                if(description == null || description.trim().length() == 0) {
                    error.setBlankDescription("Description can not be blank");
                    checkErr = true;
                }
                if(price == null || price.trim().length() == 0) {
                    error.setBlankPrice("Price can not be blank");
                    checkErr = true;
                }
                else {
                    boolean match = price.matches("-?\\d+(\\.\\d+)?");
                    if(!match) {
                        error.setErrorFormatPrice("Price is a positive number");
                        checkErr = true;
                    }
                    else if(price.contains("-")) {
                        error.setErrorFormatPrice("Price is allways positive");
                        checkErr = true;
                    }
                    else {
                        priceNumber = Float.parseFloat(price);
                    }
                }
                if(quantity == null || quantity.trim().length() == 0 ) {
                    error.setBlankQuantity("Quantity field can not be blank");
                    checkErr = true;
                }
                else {
                    boolean match = quantity.matches("[0-9]{1,}");
                    if(!match) {
                        error.setErrorFormatQuantity("Just input positive number");
                        checkErr = true;
                    }
                    else {
                        quantityNumber = Integer.parseInt(quantity);
                    }
                }
                if(checkErr) {
                    request.setAttribute("ERROR", error);
                }
                else {
                    ProductsDAO dao = new ProductsDAO();
                    ProductsDTO dto = new ProductsDTO(productId, productName, new Date(), true, quantityNumber, 
                            description, priceNumber, categoryId, imageSrc);
                    String username = "";
                    HttpSession session = request.getSession(false);
                    if(session != null) {
                        username = (String) session.getAttribute("WELLCOMENAME");
                    }
                    boolean checkUpdate = dao.updateProduct(dto, username);
                    if(checkUpdate) {
                        request.setAttribute("STATUS", "Update product successfuly");
                    }
                    else {
                        request.setAttribute("STATUS", "Update product fail");
                    }
                }

                CategoriesDAO daoCategory = new CategoriesDAO();
                List<CategoriesDTO> categories = daoCategory.getAllCategory();
                request.setAttribute("CATEGORIES", categories);
            }
            catch (NamingException ex) {
                Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (SQLException ex) {
                Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
            } 
            catch (Exception ex) {
                Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
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
