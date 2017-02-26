package game.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

/**
 * Rolls the dice and calculates the sum Created by supreet university on
 * 2017-02-02.
 */

public class Dice
{

    private int numberOfRolls;
    private int diceSides;
    private int maximumTurnsCount;

    /**
     * Set the number of turns a dice has to make, size of the dice and maximum
     * turns to be counted i.e. in 4d6 with 3 maximum turns the highest three
     * would be counted
     *
     * @param numberOfRolls
     * @param diceSides
     * @param maximumTurnsCount
     */
    public Dice(int numberOfRolls, int diceSides, int maximumTurnsCount)
    {
        this.numberOfRolls = numberOfRolls;
        this.diceSides = diceSides;
        this.maximumTurnsCount = maximumTurnsCount;
    }

    /*
     * roll the dice
     * 
     * @return
     */
    private int roll()
    {
        Random r = new Random();
        int rolledNum = r.nextInt(this.diceSides) + 1;
        return rolledNum;
    }

    /**
     * Rolls the dice and finds the sum of the highest n rolls where n is the
     * provided maximumTurnsCount
     * 
     * @return
     */
    public int getRollSum()
    {
        int sumOfRoll = 0;

        ArrayList<Integer> rolledNumbers = new ArrayList<>();

        // Let's roll the numbers
        int n_o_t = this.numberOfRolls;
        while (n_o_t > 0)
        {
            int randomNum = this.roll();
            rolledNumbers.add(randomNum);
            n_o_t--;
        }

        // Sort numbers in a descending order
        Collections.sort(rolledNumbers, Collections.reverseOrder());

        // Let's iterate through the sorted rolled number list to find
        // the sum of rolled numbers according to maximum numbers allowed
        Iterator<Integer> rolledNumberIterator = rolledNumbers.iterator();
        int m_t_c = this.maximumTurnsCount;
        while (rolledNumberIterator.hasNext() && m_t_c > 0)
        {
            sumOfRoll += rolledNumberIterator.next();
            m_t_c--;
        }

        return sumOfRoll;
    }
}
