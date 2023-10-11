import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public interface Categorizer {

    //map for storing al; vendors with their categories
    public HashMap<String, String> categoryMap = new HashMap<>();


    // takes a list of files and adds them to the categoryMap,
    // where the key is each line(vendor) in the file and the value is the
    // files name
    void setUp(ArrayList<File> categoryFiles) throws FileNotFoundException;

    // takes a transaction description, and returns a list with the transactionDescription as arr[0], and
    // its category as arr[1]. if category can't be determined, then arr[1] = null
    ArrayList<String> getNameAndCategory(String transactionDescription);

    //helper method that returns if a given transactionDescription is in a category
    boolean isInCategory(String category, String transactionDesc);
}
