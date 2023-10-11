import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class IUCUCategorizer implements Categorizer{

    //hashmap that maps vendors to their categories
    public HashMap<String, String> categoryMap = new HashMap<>();
    //ex. categoryMap.add("Kroger", "superMarkets");
    //ex. categoryMap.add("Spotify", "subscriptions");

    //takes a list of files and adds them to the categoryMap,
    public void setUp(ArrayList<File> categoryFiles) throws FileNotFoundException {
        for(File file : categoryFiles) {
            Scanner sc = new Scanner(file);
            sc.useDelimiter("\n");
            while(sc.hasNext()) {
                categoryMap.put(sc.next(), file.getName());
            }
        }
    }

    public IUCUCategorizer() {}

    //helper method that returns if a given transactionDescription is in a category

    public boolean isInCategory(String category, String transactionDescription) {
        return categoryMap.get(transactionDescription).equals(category);
    }


    //method which takes a transaction description, and returns its category
    //arr[0] = name
    //arr[1] = category
    public ArrayList<String> getNameAndCategory(String transactionDesc) {
        ArrayList<String> nameAndCategory = new ArrayList<>();
        Set<String> keySet = categoryMap.keySet();
        for(int i = 0; i < transactionDesc.length(); i++) {
            if(keySet.size() == 1) {
                String name = keySet.stream().toList().get(0);
                //if the transaction substring equals the name of the key, then a match is found.
                nameAndCategory.add(name);
                nameAndCategory.add(categoryMap.get(name));
                return nameAndCategory;
            }
            int streamI = i;
            //reduces the key set to match the transaction
            keySet = keySet.stream().filter(set -> set.charAt(streamI) == transactionDesc.charAt(streamI)).collect(Collectors.toSet());
        }
        return new ArrayList<>();
    }
}