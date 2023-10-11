import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DescAmtReader implements CSVReader {

    File file;
    ArrayList<String[]> transactions = new ArrayList<>();
    Scanner sc;

    //this general reader will take in a csv of type
    //description, amount,

    public DescAmtReader(File file) throws FileNotFoundException {
        this.file = file;
        sc = new Scanner(file);
        sc.useDelimiter("\n");
        while (sc.hasNext()) {
            String line = sc.next();
            String[] transaction = line.split(",");
            transactions.add(transaction);
        }
    }

    @Override
    public void printCSV() {
        for(String[] transaction: transactions) {
            System.out.println(transaction.toString());
        }
    }

    @Override
    public ArrayList<String[]> filterBy(String filter) {
        ArrayList<String[]> filtered = new ArrayList<>();
        for(String[] transaction: transactions) {
            for(String s: transaction) {
                if(s.contains(filter)) {
                    filtered.add(transaction);
                }
            }
        }
        return filtered;
    }

    @Override
    public double totalAmountPayed() {
        double total = 0;
        for(String[] transaction: transactions) {
            total += Double.parseDouble(transaction[1]);
        }
        return total;
    }

    @Override
    public double totalAmountPayedBy(String filter) {
        double total = 0;
        for(String[] transaction: filterBy(filter)) {
            total += Double.parseDouble(transaction[1]);
        }
        return total;
    }

    @Override
    public double sumDeposits() {
        double total = 0;
        for(String[] transaction: transactions) {
            if(transaction[0].toLowerCase().equals("deposit")) {
                total += Double.parseDouble(transaction[1]);
            }
        }
        return total;
    }

    @Override
    public double sumWithdrawals() {
        double total = 0;
        for(String[] transaction: transactions) {
            if(transaction[0].toLowerCase().equals("withdrawal")) {
                total += Double.parseDouble(transaction[1]);
            }
        }
        return total;
    }

    @Override
    public double numOfPayments() {
        return transactions.size() - numOfDeposits() - numOfWithdrawals();
    }

    @Override
    public double numOfWithdrawals() {
        double total = 0;
        for(String[] transaction: transactions) {
            if(transaction[0].toLowerCase().equals("withdrawal")) {
                total += 1;
            }
        }
        return total;
    }

    @Override
    public double numOfDeposits() {
        double total = 0;
        for(String[] transaction: transactions) {
            if(transaction[0].toLowerCase().equals("deposit")) {
                total += 1;
            }
        }
        return total;
    }
}
