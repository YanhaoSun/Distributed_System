package service.core;

import service.core.ClientInfo;
import service.core.Product;

import java.util.ArrayList;

public class ProductList {

    private static int COUNTER = 0; 
    public int id; 
    public ArrayList<Product> products;

    public ProductList(ClientInfo info) {
        this.id = COUNTER++;
        this.products = new ArrayList<>();
    }

    public ProductList() {}
    
}
