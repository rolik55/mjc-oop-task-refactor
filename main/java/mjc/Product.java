package main.java.mjc;

public class Product {
    private final String name;
    private final Euro price;

    public Product(String name, int price) {
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
}
