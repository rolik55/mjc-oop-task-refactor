package main.java.mjc;

import java.util.Locale;

public class Euro implements Comparable<Euro> {
    private final int value;

    public Euro(int cents) {
        this.value = cents;
    }

    public Euro(int euros, int cents) {
        this.value = euros * 100 + cents;
    }

    public Euro(Euro euro) {
        this.value = euro.value;
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
    public String toString() {
        String formattedValue = String.format(Locale.ROOT, "%.02f", value * 1.);
        return formattedValue;
    }

    public boolean equals(Euro euro) {
        return this.value == euro.value;
    }

    @Override
    public int compareTo(Euro euro) {
        if (this.value < euro.value)
            return 1;
        else if (this.value == euro.value)
            return 0;
        else
            return -1;
    }

    public Euro mul(double x, RoundMethod roundMethod, int d) {
        return new Euro(roundMethod.round(value * x, d));
    }

    public Euro round(RoundMethod roundMethod, int d) {
        return new Euro(roundMethod.round(value, d));
    }
}
