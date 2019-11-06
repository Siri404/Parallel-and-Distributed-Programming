package lab1.model;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Product {
    private Long id;
    private Long price;
    private String name;
    public Lock _mutex = new ReentrantLock();

    public Product(Long id, Long price, String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRandomQuantity(){
        Random random = new Random();
        return random.nextInt(2000) + 300;
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product) {
            return this.getName().equals(((Product) obj).getName());
        }
        return false;
    }
}
