package game.model.character;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Observable;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import game.components.Dice;
import game.components.SharedVariables;
import game.model.character.classes.CharacterHelper;
import game.model.character.classes.CharacterClassStructure;
import game.model.character.strategyPattern.MomentStrategy;
import game.model.item.Item;
import game.model.jaxb.ItemJaxb;

/**
 * Build a new character with a set of abilities associated with it
 * @author Supreet Singh (s_supree)
 * @version 1.0.0
 */
public class Character extends Observable implements Cloneable
{
    private int level = 1;
    private int strength;
    private int dexterity;
    private int constitution;
    private int hitScore = 0;
    public HashMap<String, String> items = new HashMap<>();
    private String characterClass;
    private String characterType;
    private String name;
    private String fname;
    private String backpackFileName;    
    private boolean isBuilt = false;
    public Backpack backpack;

    private ArrayList<MomentStrategy> momentStrategy;
    private boolean isKeyCollected = false;
    private boolean isFriendlyMonster = true;
    private boolean isPlayer = false;
    public int turnPoints = 0;
    
    public int burningTurn, bruningDamagePoints;

    /**
     * This method sets the name for the character
     * @param name set character name
     */
    public void setName(String name)
    {
        this.name = name;
        
        if(items == null)
            items = new HashMap<>();
    }

    /**
     * Retrieves character name
     * @return String character name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * This method sets the character class 
     * @param characterClass set character class
     */
    public void setCharacterClass(String characterClass)
    {
        this.characterClass = characterClass;
        this.draw();
    }

    /**
     * This method returns the type of character
     * @return the character type
     */
    public String getCharacterType()
    {
        return characterType;
    }

    /** 
     * Method sets the character type
     * @param characterType the character type to set
     */
    public void setCharacterType(String characterType)
    {
        this.characterType = characterType;
    }

