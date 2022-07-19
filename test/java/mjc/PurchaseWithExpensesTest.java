package test.java.mjc;

import main.java.mjc.Euro;
import main.java.mjc.Product;
import main.java.mjc.PurchaseWithExpenses;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PurchaseWithExpensesTest {

    @Test
    void getCost() {
        Product product = new Product("Book", 1234);
        int amount = 5;
        PurchaseWithExpenses purchase = new PurchaseWithExpenses(product, amount, new Euro(525));
        String expected = "66.0";
        assertEquals(expected, purchase.getCost().toString());
    }
}