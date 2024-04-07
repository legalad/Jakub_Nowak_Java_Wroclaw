package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class BasketSplitter {

    private final Map<String, List<String>> deliveryMethods;

    /**
     * <p>Constructs a new BasketSplitter instance.</p>
     * @param absolutePathToConfigFile absolute path to the configuration file that contains possible delivery methods for all products offered in the store.
     */
    public BasketSplitter(String absolutePathToConfigFile) {
        deliveryMethods = readProductsDeliveryConfigFile(absolutePathToConfigFile);
    }

    /**
     * <p>
     * The algorithm divided products into the minimum possible number of delivery groups.
     * The largest group contained as many products as possible.</p>
     * @param items absolute path to the configuration file that contains possible delivery methods for all products offered in the store.
     * @return the division of products into delivery groups as a map.
     */
    public Map<String, List<String>> split(List<String> items) {

        var mutableBasketList = new ArrayList<>(items);
        Map<String, List<String>> productDeliveryMethods = getRemainingProductDeliveryMethods(mutableBasketList, deliveryMethods);
        Map<String, List<String>> result = new HashMap<>();

        while (!mutableBasketList.isEmpty()) {
            var remainingProductDeliveryMethods = getRemainingProductDeliveryMethods(mutableBasketList, productDeliveryMethods);
            productDeliveryMethods = remainingProductDeliveryMethods;
            var mostPopularDeliveryTypeName = getMostPopularDeliveryTypeName(remainingProductDeliveryMethods);
            var deliveryTypeNameMatchingItems = getDeliveryTypeNameMatchingItems(mutableBasketList, remainingProductDeliveryMethods, mostPopularDeliveryTypeName);
            result.put(mostPopularDeliveryTypeName, deliveryTypeNameMatchingItems);
            mutableBasketList.removeAll(deliveryTypeNameMatchingItems);
        }

        return result;
    }

    private static List<String> getDeliveryTypeNameMatchingItems(List<String> remainingItems, Map<String, List<String>> productsDeliveryMethods, String delivery) {
        return remainingItems
                .stream()
                .filter(it -> productsDeliveryMethods.get(it).contains(delivery))
                .toList();
    }

    private static String getMostPopularDeliveryTypeName(Map<String, List<String>> productDeliveryMethods) {
        var grouped = productDeliveryMethods.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));

        var sorted = grouped.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return sorted.keySet().iterator().next();
    }

    private static Map<String, List<String>> getRemainingProductDeliveryMethods(List<String> basket, Map<String, List<String>> remaining) {
        return remaining
                .keySet()
                .stream()
                .filter(basket::contains)
                .collect(Collectors.toMap((key -> key), (remaining::get)));
    }

    private static Map<String, List<String>> readProductsDeliveryConfigFile(String absolutePathToConfigFile) {
        try (Reader reader = Files.newBufferedReader(Paths.get(absolutePathToConfigFile))) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(reader, new TypeReference<Map<String, List<String>>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
