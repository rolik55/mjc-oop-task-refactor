package test.java.mjc;

import main.java.mjc.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    @Test
    void testToString() {
        String expected = "Book;12.34";
        Product product = new Product("Book", 1234);
        assertEquals(expected, product.toString());
    }
}