package com.luevar.models;

import com.luevar.exceptions.ItemNotFoundException;

/**
 * Класс с доступными продуктами и их ценами
 */
public class PriceList {
    private String[] products = {"Папайя", "Агава", "Бумага"};
    private double[] prices = {15.99, 100.5, 11.1};


    /**
     * Метод, устанавливающий цену введеному продукту в соответствии с прайслистом
     * @param product продукт
     * @throws ItemNotFoundException пробрасывается в случае если в прайслисте нет продукта, введеного пользователем
     */
    public void setPrice(Product product) throws ItemNotFoundException {
        for (int i = 0; i < products.length; i++) {
            if (product.getName().equals(products[i])) {
                product.setPrice(prices[i]);
                return;
            }
        }
        throw new ItemNotFoundException(product.getName());
    }


    /**
     * Метод, возвращающий список товаров и цен на них
     * @return строка, содержащая список товаров и их цен
     */
    public String providePriceList() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < products.length; i++) {
            output.append(products[i]).append("         ").append(prices[i]).append("\n");
        }
        return output.toString();
    }
}
