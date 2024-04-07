package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BasketSplitterTest {
    private final BasketSplitter basketSplitter = new BasketSplitter("src/test/resources/config.json");

    @Test
    void testSplitWithCorrectBasket1Example() {
        List<String> testInput = List.of(
                "Cocoa Butter",
                "Tart - Raisin And Pecan",
                "Table Cloth 54x72 White",
                "Flower - Daisies",
                "Fond - Chocolate",
                "Cookies - Englishbay Wht"
        );
        Map<String, List<String>> expectedOutput = Map.of(
                "Pick-up point", List.of("Fond - Chocolate"),
                "Courier", List.of(
                        "Cocoa Butter",
                        "Tart - Raisin And Pecan",
                        "Table Cloth 54x72 White",
                        "Flower - Daisies",
                        "Cookies - Englishbay Wht")
        );
        assertEquals(expectedOutput, basketSplitter.split(testInput));
    }

    @Test
    void testSplitWithCorrectBasket2Example() {
        List<String> testInput = List.of(
                "Fond - Chocolate",
                "Chocolate - Unsweetened",
                "Nut - Almond, Blanched, Whole",
                "Haggis",
                "Mushroom - Porcini Frozen",
                "Cake - Miini Cheesecake Cherry",
                "Sauce - Mint",
                "Longan",
                "Bag Clear 10 Lb",
                "Nantucket - Pomegranate Pear",
                "Puree - Strawberry",
                "Numi - Assorted Teas",
                "Apples - Spartan",
                "Garlic - Peeled",
                "Cabbage - Nappa",
                "Bagel - Whole White Sesame",
                "Tea - Apple Green Tea"
        );
        Map<String, List<String>> expectedOutput = Map.of(
                "Same day delivery", List.of(
                        "Sauce - Mint",
                        "Numi - Assorted Teas",
                        "Garlic - Peeled"
                ),
                "Courier", List.of("Cake - Miini Cheesecake Cherry"),
                "Express Collection", List.of(
                        "Fond - Chocolate",
                        "Chocolate - Unsweetened",
                        "Nut - Almond, Blanched, Whole",
                        "Haggis", "Mushroom - Porcini Frozen",
                        "Longan",
                        "Bag Clear 10 Lb",
                        "Nantucket - Pomegranate Pear",
                        "Puree - Strawberry",
                        "Apples - Spartan",
                        "Cabbage - Nappa",
                        "Bagel - Whole White Sesame",
                        "Tea - Apple Green Tea")
                );
        assertEquals(expectedOutput, basketSplitter.split(testInput));
    }

    @Test
    void testSplitWithWrongBasketProductNameExample() {
        List<String> testInput = List.of(
                "Cocoa Butter",
                "Tart - Raisin And Pecan",
                "Table Cloth 54x72 White",
                "Flower - Daizzzzies",
                "Fond - Chocolate",
                "Cookies - Englishbay Wht"
        );
        assertThrows(NullPointerException.class, () -> basketSplitter.split(testInput));
    }
    @Test
    void testSplitWithEmptyBasket() {
        List<String> testInput = new ArrayList<>();
        Map<String, List<String>> expectedOutput = new HashMap<>();
        assertEquals(expectedOutput, basketSplitter.split(testInput));
    }

    @Test
    void textBasketSplitterConstructorWithNonExistentFile() {
        assertThrows(RuntimeException.class, () -> new BasketSplitter("non-existence/file/path"));
    }

}