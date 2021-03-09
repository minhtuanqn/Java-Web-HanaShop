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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import tuanlm.dto.OrderDetailsDTO;
import tuanlm.dto.OrdersDTO;
import tuanlm.utilities.DBConnector;

/**
 *
 * @author MINH TUAN
 */
public class OrdersDAO {

    private Connection cnn = null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;

    private void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (cnn != null) {
            cnn.close();
        }
    }

    public boolean insertOrder(Float totalPrice, String username) throws NamingException, SQLException {
        try {
            cnn = DBConnector.makeConnection();
            if (cnn != null) {
                String sql = "insert into Orders(createDate,username,total) "
                        + "values (?,?,?)";
                ps = cnn.prepareStatement(sql);
                ps.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
                ps.setString(2, username);
                ps.setFloat(3, totalPrice);
                int row = ps.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public int getCurOrderId() throws NamingException, SQLException {
        int curId = 0;
        try {
            cnn = DBConnector.makeConnection();
            if (cnn != null) {
                String sql = "select max(id) as id from Orders ";
                ps = cnn.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next()) {
                    curId = rs.getInt("id");
                }
            }
        } finally {
            closeConnection();
        }
        return curId;
    }

    public Map<OrdersDTO, List<OrderDetailsDTO>> searchOrder(Integer searchOrderID, Timestamp orderDate, String username) throws SQLException, NamingException {
        Map<OrdersDTO, List<OrderDetailsDTO>> historyMap = null;
        try {
            OrderDetailsDAO detailsDAO = new OrderDetailsDAO();
            String sql = "select id, createDate, username, total from Orders where username = '" + username + "' ";
            sql += searchOrderID == null ? "" : searchOrderID == null ? "AND id = "+ searchOrderID : "AND (id = " + searchOrderID;
            sql += searchOrderID == null ? orderDate == null? "" : " AND CONVERT(DATE, createDate) = CONVERT(DATE, ?)" :
                    orderDate == null ? ") " : " OR CONVERT(DATE, createDate) = CONVERT(DATE, ?) )";
            
            cnn = DBConnector.makeConnection();
            System.out.println(sql);
            if (cnn != null) {
                ps = cnn.prepareStatement(sql);
                if(orderDate != null) {
                    ps.setTimestamp(1, orderDate);
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (historyMap == null) {
                        historyMap = new HashMap<>();
                    }
                    OrdersDTO dto = new OrdersDTO();
                    Integer orderId = rs.getInt("id");
                    Timestamp timestamp = rs.getTimestamp("createDate");
                    dto.setId(orderId);
                    dto.setCreateDate(timestamp);
                    dto.setTotal(rs.getFloat("total"));
                    List<OrderDetailsDTO> detailsList = detailsDAO.getListDetailsByOrderId(orderId);
                    historyMap.put(dto, detailsList);

                }
            }
        } 
        finally {
            closeConnection();
        }
        return historyMap;
    }
    
    public Map<OrdersDTO, List<OrderDetailsDTO>> searchAdsLeft() throws SQLException, NamingException {
        Map<OrdersDTO, List<OrderDetailsDTO>> historyMap = null;
        try {
            OrderDetailsDAO detailsDAO = new OrderDetailsDAO();
            String sql = "select id from Orders";
            
            cnn = DBConnector.makeConnection();
            if (cnn != null) {
                ps = cnn.prepareStatement(sql);

                rs = ps.executeQuery();
                while (rs.next()) {
                    if (historyMap == null) {
                        historyMap = new HashMap<>();
                    }
                    OrdersDTO dto = new OrdersDTO();
                    Integer orderId = rs.getInt("id");
                    dto.setId(orderId);
                    List<OrderDetailsDTO> detailsList = detailsDAO.getListDetailsByOrderId(orderId);
                    historyMap.put(dto, detailsList);
                }
            }
        } 
        finally {
            closeConnection();
        }
        return historyMap;
    }
    
    public Map<OrdersDTO, List<OrderDetailsDTO>> searchAdsRight(String username) throws SQLException, NamingException {
        Map<OrdersDTO, List<OrderDetailsDTO>> historyMap = null;
        try {
            OrderDetailsDAO detailsDAO = new OrderDetailsDAO();
            String sql = "select id from Orders where username = ?";
            
            cnn = DBConnector.makeConnection();
            if (cnn != null) {
                ps = cnn.prepareStatement(sql);
                ps.setString(1, username);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (historyMap == null) {
                        historyMap = new HashMap<>();
                    }
                    OrdersDTO dto = new OrdersDTO();
                    Integer orderId = rs.getInt("id");
                    dto.setId(orderId);
                    List<OrderDetailsDTO> detailsList = detailsDAO.getListDetailsByOrderId(orderId);
                    historyMap.put(dto, detailsList);
                }
            }
        } 
        finally {
            closeConnection();
        }
        return historyMap;
    }
    
    
}
