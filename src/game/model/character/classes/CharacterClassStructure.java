package game.model.character.classes;

import java.util.ArrayList;

import game.interfaces.DiceImplementationInterface;

/**
 * Custom data structure for storing character classes
 * @author Supreet Singh (s_supree)
 * @version 1.0.0
 */
public class CharacterClassStructure implements DiceImplementationInterface
{
    private int numberOfRolls;
    private int diceSides;
    private ArrayList<String> types;

    /**
     * Set number of dice rolls
     * @param nOfRolls set number of dice rolls
     */
    public void setNumberOfRolls(int nOfRolls)
    {
        this.numberOfRolls = nOfRolls;
    }

    /**
     * Set number of dice sides
     * @param sideDices set dice sides
     */
    public void setDiceSides(int sideDices)
    {
        this.diceSides = sideDices;
    }
    
    /**
     * Set allowed character types
     * @param types ArrayList of types of the character
     */
    public void setTypes(ArrayList<String> types){
        this.types = types;
    }
    
    /**
     * Get allowed character types
     * @return arrayList allowed character types
     */
    public ArrayList<String> getTypes(){
        return this.types;
    }

    /**
     * This method returns the number of rolls of a Dice
     * @return int number of dice rolls
     */
    public int getNumberOfRolls()
    {
        return this.numberOfRolls;
    }

    /**
     * This method returns the total Sides for a Dice
     * @return int number of dice sides
     */
    public int getDiceSides()
    {
        return this.diceSides;
    }
    
}