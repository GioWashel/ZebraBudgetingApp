import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface CSVReader {

    //method will simply print all transactions
    void printCSV();

    //method will organize transactions into a list
    ArrayList<String[]> extractCSV() throws FileNotFoundException;

    //method which will return a list of transaction that include a specific string
    ArrayList<String[]> filterBy(String filter);

    //method which will return the total amount between all transactions
    double totalAmountPayed();
    //method which will filter products, then sum the amounts of those products

    double totalAmountPayedBy(String filter);

    //method which will sum all the deposits made
    double sumDeposits();

    //method which will sum all the withdrawals made
    double  sumWithdrawals();
    double numOfPayments();
    double numOfWithdrawals();
    double numOfDeposits();

}
