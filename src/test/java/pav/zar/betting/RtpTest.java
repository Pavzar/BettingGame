package pav.zar.betting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pav.zar.betting.controllers.GameController;
import pav.zar.betting.models.Bet;
import pav.zar.betting.models.BetResult;

import java.util.concurrent.*;
import java.util.concurrent.atomic.DoubleAdder;


@SpringBootTest
public class RtpTest {

    @Autowired
    private GameController gameController;

    @Test
    void testRTP() throws InterruptedException {
        int numThreads = 24;
        int numRounds = 1_000_000;
        DoubleAdder totalAmountWon = new DoubleAdder();

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < numRounds; i++) {
            executor.submit(() -> {
                int playerNumber = ThreadLocalRandom.current().nextInt(1, 101);
                double betAmount = 1;

                Bet bet = new Bet(playerNumber, betAmount);
                BetResult betResult = gameController.getBet(bet);
                if (betResult.isWin()) {
                    totalAmountWon.add(betResult.getWinAmount());
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);

        double rtp = totalAmountWon.sum() / (double) numRounds * 100;
        System.out.printf("Total Amount Spent: %f%n", (double) numRounds);
        System.out.printf("Total Amount Won: %f%n", totalAmountWon.sum());
        System.out.printf("RTP: %.2f%%%n", rtp);
    }
}
