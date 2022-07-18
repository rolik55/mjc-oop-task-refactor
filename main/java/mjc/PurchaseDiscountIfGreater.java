package main.java.mjc;

public class PurchaseDiscountIfGreater extends AbstractPurchase {
    private final double discount;
    private final int requiredAmount;

    public PurchaseDiscountIfGreater(Product product, int amount, double discount, int requiredAmount) {
        super(product, amount);
        this.discount = 1 - discount / 100.;
        this.requiredAmount = requiredAmount;
    }

    @Override
    public Euro getFinalCost(Euro baseCost) {
        if (getAmount() > requiredAmount)
            return baseCost.mul(discount, RoundMethod.FLOOR, 2);
        else
            return baseCost;
    }
}
