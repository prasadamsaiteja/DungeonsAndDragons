package game.model.character.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import game.components.Dice;
import game.model.character.Character;

/**
 * Get character class - Allowed classes and hit score
 * @author Supreet Singh (s_supree)
 * @version 1.0.0
 */
public class CharacterClass
{

    /**
     * Class for Define allowed classes
     * @author Supreet Singh (s_supree)
     * @version 1.0.0
     */
    private static class DefinedClasses
    {

        /**
         * Method that returns the list the characters in form of hashmap
         * @return a hashmap with character class and corresponding structure
         */
        public static HashMap<String, CharacterClassStructure> getCharacterList()
        {

            HashMap<String, CharacterClassStructure> characterList = new HashMap<>();

            // Add character classes
            // add fighter class
            CharacterClassStructure fighterStructure = new CharacterClassStructure();
            fighterStructure.setDiceSides(10);
            fighterStructure.setNumberOfRolls(1);
            
            ArrayList<String> fighterTypes = new ArrayList<String>();
            fighterTypes.add("Bully");
            fighterTypes.add("Nimble");
            fighterTypes.add("Tank");
            
            fighterStructure.setTypes(fighterTypes);
            
            characterList.put("Fighter", fighterStructure);

            return characterList;
        }
    }

    private Character character;
    private int hitPoints = 0;
    private String name;
  
    // Store allowed character classes

    /**
     * Constructor that takes the name and character object for initialization
     * @param name allowed character class name
     * @param character Character object on which this class is applied
     * @throws Exception if an unallowed class is provided
     */
    public CharacterClass(String name, Character character) throws Exception
    {
        if (!CharacterClass.isClassAllowed(name))
        {
            throw new Exception("Class not allowed " + name);
        }

        this.name = name;
        this.character = character;
    }

    /**
     * This method checks whether class is allowed or not
     * @param cClass character class 
     * @return boolean true or false if the class exists
     */
    private static boolean isClassAllowed(String cClass)
    {
        return DefinedClasses.getCharacterList().containsKey(cClass);
    }

    /** 
     * Method for returning a set of allowed classes
     * @return String set of allowed classes
     */
    public static Set<String> getAllowedClasses()
    {
        return DefinedClasses.getCharacterList().keySet();
    }

    /**
     * Calculate the hit score
     * @param dice Dice class object to calculate hit score
     */
    public void calculateHitScore(Dice dice)
    {
        CharacterClassStructure cStructure = DefinedClasses.getCharacterList().get(this.name);

        hitPoints += this.character.getConstitutionModifier() * this.character.getLevel();

        if (this.character.getLevel() > 1)
        {
            int diceRoll = dice.getRollSum();
            hitPoints += diceRoll;
        }
        else
        {
            hitPoints += cStructure.getDiceSides();
        }
    }

    /** 
     * This method returns the hit score for character
     * @return hit points score
     */
    public int getHitScore()
    {
        return hitPoints;
    }

    /**
     * This method returns the charactrer class strucure object
     * @return character class structure of instantiated class
     */
    public CharacterClassStructure get()
    {
        return DefinedClasses.getCharacterList().get(this.name);
    }

}
