package game.model.character;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Observable;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import game.components.Dice;
import game.components.SharedVariables;
import game.model.character.classes.CharacterClass;
import game.model.character.classes.CharacterClassStructure;

/**
 * Build a new character
 * 
 * @author Supreet Singh (s_supree)
 *
 */
public class Character extends Observable
{

    private String characterClass;
    private String name;
    private int level;
    private int strength;
    private int dexterity;
    private int constitution;
    private boolean isBuilt = false;
    private int hitScore = 0;
    private String weaponName;
    private String fname;

    /**
     * @param String name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * retrieve character name
     *
     * @return character name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @param String characterClass
     */
    public void setCharacterClass(String characterClass)
    {
        this.characterClass = characterClass;
    }

    /**
     * set character level
     * 
     * @param int level
     */
    public Character setLevel(int level)
    {
        this.level = level;
        return this;
    }

    /**
     * retrieve character level
     */
    public int getLevel()
    {
        return this.level;
    }

    /**
     * @param int strength
     */
    public Character setStrength(int strength)
    {
        this.strength = strength;
        return this;
    }

    /**
     * retrieve character strength
     *
     * @return int strength
     */
    public int getStrength()
    {
        return strength;
    }

    /**
     * @param dexterity
     * @return
     */
    public Character setDexterity(int dexterity)
    {
        this.dexterity = dexterity;
        return this;
    }

    /**
     * @return dexterity
     */
    public int getDexterity()
    {
        return dexterity;
    }

    /**
     * @param int constitution
     */
    public Character setConstitution(int constitution)
    {
        this.constitution = constitution;
        return this;
    }

    /**
     * return constitution
     */
    public int getConstitution()
    {
        return constitution;
    }

    public String getCharacterClass()
    {
        return this.characterClass;
    }

    /**
     * sets the weapon and redraws the character
     *
     * @param String weapon
     */
    public void setWeaponName(String weaponName)
    {
        this.weaponName = weaponName;
    }

    /**
     * @return weapon
     */
    public String getWeaponName()
    {
        return this.weaponName;
    }

    /**
     * draws/redraws the character and notifies observers
     */
    public void draw()
    {
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Set file name
     * 
     * @param fname
     */
    private void setFileName(String fname)
    {
        this.fname = fname;
    }

    /**
     * @return file name or null if not present
     */
    private String getFileName()
    {
        return this.fname;
    }

    /**
     * builds and initializes the character this method is run just once in a
     * characters lifetime and calculates - hit score, based on chacaters class
     */
    public void build()
    {
        if (!this.isBuilt)
        {
            // Build characters class and get hit score
            CharacterClass cClass = new CharacterClass(this.getCharacterClass(), this);
            CharacterClassStructure cClassVal = cClass.get();

            cClass.calculateHitScore(
                    new Dice(cClassVal.getNumberOfRolls(), cClassVal.getDiceSides(), cClassVal.getNumberOfRolls()));
            this.hitScore = cClass.getHitScore();
            this.isBuilt = true;
        }
    }

    public void hit(int damage) throws Exception
    {
        this.hitScore -= damage;
    }

    /**
     * @return calculate hit score
     */
    public int getHitScore()
    {
        return this.hitScore;
    }

    /**
     * saves character state in the generated xml file
     * 
     * @return
     */
    public boolean save()
    {
        this.build();

        if (this.getFileName() == null)
        {
            Date d = new Date();
            long dTime = d.getTime();
            this.setFileName(this.name + dTime);
        }

        XStream xstream = new XStream(new StaxDriver());
        String xml = xstream.toXML(this);
        FileWriter out;
        try
        {
            String filepath = SharedVariables.CharactersDirectory + File.separator + this.getFileName() + ".xml";
            out = new FileWriter(filepath);
            out.write(xml);
            out.close();
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
        return true;
    }

    /**
     * deletes the characters xml file
     */
    public void delete()
    {
        if (this.getFileName() != null)
        {
            (new File(SharedVariables.CharactersDirectory + File.separator + this.fname + ".xml")).delete();
            CharactersList.init().updateCharactersList();
        }
    }
}
