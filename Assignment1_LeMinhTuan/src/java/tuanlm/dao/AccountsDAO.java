/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import tuanlm.dto.AccountsDTO;
import tuanlm.utilities.DBConnector;

/**
 *
 * @author MINH TUAN
 */
public class AccountsDAO implements Serializable{
    private Connection cnn = null;
    private PreparedStatement ps = null;
    ResultSet rs = null;
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
    
    public AccountsDTO checkLogin(String username, String password) throws SQLException, NamingException {
        AccountsDTO dto = null;
        try {
            String sql = "select  fullname, role from Accounts where username = ? and password = ?";
            cnn = DBConnector.makeConnection();
            if(null != cnn) {
                ps = cnn.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password);
                rs = ps.executeQuery();
                if(rs.next()) {
                    String fullname = rs.getString("fullname");
                    String role = rs.getString("role");
                    dto = new AccountsDTO();
                    dto.setFullname(fullname);
                    dto.setRole(role);
                    dto.setUsername(username);
                }
            }
        } 
        finally {
            closeConnection();
        }
        return dto;
    }
    
    public AccountsDTO checkLoginGoogle(String email) throws SQLException, NamingException {
        AccountsDTO dto = null;
        try {
            String sql = "select  fullname, role from Accounts where username = ?";
            cnn = DBConnector.makeConnection();
            if(cnn != null) {
                ps = cnn.prepareStatement(sql);
                ps.setString(1, email);
                rs = ps.executeQuery();
                if(rs.next()) {
                    String fullname = rs.getString("fullname");
                    String role = rs.getString("role");
                    dto = new AccountsDTO();
                    dto.setFullname(fullname);
                    dto.setRole(role);
                    dto.setUsername(email);
                }
            }
        }
        finally {
            closeConnection();
        }
        return dto;
    }
}
