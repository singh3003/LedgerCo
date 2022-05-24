package service;

import interfaces.ILedgerService;
import model.Interest;
import model.Loan;

import javax.xml.bind.ValidationException;
import java.util.HashMap;
import java.util.Map;

public class LedgerService implements ILedgerService {

    Map<String, Loan> ledgerMap;

    public LedgerService() {
        this.ledgerMap = new HashMap<>();
    }

    @Override
    public void borrowLoan(String bankName, String borrowerName, Long amount, Long interestRate, Long time) {
        String key = bankName.concat("_").concat(borrowerName);
        if(!ledgerMap.containsKey(key)) {
            Interest interest = new Interest();
            Long val = interest.getInterest(amount, interestRate, time);
            Long totalAmount = amount+val;
            long emiAmount = (long) Math.ceil((double)((totalAmount)/time)/12);
            Long emiDays = (long) Math.ceil((double) totalAmount/emiAmount);
            ledgerMap.put(key, new Loan(emiDays, totalAmount, emiAmount));
        }
    }


    @Override
    public void payment(String bankName, String borrowerName, Long amount, Long emiCount) throws ValidationException {
        String key = bankName.concat("_").concat(borrowerName);
        if(!ledgerMap.containsKey(key)) {
            throw new ValidationException("Invalid details");
        }
        Loan loan = ledgerMap.get(key);
        Map<Long, Long> paymentMap = loan.getPaymentMade();
        paymentMap.put(emiCount, amount);

    }

    @Override
    public String balance(String bankName, String borrowerName, Long emiCount) throws ValidationException {
        String key = bankName.concat("_").concat(borrowerName);
        if(!ledgerMap.containsKey(key)) {
            throw new ValidationException("Invalid details");
        }
        Loan loan = ledgerMap.get(key);
        Long loanAmount = loan.getTotalAmount();
        Long emiAmount = loan.getEmiAmount();
        Long totalAmount = emiAmount * emiCount;
        Long emiLeft = (long)Math.ceil((double) (loanAmount-totalAmount)/emiAmount);

        if (!loan.getPaymentMade().isEmpty()) {
            Map<Long, Long> paymentMap = loan.getPaymentMade();
            Map.Entry<Long, Long> actualValue = loan.getPaymentMade().entrySet().stream().findFirst().get();
        if (emiCount >= actualValue.getKey()) {
            totalAmount =0L;
            for (int i = 1;i<=emiCount && emiLeft>=emiCount;i++) {
                if((loanAmount-totalAmount) >=0) {
                    if (paymentMap.containsKey((long) i)) {
                        totalAmount += emiAmount;
                        totalAmount += paymentMap.get((long) i);
                    } else {
                        totalAmount += emiAmount;
                    }
                    emiLeft = (long)Math.ceil((double) (loanAmount-totalAmount)/emiAmount);

                }
            }
        }
    }
        return bankName.concat(" ").concat(borrowerName).concat(" ").concat(String.valueOf(totalAmount)).concat(" ").concat(String.valueOf(emiLeft));
    }


}
