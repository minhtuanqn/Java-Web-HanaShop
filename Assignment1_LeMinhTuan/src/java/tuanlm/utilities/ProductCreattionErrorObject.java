/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.utilities;

/**
 *
 * @author MINH TUAN
 */
public class ProductCreattionErrorObject {
    private String blankProductId;
    private String duplicateProductId;
    private String blankName;
    private String errorFormatQuantity;
    private String blankDescription;
    private String blankPrice;
    private String blankQuantity;
    private String errorFormatPrice;
    private String emptyResourceImage;

    public String getBlankPrice() {
        return blankPrice;
    }

    public void setBlankPrice(String blankPrice) {
        this.blankPrice = blankPrice;
    }

    public String getBlankQuantity() {
        return blankQuantity;
    }

    public void setBlankQuantity(String blankQuantity) {
        this.blankQuantity = blankQuantity;
    }

    public String getBlankProductId() {
        return blankProductId;
    }

    public void setBlankProductId(String blankProductId) {
        this.blankProductId = blankProductId;
    }
    
    public String getDuplicateProductId() {
        return duplicateProductId;
    }

    public void setDuplicateProductId(String duplicateProductId) {
        this.duplicateProductId = duplicateProductId;
    }

    public String getBlankName() {
        return blankName;
    }

    public void setBlankName(String blankName) {
        this.blankName = blankName;
    }

    public String getErrorFormatQuantity() {
        return errorFormatQuantity;
    }

    public void setErrorFormatQuantity(String errorFormatQuantity) {
        this.errorFormatQuantity = errorFormatQuantity;
    }

    public String getBlankDescription() {
        return blankDescription;
    }

    public void setBlankDescription(String blankDescription) {
        this.blankDescription = blankDescription;
    }

    public String getErrorFormatPrice() {
        return errorFormatPrice;
    }

    public void setErrorFormatPrice(String errorFormatPrice) {
        this.errorFormatPrice = errorFormatPrice;
    }

    public String getEmptyResourceImage() {
        return emptyResourceImage;
    }

    public void setEmptyResourceImage(String emptyResourceImage) {
        this.emptyResourceImage = emptyResourceImage;
    }
    
    
}
