package main.java.mjc;

public class PurchaseAllDiscounted extends AbstractPurchase {
    private final Euro discount;

    public PurchaseAllDiscounted(Product product, int amount, int discount) {
        super(product, amount);
        this.discount = new Euro(discount);
    }

    @Override
    public Euro getFinalCost(Euro baseCost) {
        Euro discountedCost = new Euro(discount.mul(getAmount()));
        return baseCost.sub(discountedCost);
    }

    @Override
    protected String fieldsToString() {
        return super.fieldsToString() + ";" + discount.toString();
    }
}
