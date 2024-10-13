package service.message;

import service.core.Product;

public class ProductMessage implements java.io.Serializable {
    private long token;
    private Product product;

    public ProductMessage(long token, Product product) {
        this.token = token;
        this.product = product;
    }

    public long getToken() {
        return token;
    }

    public Product getProduct() {
        return product;
    }
}

