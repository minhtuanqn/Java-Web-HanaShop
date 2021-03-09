/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import tuanlm.dto.OrderDetailsDTO;
import tuanlm.dto.OrdersDTO;
import tuanlm.dto.ProductsDTO;
import tuanlm.utilities.DBConnector;

/**
 *
 * @author MINH TUAN
 */
public class ProductsDAO {

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


    public List<ProductsDTO> searchProductByOr(Float minPrice, Float maxPrice, String productName, String categoryId) throws SQLException, NamingException {
        List<ProductsDTO> productList = null;
        try {
            String sql = "select id, name, description , price, createDate ,imageSource, categoryId, quantityRemain "
                    + "from Products where status = ? and quantityRemain > ? ";
            sql += " order by createDate DESC";
            cnn = DBConnector.makeConnection();
            if (null != cnn) {
                ps = cnn.prepareStatement(sql);
                ps.setBoolean(1, true);
                ps.setInt(2, 0);
                rs = ps.executeQuery();
                CategoriesDAO dao = new CategoriesDAO();
                while (rs.next()) {
                    if (null == productList) {
                        productList = new ArrayList<>();
                    }
                    ProductsDTO dto = new ProductsDTO();
                    dto.setId(rs.getString("id"));
                    dto.setDescription(rs.getString("description"));
                    dto.setName(rs.getString("name"));
                    dto.setPrice(rs.getFloat("price"));
                    dto.setCreateDate(rs.getDate("createDate"));
                    dto.setQuantityRemain(rs.getInt("quantityRemain"));
                    dto.setImageSource("image/" + rs.getString("imageSource"));
                    dto.setCategoryName(dao.getCategoryById(rs.getString("categoryId")));
                    dto.setCategoryId(rs.getString("categoryId"));
                    productList.add(dto);

                }
                if (productName == null && minPrice == null && maxPrice == null && categoryId == null) {
                } 
                else {
                    if (productList != null && !productList.isEmpty()) {
                        List<ProductsDTO> deletedProduct = new ArrayList<>();
                        for (ProductsDTO productsDTO : productList) {
                            boolean check = true;
                            if (categoryId != null && !categoryId.equals("")) {
                                if (categoryId.equals(productsDTO.getCategoryId())) {
                                    check = false;
                                }
                            }
                            if (minPrice != null && maxPrice != null
                                    && minPrice <= productsDTO.getPrice() && maxPrice >= productsDTO.getPrice()) {
                                check = false;
                            }
                            if (productName != null && !productName.equals("") && productsDTO.getName().contains(productName)) {
                                check = false;
                            }

                            if (check) {
                                deletedProduct.add(productsDTO);
                            }
                        }

                        for (ProductsDTO deleted : deletedProduct) {
                            int index = -1;
                            for (ProductsDTO productDTO : productList) {
                                if (deleted.getId().equals(productDTO.getId())) {
                                    index = productList.indexOf(productDTO);
                                    break;
                                }
                            }
                            if (index >= 0) {
                                productList.remove(index);
                            }
                        }
                    }
                }

            }
        } finally {
            closeConnection();
        }
        return productList;
    }

