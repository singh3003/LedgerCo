package model;

import java.util.Map;
import java.util.TreeMap;

public class Loan {

    Long emiDays;
    Long totalAmount;
    Long emiAmount;

    Map<Long, Long> paymentMade;

    public Loan(Long emiDays, Long totalAmount, Long emiAmount) {
        this.emiDays = emiDays;
        this.totalAmount = totalAmount;
        this.emiAmount = emiAmount;
        this.paymentMade = new TreeMap<>();
    }


    public Long getEmiDays() {
        return emiDays;
    }

    public void setEmiDays(Long emiDays) {
        this.emiDays = emiDays;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getEmiAmount() {
        return emiAmount;
    }

    public void setEmiAmount(Long emiAmount) {
        this.emiAmount = emiAmount;
    }

    public Map<Long, Long> getPaymentMade() {
        return paymentMade;
    }

    public void setPaymentMade(Map<Long, Long> paymentMade) {
        this.paymentMade = paymentMade;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "emiDays=" + emiDays +
                ", totalAmount=" + totalAmount +
                ", emiAmount=" + emiAmount +
                ", paymentMade=" + paymentMade +
                '}';
    }
}
