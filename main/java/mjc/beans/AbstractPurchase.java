package main.java.mjc.beans;

import java.util.Objects;

public abstract class AbstractPurchase implements Comparable<AbstractPurchase> {
    private final Product product;
    private final int amount;

    public AbstractPurchase(String[] fields) {
        if (isValidPurchase(fields)) {
            product = new Product(fields[0], Integer.parseInt(fields[1]));
            amount = Integer.parseInt(fields[2]);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public AbstractPurchase(AbstractPurchase purchase) {
        this(purchase.product, purchase.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o instanceof Purchase || o instanceof PurchaseAllDiscounted) {
            AbstractPurchase purchase = (AbstractPurchase) o;
            return Objects.equals(product, purchase.product);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }

    public AbstractPurchase(Product product, int amount) {
        this(new String[]{product.getName(), String.valueOf(product.getPrice().getValue()), String.valueOf(amount)});
    }

    public AbstractPurchase(String name, int price, int amount) {
        this(new Product(name, price), amount);
    }

    protected abstract Euro getFinalCost(Euro baseCost);

    public Euro getCost() {
        Euro baseCost = product.getPrice().mul(amount);
        Euro finalCost = getFinalCost(baseCost).round(RoundMethod.FLOOR, 2);
        return finalCost;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ";" + fieldsToString() + ';' + getCost();
    }

    protected String fieldsToString() {
        return product + ";" + amount;
    }

    @Override
    public int compareTo(AbstractPurchase purchase) {
        return -getCost().compareTo(purchase.getCost());
    }

    public int getAmount() {
        return amount;
    }

    public Product getProduct() {
        return product;
    }

    private static boolean isValidPurchase(String[] fields) {
        if (fields.length == 3 || fields.length == 4) {
            int price = Integer.parseInt(fields[1]);
            int amount = Integer.parseInt(fields[2]);
            if (fields.length == 4) {
                int discount = Integer.parseInt(fields[3]);
                if (discount == 0 || discount >= price) {
                    throw new IllegalArgumentException();
                }
            }
            if (price == 0 || amount == 0) {
                throw new IllegalArgumentException();
            }
            return true;
        } else {
            throw new IllegalArgumentException();
        }
    }
}