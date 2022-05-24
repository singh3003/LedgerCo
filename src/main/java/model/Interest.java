package model;

public class Interest {

    public Long calculateInterest(Long amount, Long rate, Long time){
        return  (amount*rate*time)/100;
    }
}
