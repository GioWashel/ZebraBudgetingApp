import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class IUCSVReader implements CSVReader{

    //will handle a csv that is structured like
    //Transaction Number,Date,Description,Memo,Amount Debit,Amount Credit,Balance,Check Number,Fees
    private static File file;
    private static Scanner sc;
    private ArrayList<String[]> transactions = new ArrayList<>();
    public IUCSVReader(File file) throws FileNotFoundException {
        this.file = file;
        this.sc = new Scanner(file);
        sc.useDelimiter("\n");
        //save all the lines of csv within transactions
        while(sc.hasNext()) {
            String line = sc.next();
            String[] transaction = line.split(",");
            transactions.add(transaction);
        }
    }
    //prints the statement line by line
    @Override
    public void printCSV() {
        System.out.println(transactions.toString());
    }

    @Override
    public ArrayList<String[]> filterBy(String s) {
        ArrayList<String[]> filtered = new ArrayList<>();
        for(String[] transaction: transactions) {
            if(transaction[0].equals(s) || transaction[1].equals(s) || transaction[2].equals(s) || transaction[3].equals(s) || transaction[4].equals(s) || transaction[5].equals(s) || transaction[6].equals(s)){
                filtered.add(transaction);
            }
        }
        return filtered;
    }
    //prints the total amount paid in bank statement (not including withdrawals)

    @Override
    public double totalAmountPayed() {
        ArrayList<String> listOfPayments = new ArrayList<>();
        double total = 0;
        while(sc.hasNext()) {
            String part = sc.next();
            if(part.contains("Withdrawal")) {
                String[] listedLine = part.split(",");
                listOfPayments.add(listedLine[4]);
            }
        }
        for(String payment: listOfPayments) {
            try {
                total = total + Double.parseDouble(payment);
            }
            catch (NumberFormatException nfe) {
            }
        }
        total = total * -1;
        System.out.println(total);
        return 0;
    }

    @Override
    public double totalAmountPayedBy(String filter) {
        double amount = 0;
        for(String[] transaction: filterBy(filter)) {
            String amountDebit = transaction[4];
            String amountCredit = transaction[5];
            String balance = transaction[6];
            try {
                amount += Double.parseDouble(amountDebit);
            }
            catch(NumberFormatException e) {
                try {
                    amount += Double.parseDouble(amountCredit);
                }
                catch(NumberFormatException f) {
                    amount +=Double.parseDouble(balance);
                }
            }
        }
        return amount;
    }

    //formats csv properly for the app
    //removes garbage values and makes the statement readable
    // case 1: payment to a company
    //      ie "20230207000000[-5:EST]*-18.86*13**Withdrawal MCDONALD'S F289 2300",02/07/2023,"Withdrawal MCDONALD'S F289 2300","N WALNUT ST BLOOMINGTON IN %% Card 01 #9343 #000072327500",-18.86,,"163.81",,
    //      becomes 02/7/2023 MCDONALD'S $18.86
    // case 2: deposit
    //      ie "20230216000000[-5:EST]*35.38*515**Deposit DAILYPAY",02/16/2023,"Deposit DAILYPAY","TYPE: KROGER ID: 1475035714 CO: DAILYPAY %% ACH ECC PPD",,35.38,"46.88",,
    //      becomes 02/16/2023 Deposit $35.38
    // case 3: withdrawal
    //      ie "20230209000000[-5:EST]*-100.00*0**Withdrawal %%XCTR",02/09/2023,"Withdrawal %%XCTR","20222910000013991 WASHEL,GIOVANNI %% TCode01 CD 100.00",-100.00,,"92.05",,
    //      becomes 02/09/2023 Withdrawal $100

    @Override
    public ArrayList<String[]> extractCSV() throws FileNotFoundException {
        ArrayList<String[]> extracted = new ArrayList<>();
        Categorizer categorizer = new Categorizer();
        // find amount -> find type -> extractCategory -> return extracted amounts.
        for(String[] transaction: transactions) {
            String date = transaction[1];
            String type = transaction[2];
            String place = transaction[3];
            String amountDebit = transaction[4];
            String amountCredit = transaction[5];
            String balance = transaction[6];
            double amount;

            //check to see if transaction was made with a debit or credit card
            //if debit amount is empty, then a credit card was used
            //else if credit is empty or not number, then set it equal to transactionParts[6] (balance);
            try {
                amount = Double.parseDouble(amountDebit);
            }
            catch(NumberFormatException e) {
                try {
                    amount = Double.parseDouble(amountCredit);
                }
                catch(NumberFormatException f) {
                    amount = Double.parseDouble(balance);
                }
            }
            //now lets check if the transaction was a deposit, withdrawal, or a payment
            if(type.startsWith("\"D")) {
                System.out.printf("Deposit %s, %f\n", date, amount);
            }
            //if withdrawal, check if it's a payment or actual withdrawal
            else if(type.startsWith("\"W") && type.contains("XCTR")) {
                //if withdrawal contains XCTR, then it's a withdrawal
                System.out.printf("Withdrawal %s %f\n", date, amount);
            }
            else if(type.startsWith("\"W")) {
                System.out.printf("Payment %s %s %f\n", date, place, amount);
            }
            String[] extractedTransaction = new String[3];
            extractedTransaction[0] = date;
            extractedTransaction[2] = String.valueOf(amount);
            if(categorizer.getCategory(type).length != 0) {
                extractedTransaction[1] = type;
            }
            else if(categorizer.getCategory(place).length != 0) {
                extractedTransaction[1] = place;
            }
            else {
                extractedTransaction[1] = "Unknown";
                //make it where the user can see the original statement so they can decide for themselves what category
            }
        }
        return extracted;
    }
    @Override
    public double sumDeposits() {
        double amount = 0;
        for(String[] transaction: transactions) {
            String type = transaction[2];
            String amountDebit = transaction[4];
            String amountCredit = transaction[5];
            String balance = transaction[6];
            //if not a deposit, then continue to next line
            if (type.startsWith("D")) {
                try {
                    amount += Double.parseDouble(amountDebit);
                } catch (NumberFormatException e) {
                    try {
                        amount += Double.parseDouble(amountCredit);
                    } catch (NumberFormatException f) {
                        amount += Double.parseDouble(balance);
                    }
                }
            }
        }
        return amount;
    }
    @Override
    public double sumWithdrawals() {
        double amount = 0;
        for(String[] transaction: transactions) {
            String type = transaction[2];
            String amountDebit = transaction[4];
            String amountCredit = transaction[5];
            String balance = transaction[6];
            //if not a deposit, then continue to next line
            if (type.startsWith("W")) {
                try {
                    amount += Double.parseDouble(amountDebit);
                } catch (NumberFormatException e) {
                    try {
                        amount += Double.parseDouble(amountCredit);
                    } catch (NumberFormatException f) {
                        amount += Double.parseDouble(balance);
                    }
                }
            }
        }
        return amount;
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
}
