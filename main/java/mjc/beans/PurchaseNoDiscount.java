package main.java.mjc.beans;

public class PurchaseNoDiscount extends AbstractPurchase{
    public PurchaseNoDiscount(String[] fields) {
        super(fields);
    }

    public PurchaseNoDiscount(AbstractPurchase purchase) {
        super(purchase);
    }

    public PurchaseNoDiscount(Product product, int amount) {
        super(product, amount);
    }

    public PurchaseNoDiscount(String name, int price, int amount) {
        super(name, price, amount);
    }

    @Override
    protected Euro getFinalCost(Euro baseCost) {
        return baseCost;
    }
}
