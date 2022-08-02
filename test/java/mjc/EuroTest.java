package test.java.mjc;

import main.java.mjc.beans.Euro;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EuroTest {

    @Test
    void add() {
        Euro euro1 = new Euro(123);
        Euro euro2 = new Euro(3685);
        String expected = "38.08";
        assertEquals(expected, euro1.add(euro2).toString());
    }

    @Test
    void sub() {
        Euro euro1 = new Euro(123);
        Euro euro2 = new Euro(3685);
        String expected = "35.62";
        assertEquals(expected, euro2.sub(euro1).toString());
    }

    @Test
    void mul() {
        Euro euro = new Euro(123);
        int k = 8;
        String expected = "9.84";
        assertEquals(expected, euro.mul(k).toString());
    }

    @Test
    void testToString() {
        Euro euro = new Euro(65278);
        String expected = "652.78";
        assertEquals(expected, euro.toString());
    }
}