package main.java.mjc;

import java.util.Arrays;

public class Runner {

    public void printPurchases(AbstractPurchase[] purchases) {
        for (int i = 0; i < purchases.length; i++)
            System.out.println(purchases[i]);
    }

    public void sort(AbstractPurchase[] purchases) {
        Arrays.sort(purchases);
    }

    public void printMinimumCost(AbstractPurchase[] purchases) {
        System.out.println("Minimum cost = " + purchases[purchases.length - 1].getCost());
    }

    public int search(AbstractPurchase[] purchases, Euro searchKey) {
        //Array of costs that will be searched
        Euro[] costs = new Euro[purchases.length];
        for (int i = 0; i < purchases.length; i++)
            costs[i] = purchases[i].getCost();

        int index = Arrays.binarySearch(costs, searchKey);
        return index;
    }
}
