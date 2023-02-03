package test.java.mjc;

import main.java.mjc.beans.Euro;
import main.java.mjc.beans.Product;
import main.java.mjc.beans.PurchaseWithExpenses;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PurchaseWithExpensesTest {

    @Test
    void getCost() {
        Product product = new Product("Book", 1234);
        int amount = 5;
        PurchaseWithExpenses purchase = new PurchaseWithExpenses(product, amount, new Euro(525));
        String expected = "66.00";
        assertEquals(expected, purchase.getCost().toString());
    }
}