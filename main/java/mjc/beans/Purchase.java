package main.java.mjc.beans;

public class Purchase extends AbstractPurchase {
    public Purchase(String[] fields) {
        super(fields);
    }

    public Purchase(AbstractPurchase purchase) {
        super(purchase);
    }

    public Purchase(Product product, int amount) {
        super(product, amount);
    }

    public Purchase(String name, int price, int amount) {
        super(name, price, amount);
    }

    @Override
    protected Euro getFinalCost(Euro baseCost) {
        return baseCost;
    }
}
