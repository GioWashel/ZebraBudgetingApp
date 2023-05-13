import java.util.ArrayList;

public class DescAmtReader implements CSVReader {

    //this general reader will take in a csv of type
    //description, amount

    @Override
    public void printCSV() {

    }
    @Override
    public ArrayList<String[]> extractCSV() {
        return null;
    }

    @Override
    public ArrayList<String[]> filterBy(String filter) {
        return null;
    }

    @Override
    public double totalAmountPayed() {
        return 0;
    }

    @Override
    public double totalAmountPayedBy(String filter) {
        return 0;
    }

    @Override
    public double sumDeposits() {
        return 0;
    }

    @Override
    public double sumWithdrawals() {
        return 0;
    }

    @Override
    public double numOfPayments() {
        return 0;
    }

    @Override
    public double numOfWithdrawals() {
        return 0;
    }

    @Override
    public double numOfDeposits() {
        return 0;
    }

    //this general reader will take in a csv of type
    //date, description, amount

}
