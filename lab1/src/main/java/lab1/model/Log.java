package lab1.model;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Log {
    private ArrayList<Bill> bills = new ArrayList<>();
    private long salesValue = 0L;
    private int soldItems = 0;
    public Lock _mutex = new ReentrantLock();

    public  ArrayList<Bill> getBills() {
        return bills;
    }

    public Long getSalesValue() {
        return salesValue;
    }

    public void setSalesValue(Long salesValue) {
        this.salesValue = salesValue;
    }


    public void setSoldItems(int soldItems) {
        this.soldItems = soldItems;
    }

    public int getSoldItems() {
        return soldItems;
    }
}
