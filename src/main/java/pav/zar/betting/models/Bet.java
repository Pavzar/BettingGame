package pav.zar.betting.models;

import javax.validation.constraints.*;

public class Bet {

    @Min(value = 1, message = "Number must be between 1 and 100")
    @Max(value = 100, message = "Number must be between 1 and 100")
    private int number;

    @DecimalMin(value = "0.01", message = "Bet amount must be greater than 0")
    @DecimalMax(value = Double.MAX_VALUE - 1 + "", message = "Bet amount must be lower than infinity" )
    private double betAmount;

    public Bet(int number, double betAmount) {
        this.number = number;
        this.betAmount = betAmount;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }
}
