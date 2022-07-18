package main.java.mjc;

public class PurchaseAllDiscounted extends AbstractPurchase {
    private final int discount;

    public PurchaseAllDiscounted(Product product, int amount, int discount) {
        super(product, amount);
        this.discount = discount;
    }

    @Override
    public Euro getFinalCost(Euro baseCost) {
        return baseCost.sub(new Euro(discount * getAmount()));
    }
}
