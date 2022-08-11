package main.java.mjc.beans;

import main.java.mjc.exceptions.CsvLineException;

public class PurchaseFactory {
    public static AbstractPurchase getPurchase(String csvLine) throws CsvLineException {
        String[] fields = csvLine.split(";");
        try {
            return getPurchaseKind(fields.length).getPurchase(fields);
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            throw new CsvLineException();
        }
    }

    private enum PurchaseKind {
        PURCHASE {
            @Override
            protected AbstractPurchase getPurchase(String[] fields) throws IllegalArgumentException {
                return new PurchaseNoDiscount(fields);
            }
        },
        PURCHASE_DISCOUNTED {
            @Override
            protected AbstractPurchase getPurchase(String[] fields) throws IllegalArgumentException {
                return new PurchaseAllDiscounted(fields);
            }
        };

        abstract protected AbstractPurchase getPurchase(String[] fields) throws IllegalArgumentException;
    }

    private static PurchaseKind getPurchaseKind(int length) {
        if (length == 3) {
            return PurchaseKind.values()[0];
        } else {
            return PurchaseKind.values()[1];
        }
    }

}
