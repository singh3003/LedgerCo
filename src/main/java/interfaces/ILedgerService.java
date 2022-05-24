package interfaces;

public interface ILedgerService {
    void borrowLoan(String bankName, String borrowerName, Long amount, Long interestRate, Long time);

    void payment(String bankName, String borrowerName, Long amount, Long emiCount);

    String balance(String bankName, String borrowerName, Long emiCount);
}
