/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import tuanlm.dto.CategoriesDTO;
import tuanlm.utilities.DBConnector;

/**
 *
 * @author MINH TUAN
 */
public class CategoriesDAO {
    private Connection cnn = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;
    
    private void closeConnection() throws SQLException {
        if(rs != null) {
            rs.close();
        }
        if(ps != null) {
            ps.close();
        }
        if(cnn != null) {
            cnn.close();
        }
    }
    
    public String getCategoryById(String categoryId) throws SQLException, NamingException {
        try {    
            String sql = "select name from Categories where id = ?";
            cnn = DBConnector.makeConnection();
            if(null != cnn) {
                ps = cnn.prepareStatement(sql);
                ps.setString(1, categoryId);
                rs = ps.executeQuery();
                while(rs.next()) {
                    return rs.getString("name");
                }
            }
        } 
        finally {
            closeConnection();
        }
        return null; 
    }
    
    public List<CategoriesDTO> getAllCategory() throws NamingException, SQLException {
        List<CategoriesDTO> categoriesList = null;
        try {
            cnn = DBConnector.makeConnection();
            if(cnn != null) {
                String sql = "select id, name from Categories";
                ps = cnn.prepareStatement(sql);
                rs = ps.executeQuery();
                while(rs.next()) {
                    if(categoriesList == null) {
                        categoriesList = new ArrayList();
                    }
                    CategoriesDTO dto = new CategoriesDTO();
                    dto.setId(rs.getString("id"));
                    dto.setName(rs.getString("name"));
                    categoriesList.add(dto);
                }
            }
        } 
        finally {
            closeConnection();
        }
        return categoriesList;
    }
    
    
}
