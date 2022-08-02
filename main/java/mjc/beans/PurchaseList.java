package main.java.mjc.beans;

import main.java.mjc.exceptions.CsvLineException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PurchaseList{
    private List<AbstractPurchase> purchases;
    private final Comparator<AbstractPurchase> comparator;
    private boolean isSorted = false;

    public PurchaseList(String csv, Comparator<AbstractPurchase> comparator) {
        this.comparator = comparator;
        try {
            readFromFile(csv);
        } catch (IOException e) {
            purchases = new ArrayList<>();
        }
    }

    private void readFromFile(String csv) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(csv));
        String csvLine;
        purchases = new ArrayList<>();

        while ((csvLine = reader.readLine()) != null) {
            try {
                purchases.add(PurchaseFactory.getPurchase(csvLine));
            } catch (CsvLineException e) {
                System.err.println(csvLine);
            }
        }
        reader.close();
    }


    public void insert(int index, AbstractPurchase purchase) {
        if (index < 0) {
            index = 0;
        } else if (index > purchases.size()) {
            index = purchases.size();
        }
        purchases.add(index, purchase);
        isSorted = false;
    }

    public void delete(int from, int to) {
        if (from < 0) {
            from = 0;
        }
        if (to > purchases.size()) {
            to = purchases.size();
        }
        //k - how many times to delete (>= from and < to)
        int k = to - from;
        for (int i = 0; i < k; i++) {
            purchases.remove(from);
        }
        isSorted = false;
    }

    public Euro getTotalCost() {
        Euro totalCost = new Euro(0);
        for (AbstractPurchase purchase : purchases) {
            totalCost = totalCost.add(purchase.getCost());
        }
        return totalCost;
    }

    @Override
    public String toString() {
        String string = "";
        for (AbstractPurchase purchase : purchases) {
            string += purchase + "\n";
        }
        return string;
    }

    public void sort() {
        if(!isSorted) {
            purchases.sort(comparator);
        }
        isSorted = true;
    }

    public AbstractPurchase binarySearch(AbstractPurchase searchKey) {
        sort();
        int index = Collections.binarySearch(purchases, searchKey);
        try {
            return purchases.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public List<AbstractPurchase> getPurchases() throws CloneNotSupportedException {
        ArrayList<AbstractPurchase> purchasesClone = new ArrayList<>();

        for (AbstractPurchase purchase : purchases) {
            purchasesClone.add((AbstractPurchase) purchase.clone());
        }
        return purchasesClone;
    }

    public PurchaseList() {
        this.purchases = new ArrayList<>();
        this.comparator = AbstractPurchase::compareTo;
    }
}
