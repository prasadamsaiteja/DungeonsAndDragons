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
import game.model.Item;
import game.model.character.classes.CharacterClass;
import game.model.character.classes.CharacterClassStructure;
import game.model.jaxb.ItemJaxb;

/**
 * Build a new character
 * 
 * @author Supreet Singh (s_supree)
 *
 */
public class Character extends Observable implements Cloneable
{

    private String characterClass;
    private String name;
    private int level = 1;
    private int strength;
    private int dexterity;
    private int constitution;
    private boolean isBuilt = false;
    private int hitScore = 0;
    private String weaponName;
    private String beltName;
    private String ringName;
    private String shield;
    private String boots;
    private String armor;
    private String helmet;
    private String fname;
    private Boolean isFriendlyMonster = true;
    private Boolean isPlayer = false;

    /**
     * @param name set character name
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
     * @param characterClass set character class
     */
    public void setCharacterClass(String characterClass)
    {
        this.characterClass = characterClass;
        this.draw();
    }

    /**
     * set character level
     * 
     * @param level set character level
     */
    public void setLevel(int level)
    {
        this.level = level;
        this.draw();
    }

    /**
     * @return character level
     */
    public int getLevel()
    {
        return this.level;
    }

    /**
     * @param strength set character strength
     * @return character object
     */
    public Character setStrength(int strength)
    {
        this.strength = strength;
        return this;
    }

    /**
     * @return original strength
     */
    public int getOriginalStrength()
    {
        return strength;
    }

