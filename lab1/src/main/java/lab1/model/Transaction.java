package lab1.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Transaction implements Runnable {
    private Log log;
    private Inventory inventory;
    private Bill bill;

    public Transaction(Log log, Inventory inventory, Bill bill) {
        this.log = log;
        this.inventory = inventory;
        this.bill = bill;
    }

    @Override
    public void run() {
        Product[] product = new Product[bill.getProducts().size()];
        bill.getProducts().toArray(product);
        for(int i = 0; i < bill.getProducts().size(); i++){
        //for(Product product: bill.getProducts()){
            product[i]._mutex.lock();
            try {
                inventory.remove(product[i], bill.getQuantity(product[i]));
                log._mutex.lock();
                log.setSalesValue(log.getSalesValue() + product[i].getPrice()* bill.getQuantity(product[i]));
                log.setSoldItems(log.getSoldItems() + bill.getQuantity(product[i]));
                log._mutex.unlock();
            }catch (RuntimeException er){
                System.out.println(String.format("Thread %s product %s not enough in stock => removed from list",
                        Thread.currentThread().getName(), product[i].getName()));
                bill.deleteProductFromBill(product[i]);
            }
            finally {
                product[i]._mutex.unlock();
            }
        }
        log.getBills().add(bill);
    }
}