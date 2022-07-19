package test.java.mjc;

import main.java.mjc.Product;
import main.java.mjc.PurchaseDiscountIfGreater;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PurchaseDiscountIfGreaterTest {

    @Test
    void getCostWhenAmountIsGreater() {
        Product product = new Product("Book", 1234);
        int amount = 7;
        PurchaseDiscountIfGreater purchase = new PurchaseDiscountIfGreater(product, amount, 15, 5);
        String expected = "73.0";
        assertEquals(expected, purchase.getCost().toString());
    }

    @Test
    void getCostWhenAmountIsLesser() {
        Product product = new Product("Book", 1234);
        int amount = 7;
        PurchaseDiscountIfGreater purchase = new PurchaseDiscountIfGreater(product, amount, 15, 10);
        String expected = "86.0";
        assertEquals(expected, purchase.getCost().toString());
    }
}