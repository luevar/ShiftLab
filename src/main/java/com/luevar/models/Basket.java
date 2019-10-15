package com.luevar.models;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    private String id;
    private List<Product> products;

    Basket() {
        id = "12312";
        products = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        //если такой продукт уже добавляли, то найти его и увеличить его число
        for (Product p : products) {
            if (p.getName().equals(product.getName())) {
                p.increaseQuantityBy(product.getQuantity());
                return;
            }
        }
        products.add(product);
    }
}
