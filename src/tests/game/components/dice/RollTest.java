package tests.game.components.dice;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import game.components.Dice;

/**
 * Test the Roll Action
 * @author supreet (s_supree)
 * @version 1.0.0
 */
public class RollTest
{
    int diceSides;
    int numberOfRolls;
    int maximumTurnsCount;

    /**
     * Set the value of dice sides, number of rolls and maximum turns count
     */
    @Before
    public void setVals()
    {
        diceSides = 6;
        numberOfRolls = 4;
        maximumTurnsCount = 3;
    }

    /**
     * Roll sum validates the sum of dice rolls based on dice sides, number of
     * rolls and maximum turns count
     */
    @Test
    public void rollSumValidation()
    {
        Dice dice = new Dice(numberOfRolls, diceSides, maximumTurnsCount);
        int sum;

        // re-running multiple times to ensure the validity
        for (int i = 0; i < 10; i++)
        {
            sum = dice.getRollSum();
            assertTrue(sum > 0);
            assertTrue(sum < 18);
        }
    }

}
