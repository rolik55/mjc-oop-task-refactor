package main.java.mjc.beans;

public class Product {
    private final String name;
    private final Euro price;

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
