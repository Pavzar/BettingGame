package pav.zar.betting;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import pav.zar.betting.controllers.GameController;
import pav.zar.betting.models.Bet;
import pav.zar.betting.models.BetResult;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {

    @InjectMocks
    private GameController gameController;

    @Test
    public void testGetBetWin() {
        int number = 50;
        double betAmount = 40.5;
        Bet bet = new Bet(number, betAmount);
        BetResult result = gameController.getBet(bet);
        if (result.getGeneratedNumber() < number) {
            assertTrue(result.isWin());
            assertEquals(80.19, result.getWinAmount(), 0);
        } else if (result.getGeneratedNumber() > number) {
            assertEquals(0, result.getWinAmount(), 0);
        }
    }

    @Test
    public void testGetBetLose() {
        int number = 1;
        double betAmount = 100.0;
        Bet bet = new Bet(number, betAmount);
        BetResult result = gameController.getBet(bet);

        if (result.getGeneratedNumber() < number) {
            assertEquals(100, result.getWinAmount(), 0);
        } else if (result.getGeneratedNumber() > number) {
            assertFalse(result.isWin());
            assertEquals(0, result.getWinAmount(), 0);
        }

    }

    @Test
    public void testGetBetNumberNegative() {
        int number = -50;
        double betAmount = 40.5;
        Bet bet = new Bet(number, betAmount);
        BetResult result = gameController.getBet(bet);
        assertFalse(result.isWin());
    }

    @Test
    public void testGetBetAmountWinMaximum() {
        int number = 50;
        double betAmount = Double.MAX_VALUE;
        Bet bet = new Bet(number, betAmount);
        BetResult result = gameController.getBet(bet);

        if (result.getGeneratedNumber() < number) {
            assertEquals(9.223372036854776E16, result.getWinAmount(), 0);
        } else if (result.getGeneratedNumber() > number) {
            assertFalse(result.isWin());
            assertEquals(0, result.getWinAmount(), 0);
        }
    }

    @Test
    public void testGetBetAmountWinMinimum() {
        int number = 99;
        double betAmount = 0.01;
        Bet bet = new Bet(number, betAmount);
        BetResult result = gameController.getBet(bet);
        if (result.getGeneratedNumber() < number) {
            assertEquals(0.99, result.getWinAmount(), 0);
        } else if (result.getGeneratedNumber() > number) {
            assertFalse(result.isWin());
            assertEquals(0, result.getWinAmount(), 0);
        }
    }

    @Test
    public void testGetBetLoseMinimum() {
        int number = 1;
        double betAmount = 0.01;
        Bet bet = new Bet(number, betAmount);
        BetResult result = gameController.getBet(bet);

        if (result.getGeneratedNumber() < number) {
            assertEquals(0.01, result.getWinAmount(), 0);
        } else if (result.getGeneratedNumber() > number) {
            assertFalse(result.isWin());
            assertEquals(0, result.getWinAmount(), 0);
        }
    }

    @Test
    public void testGetBetAmountNegative() {
        int number = 50;
        double betAmount = -40.5;
        Bet bet = new Bet(number, betAmount);
        BetResult result = gameController.getBet(bet);
        assertFalse(result.isWin());
        assertEquals(0, result.getWinAmount(), 0.01);
    }

    @Test
    public void testGetBetNumberOutOfBound() {
        int number = 101;
        double betAmount = 50;
        Bet bet = new Bet(number, betAmount);
        BetResult result = gameController.getBet(bet);
        assertFalse(result.isWin());
        assertEquals(0, result.getWinAmount(), 0.01);
    }

    @Test
    public void testGetBetAmountOutOfBound() {
        int number = 70;
        double betAmount = Double.MAX_VALUE * 2;
        Bet bet = new Bet(number, betAmount);
        BetResult result = gameController.getBet(bet);
        assertFalse(result.isWin());
        assertEquals(0, result.getWinAmount(), 0.01);
    }


}
