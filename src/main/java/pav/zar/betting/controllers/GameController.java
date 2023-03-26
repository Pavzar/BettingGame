package pav.zar.betting.controllers;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import pav.zar.betting.models.Bet;
import pav.zar.betting.models.BetResult;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class GameController {


    @MessageMapping("/bet")
    @SendTo("/topic/betting")
    public BetResult getBet(@Valid Bet bet) {

        double winAmount = 0;
        boolean win = false;
        int generatedNumber = ThreadLocalRandom.current().nextInt(1, 101);

        if ((bet.getNumber() >= 1 && bet.getNumber() <= 100)
                && (bet.getBetAmount() >= 0.01 && bet.getBetAmount() <= Double.MAX_VALUE)) {
            win = bet.getNumber() > generatedNumber;
            if (win) {
                if(bet.getNumber() == 100){
                    BetResult betResult = new BetResult();
                    betResult.setGeneratedNumber(generatedNumber);
                    betResult.setWin(true);
                    betResult.setWinAmount(0);
                    return betResult;
                }
                winAmount = bet.getBetAmount() * (99.0 / (100 - bet.getNumber()));
            } else {
                winAmount = 0;
            }
        }

        double roundNumberToTwoDecimals = Math.round(winAmount * 100) / 100.0D;

        BetResult betResult = new BetResult();
        betResult.setGeneratedNumber(generatedNumber);
        betResult.setWin(win);
        betResult.setWinAmount(roundNumberToTwoDecimals);

        return betResult;
    }
}