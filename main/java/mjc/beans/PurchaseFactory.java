package main.java.mjc.beans;

import main.java.mjc.exceptions.CsvLineException;

public class PurchaseFactory {
    public static AbstractPurchase getPurchase(String csvLine) throws CsvLineException {
        String[] fields = csvLine.split(";");
        try {
            return getPurchaseKind(fields.length).getPurchase(fields);
        } catch (Exception e) {
            throw new CsvLineException();
        }
    }

    private enum PurchaseKind {
        PURCHASE {
            @Override
            protected AbstractPurchase getPurchase(String[] fields) throws Exception {
                return new AbstractPurchase(fields) {
                    @Override
                    protected Euro getFinalCost(Euro baseCost) {
                        return baseCost;
                    }
                };
            }
        },
        PURCHASE_DISCOUNTED {
            @Override
            protected AbstractPurchase getPurchase(String[] fields) throws Exception {
                return new PurchaseAllDiscounted(fields);
            }
        };

        abstract protected AbstractPurchase getPurchase(String[] fields) throws Exception;
    }

    private static PurchaseKind getPurchaseKind(int length) {
        if (length == 3) {
            return PurchaseKind.PURCHASE;
        } else {
            return PurchaseKind.PURCHASE_DISCOUNTED;
        }
    }

}
