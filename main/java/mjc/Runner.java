package main.java.mjc;

import java.util.Arrays;

public class Runner {
    public static void main(String[] args) {
        Product product = new Product("Milk", 150);
        AbstractPurchase[] purchases = {
                new PurchaseAllDiscounted(product, 16, 10),
                new PurchaseAllDiscounted(product, 8, 5),
                new PurchaseDiscountIfGreater(product, 15, 5.825, 10),
                new PurchaseDiscountIfGreater(product, 10, 20, 10),
                new PurchaseWithExpenses(product, 5, new Euro(230)),
                new PurchaseWithExpenses(product, 8, new Euro(180))
        };

        Runner.printPurchases(purchases);
        Arrays.sort(purchases);
        System.out.println();
        Runner.printPurchases(purchases);
        Runner.printMinimumCost(purchases);
        PurchaseAllDiscounted searchKey = new PurchaseAllDiscounted(product, 1, 0);
        int index = Runner.search(purchases, searchKey);
        if (index < 0)
            System.out.println("No purchase with cost " + searchKey.getCost() + " found.");
        else System.out.println(purchases[index]);
    }

    private static void printPurchases(AbstractPurchase[] purchases) {
        for (AbstractPurchase purchase : purchases) System.out.println(purchase);
    }

    private static void printMinimumCost(AbstractPurchase[] purchases) {
        System.out.println("Minimum cost = " + purchases[purchases.length - 1].getCost());
    }

    public static int search(AbstractPurchase[] purchases, AbstractPurchase searchKey) {
        int index = Arrays.binarySearch(purchases, searchKey);
        return index;
    }
}
