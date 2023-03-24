import java.io.File;
import java.io.FileNotFoundException;

public interface CSVReader {

    void printCSV() throws FileNotFoundException;
    void extractCSV() throws FileNotFoundException;
    void filterBy(String s) throws FileNotFoundException;
    void totalAmountPayed() throws FileNotFoundException;
    void totalDeposits() throws FileNotFoundException;
    void totalWithdrawals() throws FileNotFoundException;


}
