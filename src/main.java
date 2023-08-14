import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class main {
    public static void main(String[] args) throws FileNotFoundException {
            File f = new File("C:\\Users\\Gio\\Desktop\\Code\\ZebraBudgetingApp\\resources\\bankStatementExamples\\BigExport2.csv");
            IUCSVReader reader = new IUCSVReader(f);
            ArrayList<String[]> list = reader.extractCSV();
            System.out.println(list.toString());

    }
}
