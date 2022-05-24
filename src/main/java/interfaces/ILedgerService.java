package interfaces;

import javax.xml.bind.ValidationException;

public interface ILedgerService {

    public void borrowLoan(String bankName, String borrowerName, Long amount, Long interestRate, Long time);

    void payment(String bankName, String borrowerName, Long amount, Long emiCount) throws ValidationException;

    String balance(String bankName, String borrowerName, Long emiCount) throws ValidationException;
}
