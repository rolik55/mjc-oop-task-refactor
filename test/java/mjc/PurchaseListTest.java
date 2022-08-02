package test.java.mjc;

import main.java.mjc.beans.AbstractPurchase;
import main.java.mjc.beans.Euro;
import main.java.mjc.beans.PurchaseAllDiscounted;
import main.java.mjc.beans.PurchaseList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PurchaseListTest {
    PurchaseList expected;
    PurchaseList list;
    Comparator<AbstractPurchase> comparator = AbstractPurchase::compareTo;


    @Test
    @BeforeEach
    void ConstructorTest() throws CloneNotSupportedException {
        initializeLists();
        assertEquals(expected.toString(), list.toString());
    }

    @Test
    void ConstructorTestFileDoesNotExist() {
        list = new PurchaseList("doesNotExist.csv", comparator);
        expected = new PurchaseList();
        assertEquals(expected.toString(), list.toString());
    }

    @Test
    @DisplayName("insert: index < 0")
    void insertNegativeIndex() throws CloneNotSupportedException {
        PurchaseAllDiscounted purchase = new PurchaseAllDiscounted("bread", 260, 3, 20);
        list.insert(-46, purchase);
        assertEquals(purchase.toString(), list.getPurchases().get(0).toString());
    }

    @Test
    @DisplayName("insert: index > list size")
    void insertIndexExceedsSize() throws CloneNotSupportedException {
        PurchaseAllDiscounted purchase = new PurchaseAllDiscounted("bread", 260, 3, 20);
        list.insert(6512, purchase);
        assertEquals(purchase.toString(), list.getPurchases().get(list.getPurchases().size() - 1).toString());
    }

    @Test
    void insert() throws CloneNotSupportedException {
        PurchaseAllDiscounted purchase = new PurchaseAllDiscounted("bread", 260, 3, 20);
        list.insert(5, purchase);
        assertEquals(purchase.toString(), list.getPurchases().get(5).toString());
    }

    @Test
    void delete() throws CloneNotSupportedException {
        expected = new PurchaseList();
        addExpectedAbstractPurchase(0, "bread", 145, 5);
        expected.insert(1, new PurchaseAllDiscounted("potato", 180, 2, 10));
        addExpectedAbstractPurchase(2, "butter", 370, 1);
        addExpectedAbstractPurchase(3, "milk", 131, 2);

        list.delete(4, 8);

        assertEquals(expected.toString(), list.toString());
    }

    @Test
    @DisplayName("delete: from index < 0")
    void deleteNegativeFrom() throws CloneNotSupportedException {
        expected = new PurchaseList();
        addExpectedAbstractPurchase(4, "bread", 154, 3);
        expected.insert(5, new PurchaseAllDiscounted("butter", 341, 1, 1));
        expected.insert(6, new PurchaseAllDiscounted("meat", 1100, 2, 80));
        expected.insert(7, new PurchaseAllDiscounted("bread", 155, 1, 2));

        list.delete(-4, 4);

        assertEquals(expected.toString(), list.toString());
    }

    @Test
    @DisplayName("delete: to index > list size")
    void deleteLargeTo() throws CloneNotSupportedException {
        expected = new PurchaseList();
        addExpectedAbstractPurchase(0, "bread", 145, 5);
        expected.insert(1, new PurchaseAllDiscounted("potato", 180, 2, 10));
        addExpectedAbstractPurchase(2, "butter", 370, 1);
        addExpectedAbstractPurchase(3, "milk", 131, 2);
        addExpectedAbstractPurchase(4, "bread", 154, 3);
        expected.insert(5, new PurchaseAllDiscounted("butter", 341, 1, 1));

        list.delete(6, 52368);

        assertEquals(expected.toString(), list.toString());
    }

    @Test
    @DisplayName("delete: from index > to index")
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

    private void addExpectedAbstractPurchase(int index, String name, int price, int amount) throws CloneNotSupportedException {
        expected.insert(index, new AbstractPurchase(name, price, amount) {
            @Override
            protected Euro getFinalCost(Euro baseCost) {
                return baseCost;
            }
        });
    }

    void initializeLists() throws CloneNotSupportedException {
        list = new PurchaseList("test/java/mjc/resources/purchases.csv", comparator);

        expected = new PurchaseList();
        addExpectedAbstractPurchase(0, "bread", 145, 5);
        expected.insert(1, new PurchaseAllDiscounted("potato", 180, 2, 10));
        addExpectedAbstractPurchase(2, "butter", 370, 1);
        addExpectedAbstractPurchase(3, "milk", 131, 2);
        addExpectedAbstractPurchase(4, "bread", 154, 3);
        expected.insert(5, new PurchaseAllDiscounted("butter", 341, 1, 1));
        expected.insert(6, new PurchaseAllDiscounted("meat", 1100, 2, 80));
        expected.insert(7, new PurchaseAllDiscounted("bread", 155, 1, 2));
    }
}