package main.java.mjc;

public abstract class AbstractPurchase implements Comparable<AbstractPurchase> {
    private final Product product;
    private final int amount;

    public AbstractPurchase(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    protected abstract Euro getFinalCost(Euro baseCost);

    public Euro getCost() {
        Euro baseCost = product.getPrice().mul(amount);
        Euro finalCost = getFinalCost(baseCost).mul(0.01, RoundMethod.FLOOR, 0);
        return finalCost;
    }

    @Override
    public String toString() {
        return fieldsToString() + ';' + getCost();
    }

    protected String fieldsToString() {
        return getClass().getSimpleName() + ";" + product + ";" + amount;
    }

    @Override
    public int compareTo(AbstractPurchase purchase) {
        if (this.getCost().compareTo(purchase.getCost()) > 0)
            return 1;
        if (this.getCost().equals(purchase.getCost()))
            return 0;
        else
            return -1;
    }

    public int getAmount() {
        return amount;
    }
}
