package service;

import interfaces.ILedgerService;
import model.Interest;
import model.Loan;

import java.util.HashMap;
import java.util.Map;

public class LedgerService implements ILedgerService {

    Map<String, Loan> loanMap;
    private final String TOTAL_MONTHS_IN_YEAR = String.valueOf(12);

    public LedgerService() {
        this.loanMap = new HashMap<>();
    }

    @Override
    public void borrowLoan(String bankName, String borrowerName, Long amount, Long interestRate, Long time) {
        String key = bankName.concat("_").concat(borrowerName);
        if (!loanMap.containsKey(key)) {
            Long totalAmount = amount + new Interest().calculateInterest(amount, interestRate, time);
            long emiAmount = (long) Math.ceil((double) (totalAmount / time) / Long.parseLong(TOTAL_MONTHS_IN_YEAR));
            Long emiDays = (long) Math.ceil((double) totalAmount / emiAmount);
            loanMap.put(key, new Loan(emiDays, totalAmount, emiAmount));
        }
    }

    @Override
    public void payment(String bankName, String borrowerName, Long amount, Long emiCount) {
        String key = bankName.concat("_").concat(borrowerName);
        Loan loan = loanMap.get(key);
        Map<Long, Long> paymentMap = loan.getPaymentMade();
        paymentMap.put(emiCount, amount);

    }

    @Override
    public String balance(String bankName, String borrowerName, Long emiCount) {
        String key = bankName.concat("_").concat(borrowerName);
        Loan loan = getLoanDetails(key);
        Long loanAmount = loan.getTotalAmount();
        Long emiAmount = loan.getEmiAmount();
        Long totalAmount = emiAmount * emiCount;
        Long emiLeft = (long) Math.ceil((double) (loanAmount - totalAmount) / emiAmount);
        if (!loan.getPaymentMade().isEmpty()) {
            Map<Long, Long> paymentMap = loan.getPaymentMade();
            Map.Entry<Long, Long> actualValue = loan.getPaymentMade().entrySet().stream().findFirst().get();
            if (emiCount >= actualValue.getKey()) {
                totalAmount = 0L;
                for (int i = 0; i <= emiCount && (emiLeft + emiCount) >= emiCount; i++) {
                    if ((loanAmount - totalAmount) >= 0) {
                        if (i == 0 && paymentMap.containsKey((long) 0)) {
                            totalAmount += paymentMap.get((long) i);
                            emiLeft = (long) Math.ceil((double) (loanAmount - totalAmount) / emiAmount);
                        }
                        if(i!=0) {
                            if (paymentMap.containsKey((long) i)) {
                                totalAmount += emiAmount;
                                totalAmount += paymentMap.get((long) i);
                            } else {
                                totalAmount += emiAmount;
                            }
                            emiLeft = (long) Math.ceil((double) (loanAmount - totalAmount) / emiAmount);

                        }
                    }
                }
            }
        }

        return bankName.concat(" ").concat(borrowerName).concat(" ").concat(String.valueOf(totalAmount > loanAmount ? loanAmount: totalAmount)).concat(" ").concat(String.valueOf(emiLeft));
    }

    public Loan getLoanDetails(String key){
        return loanMap.get(key);
    }


}
