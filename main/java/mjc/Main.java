package main.java.mjc;

public class Main {

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
        Runner runner = new Runner();

        runner.printPurchases(purchases);
        runner.sort(purchases);
        System.out.println();
        runner.printPurchases(purchases);
        runner.printMinimumCost(purchases);
        Euro searchKey = new Euro(5, 0);
        searchKey = searchKey.mul(0.01, RoundMethod.FLOOR, 0);
        int index = runner.search(purchases, searchKey);
        if (index < 0 || index > purchases.length)
            System.out.println("No purchase with cost " + searchKey + " found.");
        else System.out.println(purchases[index]);
    }
}