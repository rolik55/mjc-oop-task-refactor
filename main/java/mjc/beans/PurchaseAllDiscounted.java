package main.java.mjc.beans;

public class PurchaseAllDiscounted extends AbstractPurchase {
    private final Euro discount;

    public PurchaseAllDiscounted(Product product, int amount, int discount) {
        super(product, amount);
        this.discount = new Euro(discount);
    }

    public PurchaseAllDiscounted(String[] fields) throws Exception {
        super(fields);
        this.discount = new Euro(Integer.parseInt(fields[3]));
    }

    public PurchaseAllDiscounted(String name, int price, int amount, int discount) {
        super(name, price, amount);
        this.discount = new Euro(discount);
    }

    @Override
    public Euro getFinalCost(Euro baseCost) {
        Euro discountedCost = new Euro(discount.mul(getAmount()));
        return baseCost.sub(discountedCost);
    }

    @Override
    protected String fieldsToString() {
        return super.fieldsToString() + ";" + discount;
    }
}
