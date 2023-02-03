package main.java.mjc;

import main.java.mjc.beans.*;
import main.java.mjc.exceptions.CsvLineException;
import main.java.mjc.exceptions.InvalidSourceException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Runner {
    public static void main(String[] args) {
        final String FILENAME = "main/java/mjc/resources/purchases.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            Map<AbstractPurchase, Weekday> purchasesLastDayMap = new HashMap<>();
            Map<AbstractPurchase, Weekday> purchasesFirstDayMap = new HashMap<>();
            Map<Weekday, List<AbstractPurchase>> weekdayPurchaseEnumMap = new EnumMap<>(Weekday.class);
            List<PurchaseAllDiscounted> discountedPurchases = new ArrayList<>();
            String day, csvLine;

            while ((csvLine = reader.readLine()) != null && (day = reader.readLine()) != null) {
                AbstractPurchase purchase;
                try {
                    purchase = PurchaseFactory.getPurchase(csvLine);
                } catch (CsvLineException e) {
                    throw new InvalidSourceException(e);
                }
                Weekday weekday = Weekday.valueOf(day);

                putLastDay(purchasesLastDayMap, weekday, purchase);
                putFirstDay(purchasesFirstDayMap, weekday, purchase);
                putEnumMapPurchase(weekdayPurchaseEnumMap, weekday, purchase);
                if (purchase instanceof PurchaseAllDiscounted) {
                    discountedPurchases.add((PurchaseAllDiscounted) purchase);
                }
            }

            printMap(purchasesLastDayMap, "Purchase => Weekday of LAST purchase");
            printMap(purchasesFirstDayMap, "Purchase => Weekday of FIRST purchase");

            Purchase purchase1 = new Purchase("bread", 155, 1);
            Purchase purchase2 = new Purchase("bread", 170, 1);
            findAndPrintMapValue(purchasesFirstDayMap, purchase1, "Find FIRST weekday for product " + purchase1.getProduct());
            findAndPrintMapValue(purchasesLastDayMap, purchase1, "Find LAST weekday for product " + purchase1.getProduct());
            findAndPrintMapValue(purchasesFirstDayMap, purchase2, "Find FIRST weekday for product " + purchase2.getProduct());

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

            printMap(purchasesLastDayMap, "\nRemoved entries where name is 'meat'");
            printMap(purchasesFirstDayMap, "Removed entries where weekday is 'FRIDAY'");

            System.out.println("Total cost of discounted purchases: " + getTotalCost(discountedPurchases) + "\n");

            printMap(weekdayPurchaseEnumMap, "Weekday => List of purchases");

            System.out.println("Total cost of purchases in a single day");
            for (Map.Entry<Weekday, List<AbstractPurchase>> entry : weekdayPurchaseEnumMap.entrySet()) {
                System.out.println(entry.getKey() + " " + getTotalCost(entry.getValue()));
            }

            findAndPrintMapValue(weekdayPurchaseEnumMap, Weekday.MONDAY, "\nFind purchases on MONDAY");
            removeMapEntries(weekdayPurchaseEnumMap, new EntryChecker<>() {
                @Override
                public boolean check(Map.Entry<Weekday, List<AbstractPurchase>> entry) {
                    return entry.getValue().removeIf(purchase -> purchase.getProduct().getName().equals("milk"));
                }
            });
            printMap(weekdayPurchaseEnumMap, "\nRemoved entries where name is 'milk'");
        } catch (IOException e) {
            System.err.println("No source file found.");
        }
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
        map.put(purchase, day);
    }

    private static void putEnumMapPurchase(Map<Weekday, List<AbstractPurchase>> map, Weekday day, AbstractPurchase purchase) {
        List<AbstractPurchase> purchases = map.get(day);
        if (purchases == null) {
            map.put(day, purchases = new ArrayList<>());
        }
        purchases.add(purchase);
    }

    private static <K, V> void printMap(Map<K, V> map, String header) {
        System.out.println(header);
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "=>" + entry.getValue().toString());
        }
        System.out.println();
    }

    private static <K, V> void findAndPrintMapValue(Map<K, V> map, K searchKey, String header) {
        System.out.println(header);
        V value = map.get(searchKey);
        if (value == null) {
            System.out.println("No value found.");
        } else {
        System.out.println(value);
        }
    }

    private static Euro getTotalCost(List<? extends AbstractPurchase> purchases) {
        Euro totalCost = new Euro(0);
        for (AbstractPurchase purchase : purchases) {
            totalCost = totalCost.add(purchase.getCost());
        }
        return totalCost;
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
