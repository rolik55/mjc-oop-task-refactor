package main.java.mjc;

public class PurchaseDiscountIfGreater extends AbstractPurchase {
    private final double discountPercentage;
    private final int requiredAmount;

    public PurchaseDiscountIfGreater(Product product, int amount, double discount, int requiredAmount) {
        super(product, amount);
        this.discountPercentage = discount;
        this.requiredAmount = requiredAmount;
    }

    @Override
    public Euro getFinalCost(Euro baseCost) {
        Euro finalCost = new Euro(baseCost);
        if (getAmount() > requiredAmount)
            finalCost = finalCost.mul(1 - discountPercentage / 100., RoundMethod.ROUND, 0);
        return finalCost;
    }

    @Override
    protected String fieldsToString() {
        return super.fieldsToString() + ";" + discountPercentage + ";" + requiredAmount;
    }
}
