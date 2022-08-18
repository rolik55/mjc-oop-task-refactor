package main.java.mjc.beans;

import java.util.Locale;
import java.util.Objects;

public class Euro implements Comparable<Euro> {
    private final int value;

    public Euro(int cents) {
        if (cents < 0) {
            throw new IllegalArgumentException();
        }
        this.value = cents;
    }

    public Euro(int euros, int cents) {
        this(getValidValue(euros, cents));
    }

    private static int getValidValue(int euros, int cents) {
        if (euros < 0 || cents < 0 || cents >= 100) {
            throw new IllegalArgumentException();
        }
        return euros * 100 + cents;
    }

    public Euro(String cents) {
        this(Integer.parseInt(cents));
    }

    public Euro(Euro euro) {
        this(euro.value);
    }

    public Euro() {
        this(0);
    }

    public Euro add(Euro euro) {
        return new Euro(this.value + euro.value);
    }

    public Euro sub(Euro euro) {
        return new Euro(this.value - euro.value);
    }

    public Euro mul(int k) {
        return new Euro(this.value * k);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Euro euro = (Euro) o;
        return value == euro.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        String formattedValue = String.format(Locale.ROOT, "%.02f", value / 100.);
        return formattedValue;
    }

    public boolean equals(Euro euro) {
        return this.value == euro.value;
    }

    @Override
    public int compareTo(Euro euro) {
        return value - euro.value;
    }

    public Euro mul(double x, RoundMethod roundMethod, int d) {
        return new Euro(roundMethod.round(value * x, d));
    }

    public Euro round(RoundMethod roundMethod, int d) {
        return new Euro(roundMethod.round(value, d));
    }

    public int getValue() {
        return value;
    }
}
