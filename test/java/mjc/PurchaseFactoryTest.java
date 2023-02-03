package test.java.mjc;

import main.java.mjc.beans.*;
import main.java.mjc.exceptions.CsvLineException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseFactoryTest {

    @Test
    @DisplayName("purchase no discount")
    void getPurchase() throws CsvLineException {
        String csv = "bread;145;5";
        AbstractPurchase expected = new Purchase("bread", 145, 5);

        assertEquals(expected.toString(), PurchaseFactory.getPurchase(csv).toString());
    }

    @Test
    @DisplayName("discounted purchase")
    void getDiscountedPurchase() throws CsvLineException {
        String csv = "potato;180;2;10";
        PurchaseAllDiscounted expected = new PurchaseAllDiscounted("potato", 180, 2, 10);

        assertEquals(expected.toString(), PurchaseFactory.getPurchase(csv).toString());
    }

    @Test
    @DisplayName("invalid input csv")
    void getPurchaseInvalidCsv() {
        String csv = "invalid;csv;";
        assertThrows(CsvLineException.class , () -> PurchaseFactory.getPurchase(csv));
    }
}