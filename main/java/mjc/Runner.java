package main.java.mjc;

import main.java.mjc.beans.*;
import main.java.mjc.exceptions.CsvLineException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Runner {
    public static void main(String[] args) {
        //OOP Task
//        final Product product = new Product("Milk", 150);
//        AbstractPurchase[] purchases = {
//                new PurchaseAllDiscounted(product, 16, 10),
//                new PurchaseAllDiscounted(product, 8, 5),
//                new PurchaseDiscountIfGreater(product, 15, 5.825, 10),
//                new PurchaseDiscountIfGreater(product, 10, 20, 10),
//                new PurchaseWithExpenses(product, 5, new Euro(230)),
//                new PurchaseWithExpenses(product, 8, new Euro(180))
//        };
//
//        printPurchases(purchases);
//        Arrays.sort(purchases);
//        System.out.println();
//        printPurchases(purchases);
//        printMinimumCost(purchases);
//
//        PurchaseAllDiscounted searchKey = new PurchaseAllDiscounted(product, 555, 0);
//        int index = search(purchases, searchKey);
//        if (index < 0) {
//            System.out.println("No purchase with cost " + searchKey.getCost() + " found.");
//        } else {
//            System.out.println(purchases[index]);
//        }

        //Exceptions Task
//        Comparator<AbstractPurchase> comparator = AbstractPurchase::compareTo;
//        PurchaseList list = new PurchaseList("main/java/mjc/resources/purchases.csv", comparator);
//        PurchaseAllDiscounted purchase = new PurchaseAllDiscounted(product, 10, 10);
//        list.insert(-4, purchase);
//        list.delete(4, 7);
//        System.out.println(list.getTotalCost());
//        System.out.println(list.toString());
//        list.sort();
//        System.out.println(list.binarySearch(searchKey));;

        //Collections Task
        Map<AbstractPurchase, Weekday> purchasesLastDayMap = new HashMap<>();
        Map<AbstractPurchase, Weekday> purchasesFirstDayMap = new HashMap<>();
        EnumMap<Weekday, PurchaseList> weekdayPurchaseEnumMap = new EnumMap<>(Weekday.class);
        PurchaseList discountedPurchases = new PurchaseList();

        try (BufferedReader reader = new BufferedReader(new FileReader("main/java/mjc/resources/purchases.csv"))) {
            String day, csvLine;
            while ((csvLine = reader.readLine()) != null && (day = reader.readLine()) != null) {
                AbstractPurchase purchase = PurchaseFactory.getPurchase(csvLine);
                if (purchase instanceof PurchaseAllDiscounted) {
                    discountedPurchases.add(purchase);
                }
                putLastDay(purchasesLastDayMap, day, purchase);
                putFirstDay(purchasesFirstDayMap, day, purchase);
                putEnumMapPurchase(weekdayPurchaseEnumMap, day, purchase);
            }
        } catch (IOException e) {
            throw new RuntimeException("No source file found.");
        } catch (CsvLineException e) {
            throw new RuntimeException();
        }

//        printMap(purchasesLastDayMap);
//        printMap(purchasesFirstDayMap);
//        printMap(weekdayPurchaseEnumMap);

//        Product product = new Product("bread", 155);
//        System.out.println("First day: " + findMapValue(purchasesFirstDayMap, product));
//        System.out.println("Last day: " + findMapValue(purchasesLastDayMap, product));
//        System.out.println("First day: " + findMapValue(purchasesFirstDayMap, new Product("bread", 170)));

//        removeMapEntries(purchasesLastDayMap, "meat");
//        removeMapEntries(purchasesFirstDayMap, Weekday.FRIDAY);

//        System.out.println(discountedPurchases.getTotalCost());

//        for (Map.Entry<Weekday, PurchaseList> entry : weekdayPurchaseEnumMap.entrySet()) {
//            System.out.println(entry.getKey() + " " + entry.getValue().getTotalCost());
//        }

//        System.out.println(findMapValue(weekdayPurchaseEnumMap, Weekday.MONDAY));
    }

    private static <T> void removeMapEntries(Map<AbstractPurchase, Weekday> map, T field) {
        List<AbstractPurchase> toBeRemoved = new ArrayList<>();
        for (Map.Entry<AbstractPurchase, Weekday> entry : map.entrySet()) {
            if (entry.getValue() == field || entry.getKey().getProduct().getName().equals(field)) {
                toBeRemoved.add(entry.getKey());
            }
        }
        for (AbstractPurchase purchase : toBeRemoved) {
            map.remove(purchase);
        }
    }

    private static void putFirstDay(Map<AbstractPurchase, Weekday> map, String day, AbstractPurchase purchase) {
        if (findMapValue(map, purchase.getProduct()) == null) {
            map.put(purchase, Weekday.valueOf(day));
        }
    }

    private static void putLastDay(Map<AbstractPurchase, Weekday> map, String day, AbstractPurchase purchase) {
        for (AbstractPurchase key : map.keySet()) {
            if (key.getProduct().toString().equals(purchase.getProduct().toString())) {
                map.remove(key);
                break;
            }
        }
        map.put(purchase, Weekday.valueOf(day));
    }

    private static void putEnumMapPurchase(EnumMap<Weekday, PurchaseList> map, String day, AbstractPurchase purchase) {
        PurchaseList value = findMapValue(map, Weekday.valueOf(day));
        if (value != null) {
            value.add(purchase);
        } else {
            map.put(Weekday.valueOf(day), new PurchaseList(new ArrayList<>(List.of(purchase))));
        }
    }

    private static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "=>" + entry.getValue().toString());
        }
    }

    private static <K, V, T> V findMapValue(Map<K, V> map, T searchKey) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getKey() == searchKey || entry.getKey().toString().contains(searchKey.toString())) {
                return entry.getValue();
            }
        }
        return null;
    }

    private static void printPurchases(AbstractPurchase[] purchases) {
        for (AbstractPurchase purchase : purchases) {
            System.out.println(purchase);
        }
    }

    private static void printMinimumCost(AbstractPurchase[] purchases) {
        System.out.println("Minimum cost = " + purchases[purchases.length - 1].getCost());
    }

    public static int search(AbstractPurchase[] purchases, AbstractPurchase searchKey) {
        int index = Arrays.binarySearch(purchases, searchKey);
        return index;
    }
}
