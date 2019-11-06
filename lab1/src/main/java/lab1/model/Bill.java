package lab1.model;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Bill extends Inventory {
    public Bill(Map<Product, Integer> items) {
        this.products = items;
    }

    public void deleteProductFromBill(Product product){
        this.products.remove(product);
    }


}
