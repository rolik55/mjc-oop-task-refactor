package test.java.mjc;

import main.java.mjc.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RunnerTest {

    @Test
    void searchCostExists() {
        Runner runner = new Runner();
        Product product = new Product("Apple", 85);
        AbstractPurchase[] purchases = {
                new PurchaseAllDiscounted(product, 16, 10),
                new PurchaseDiscountIfGreater(product, 15, 5.825, 10),
                new PurchaseWithExpenses(product, 5, new Euro(230)),
        };
        runner.sort(purchases);
        Euro searchKey = new Euro(600);
        searchKey = searchKey.mul(0.01, RoundMethod.FLOOR, 0);
        int expected = 2;
        assertEquals(expected, runner.search(purchases, searchKey));
    }

    @Test
    void searchCostDoesNotExist() {
        Runner runner = new Runner();
        Product product = new Product("Apple", 85);
        AbstractPurchase[] purchases = {
                new PurchaseAllDiscounted(product, 16, 10),
                new PurchaseDiscountIfGreater(product, 15, 5.825, 10),
                new PurchaseWithExpenses(product, 5, new Euro(230)),
        };
        runner.sort(purchases);
        Euro searchKey = new Euro(700);
        searchKey = searchKey.mul(0.01, RoundMethod.FLOOR, 0);
        int expected = -3;
        assertEquals(expected, runner.search(purchases, searchKey));
    }
}