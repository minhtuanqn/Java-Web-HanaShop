/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.dto;

import java.io.Serializable;

/**
 *
 * @author MINH TUAN
 */
public class OrderDetailsDTO implements Serializable{
    private Integer orderId;
    private String productId;
    private String productName;
    private String imgSource;
    private int quantity;
    private Float price;
    private Float totalPriceEachProduct;

    public OrderDetailsDTO() {
    }

    public String getImgSource() {
        return imgSource;
    }

    public void setImgSource(String imgSource) {
        this.imgSource = imgSource;
    }
    
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getTotalPriceEachProduct() {
        return totalPriceEachProduct;
    }

    public void setTotalPriceEachProduct(Float totalPriceEachProduct) {
        this.totalPriceEachProduct = totalPriceEachProduct;
    }

}
