package main.java.mjc;

import main.java.mjc.beans.AbstractPurchase;
import main.java.mjc.beans.Product;
import main.java.mjc.beans.PurchaseAllDiscounted;
import main.java.mjc.beans.PurchaseList;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;

public class Runner {
    public static void main(String[] args) throws FileNotFoundException {
        //OOP Task
        final Product product = new Product("Milk", 150);
//        AbstractPurchase[] purchases = {
//                new PurchaseAllDiscounted(product, 16, 10),
//                new PurchaseAllDiscounted(product, 8, 5),
//                new PurchaseDiscountIfGreater(product, 15, 5.825, 10),
//                new PurchaseDiscountIfGreater(product, 10, 20, 10),
//                new PurchaseWithExpenses(product, 5, new Euro(230)),
//                new PurchaseWithExpenses(product, 8, new Euro(180))
//        };
//
//        printPurchases(purchases);
//        Arrays.sort(purchases);
//        System.out.println();
//        printPurchases(purchases);
//        printMinimumCost(purchases);
//
        PurchaseAllDiscounted searchKey = new PurchaseAllDiscounted(product, 555, 0);
//        int index = search(purchases, searchKey);
//        if (index < 0) {
//            System.out.println("No purchase with cost " + searchKey.getCost() + " found.");
//        } else {
//            System.out.println(purchases[index]);
//        }

        //Exceptions Task
        Comparator<AbstractPurchase> comparator = AbstractPurchase::compareTo;
        PurchaseList list = new PurchaseList("main/java/mjc/resources/purchases.csv", comparator);
        PurchaseAllDiscounted purchase = new PurchaseAllDiscounted(product, 10, 10);
        //list.insert(-4, purchase);
        //list.delete(4, 7);
        //System.out.println(list.getTotalCost());
        System.out.println(list.toString());
        //list.sort();
        //System.out.println(list.binarySearch(searchKey));;
    }

    private static void printPurchases(AbstractPurchase[] purchases) {
        for (AbstractPurchase purchase : purchases) {
            System.out.println(purchase);
        }
    }

    private static void printMinimumCost(AbstractPurchase[] purchases) {
        System.out.println("Minimum cost = " + purchases[purchases.length - 1].getCost());
    }

    public static int search(AbstractPurchase[] purchases, AbstractPurchase searchKey) {
        int index = Arrays.binarySearch(purchases, searchKey);
        return index;
    }
}
