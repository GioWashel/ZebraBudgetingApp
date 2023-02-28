import java.io.File;
import java.io.FileNotFoundException;

public class main {
    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("resources/BigExport.csv");
        IUCSVReader reader = new IUCSVReader(f);
        reader.totalDeposits();
    }
}
