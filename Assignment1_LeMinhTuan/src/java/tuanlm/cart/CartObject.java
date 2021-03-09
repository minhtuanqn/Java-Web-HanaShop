/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tuanlm.dto.ProductsDTO;

/**
 *
 * @author MINH TUAN
 */
public class CartObject implements Serializable {

    private Float totalPrice;
    private Map<String, Integer> items = null;

    public Map<String, Integer> getItems() {
        return items;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void caculateTotal(List<ProductsDTO> productList) {
        totalPrice = 0f;
        if (productList != null && productList.size() > 0) {
            for (String productId : items.keySet()) {
                int quantity = items.get(productId);
                for (ProductsDTO productsDTO : productList) {
                    if (productsDTO.getId().equals(productId)) {
                        totalPrice += quantity * productsDTO.getPrice();
                        break;
                    }
                }
            }
        }
    }

    public void addToCart(String name) {
        if (this.items == null) {
            this.items = new HashMap<>();
        }

        int quantity = 1;
        if (this.items.containsKey(name)) {
            quantity = this.items.get(name) + 1;
        }

        this.items.put(name, quantity);
    }

    public void deleteFromCart(String name) {
        if (this.items == null) {
            return;
        }

        if (this.items.containsKey(name)) {
            this.items.remove(name);
        }

        if (this.items.isEmpty()) {
            this.items = null;
        }
    }

    public void updateAmount(String name, int updatedQuatity) {
        if (this.items == null) {

            return;
        }

        if (this.items.containsKey(name)) {

            if (updatedQuatity == 0) {
                this.items.remove(name);
            }
            if (updatedQuatity > 0) {
                this.items.put(name, updatedQuatity);
            }
        }

        if (this.items.size() == 0) {
            this.items = null;
        }

    }
}
