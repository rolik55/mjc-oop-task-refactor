package test.java.mjc;

import main.java.mjc.*;
import main.java.mjc.beans.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RunnerTest {
    private final Product product = new Product("Apple", 85);
    private final AbstractPurchase[] purchases = {
            new PurchaseAllDiscounted(product, 16, 10),
            new PurchaseDiscountIfGreater(product, 15, 5.825, 10),
            new PurchaseWithExpenses(product, 5, new Euro(230)),
    };

    @Test
    void searchCostExists() {
        Arrays.sort(purchases);
        PurchaseAllDiscounted searchKey = new PurchaseAllDiscounted(product, 16, 10);
        int expected = 1;
        assertEquals(expected, Runner.search(purchases, searchKey));
    }

    @Test
    void searchCostDoesNotExist() {
        Arrays.sort(purchases);
        PurchaseAllDiscounted searchKey = new PurchaseAllDiscounted(product, 1, 0);
        int expected = -4;
        assertEquals(expected, Runner.search(purchases, searchKey));
    }
}