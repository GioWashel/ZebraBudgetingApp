import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class Categorizer {
    //needs work

    //will be used to compare against hashmap timing
    public Trie restaurantTrie = new Trie();
    public Trie drinksTrie = new Trie();
    public Trie onlineClothingTrie = new Trie();
    public Trie gasStations = new Trie();
    public Trie superMarkets = new Trie();
    public Trie necessities = new Trie();
    //add values to sets from files
    public HashMap<String, String> categoryMap = new HashMap<>();
    //ex. categoryMap.add("Kroger", "superMarkets");
    //ex. categoryMap.add("Spotify", "subscriptions");
    public void setUp() throws FileNotFoundException {
        //for each category file, add it to the category map

        //todo
    }

    public Categorizer() throws FileNotFoundException {
        setUp();
    }
    //might not need these boolean methods...
    public boolean isRestaurant(String prefixMatch) {
        return categoryMap.get(prefixMatch).equals("Restaurant");

    }
    public boolean isSubscription(String prefixMatch){
        return categoryMap.get(prefixMatch).equals("subscription");
    }
    public boolean isGasStation(String prefixMatch) {
        return categoryMap.get(prefixMatch).equals("gasStation");
    }
    public boolean isSuperMarket(String prefixMatch) {
        return categoryMap.get(prefixMatch).equals("supermarket");
    }
    public boolean isNecessity(String prefixMatch) {
        return categoryMap.get(prefixMatch).equals("necessity");
    }

    //method which will take a transaction, and return the merchant
    // ie "WITHDRAWAL KROGER 129 # 4" -> KROGER
    //need to use some sort of prefix matching algorithm (either with trie or hashmap) to get the category.
    //this method will return a string[] with the key (place/vendor) and value (category)
    //will be horrible time complexity, but we could make a method to return all subsets of a string, then
    //check if each subset is contained in each trie for the prefix search.
    //use trie to get the key, then use hashmap to get the category
        //if multiple matches, ask the user which one is right


    //gets a percentage of how similar the strings are
    public String get(String transaction) {
        String match = "";
        boolean end = false;
        ArrayList<String> keys = getPossibleKeys(transaction);
        for(String key: keys) {
            while(end == false) {
                transaction.
            }
        }
        return match;
    }
    public ArrayList<String> getPossibleKeys(String transaction) {
        ArrayList<String> categories = new ArrayList<>();
        for(String key: categoryMap.keySet()) {
            if(transaction.contains(key)) {
                categories.add(key);
            }
        }
        //todo
        return
    }
}