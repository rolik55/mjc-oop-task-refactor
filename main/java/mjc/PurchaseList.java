package main.java.mjc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class PurchaseList {
    private ArrayList<AbstractPurchase> purchases;

    public PurchaseList(String csv) {
        try {
            readFromFile(csv);
        } catch (IOException e) {
            purchases = new ArrayList<>();
        }
    }

    private void readFromFile(String csv) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(csv));
        String line;
        //[0] - name, [1] - price, [2] - amount, [3] - discount
        String[] data;
        purchases = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            data = line.split(";");
            if (isValidData(data)) {
                if (data.length == 3) {
                    addPurchase(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]));
                } else {
                    addPurchase(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]));
                }
            } else {
                System.err.println(line);
            }
        }
        reader.close();
    }

    private void addPurchase(String name, int price, int amount) {
        AbstractPurchase purchase = new AbstractPurchase(name, price, amount) {
            @Override
            protected Euro getFinalCost(Euro baseCost) {
                return baseCost;
            }
        };
        purchases.add(purchase);
    }

    private void addPurchase(String name, int price, int amount, int discount) {
        PurchaseAllDiscounted purchase = new PurchaseAllDiscounted(name, price, amount, discount);
        purchases.add(purchase);
    }


    public void insert(int index, AbstractPurchase purchase) {
        if (index < 0) {
            index = 0;
        } else if (index > purchases.size()) {
            index = purchases.size();
        }
        purchases.add(index, purchase);
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

    public AbstractPurchase binarySearch(AbstractPurchase searchKey) {
        int index = Collections.binarySearch(purchases, searchKey);
        try {
            return purchases.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    private boolean isValidData(String[] data) {
        try {
            //checks if discount is larger or equal to the price when discount exists
            if (data.length == 4 && Integer.parseInt(data[1]) - Integer.parseInt(data[3]) <= 0) {
                return false;
                //checks the number of elements
            } else if (data.length < 3 || data.length > 4) {
                return false;
                //checks the validity of separate elements
            } else {
                for (int i = 0; i < data.length; i++) {
                    //checks if elements (excluding the name) has letters
                    if (i != 0 && data[i].matches("[a-zA-Z]+")) {
                        return false;
                    } else if (data[i].isEmpty() || data[i].equals("0") || data[i].contains("-") || data[i].contains(".")) {
                        return false;
                    }
                }
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public ArrayList<AbstractPurchase> getPurchases() {
        return purchases;
    }

    public PurchaseList() {
        this.purchases = new ArrayList<>();
    }
}
