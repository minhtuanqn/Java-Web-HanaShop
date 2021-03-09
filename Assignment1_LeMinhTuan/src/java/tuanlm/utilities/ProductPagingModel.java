/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuanlm.utilities;

import java.util.ArrayList;
import java.util.List;
import tuanlm.dto.ProductsDTO;

/**
 *
 * @author MINH TUAN
 */
public class ProductPagingModel {
    public final static int NUMRECORDOFEACHPAGE = 6;
    private int totalPage = 1;
    private List<ProductsDTO> pagingList;

    public int getNUMRECORDOFEACHPAGE() {
        return NUMRECORDOFEACHPAGE;
    }

    
    
    public int getTotalPage(List<ProductsDTO> productList) {
        if(productList == null || productList.size() <= NUMRECORDOFEACHPAGE) {
            return 1;
        }
        if(productList.size() % NUMRECORDOFEACHPAGE == 0) {
            totalPage = productList.size() / NUMRECORDOFEACHPAGE;
        }
        else {
            totalPage = productList.size() / NUMRECORDOFEACHPAGE + 1;
        }
        return totalPage;
    }
    

    public List<ProductsDTO> loadPaging(List<ProductsDTO> productList, int curPage) {
        if(productList == null || productList.size() <= NUMRECORDOFEACHPAGE) {
            return productList;
        }
        if(pagingList == null) {
            pagingList = new ArrayList<>();
        }

        int sizeTotal = productList.size();
        int startRecord = (curPage - 1) * NUMRECORDOFEACHPAGE + 1;
        int endRecord = startRecord + NUMRECORDOFEACHPAGE - 1;
        if(sizeTotal < endRecord) {
            endRecord = sizeTotal;
        }
        for (int count = 0; count < productList.size(); count ++) {
            if(count + 1 >= startRecord && count + 1 <= endRecord) {
                pagingList.add(productList.get(count));
            }
        }

        return pagingList;
    }
    
    
    
    
}
