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
        final String FILENAME = "main/java/mjc/resources/purchases.csv";
        Map<AbstractPurchase, Weekday> purchasesLastDayMap;
        Map<AbstractPurchase, Weekday> purchasesFirstDayMap;
        Map<Weekday, PurchaseList> weekdayPurchaseEnumMap;
        PurchaseList discountedPurchases;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            purchasesLastDayMap = new HashMap<>();
            purchasesFirstDayMap = new HashMap<>();
            weekdayPurchaseEnumMap = new EnumMap<>(Weekday.class);
            discountedPurchases = new PurchaseList();
            String day, csvLine;

            while ((csvLine = reader.readLine()) != null && (day = reader.readLine()) != null) {
                AbstractPurchase purchase = PurchaseFactory.getPurchase(csvLine);
                Weekday weekday = Weekday.valueOf(day);

                putLastDay(purchasesLastDayMap, weekday, purchase);
                putFirstDay(purchasesFirstDayMap, weekday, purchase);
                putEnumMapPurchase(weekdayPurchaseEnumMap, weekday, purchase);
                if (purchase instanceof PurchaseAllDiscounted) {
                    discountedPurchases.add(purchase);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("No source file found.");
        } catch (CsvLineException e) {
            throw new RuntimeException();
        }

        System.out.println("Purchase => Weekday of LAST purchase");
        printMap(purchasesLastDayMap);
        System.out.println("Purchase => Weekday of FIRST purchase");
        printMap(purchasesFirstDayMap);

        Purchase purchase1 = new Purchase("bread", 155, 1);
        System.out.println("Find FIRST weekday for product " + purchase1.getProduct() + ": " + findMapValue(purchasesFirstDayMap, purchase1));
        System.out.println("Find LAST weekday for product " + purchase1.getProduct() + ": " + findMapValue(purchasesLastDayMap, purchase1));
        Purchase purchase2 = new Purchase("bread", 170, 1);
        System.out.println("Find FIRST weekday for product " + purchase2.getProduct() + ": " + findMapValue(purchasesFirstDayMap, purchase2));

        removeMapEntries(purchasesLastDayMap, new EntryChecker<>() {
            @Override
            public boolean check(Map.Entry<AbstractPurchase, Weekday> entry) {
                return entry.getKey().getProduct().getName().equals("meat");
            }
        });
        removeMapEntries(purchasesFirstDayMap, new EntryChecker<>() {
            @Override
            public boolean check(Map.Entry<AbstractPurchase, Weekday> entry) {
                return entry.getValue().equals(Weekday.FRIDAY);
            }
        });

        System.out.println("\nRemoved entries where name is 'meat'");
        printMap(purchasesLastDayMap);
        System.out.println("Removed entries where weekday is 'FRIDAY'");
        printMap(purchasesFirstDayMap);

        System.out.println("Total cost of discounted purchases: " + discountedPurchases.getTotalCost() + "\n");

        System.out.println("Weekday => List of purchases");
        printMap(weekdayPurchaseEnumMap);

        System.out.println("Total cost of purchases in a single day");
        for (Map.Entry<Weekday, PurchaseList> entry : weekdayPurchaseEnumMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue().getTotalCost());
        }

        System.out.println("\nFind purchases on MONDAY");
        System.out.println(findMapValue(weekdayPurchaseEnumMap, Weekday.MONDAY));

        removeMapEntries(weekdayPurchaseEnumMap, new EntryChecker<>() {
            @Override
            public boolean check(Map.Entry<Weekday, PurchaseList> entry) {
                return entry.getValue().getPurchases().removeIf(purchase -> purchase.getProduct().getName().equals("milk"));
            }
        });
        System.out.println("Removed entries where name is 'milk'");
        printMap(weekdayPurchaseEnumMap);
    }

    public interface EntryChecker<K, V> {
        boolean check(Map.Entry<K, V> entry);
    }

    private static <K, V> void removeMapEntries(Map<K, V> map, EntryChecker<K, V> checker) {
        map.entrySet().removeIf(checker::check);
    }

    private static void putFirstDay(Map<AbstractPurchase, Weekday> map, Weekday day, AbstractPurchase purchase) {
        if (!map.containsKey(purchase)) {
            map.put(purchase, day);
        }
    }

    private static void putLastDay(Map<AbstractPurchase, Weekday> map, Weekday day, AbstractPurchase purchase) {
        map.remove(purchase);
        map.put(purchase, day);
    }

    private static void putEnumMapPurchase(Map<Weekday, PurchaseList> map, Weekday day, AbstractPurchase purchase) {
        if (map.containsKey(day)) {
            map.get(day).add(purchase);
        } else {
            map.put(day, new PurchaseList(new ArrayList<>(List.of(purchase))));
        }
    }

    private static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "=>" + entry.getValue().toString());
        }
        System.out.println();
    }

    private static <K, V> V findMapValue(Map<K, V> map, K searchKey) {
        return map.get(searchKey);
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
