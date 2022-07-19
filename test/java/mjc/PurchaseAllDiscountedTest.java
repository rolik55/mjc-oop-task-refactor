package test.java.mjc;

import main.java.mjc.Product;
import main.java.mjc.PurchaseAllDiscounted;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PurchaseAllDiscountedTest {

    @Test
    void testToString() {
        Product product = new Product("Book", 1234);
        int amount = 7;
        PurchaseAllDiscounted purchase = new PurchaseAllDiscounted(product, amount, 15);
        String expected = "PurchaseAllDiscounted;Book;12.34;7;0.15;85.0";
        assertEquals(expected, purchase.toString());
    }

    @Test
    void compareToLess() {
        Product product1 = new Product("Book", 1234);
        Product product2 = new Product("Chair", 30450);
        int amount1 = 10;
        int amount2 = 2;
        PurchaseAllDiscounted purchase1 = new PurchaseAllDiscounted(product1, amount1, 234);
        PurchaseAllDiscounted purchase2 = new PurchaseAllDiscounted(product2, amount2, 500);
        int expected = 49900;
        assertEquals(expected, purchase1.compareTo(purchase2));
    }

    @Test
    void compareToEqual() {
        Product product1 = new Product("Book", 1234);
        Product product2 = new Product("Chair", 10500);
        int amount1 = 10;
        int amount2 = 1;
        PurchaseAllDiscounted purchase1 = new PurchaseAllDiscounted(product1, amount1, 234);
        PurchaseAllDiscounted purchase2 = new PurchaseAllDiscounted(product2, amount2, 500);
        int expected = 0;
        assertEquals(expected, purchase1.compareTo(purchase2));
    }

    @Test
    void compareToGreater() {
        Product product1 = new Product("Book", 1234);
        Product product2 = new Product("Chair", 3045);
        int amount1 = 10;
        int amount2 = 1;
        PurchaseAllDiscounted purchase1 = new PurchaseAllDiscounted(product1, amount1, 234);
        PurchaseAllDiscounted purchase2 = new PurchaseAllDiscounted(product2, amount2, 500);
        int expected = -7500;
        assertEquals(expected, purchase1.compareTo(purchase2));
    }

    @Test
    void getCost() {
        Product product = new Product("Book", 1234);
        int amount = 7;
        PurchaseAllDiscounted purchase = new PurchaseAllDiscounted(product, amount, 15);
        String expected = "85.0";
        assertEquals(expected, purchase.getCost().toString());
    }
}