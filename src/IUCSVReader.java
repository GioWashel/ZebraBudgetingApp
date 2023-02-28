import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class IUCSVReader {


    //list of void methods for printing the csv

    private static File file;
    private static Scanner sc;
    public IUCSVReader(File file) throws FileNotFoundException {
        this.file = file;
        this.sc = new Scanner(file);
        sc.useDelimiter("\n");
    }

    //prints the statement line by line
    public void printCSV() throws FileNotFoundException {
        sc = new Scanner(file);
        sc.useDelimiter("\n");
        while(sc.hasNext()) {
            System.out.println(sc.next());
        }
    }
    //filters the statement by a specific string contained in the line
    public void filterBy(String s) throws FileNotFoundException {
        sc = new Scanner(file);
        int numFiltered = 0;
        sc.useDelimiter("\n");
        while(sc.hasNext()) {
            String part = sc.next();
            if(part.contains(s)) {
                numFiltered++;
                System.out.println(part);
            }
        }
        System.out.printf("Total of %d instances of %s\n", numFiltered, s);
    }
    //prints the total amount payed in bank statement (not including withdrawals)
    public void totalAmountPayed() throws FileNotFoundException {
        sc = new Scanner(file);
        sc.useDelimiter("\n");
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
    public void extractCSV() throws FileNotFoundException {
        sc = new Scanner(file);
        sc.useDelimiter("\n");
        while(sc.hasNext()) {
            String transaction = sc.next();
            String[] transactionParts = transaction.split(",");
            //if the line is a transaction, parse it
            // ie, not first 4 lines of csv
            if(transactionParts[0].startsWith("\"A") || transactionParts[0].startsWith("\"D") || transactionParts[0].startsWith("T")) {
                continue;
            }
            String date = transactionParts[1];
            String type = transactionParts[2];
            String place = transactionParts[3];
            String amountDebit = transactionParts[4];
            String amountCredit = transactionParts[5];
            double amount;
            //check to see if amount is debit or credit
            //if debit amount is empty or not number, set it equal to credit amount
            //if credit is empty or not number, then set it equal to transactionParts[6];
            try {
                amount = Double.parseDouble(amountDebit);
            }
            catch(NumberFormatException e) {
                try {
                    amount = Double.parseDouble(amountCredit);
                }
                catch(NumberFormatException f) {
                    amount = Double.parseDouble(transactionParts[6]);
                }
            }
            //if withdrawal, check if it's a payment or actual withdrawal
            if(type.startsWith("\"W")) {
                //if withdrawal contains XCTR, then it's a withdrawal
                if(type.contains("XCTR")) {
                    System.out.printf("Withdrawal %s %f\n", date, amount);
                }
                else {
                    System.out.printf("Payment %s %s %f\n", date, place, amount);
                }
            }
            //if transaction is a Deposit, print the amount
            else if(type.startsWith("\"D")) {
                System.out.printf("Deposit %s, %f\n", date, amount);
            }
        }
    }
    public void totalDeposits() throws FileNotFoundException {
        sc = new Scanner(file);
        sc.useDelimiter("\n");
        double total = 0;
        int numOfDeposits = 0;
        while(sc.hasNext()) {
            String transaction = sc.next();
            String[] transactionParts = transaction.split(",");
            if(transactionParts[0].startsWith("\"A") || transactionParts[0].startsWith("\"D") || transactionParts[0].startsWith("T")) {
                continue;
            }
            //if not a deposit, then continue to next line
            String type = transactionParts[2];
            if(!type.contains("Deposit")) continue;
            numOfDeposits++;
            String date = transactionParts[1];
            double amount;
            try {
                amount = Double.parseDouble(transactionParts[4]);
            }
            catch(NumberFormatException e) {
                try {
                    amount = Double.parseDouble(transactionParts[5]);
                }
                catch(NumberFormatException f) {
                    amount = Double.parseDouble(transactionParts[6]);
                }
            }
            total+=amount;
            System.out.printf("Deposit %s %.2f\n", date, amount);
        }
        System.out.printf("Total of %d deposits totalling to: $%.2f\n", numOfDeposits, total);
    }
}
