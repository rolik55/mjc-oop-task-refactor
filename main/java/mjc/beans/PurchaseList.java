package main.java.mjc.beans;

import main.java.mjc.exceptions.CsvLineException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PurchaseList {
    private List<AbstractPurchase> purchases;
    private final Comparator<AbstractPurchase> comparator;
    private boolean isSorted = false;

    public PurchaseList(String csv, Comparator<AbstractPurchase> comparator) {
        this.comparator = comparator;

        try (BufferedReader reader = new BufferedReader(new FileReader(csv))) {
            purchases = new ArrayList<>();
            String csvLine;

            while ((csvLine = reader.readLine()) != null) {
                try {
                    purchases.add(PurchaseFactory.getPurchase(csvLine));
                } catch (CsvLineException e) {
                    System.err.println(csvLine);
                }
            }
        } catch (IOException e) {
            purchases = new ArrayList<>();
        }
    }

    public void add(AbstractPurchase purchase) {
        purchases.add(purchase);
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

    public int delete(int from, int to) {
        if (from < 0) {
            from = 0;
        }
        if (to > purchases.size()) {
            to = purchases.size();
        }

        purchases.subList(from, to).clear();
        isSorted = false;
        return to - from;
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
        StringBuilder string = new StringBuilder();
        for (AbstractPurchase purchase : purchases) {
            string.append(purchase).append("\n");
        }
        return string.toString();
    }

    public void sort() {
        if (!isSorted) {
            purchases.sort(comparator);
        }
        isSorted = true;
    }

    public AbstractPurchase binarySearch(AbstractPurchase searchKey) {
        sort();
        int index = Collections.binarySearch(purchases, searchKey);
        return purchases.get(index);
    }

    public List<AbstractPurchase> getPurchases() {
        ArrayList<AbstractPurchase> purchasesClone = new ArrayList<>();

        for (AbstractPurchase purchase : purchases) {
            if (purchase instanceof Purchase) {
                purchasesClone.add(new Purchase(purchase));
            } else {
                purchasesClone.add(new PurchaseAllDiscounted((PurchaseAllDiscounted) purchase));
            }

        }
        return purchasesClone;
    }

    public PurchaseList() {
        this.purchases = new ArrayList<>();
        this.comparator = AbstractPurchase::compareTo;
    }

    public PurchaseList(List<AbstractPurchase> purchases) {
        this.purchases = purchases;
        this.comparator = AbstractPurchase::compareTo;
    }
}
