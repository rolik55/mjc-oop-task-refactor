package main.java.mjc;

public class PurchaseWithExpenses extends AbstractPurchase {
    private final Euro expenses;

    public PurchaseWithExpenses(Product product, int amount, Euro expenses) {
        super(product, amount);
        this.expenses = expenses;
    }

    @Override
    public Euro getFinalCost(Euro baseCost) {
        return baseCost.add(expenses);
    }
}