    /**
     * @return character strength
     */
    public int getStrength()
    {
        int strength = 0;

        try
        {
            Item ringObject = this.getRingObject();
            if (ringObject.itemClass.equalsIgnoreCase("Strength"))
            {
                strength += ringObject.getModifier();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        try
        {
            Item beltObject = this.getBeltObject();
            if (beltObject.itemClass.equalsIgnoreCase("Strength"))
            {
                strength += beltObject.getModifier();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return strength + getOriginalStrength();
    }

    /**
     * @param dexterity set character dexterity
     * @return character object
     */
    public Character setDexterity(int dexterity)
    {
        this.dexterity = dexterity;
        return this;
    }

    /**
     * @return original dexterity
     */
    public int getOriginalDexterity()
    {
        return dexterity;
    }

    /**
     * @return character dexterity
     */
    public int getDexterity()
    {
        int dexterity = 0;

        try
        {
            Item bootsObject = this.getBootsObject();
            if (bootsObject.itemClass.equalsIgnoreCase("Dexterity"))
            {
                dexterity += bootsObject.getModifier();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return dexterity + getOriginalDexterity();
    }

    /**
     * @param constitution set character constitution
     * @return character object
     */
    public Character setConstitution(int constitution)
    {
        this.constitution = constitution;
        return this;
    }

    /**
     * @return original constitution
     */
    public int getOriginalConstitution()
    {
        return constitution;
    }

    /**
     * @return constitution
     */
    public int getConstitution()
    {
        int constitution = 0;

        try
        {
            Item ringObject = this.getRingObject();
            if (ringObject.itemClass.equalsIgnoreCase("Constitution"))
            {
                constitution += ringObject.getModifier();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        try
        {
            Item beltObject = this.getBeltObject();
            if (beltObject.itemClass.equalsIgnoreCase("Constitution"))
            {
                constitution += beltObject.getModifier();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return constitution + getOriginalConstitution();
    }

    public String getCharacterClass()
    {
        return this.characterClass;
    }

    /**
     * sets the weapon and redraws the character
     *
     * @param weaponName set character weapon
     */
    public void setWeaponName(String weaponName)
    {
        this.weaponName = weaponName;
        this.draw();
    }

    /**
     * @return weapon
     */
    public String getWeaponName()
    {
        return this.weaponName;
    }

    /**
     * @return the beltName
     */
    public String getBeltName()
    {
        return beltName;
    }

    /**
     * @param beltName the beltName to set
     */
    public void setBeltName(String beltName)
    {
        this.beltName = beltName;
        this.draw();
    }

    /**
     * @return attack bonus
     */
    public int getAttackBonus()
    {
        int attackBonus = 0;

        try
        {
            Item weaponObj = this.getWeaponObject();
            if (weaponObj.itemClass.equalsIgnoreCase("Ranged"))
            {
                // strength modifier + weapon modier and level
                attackBonus += this.getStrength() + weaponObj.getModifier();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return attackBonus;
    }

    /**
     * @return damage bonus
     */
    public int getDamageBonus()
    {
        int damageBonus = 0;

        try
        {
            Item weaponObj = this.getWeaponObject();
            if (weaponObj != null && weaponObj.itemClass.equalsIgnoreCase("Melee"))
            {
                System.out.print(weaponObj.getModifier() + "\n");
                damageBonus += this.getDexterity() + weaponObj.getModifier();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return damageBonus;
    }

    /**
     * @return the armor class
     */
    public int getArmorClass()
    {
        int armorClass = 0;

        try
        {
            Item ringObject = this.getRingObject();
            if (ringObject.itemClass.equalsIgnoreCase("ArmorClass"))
            {
                armorClass += ringObject.getModifier();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        try
        {
            Item armorObject = this.getArmorObject();
            armorClass += armorObject.getModifier();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        try
        {
            Item shieldObject = this.getShieldObject();
            armorClass += shieldObject.getModifier();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        try
        {
            Item helmetObject = this.getHelmetObject();
            armorClass += helmetObject.getModifier();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        try
        {
            Item bootsObject = this.getBootsObject();
            if (bootsObject.itemClass.equalsIgnoreCase("ArmorClass"))
            {
                armorClass += bootsObject.getModifier();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return armorClass;
    }

    /**
     * @return the ringName
     */
    public String getRingName()
    {
        return ringName;
    }

    /**
     * @param ringName the ringName to set
     */
    public void setRingName(String ringName)
    {
        this.ringName = ringName;
        this.draw();
    }

    /**
     * @return the shield
     */
    public String getShield()
    {
        return shield;
    }

    /**
     * @param shield the shield to set
     */
    public void setShield(String shield)
    {
        this.shield = shield;
        this.draw();
    }

    /**
     * @return the boots
     */
    public String getBoots()
    {
        return boots;
    }

    /**
     * @param boots the boots to set
     */
    public void setBoots(String boots)
    {
        this.boots = boots;
        this.draw();
    }

    /**
     * @return the armor
     */
    public String getArmor()
    {
        return armor;
    }

    /**
     * @param armor the armor to set
     */
    public void setArmor(String armor)
    {
        this.armor = armor;
        this.draw();
    }

    /**
     * @return the helmet
     */
    public String getHelmet()
    {
        return helmet;
    }

    /**
     * @param helmet the helmet to set
     */
    public void setHelmet(String helmet)
    {
        this.helmet = helmet;
        this.draw();
    }

    /**
     * @return weapon item object
     * @throws Exception weapon item does not exist
     */
    public Item getWeaponObject() throws Exception
    {
        Item i = ItemJaxb.getItemFromXml(this.getWeaponName());
        if (i == null)
            throw new Exception(this.getWeaponName() + " not found");

        i.setCharacter(this);
        return i;
    }

    /**
     * @return shield item object
     * @throws Exception shield item does not exist
     */
    public Item getShieldObject() throws Exception
    {
        Item i = ItemJaxb.getItemFromXml(this.getShield());
        if (i == null)
            throw new Exception(this.getShield() + " not found");

        i.setCharacter(this);

        return i;
    }

    /**
     * @return ring item object
     * @throws Exception ring item does not exist
     */
    public Item getRingObject() throws Exception
    {
        Item i = ItemJaxb.getItemFromXml(this.getRingName());
        if (i == null)
            throw new Exception(this.getRingName() + " not found");

        i.setCharacter(this);

        return i;
    }

    /**
     * @return helmet item object
     * @throws Exception helmet item does not exist
     */
    public Item getHelmetObject() throws Exception
    {
        Item i = ItemJaxb.getItemFromXml(this.getHelmet());
        if (i == null)
            throw new Exception(this.getHelmet() + " not found");

        i.setCharacter(this);

        return i;
    }

    /**
     * @return armor item object
     * @throws Exception armor item does not exist
     */
    public Item getArmorObject() throws Exception
    {
        Item i = ItemJaxb.getItemFromXml(this.getArmor());
        if (i == null)
            throw new Exception(this.getArmor() + " not found");

        i.setCharacter(this);

        return i;
    }

    /**
     * @return boots item object
     * @throws Exception boots item does not exist
     */
    public Item getBootsObject() throws Exception
    {
        Item i = ItemJaxb.getItemFromXml(this.getBoots());
        if (i == null)
            throw new Exception(this.getBoots() + " not found");

        i.setCharacter(this);

        return i;
    }

    /**
     * @return belt item object
     * @throws Exception belt item not found
     */
    public Item getBeltObject() throws Exception
    {
        Item i = ItemJaxb.getItemFromXml(this.getBeltName());
        if (i == null)
            throw new Exception(this.getBeltName() + " not found");

        i.setCharacter(this);

        return i;
    }

    /**
     * draws/redraws the character and notifies observers
     */
    private void draw()
    {
        if (!this.isBuilt)
            return;

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
    public String getFileName()
    {
        return this.fname;
    }

    /**
     * builds and initializes the character this method is run just once in a
     * characters lifetime and calculates - hit score, based on chacaters class
     */
    private void build()
    {
        if (!this.isBuilt)
        {
            // Build characters class and get hit score
            CharacterClass cClass;
            try
            {
                cClass = new CharacterClass(this.getCharacterClass(), this);
                CharacterClassStructure cClassVal = cClass.get();

                cClass.calculateHitScore(
                        new Dice(cClassVal.getNumberOfRolls(), cClassVal.getDiceSides(), cClassVal.getNumberOfRolls()));
                this.hitScore = cClass.getHitScore();
                this.isBuilt = true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }
 
    /**
     * keeps a track of characters damage. Damage should reduce everytime a
     * character is hit
     * 
     * @param damage set damage to a character
     */
    public void hit(int damage)
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
     * @return true if the operation was successful else false
     */
    public boolean save()
    {
        this.build();
        this.draw();

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
            // check if the directory exists and if not then make it
            File dir = new File(SharedVariables.CharactersDirectory);
            if (!dir.isDirectory())
            {
                dir.mkdirs();
            }

            // write file to path
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

    /**
     * This returns if the monster is friendly or not
     * @return returns true if it a friendly monster
     */
    public Boolean getIsFriendlyMonster(){
      return isFriendlyMonster;
    }
    
    /**
     * This method set if the monster is friendly or not
     * @param value true/false to set is friendly monster 
     */
    public void setIsFriendlyMonster(Boolean value){
        isFriendlyMonster = value;
    }

    /**
     * Return true if the character is a player
     * @return the isPlayer
     */
    public Boolean getIsPlayer() {
      return this.isPlayer;
    }

    /**
     * This method set is the character is a player or not
     * @param isPlayer the isPlayer to set
     */
    public void setIsPlayer(Boolean isPlayer) {
      this.isPlayer = isPlayer;
    }

    /**
     * This method clone this class object 
     * @retrun Charctaer cloned object
     */
    public Character clone(){
        
        try{
          return (Character) super.clone();
        }
        
        catch(CloneNotSupportedException ignored){}
        
        return null;
    }
}