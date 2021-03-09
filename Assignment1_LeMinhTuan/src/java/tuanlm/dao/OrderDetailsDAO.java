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
import java.util.Map;
import javax.naming.NamingException;
import tuanlm.dto.OrderDetailsDTO;
import tuanlm.dto.ProductsDTO;
import tuanlm.utilities.DBConnector;

/**
 *
 * @author MINH TUAN
 */
public class OrderDetailsDAO {

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

    public boolean insertOrderDetails(Map<String, Integer> items, Float totalPrice, String username, int curOrderId) throws NamingException, SQLException {
        boolean check = false;
        try {
            cnn = DBConnector.makeConnection();
            if (cnn != null) {

                String sql = "insert into OrderDetails(orderId,productId,quantity) "
                        + "values (?,?,?)";
                for (String itemKey : items.keySet()) {

                    ps = cnn.prepareStatement(sql);
                    ps.setInt(1, curOrderId);
                    ps.setString(2, itemKey);
                    ps.setFloat(3, items.get(itemKey));
                    int row = ps.executeUpdate();
                    if (row > 0) {
                        check = true;
                    }
                }
            }
        } finally {
            closeConnection();
        }
        return check;
    }

    public List<OrderDetailsDTO> getListDetailsByOrderId(Integer orderId) throws NamingException, SQLException {
        List<OrderDetailsDTO> detailList = null;
        ProductsDAO productsDAO = new ProductsDAO();
        try {
            cnn = DBConnector.makeConnection();
            if (cnn != null) {

                String sql = "select productId, quantity from OrderDetails where orderId = ?";
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, orderId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (detailList == null) {
                        detailList = new ArrayList<>();

                    }
                    OrderDetailsDTO dto = new OrderDetailsDTO();
                    dto.setOrderId(orderId);
                    ProductsDTO productsDTO = productsDAO.getProductById(rs.getString("productId"));
                    if(productsDTO != null) {
                        dto.setImgSource(productsDTO.getImageSource());
                        dto.setProductName(productsDTO.getName());
                        dto.setPrice(productsDTO.getPrice());
                    }
                    dto.setProductId(rs.getString("productId"));
                    dto.setQuantity(rs.getInt("quantity"));
                    detailList.add(dto);
                }
            }
        } finally {
            closeConnection();
        }
        return detailList;
    }
}
