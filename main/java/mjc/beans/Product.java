package main.java.mjc.beans;

import java.util.Objects;

public class Product {
    private final String name;
    private final Euro price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    public Product(String name, int price) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.price = new Euro(price);
    }

    public Product(String name, String price) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.price = new Euro(price);
    }

    public Euro getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + ";" + price;
    }

    public String getName() {
        return name;
    }
}
