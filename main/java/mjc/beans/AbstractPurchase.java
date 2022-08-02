package main.java.mjc.beans;

import main.java.mjc.exceptions.CsvLineException;

public abstract class AbstractPurchase implements Comparable<AbstractPurchase>, Cloneable {
    private final Product product;
    private final int amount;

    public AbstractPurchase(String[] fields) throws Exception {
        this(getValidPurchase(fields));
    }

    public AbstractPurchase(AbstractPurchase purchase) {
        this(purchase.product, purchase.amount);
    }

    public AbstractPurchase(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public AbstractPurchase(String name, int price, int amount) {
        this.product = new Product(name, price);
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
        return product + ";" + amount;
    }

    @Override
    public int compareTo(AbstractPurchase purchase) {
        return this.getCost().compareTo(purchase.getCost());
    }

    public int getAmount() {
        return amount;
    }

    static AbstractPurchase getValidPurchase(String[] fields) throws Exception {
        if (fields.length == 4 && Integer.parseInt(fields[1]) - Integer.parseInt(fields[3]) <= 0) {
            throw new CsvLineException();
        }
        for (int i = 0; i < fields.length; i++) {
            if (i != 0 && fields[i].matches("[a-zA-Z]+")) {
                throw new CsvLineException();
            } else if (fields[i].isEmpty() || fields[i].equals("0") || fields[i].contains("-") || fields[i].contains(".")) {
                throw new CsvLineException();
            }
        }
        return new AbstractPurchase(fields[0], Integer.parseInt(fields[1]), Integer.parseInt(fields[2])) {
            @Override
            protected Euro getFinalCost(Euro baseCost) {
                return baseCost;
            }
        };
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
