import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DateDescAmtReader {


    //this general reader will take in a csv of type
    //date, description, amount

    private Scanner sc;
    private File file;
    private ArrayList<String[]> transactions = new ArrayList<>();

    public DateDescAmtReader(File file) throws FileNotFoundException {
        this.file = file;
        sc = new Scanner(file);
        sc.useDelimiter("\n");
        while(sc.hasNext()) {
            String line = sc.next();
            String[] transaction = line.split(",");
            transactions.add(transaction);
        }

    }
    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }
    public ArrayList<String[]> getTransactions() {
        return transactions;
    }
    public void setTransactions(ArrayList<String[]> transactions) {
        this.transactions = transactions;
    }
    public void printCSV() throws FileNotFoundException {
        System.out.println(transactions.toString());
    }

    public ArrayList<String[]> extractCSV() throws FileNotFoundException {
        return transactions;
    }

    public ArrayList<String[]> filterBy(String s)  {
            ArrayList<String[]> filtered = new ArrayList<>();
            for(String[] transaction: transactions) {
                if(transaction[0].equals(s) || transaction[1].equals(s) || transaction[2].equals(s)) {
                    filtered.add(transaction);
                }
            }
            return filtered;
    }

    public double totalAmountPayed()  {
        double totalAmount = 0;
        for(String[] transaction: transactions) {
            totalAmount += Double.parseDouble(transaction[2]);
        }
        return totalAmount;
    }
    public double totalAmountPayedBy(String filter) {
        double amount = 0;
        for(String[] transaction: filterBy(filter)) {
            amount += Double.parseDouble(transaction[2]);
        }
        return amount;
    }
    public double numOfPayments() {
        return 0;
    }
    public double totalDeposits() {
            ArrayList<String[]> lowerCaseDeposits = filterBy("deposit");
            ArrayList<String[]> upperCaseDeposits = filterBy("Deposit");
            return lowerCaseDeposits.size() + upperCaseDeposits.size();

    }
    public double totalWithdrawals() throws FileNotFoundException {
        ArrayList<String[]> lowerCaseWithdrawals= filterBy("withdrawal");
        ArrayList<String[]> upperCaseWithdrawals= filterBy("Withdrawal");
        return lowerCaseWithdrawals.size() + upperCaseWithdrawals.size();
    }

}

