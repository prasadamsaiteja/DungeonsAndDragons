package game.model.character;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
public class Character extends Observable
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
    private HashMap<String, ArrayList<Item>> itemModifiers = new HashMap<String, ArrayList<Item>>();

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
    public int getOriginalStrength()
    {        
        return strength;
    }

    /**
     * retrieve character strength
     *
     * @return int strength
     */
    public int getStrength()
    {
        int strength = 0;
        
        Item ringObject = this.getRingObject();
        if (ringObject != null && ringObject.itemClass.equalsIgnoreCase("Strength")){
            strength += ringObject.getModifier();
        }
        
        Item beltObject = this.getBeltObject();
        if (beltObject != null && beltObject.itemClass.equalsIgnoreCase("Strength")){
            strength += beltObject.getModifier();
        }
        
        return strength+getOriginalStrength();
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
    public int getOriginalDexterity()
    {
        return dexterity;
    }
    
    /**
     * @return dexterity
     */
    public int getDexterity()
    {
        int dexterity = 0;
        Item bootsObject = this.getBootsObject();
        if (bootsObject != null && bootsObject.itemClass.equalsIgnoreCase("Dexterity")){
            dexterity += bootsObject.getModifier();
        }
        return dexterity + getOriginalDexterity();
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
    public int getOriginalConstitution()
    {
        return constitution;
    }

    /**
     * return constitution
     */
    public int getConstitution()
    {
        int constitution = 0;
        
        Item ringObject = this.getRingObject();
        if (ringObject != null && ringObject.itemClass.equalsIgnoreCase("Constitution")){
            constitution += ringObject.getModifier();
        }
        
        Item beltObject = this.getBeltObject();
        if (beltObject != null && beltObject.itemClass.equalsIgnoreCase("Constitution")){
            constitution += beltObject.getModifier();
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
    }

    /**
     * @return attack bonus
     */
    public int getAttackBonus()
    {
        int attackBonus = 0;
        Item weaponObj = this.getWeaponObject();
        if (weaponObj != null && weaponObj.itemClass.equalsIgnoreCase("Ranged")){
            attackBonus += this.getStrength() + weaponObj.getModifier();
        }
        return attackBonus;
    }

    /**
     * @return damage bonus
     */
    public int getDamageBonus()
    {
        int damageBonus = 0;
        Item weaponObj = this.getWeaponObject();
        if (weaponObj != null && weaponObj.itemClass.equalsIgnoreCase("Melee")){
            damageBonus += this.getDexterity() + weaponObj.getModifier();
        }
        return damageBonus;
    }


    /**
     * @return the armor class
     */
    public int getArmorClass()
    { 
        int armorClass = 0;
        Item ringObject = this.getRingObject();
        if (ringObject != null && ringObject.itemClass.equalsIgnoreCase("ArmorClass")){
            armorClass += ringObject.getModifier();
        }
        
        Item armorObject = this.getArmorObject();
        if (armorObject != null){
            armorClass += armorObject.getModifier();
        }
        
        Item shieldObject = this.getShieldObject();
        if (shieldObject != null){
            armorClass += shieldObject.getModifier();
        }
        
        Item helmetObject = this.getHelmetObject();
        if (helmetObject != null){
            armorClass += helmetObject.getModifier();
        }
        
        Item bootsObject = this.getBootsObject();
        if (bootsObject != null && bootsObject.itemClass.equalsIgnoreCase("ArmorClass")){
            armorClass += bootsObject.getModifier();
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
    }
    
    /**
     * @return
     */
    public Item getWeaponObject()
    {
        if (this.getWeaponName() == null)
            return null;
        
        return ItemJaxb.getItemFromXml(this.getWeaponName());
    }
    
    /**
     * @return
     */
    public Item getShieldObject()
    {
        if (this.getShield() == null)
            return null;
        
        return ItemJaxb.getItemFromXml(this.getShield());
    }
    
    
    /**
     * @return
     */
    public Item getRingObject()
    {
        if (this.getRingName() == null)
            return null;
        
        return ItemJaxb.getItemFromXml(this.getRingName());
    }
    
    
    /**
     * @return
     */
    public Item getHelmetObject()
    {
        if (this.getHelmet() == null)
            return null;
        
        return ItemJaxb.getItemFromXml(this.getHelmet());
    }
    
    
    /**
     * @return
     */
    public Item getArmorObject()
    {
        if (this.getArmor() == null)
            return null;
        
        return ItemJaxb.getItemFromXml(this.getArmor());
    }
    
    
    /**
     * @return
     */
    public Item getBootsObject()
    {
        if (this.getBoots() == null)
            return null;
        
        return ItemJaxb.getItemFromXml(this.getBoots());
    }
    
    /**
     * @return
     */
    public Item getBeltObject()
    {
        if (this.getBeltName() == null)
            return null;
        
        return ItemJaxb.getItemFromXml(this.getBeltName());
    }

    /**
     * draws/redraws the character and notifies observers
     */
    public void draw()
    {
        if (!this.isBuilt)
            return;
        

        ArrayList<Item> list = new ArrayList<Item>();
        
        Item weaponObj = getWeaponObject();
        itemModifiers.put(weaponObj.itemClass.toLowerCase(), list);
        list.add(weaponObj);
        
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
