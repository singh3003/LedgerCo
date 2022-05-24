package model;

public class Interest {

    public Long getInterest(Long amount, Long rate, Long time){
        return  (amount*rate*time)/100;
    }
}
