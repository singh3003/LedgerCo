import interfaces.ILedgerService;
import service.LedgerService;


import javax.xml.bind.ValidationException;
import java.io.*;
import java.util.*;

public class Driver {
    public static void main(String[] args) throws IOException, ValidationException {

        String filePath = args[0];
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        ILedgerService ledgerService = new LedgerService();

        while (scanner.hasNext()) {
            String string = scanner.nextLine();
            String[] arr = string.split(" ");
            switch (arr[0]) {
                case "LOAN":
                    ledgerService.borrowLoan(arr[1].trim(), arr[2].trim(), Long.valueOf(arr[3].trim()), Long.valueOf(arr[5].trim()), Long.valueOf(arr[4].trim()));
                    break;
                case "PAYMENT":
                    ledgerService.payment(arr[1].trim(), arr[2], Long.valueOf(arr[3].trim()), Long.valueOf(arr[4].trim()));
                    break;
                case "BALANCE":
                    System.out.println(ledgerService.balance(arr[1].trim(), arr[2].trim(), Long.valueOf(arr[3].trim())));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + arr[0]);
            }

        }
    }

}

