package test.java.mjc;

import main.java.mjc.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    @org.junit.jupiter.api.Test
    void testToString() {
        String expected = "Book;1234.00";
        Product product = new Product("Book", 1234);
        assertEquals(expected, product.toString());
    }
}