    public boolean deleteProduct(String productsId[], String username) throws SQLException, NamingException {
        try {
            String sql = "update Products set status = ?, updateDate = ?, updateUser = ? where id = ?";
            if (productsId.length > 1) {
                for (int count = 1; count < productsId.length; count++) {
                    sql = sql + " or id = ?";
                }
            }
            cnn = DBConnector.makeConnection();
            if (null != cnn) {
                ps = cnn.prepareStatement(sql);
                ps.setBoolean(1, false);
                ps.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
                ps.setString(3, username);
                ps.setString(4, productsId[0]);
                if (productsId.length > 1) {
                    for (int count = 1; count < productsId.length; count++) {
                        ps.setString(count + 4, productsId[count]);
                    }
                }

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

    public boolean checkExistProductId(String productId) throws NamingException, SQLException {
        try {
            String sql = "select id from Products where id = ?";
            cnn = DBConnector.makeConnection();
            if (null != cnn) {
                ps = cnn.prepareStatement(sql);
                ps.setString(1, productId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean createProduct(ProductsDTO dto) throws NamingException, SQLException {
        try {
            cnn = DBConnector.makeConnection();
            if (cnn != null) {
                String sql = "insert into Products(id,name,createDate,status,quantityRemain,description, price,categoryId,imageSource) "
                        + "values (?,?,?,?,?,?,?,?,?)";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, dto.getId());
                ps.setString(2, dto.getName());
                ps.setDate(3, new Date(new java.util.Date().getTime()));
                ps.setBoolean(4, true);
                ps.setInt(5, dto.getQuantityRemain());
                ps.setString(6, dto.getDescription());
                ps.setFloat(7, dto.getPrice());
                ps.setString(8, dto.getCategoryId());
                ps.setString(9, dto.getImageSource());
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

    public ProductsDTO getProductInforById(String id) throws NamingException, SQLException {
        try {
            String sql = "select id, name, description, quantityRemain , price, createDate ,imageSource, categoryId "
                    + "from Products where id = ? ";
            cnn = DBConnector.makeConnection();
            if (null != cnn) {
                ps = cnn.prepareStatement(sql);
                ps.setString(1, id);
                rs = ps.executeQuery();
                CategoriesDAO dao = new CategoriesDAO();
                if (rs.next()) {
                    ProductsDTO dto = new ProductsDTO();
                    dto.setId(rs.getString("id"));
                    dto.setDescription(rs.getString("description"));
                    dto.setName(rs.getString("name"));
                    dto.setPrice(rs.getFloat("price"));
                    dto.setQuantityRemain(rs.getInt("quantityRemain"));
                    dto.setCreateDate(rs.getDate("createDate"));
                    dto.setImageSource("image/" + rs.getString("imageSource"));
                    dto.setCategoryName(dao.getCategoryById(rs.getString("categoryId")));
                    dto.setCategoryId(rs.getString("categoryId"));
                    return dto;
                }
            }

        } finally {
            closeConnection();
        }
        return null;
    }

    public boolean updateProduct(ProductsDTO dto, String updateUser) throws SQLException, NamingException {
        try {
            String sql = "update Products set name = ?, updateDate = ?, quantityRemain = ?, "
                    + "description = ?, price = ?, categoryId = ?, imageSource = ?, updateUser = ? where id = ?";
            cnn = DBConnector.makeConnection();
            if (cnn != null) {
                ps = cnn.prepareStatement(sql);
                ps.setString(1, dto.getName());
                ps.setDate(2, new Date(new java.util.Date().getTime()));
                ps.setInt(3, dto.getQuantityRemain());
                ps.setString(4, dto.getDescription());
                ps.setFloat(5, dto.getPrice());
                ps.setString(6, dto.getCategoryId());
                ps.setString(7, dto.getImageSource());
                ps.setString(8, updateUser);
                ps.setString(9, dto.getId());
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

    public List<ProductsDTO> getAllProduct() throws SQLException, NamingException {
        List<ProductsDTO> productList = null;
        try {
            String sql = "select id, name, description , price, createDate ,imageSource, categoryId, quantityRemain "
                    + "from Products  where status = ? and quantityRemain > ?  order by createDate DESC";
            cnn = DBConnector.makeConnection();
            if (null != cnn) {
                ps = cnn.prepareStatement(sql);
                ps.setBoolean(1, true);
                ps.setInt(2, 0);
                rs = ps.executeQuery();
                CategoriesDAO dao = new CategoriesDAO();
                while (rs.next()) {
                    if (null == productList) {
                        productList = new ArrayList<>();
                    }
                    ProductsDTO dto = new ProductsDTO();
                    dto.setId(rs.getString("id"));
                    dto.setDescription(rs.getString("description"));
                    dto.setName(rs.getString("name"));
                    dto.setPrice(rs.getFloat("price"));
                    dto.setCreateDate(rs.getDate("createDate"));
                    dto.setImageSource("image/" + rs.getString("imageSource"));
                    dto.setCategoryName(dao.getCategoryById(rs.getString("categoryId")));
                    dto.setQuantityRemain(rs.getInt("quantityRemain"));
                    productList.add(dto);
                }

            }
        } finally {
            closeConnection();
        }
        return productList;
    }

    public List<ProductsDTO> getListProductByCart(Map<String, Integer> items) throws NamingException, SQLException {
        List<ProductsDTO> productList = null;
        try {
            String sql = "select id, name, description , price, createDate ,imageSource, categoryId, quantityRemain "
                    + "from Products  where id = ?";

            cnn = DBConnector.makeConnection();
            if (null != cnn) {
                if (items != null && items.size() > 0) {
                    for (String itemKey : items.keySet()) {
                        ps = cnn.prepareStatement(sql);
                        ps.setString(1, itemKey);
                        rs = ps.executeQuery();
                        CategoriesDAO dao = new CategoriesDAO();
                        if (rs.next()) {
                            if (null == productList) {
                                productList = new ArrayList<>();
                            }
                            ProductsDTO dto = new ProductsDTO();
                            dto.setId(rs.getString("id"));
                            dto.setDescription(rs.getString("description"));
                            dto.setName(rs.getString("name"));
                            dto.setPrice(rs.getFloat("price"));
                            dto.setCreateDate(rs.getDate("createDate"));
                            dto.setImageSource("image/" + rs.getString("imageSource"));
                            dto.setCategoryName(dao.getCategoryById(rs.getString("categoryId")));
                            dto.setQuantityRemain(rs.getInt("quantityRemain"));
                            productList.add(dto);
                        }
                    }
                }
            }
        } finally {
            closeConnection();
        }
        return productList;
    }

    public Integer getQuantityByProductId(String productId) throws SQLException, NamingException {
        Integer remainQuantity = null;
        try {
            String sql = "select quantityRemain from Products where id = ?";
            cnn = DBConnector.makeConnection();
            if (cnn != null) {
                ps = cnn.prepareStatement(sql);
                ps.setString(1, productId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    remainQuantity = rs.getInt("quantityRemain");
                }
            }
        } finally {
            closeConnection();
        }
        return remainQuantity;
    }

    public boolean updateQuantityAfterCheckout(Map<String, Integer> items) throws SQLException {
        boolean checkUpdate = false;
        try {

            String sql = "update Products set quantityRemain = ?  where id = ?";
            cnn = DBConnector.makeConnection();
            if (cnn != null) {
                for (String itemKey : items.keySet()) {
                    checkUpdate = false;
                    ps = cnn.prepareStatement(sql);
                    ps.setInt(1, items.get(itemKey));
                    ps.setString(2, itemKey);
                    int row = ps.executeUpdate();
                    if (row > 0) {
                        checkUpdate = true;
                    }
                }
            }

        } catch (NamingException ex) {
            Logger.getLogger(ProductsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProductsDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return checkUpdate;
    }

    public Map<OrdersDTO, List<OrderDetailsDTO>> getPriceOfProduct(Map<OrdersDTO, List<OrderDetailsDTO>> historyMap) throws SQLException, NamingException {

        try {
            if (historyMap != null && !historyMap.isEmpty()) {
                String sql = "select price from Products where id = ?";
                cnn = DBConnector.makeConnection();
                if (cnn != null) {
                    for (OrdersDTO itemKey : historyMap.keySet()) {
                        List<OrderDetailsDTO> listDetails = historyMap.get(itemKey);
                        for (OrderDetailsDTO listDetail : listDetails) {
                            String productId = listDetail.getProductId();
                            ps = cnn.prepareStatement(sql);
                            ps.setString(1, productId);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                listDetail.setPrice(rs.getFloat("price"));
                            }
                        }
                        historyMap.put(itemKey, listDetails);
                    }
                }
            }

        } finally {
            closeConnection();
        }
        return historyMap;
    }

    public ProductsDTO getProductById(String producId) throws NamingException, SQLException {
        try {
            String sql = "select name, imageSource, price from Products where id  = ?";
            cnn = DBConnector.makeConnection();
            if (cnn != null) {
                ps = cnn.prepareStatement(sql);
                ps.setString(1, producId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String productName = rs.getString("name");
                    String imgSrc = "image//" + rs.getString("imageSource");
                    Float price = rs.getFloat("price");
                    ProductsDTO dto = new ProductsDTO();
                    dto.setImageSource(imgSrc);
                    dto.setPrice(price);
                    dto.setName(productName);
                    return dto;
                }
            }
        } finally {
            closeConnection();
        }
        return null;
    }

}
