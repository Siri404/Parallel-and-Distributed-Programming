package lab1.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Inventory {
    protected Map<Product, Integer> products;

    public Inventory() {
        this.products = new HashMap<>();
    }

    public Inventory(Map<Product, Integer> products){
        this.products = products;
    }

    public void remove(Product product, int quantity){
        if (this.products.containsKey(product)){
            if (this.getQuantity(product) < quantity){
                throw new RuntimeException("Not " + product.getName() +" enough in stock");
            }
            this.products.replace(product, this.products.get(product) - quantity);
        }else{
            throw new RuntimeException("Non-existent product");
        }
    }

    public Set<Product> getProducts(){
        return this.products.keySet();
    }

    public Map<Product, Integer> getInventory(){
        return this.products;
    }

    public int getQuantity(Product product) {
        return this.products.getOrDefault(product, 0);
    }

    @Override
    public String toString() {
        StringBuilder ss = new StringBuilder();
        for (Product product : this.getProducts()){
            ss.append("{").append(product.getName()).append(", ").append(this.getQuantity(product)).append("}\n");
        }

        return ss.toString();
    }
}
