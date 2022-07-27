package test.java.mjc;

import main.java.mjc.AbstractPurchase;
import main.java.mjc.Euro;
import main.java.mjc.PurchaseAllDiscounted;
import main.java.mjc.PurchaseList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PurchaseListTest {
    PurchaseList expected;
    PurchaseList list;


    @Test
    @BeforeEach
    void ConstructorTest() {
        initializeLists();
        assertEquals(expected.toString(), list.toString());
    }

    @Test
    void ConstructorTestFileDoesNotExist() {
        list = new PurchaseList("doesNotExist.csv");
        expected = new PurchaseList();
        assertEquals(expected.toString(), list.toString());
    }

    @Test
    void insertNegativeIndex() {
        PurchaseAllDiscounted purchase = new PurchaseAllDiscounted("bread", 260, 3, 20);
        list.insert(-46, purchase);
        assertEquals(purchase, list.getPurchases().get(0));
    }

    @Test
    void insertIndexExceedsSize() {
        PurchaseAllDiscounted purchase = new PurchaseAllDiscounted("bread", 260, 3, 20);
        list.insert(6512, purchase);
        assertEquals(purchase, list.getPurchases().get(list.getPurchases().size() - 1));
    }

    @Test
    void insert() {
        PurchaseAllDiscounted purchase = new PurchaseAllDiscounted("bread", 260, 3, 20);
        list.insert(5, purchase);
        assertEquals(purchase, list.getPurchases().get(5));
    }

    @Test
    void delete() {
        list.delete(4, 8);
        expected.getPurchases().remove(4);
        expected.getPurchases().remove(4);
        expected.getPurchases().remove(4);
        expected.getPurchases().remove(4);
        assertEquals(expected.toString(), list.toString());
    }

    @Test
    void deleteNegativeFrom() {
        list.delete(-4, 4);
        expected.getPurchases().remove(0);
        expected.getPurchases().remove(0);
        expected.getPurchases().remove(0);
        expected.getPurchases().remove(0);
        assertEquals(expected.toString(), list.toString());
    }

    @Test
    void deleteLargeTo() {
        list.delete(6, 52368);
        expected.getPurchases().remove(6);
        expected.getPurchases().remove(6);
        assertEquals(expected.toString(), list.toString());
    }

    @Test
    void deleteFromBiggerThanTo() {
        list.delete(8912, 5);
        assertEquals(expected.toString(), list.toString());
    }

    @Test
    void getTotalCost() {
        Euro expectedCost = new Euro(4300);
        assertEquals(expectedCost.toString(), list.getTotalCost().toString());
    }

    @Test
    void testToString() {
        String expected = """
                bread;1.45;5;7.00
                potato;1.80;2;0.10;3.00
                butter;3.70;1;3.00
                milk;1.31;2;2.00
                bread;1.54;3;4.00
                butter;3.41;1;0.01;3.00
                meat;11.00;2;0.80;20.00
                bread;1.55;1;0.02;1.00
                """;
        assertEquals(expected, list.toString());
    }

    @Test
    void binarySearchKeyExists() {
        PurchaseAllDiscounted purchase = new PurchaseAllDiscounted("bread", 155, 1, 2);
        assertEquals(purchase.toString(), list.binarySearch(purchase).toString());
    }

    @Test
    void binarySearchKeyDoesNotExist() {
        PurchaseAllDiscounted purchase = new PurchaseAllDiscounted("bread", 10, 1, 2);
        assertNull(list.binarySearch(purchase));
    }

    private void addExpectedAbstractPurchase(String name, int price, int amount) {
        expected.getPurchases().add(new AbstractPurchase(name, price, amount) {
            @Override
            protected Euro getFinalCost(Euro baseCost) {
                return baseCost;
            }
        });
    }

    void initializeLists() {
        list = new PurchaseList("test/java/mjc/resources/purchases.csv");

        expected = new PurchaseList();
        addExpectedAbstractPurchase("bread", 145, 5);
        expected.getPurchases().add(new PurchaseAllDiscounted("potato", 180, 2, 10));
        addExpectedAbstractPurchase("butter", 370, 1);
        addExpectedAbstractPurchase("milk", 131, 2);
        addExpectedAbstractPurchase("bread", 154, 3);
        expected.getPurchases().add(new PurchaseAllDiscounted("butter", 341, 1, 1));
        expected.getPurchases().add(new PurchaseAllDiscounted("meat", 1100, 2, 80));
        expected.getPurchases().add(new PurchaseAllDiscounted("bread", 155, 1, 2));
    }
}