    /**
     * Set the character level
     * @param level set character level
     */
    public void setLevel(int level)
    {
        this.level = level;
        
        try
        {
            if (!this.isPlayer)
            {
                this.hitScore = 0;
            }
            this.calculateHitScore();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.draw();
    }

    /**
     * Increment character level and rebuild character hit score
     */ 	
    public void incrementLevel()
    {
        this.setLevel(this.getLevel() + 1);
    }

    /**
     * This method returns the level of character 
     * @return character level
     */
    public int getLevel()
    {
        return this.level;
    }
    
    /**
     * calculates the ability modifier from the ability score
     * @param abilityScore score
     * @return int ability modifier value
     */
    private int getAbilityModifier(int abilityScore)
    {
        return (int) Math.round((abilityScore-10)/2);
    }

    /**
     * This method sets the strength of the character
     * @param strength set character strength
     * @return character object
     */
    public Character setStrength(int strength)
    {
        this.strength = strength;        
        return this;
    }

    /**
     * This method gets the original strength of the character
     * @return int original strength
     */
    public int getOriginalStrength()
    {
        return strength;
    }

    /**
     * This method gets the strength of the character
     * @return character strength 
     */
    public int getStrength()
    {        
    	int strength = 0;

    	Item ringObject = this.getRingObject();
        if (ringObject != null && ringObject.itemClass.equalsIgnoreCase("Strength"))
            strength += ringObject.getModifier();    

        Item beltObject = this.getBeltObject();
        if (beltObject != null && beltObject.itemClass.equalsIgnoreCase("Strength"))
            strength += beltObject.getModifier();

        return strength + getOriginalStrength();
    }
    
    /**
     * This method gets the  strength Modifier of the character
     * @return strength modifier
     */
    public int getStrengthModifier()
    {
        return this.getAbilityModifier(this.getStrength());
    }

    /**
     * This method sets the dexterity of the character
     * @param dexterity set character dexterity
     * @return character object
     */
    public Character setDexterity(int dexterity)
    {
        this.dexterity = dexterity;
        
        return this;
    }

    /**
     * This method gets the original dexterity of the character
     * @return int original dexterity
     */
    public int getOriginalDexterity()
    {
        return dexterity;
    }

    /**
     * This method gets the dexterity of the character
     * @return int character dexterity
     */
    public int getDexterity()
    {
        int dexterity = 0;

        Item bootsObject = this.getBootsObject();
        if (bootsObject != null && bootsObject.itemClass.equalsIgnoreCase("Dexterity"))           
            dexterity += bootsObject.getModifier();

        return dexterity + getOriginalDexterity();
    }
    
    /**
     * Get calculated dexterity modifier 
     * @return int dexterity modifier
     */
    public int getDexterityModifier()
    {
        return this.getAbilityModifier(this.getDexterity());
    }

    /**
     * This method sets the constitution of the character
     * @param constitution set character constitution
     * @return character object
     */
    public Character setConstitution(int constitution)
    {
        this.constitution = constitution;        
        return this;
    }

    /**
     * This method gets the original constitution of the character
     * @return int original constitution
     */
    public int getOriginalConstitution()
    {
        return constitution;
    }

    /**
     * This method gets the original constitution of the character
     * @return int constitution
     */
    public int getConstitution()
    {
        int constitution = 0;

        Item ringObject = this.getRingObject();
        if (ringObject != null && ringObject.itemClass.equalsIgnoreCase("Constitution"))          
            constitution += ringObject.getModifier();          

        Item beltObject = this.getBeltObject();
        if (beltObject != null && beltObject.itemClass.equalsIgnoreCase("Constitution"))          
            constitution += beltObject.getModifier();     
            
        return constitution + getOriginalConstitution();
    }

    /**
     *  This method gets the constitution modifier of the character ability
     * @return constitution ability modifier
     */
    public int getConstitutionModifier()
    {
        return this.getAbilityModifier(this.getConstitution());
    }
    
    /**
     * This method gets the character class
     * @return string character class
     */
    public String getCharacterClass()
    {
        return this.characterClass;
    }
    
    /**
     * This method gets the item of a specific type
     * @param itemType type of item
     * @return String item of specific type
     */
    private String getItem(String itemType){
        
        if (this.items != null && this.items.containsKey(itemType))
            return this.items.get(itemType);
        
        return null;
    }

    /**
     * Sets the weapon and redraws the character
     * @param weaponName set character weapon
     */
    public void setWeaponName(String weaponName)
    {
        this.items.put("Weapon", weaponName);
        this.draw();
    }

    /**
     * This method gets the weapon name
     * @return string weapon name
     */
    public String getWeaponName()
    {
        return this.getItem("Weapon");
    }

    /**
     * This method gets the belt  name
     * @return string beltName
     */
    public String getBeltName()
    {
        return this.getItem("Belt");
    }

    /**
     * This method sets the belt name for the character
     * @param beltName the beltName to set
     */
    public void setBeltName(String beltName)
    {
        this.items.put("Belt", beltName);
        this.draw();
    }

    /**
     * This method gets the Attack Bonus
     * @return int attack bonus
     */
    public int getAttackBonus()
    {
        int attackBonus = 0;

        Item weaponObj = this.getWeaponObject();        
        if (weaponObj != null && weaponObj.itemClass.equalsIgnoreCase("Ranged"))            
            attackBonus += this.getDexterityModifier() + weaponObj.getModifier();
        
        else
            attackBonus += this.getStrengthModifier() + weaponObj.getModifier();

        return attackBonus;
    }

    /**
     * This method gets the damage Bonus
     * @return int damage bonus
     */
    public int getDamageBonus()
    {
        int damageBonus = 0;

        Item weaponObj = this.getWeaponObject();
        if (weaponObj != null && weaponObj.itemClass.equalsIgnoreCase("Melee"))
            damageBonus += this.getStrengthModifier() + weaponObj.getModifier();        

        return damageBonus;
    }

    //NEEDS TO RE CONFIRM LOGIC - IMPORTANT
    /**
     * Calculates armor class and returns the value
     * NEEDS TO RE CONFIRM LOGIC - IMPORTANT
     * @return int armor class
     */
    public int getArmorClass()
    {
        int armorClass = 10;

        Item ringObject = this.getRingObject();
        if (ringObject != null && ringObject.itemClass.equalsIgnoreCase("ArmorClass"))            
            armorClass += ringObject.getModifier();

        Item armorObject = this.getArmorObject();
        // FIXME this logic needs to be re-confirmed and fixed
        if (armorObject != null && (armorObject.itemClass.equalsIgnoreCase("Light") || armorObject.itemClass.equalsIgnoreCase("Medium")))            
            armorClass += this.getDexterityModifier();                
        
        else            
            armorClass += 14;
        
        if(armorObject != null)
            armorClass += armorObject.getModifier();
        
        Item shieldObject = this.getShieldObject();
        if(shieldObject != null)
            armorClass += shieldObject.getModifier();

        Item helmetObject = this.getHelmetObject();
        if(helmetObject != null)
            armorClass += helmetObject.getModifier();
        
        Item bootsObject = this.getBootsObject();
        if (bootsObject != null && bootsObject.itemClass.equalsIgnoreCase("ArmorClass"))           
            armorClass += bootsObject.getModifier();            

        return armorClass;
    }

    /**
     * This method gets the ring  name
     * @return string the ringName
     */
    public String getRingName()
    {
        return this.getItem("Ring");
    }

    /**
     * This method sets the belt name for the character
     * @param ringName the ringName to set
     */
    public void setRingName(String ringName)
    {
        this.items.put("Ring", ringName);
        this.draw();
    }

    /**
     * This method gets the Shield for the character
     * @return the shield
     */
    public String getShield()
    {
        return this.getItem("Shield");
    }

    /**
     * This method sets the Shield for the character
     * @param shield the shield to set
     */
    public void setShield(String shield)
    {
        this.items.put("Shield", shield);
        this.draw();
    }

    /**
     * This method gets the boots
     * @return int the boots
     */
    public String getBoots()
    {
        return this.getItem("Boots");
    }

    /**
     * This method sets the Boots for the character
     * @param boots the boots to set
     */
    public void setBoots(String boots)
    {
        this.items.put("Boots", boots);
        this.draw();
    }

    /**
     * This method gets the Armor 
     * @return string the armor
     */
    public String getArmor()
    {
        return this.getItem("Armor");
    }

    /**
     * This method sets the Armor for the character
     * @param armor the armor to set
     */
    public void setArmor(String armor)
    {
        this.items.put("Armor", armor);
        this.draw();
    }

    /**
     * This method gets the helmet
     * @return int the helmet
     */
    public String getHelmet()
    {
        return this.items.get("Helmet");
    }

    /**
     * This method sets the helmet for the character
     * @param helmet the helmet to set
     */
    public void setHelmet(String helmet)
    {
        this.items.put("Helmet", helmet);
        this.draw();
    }

    /**
     * This method gets the weapon item object
     * @return Item weapon item object
     */
    public Item getWeaponObject()
    {
        Item i = ItemJaxb.getItemFromXml(this.getWeaponName());
            
        if(i != null)
            i.itemLevel = level;
        
        return i;
    }

    /**
     * This method gets the Shield item object
     * @return item shield item object
     */
    public Item getShieldObject() 
    {
        Item i = ItemJaxb.getItemFromXml(this.getShield());
        
        if(i != null)
            i.itemLevel = level;
        
        return i;
    }

    /**
     * This method gets the Ring item object
     * @return item ring item object
     */
    public Item getRingObject()
    {
        Item i = ItemJaxb.getItemFromXml(this.getRingName());       
        
        if(i != null)
            i.itemLevel = level;
        return i;
    }

    /**
     * This method gets the weapon item object
     * @return item helmet item object
     */
    public Item getHelmetObject()
    {
        Item i = ItemJaxb.getItemFromXml(this.getHelmet());
        
        if(i != null)
            i.itemLevel = level;
        return i;
    }

    /**
     * This method gets the Armor item object
     * @return item armor item object
     */
    public Item getArmorObject()
    {
        Item i = ItemJaxb.getItemFromXml(this.getArmor());
        
        if(i != null)
            i.itemLevel = level;
        return i;
    }

    /**
     * This method gets the Boots item object
     * @return item boots item object
     */
    public Item getBootsObject()
    {
        Item i = ItemJaxb.getItemFromXml(this.getBoots());
        
        if(i != null)
        i.itemLevel = level;
        return i;
    }

    /**
     * This method gets the belt item object
     * @return item belt item object
     */
    public Item getBeltObject()
    {
        Item i = ItemJaxb.getItemFromXml(this.getBeltName());
        
        if(i != null)
            i.itemLevel = level;
        return i;
    }

    /**
     * Remove an item from characters list
     * @param item item object to be removed
     */
    public void removeItem(Item item)
    {
        if (item != null)
        {
            if (this.items.containsKey(item.getItemType()))            
                this.items.remove(item.getItemType(), item.getItemName());
            
        }
        draw();
    }

    /**
     * Draws/redraws the character and notifies observers
     */
    public void draw()
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
     * This method gets the file name
     * @return string file name or null if not present
     */
    public String getFileName()
    {
        return this.fname;
    }

    /**
     * Calculate hit score
     * 
     */
    @SuppressWarnings("javadoc")
    public void calculateHitScore() throws Exception
    {
        // if no class is set for the character set hit score to 0
        if (this.getCharacterClass() == null || this.getCharacterClass().isEmpty()){
            this.hitScore = 0;
            return;
        }
        
        CharacterHelper cClass;

        /*
         * http://rpg.stackexchange.com/questions/48156/is-con-modifier-%C3%97-
         * level-added-to-hp-every-level-up
         */
        cClass = new CharacterHelper(this.getCharacterClass(), this);
        CharacterClassStructure cClassVal = cClass.get();

        cClass.calculateHitScore(
                new Dice(cClassVal.getNumberOfRolls(), cClassVal.getDiceSides(), cClassVal.getNumberOfRolls()));

        // add hit score to existing hit score, useful when it's increment level
        this.hitScore += cClass.getHitScore();
    }

    /**
     * Builds and initializes the character this method is run just once in a
     * characters lifetime and calculates - hit score, based on characters class
     */
    private void build()
    {
        if (!this.isBuilt)
        {
            // Build characters class and get hit score

            try
            {
                this.calculateHitScore();
                this.isBuilt = true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    /**
     * Check if a character has backpack
     * @return true if backpack exists else false
     */
    public boolean hasBackpack()
    {
        return this.backpackFileName != null;
    }

    /**
     * This method gets the Backpack's file name
     * @return string  backpack name
     */
    public String getBackpackFileName()
    {
        return this.backpackFileName;
    }

    /**
     * Keeps a track of characters damage. Damage should reduce everytime a character is hit
     * @param damage set damage to a character
     */
    public void hit(int damage)
    {
        this.hitScore -= damage;
        this.draw();
    }

    /** 
     * Gets the hit score
     * @return int calculate hit score
     */
    public int getHitScore()
    {
        return this.hitScore;
    }

    /**
     * Saves character state in the generated xml file
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
     * Deletes the characters xml file
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
    public boolean getIsFriendlyMonster()
    {
        if (this.isFriendlyMonster == false)
            return false;

        return isFriendlyMonster;
    }

    /**
     * This method set if the monster is friendly or not
     * @param value true/false to set is friendly monster
     */
    public void setFriendlyMonsterFlag(boolean value)
    {
        isFriendlyMonster = value;
    }

    /**
     * This method returns true if the character is a player
     * @return the isPlayer
     */
    public boolean isPlayer()
    {
        return this.isPlayer;
    }

    /**
     * This method set is the character is a player or not
     * @param isPlayer the isPlayer to set
     */
    public void setPlayerFlag(boolean isPlayer)
    {
        this.isPlayer = isPlayer;
    }

    /**
     * This method clone this class object
     * @return Character cloned object
     */
    public Character clone()
    {

        try
        {
            return (Character) super.clone();
        }

        catch (CloneNotSupportedException ignored)
        {
        }

        return null;
    }

    /**
     * This method returns if the player has key or not
     * @return isKeyCollected true if user has key
     */
    public boolean isKeyCollected()
    {
        return isKeyCollected;
    }

    /**
     * This method set if the player has key or not
     * @param isKeyCollected of user has key or not
     */
    public void setKeyCollectedFlag(boolean isKeyCollected)
    {
        this.isKeyCollected = isKeyCollected;
        this.draw();
    }
    
    /**
     * Get all items that character is wearing
     * @return hash map containing all items that a character is wearing
     */
    public HashMap<String, String> getAllItems()
    {
        if (this.items == null)
            return new HashMap<String, String>();
        
        return this.items;
    }
            
    /**
     * This method retrieves all the  weapons
     * @return Array list list of weapons
     */
    public ArrayList<String> getAllWeapons(){
       
        ArrayList<String> weapons = new ArrayList<>();
        
        if(getWeaponObject() != null)
            weapons.add(getWeaponObject().itemName);
        
        if(backpack.backpackItems.get("Weapon") != null)
            weapons.addAll(backpack.backpackItems.get("Weapon"));
        
        return weapons;        
    }

    /**
     * This method returns all the helmet weapons
     * @return Arraylist list of helmets
     */
    public ArrayList<String> getAllHelmets(){
        
        ArrayList<String> weapons = new ArrayList<>();
        
        if(getHelmetObject() != null)
            weapons.add(getHelmetObject().itemName);
        
        if(backpack.backpackItems.get("Helmet") != null)
            weapons.addAll(backpack.backpackItems.get("Helmet"));
        
        return weapons;        
    }
    
    /**
     * This method returns all the selected type armor weapons
     * @return Array list list of Armor
     */
    public ArrayList<String> getAllArmor(){
        
        ArrayList<String> weapons = new ArrayList<>();
        
        if(getArmorObject() != null)
            weapons.add(getArmorObject().itemName);
        
        if(backpack.backpackItems.get("Armor") != null)
            weapons.addAll(backpack.backpackItems.get("Armor"));
        
        return weapons;        
    }
    
    /**
     * This method returns all the Shield weapons
     * @return Arraylist list of shields
     */
    public ArrayList<String> getAllShield(){
        
        ArrayList<String> weapons = new ArrayList<>();
        
        if(getShieldObject() != null)
            weapons.add(getShieldObject().itemName);        
        
        if(backpack.backpackItems.get("Shield") != null)
            weapons.addAll(backpack.backpackItems.get("Shield"));
        
        return weapons;        
    }

    /**
     * This method returns all the ring weapons
     * @return Arraylist list of rings
     */
    public ArrayList<String> getAllRing(){
        
        ArrayList<String> weapons = new ArrayList<>();
        
        if(getRingObject() != null)
            weapons.add(getRingObject().itemName);
        
        if(backpack.backpackItems.get("Ring") != null)
            weapons.addAll(backpack.backpackItems.get("Ring"));
        
        return weapons;        
    }
    
    /**
     * This method returns all the Belt weapons
     * @return Arraylist list of belts
     */
    public ArrayList<String> getAllBelts(){
        
        ArrayList<String> weapons = new ArrayList<>();
        
        if(getBeltObject() != null)
            weapons.add(getBeltObject().itemName);
        
        if(backpack.backpackItems.get("Belt") != null)
            weapons.addAll(backpack.backpackItems.get("Belt"));
        
        return weapons;        
    }
    
    /**
     * This method returns all the boot weapons
     * @return Arraylist list of boots
     */
    public ArrayList<String> getAllBoots(){
        
        ArrayList<String> weapons = new ArrayList<>();
        
        if(getBootsObject() != null)
            weapons.add(getBootsObject().itemName);
        
        if(backpack.backpackItems.get("Boots") != null)
            weapons.addAll(backpack.backpackItems.get("Boots"));
        
        return weapons;        
    }

    /**
     * This method returns strategy
     * @return the momentStrategy
     */
    public MomentStrategy getMomentStrategy() {
        return momentStrategy.get(momentStrategy.size() - 1);
    }
    
    /**
     * This method sets character strategy
     * @param momentStrategy the momentStrategy to set
     */
    public void setMomentStrategy(MomentStrategy momentStrategy) {
        this.momentStrategy = new ArrayList<>();
        this.momentStrategy.add(momentStrategy);
    }

    /**
     * This method returns attack points of the character
     * @return attack points of the character
     */
    public int attackPoint(){
        return (new Dice(1, 20, 1)).getRollSum() + getAttackBonus();
    }

    public void pushMomentStrategy(MomentStrategy momentStrategy) {
        this.momentStrategy.add(momentStrategy);
    }
    
    public void popMomentStrategy(){
        this.momentStrategy.remove(momentStrategy.size() - 1);
    }

    public void startBurning(int burningDamagePoints){
        this.burningTurn = 3;
        this.bruningDamagePoints = burningDamagePoints;
    }
}