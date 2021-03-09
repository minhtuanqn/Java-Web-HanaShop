/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author MINH TUAN
 */
public class ProductsDTO implements Serializable{
    private String id;
    private String name;
    private Date createDate;
    private Date updateDate;
    private boolean status;
    private int quantityRemain;
    private String description;
    private float price;
    private String categoryId;
    private String imageSource;
    private String categoryName;
    private String quantitySatus;
    
    public ProductsDTO() {
    }

    public ProductsDTO(String id, String name, Date createDate, boolean status, 
            int quantityRemain, String description, float price, String categoryId, 
            String imageSource) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.status = status;
        this.quantityRemain = quantityRemain;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.imageSource = imageSource;
    }

    public String getQuantitySatus() {
        return quantitySatus;
    }

    public void setQuantitySatus(String quantitySatus) {
        this.quantitySatus = quantitySatus;
    }

    

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getQuantityRemain() {
        return quantityRemain;
    }

    public void setQuantityRemain(int quantityRemain) {
        this.quantityRemain = quantityRemain;
    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    
    
}
