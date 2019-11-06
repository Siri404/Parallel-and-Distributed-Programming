package lab1;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab1.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Main {

    public static void main(String... args) throws Exception {
        Inventory inventory = new Inventory(getInventory());
        Inventory initialInventory = new Inventory(new HashMap<>(inventory.getInventory()));
        Log log = new Log();
        int numberOfThreads = 1000;
        int numberOfBills = numberOfThreads;
        List<Bill> bills = getRandomBills(inventory.getInventory(), numberOfBills);
        List<Transaction> transactions = new ArrayList<>();


        Random random = new Random(123);

        for (int i = 0; i < numberOfThreads; i++) {
            int randomIndex = random.nextInt(numberOfBills);
            transactions.add(new Transaction(log, inventory, bills.get(randomIndex)));
            bills.remove(randomIndex);
            numberOfBills--;
        }

        List<Thread> threads = new ArrayList<>();

        transactions.forEach(t -> threads.add(new Thread(t)));

        long start = System.currentTimeMillis();

        for (Thread thread : threads){
            thread.start();
        }

        for (Thread thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        checkInventory(inventory.getInventory());
        checkLog(log, inventory.getInventory(), initialInventory.getInventory());

        System.out.println(String.format("Time for %d threads in milliseconds: %d", numberOfThreads, (System.currentTimeMillis() - start)));
    }

    private static void checkLog(Log log, Map<Product, Integer> inventory, Map<Product, Integer> initialInventory) throws Exception {
        AtomicReference<Long> inventoryCosts = new AtomicReference<>(0L);
        AtomicReference<Long> totalSoldProducts = new AtomicReference<>(0L);
        inventory.forEach((k, v) -> {
            long soldProducts = initialInventory.get(k) - inventory.get(k);
            inventoryCosts.updateAndGet(v1 -> v1 + soldProducts * k.getPrice());
            totalSoldProducts.updateAndGet(v1 -> v1 + soldProducts);
        });
        if(totalSoldProducts.get() != log.getSoldItems()){
            throw new Exception("sold items not matching: " + inventoryCosts.get() + " " + log.getSalesValue());
        }
        if (!inventoryCosts.get().equals(log.getSalesValue())) {
            throw new Exception("Log sales and total sales do not match " + inventoryCosts.get() + " " + log.getSalesValue());
        }
    }


    private static void checkInventory(Map<Product, Integer> inventory) throws Exception {
        if (inventory.entrySet().stream().anyMatch(entry -> entry.getValue() < 0)) {
            throw new Exception("invalid inventory");
        }
    }

    private static List<Bill> getRandomBills(Map<Product, Integer> inventory, int numberOfBills) {
        Random random = new Random(123);
        List<Bill> result = new ArrayList<>();
        Product[] a = new Product[inventory.size()];
        inventory.keySet().toArray(a);
        for (int i = 0; i < numberOfBills; i++) {
            int randomNumberOfProducts = random.nextInt(100)+1;
            Bill bill = new Bill(new HashMap<>());
            for (int j = 0; j < randomNumberOfProducts; j++) {
                int randomQty = random.nextInt(2) + 1;
                int randomIdx = random.nextInt(inventory.size());
                bill.getInventory().put(a[randomIdx], randomQty);
            }
            result.add(bill);
        }
        return result;

    }

    private static Map<Product, Integer> getInventory() {
        List<Product> products = readProducts();
        return products.stream().collect(Collectors.toMap(p->p, Product::getRandomQuantity, (oldValue, newValue) -> oldValue));
    }

    private static List<Product> readProducts() {
        byte[] data = new byte[0];
        try {
            data = Files.readAllBytes(Paths.get("./src/main/resources/random.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        List<Product> products = null;
        try {
            products = Arrays.asList(mapper.readValue(data, Product[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }
}
