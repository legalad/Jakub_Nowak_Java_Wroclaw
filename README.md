# Basket splitter - library ![icons8-java-48](https://user-images.githubusercontent.com/109519711/229430861-79c25617-7e3f-492c-b8c8-6cb097b6ce75.png)

Library that divides the items in a customer's shopping cart into
supply groups.

## Interface & how to use
### Constructor
To constructs a new BasketSplitter instance you have to pass absolute path to the configuration file that contains possible delivery methods for all products offered in the store.
#### Example
```
BasketSplitter basketSplitter = new BasketSplitter("src/test/resources/config.json");
```
### Config file
Provided config file must be a JSON, in which the name is product name and the value is array of delivery methods.
#### Example correct JSON config file
```
{
    "Carrots (1kg)": ["Express Delivery", "Click&Collect"],
    "Cold Beer (330ml)": ["Express Delivery"],
    "Steak (300g)": ["Express Delivery", "Click&Collect"],
    "AA Battery (4 Pcs.)": ["Express Delivery", "Courier"],
    "Espresso Machine": ["Courier", "Click&Collect"],
    "Garden Chair": ["Courier"]
}
```

### Methods
#### Map<String, List<String>> split(List<String> items)

- The algorithm divided products into the minimum possible number of delivery groups.
     The largest group contained as many products as possible.
- @param items - absolute path to the configuration file that contains possible delivery methods for all products offered in the store.
- @return - the division of products into delivery groups as a map.
#### Example
```
bs.split(List.of("Fond - Chocolate", "Chocolate - Unsweetened", "Nut - Almond, Blanched, Whole"));
```

### Test
Library tests was performed on provided config file, which for test purposes is stored in ```src/test/resources``` directory in ```config.json``` file.

The tests cover cases of a valid shopping cart, an incorrect shopping cart, an empty cart and specifying the path to a wrong or non-existent file in the constructor.

## Final thoughts
Library was created only for the purposes of the recruitment.
## Authors

- [@legalad](https://www.github.com/legalad)