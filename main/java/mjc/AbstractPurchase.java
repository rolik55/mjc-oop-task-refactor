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
        Euro finalCost = getFinalCost(baseCost).round(RoundMethod.FLOOR, 2);
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
        return this.getCost().compareTo(purchase.getCost());
    }

    public int getAmount() {
        return amount;
    }
}
