package game.model.character;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Observable;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import game.components.Dice;
import game.components.SharedVariables;
import game.model.Item;
import game.model.character.classes.CharacterClass;
import game.model.character.classes.CharacterClassStructure;
import game.model.jaxb.ItemJaxb;
import game.model.Inventory;

/**
 * Build a new character
 * 
 * @author Supreet Singh (s_supree)
 *
 */
public class Character extends Observable implements Cloneable
{

    private int level = 1;
    private int strength;
    private int dexterity;
    private int constitution;
    private int hitScore = 0;
    private HashMap<String, String> items;
    private String characterClass;
    private String characterType;
    private String name;
    private String fname;
    private String backpackFileName;
    private boolean isBuilt = false;
    
    @XStreamOmitField
    private Backpack backpack;
    
    @XStreamOmitField
    private Inventory inventory;
  
    @XStreamOmitField
    private boolean isKeyCollected = false;
    
    @XStreamOmitField
    private boolean isFriendlyMonster = true;
    
    @XStreamOmitField
    private boolean isPlayer = false;

    /**
     * 
     */
    public Character(){
        this.items = new HashMap<String, String>();
    }
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
     * @return the character type
     */
    public String getCharacterType()
    {
        return characterType;
    }

    /**
     * @param characterType the character type to set
     */
    public void setCharacterType(String characterType)
    {
        this.characterType = characterType;
    }

    /**
     * set character level
     * 
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
     * increment character level and rebuild character hit score
     */
    public void incrementLevel()
    {
        this.setLevel(this.getLevel() + 1);
    }

    /**
     * @return character level
     */
    public int getLevel()
    {
        return this.level;
    }
    
    /**
     * 
     */
    private int getAbilityModifier(int abilityScore)
    {
        return (int) Math.round((abilityScore-10)/2);
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
     * @return strength modifier
     */
    public int getStrengthModifier()
    {
        return this.getAbilityModifier(this.getStrength());
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
     * get calculated dexterity modifer 
     * @return
     */
    public int getDexterityModifier()
    {
        return this.getAbilityModifier(this.getDexterity());
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

    /**
     * @return constitution ability modifier
     */
    public int getConstitutionModifier()
    {
        return this.getAbilityModifier(this.getConstitution());
    }
    
    /**
     * 
     * @return character class
     */
    public String getCharacterClass()
    {
        return this.characterClass;
    }
    
    private String getItem(String itemType){
        if (this.items != null && this.items.containsKey(itemType))
            return this.items.get(itemType);
        
        return null;
    }

    /**
     * sets the weapon and redraws the character
     *
     * @param weaponName set character weapon
     */
    public void setWeaponName(String weaponName)
    {
        this.items.put("Weapon", weaponName);
        this.draw();
    }

    /**
     * @return weapon
     */
    public String getWeaponName()
    {
        return this.getItem("Weapon");
    }

    /**
     * @return the beltName
     */
    public String getBeltName()
    {
        return this.getItem("Beltname");
    }

    /**
     * @param beltName the beltName to set
     */
    public void setBeltName(String beltName)
    {
        this.items.put("Beltname", beltName);
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
                attackBonus += this.getStrengthModifier() + weaponObj.getModifier();
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
                damageBonus += this.getDexterityModifier() + weaponObj.getModifier();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return damageBonus;
    }

    /**
     * Calculates armor class and returns the value
     * NEEDS TO RE CONFIRM LOGIC - IMPORTANT
     * @return the armor class
     */
    public int getArmorClass()
    {
        int armorClass = 10;

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
            // FIXME this logic needs to be re-confirmed and fixed
            if (armorObject.itemClass.equalsIgnoreCase("Light") || armorObject.itemClass.equalsIgnoreCase("Medium"))
            {
                armorClass += this.getDexterityModifier();
            }
            else
            {
                armorClass += 14;
            }
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
        return this.getItem("Ringname");
    }

    /**
     * @param ringName the ringName to set
     */
    public void setRingName(String ringName)
    {
        this.items.put("Ringname", ringName);
        this.draw();
    }

    /**
     * @return the shield
     */
    public String getShield()
    {
        return this.getItem("Shield");
    }

    /**
     * @param shield the shield to set
     */
    public void setShield(String shield)
    {
        this.items.put("Shield", shield);
        this.draw();
    }

    /**
     * @return the boots
     */
    public String getBoots()
    {
        return this.getItem("Boots");
    }

    /**
     * @param boots the boots to set
     */
    public void setBoots(String boots)
    {
        this.items.put("Boots", boots);
        this.draw();
    }

    /**
     * @return the armor
     */
    public String getArmor()
    {
        return this.getItem("Armor");
    }

    /**
     * @param armor the armor to set
     */
    public void setArmor(String armor)
    {
        this.items.put("Armor", armor);
        this.draw();
    }

    /**
     * @return the helmet
     */
    public String getHelmet()
    {
        return this.items.get("Helmet");
    }

    /**
     * @param helmet the helmet to set
     */
    public void setHelmet(String helmet)
    {
        this.items.put("Helmet", helmet);
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
     * gets item from backpack and swaps if an existing item for the item type is present
     * @param i
     */
    public void getItemFromBackpack(Item i)
    {
        if (i != null)
        {
            if (this.items.containsKey(i.getItemType()))
            {
                String itemName = this.items.get(i.getItemType());
                if (itemName.equals(i.getItemName()))
                {
                    // existing object is being added to the list
                    return;
                }
                
                // if an existing item exists, try to equip it to backpack
                Item iObj = ItemJaxb.getItemFromXml(itemName);
             
                System.out.println(701);
                try
                {
                    this.getBackpack().unequip(i);
                    this.getBackpack().equip(iObj);
                    
                    // if equip is successful, then remove that item from character
                    removeItem(iObj);
                    
                    // add new item
                    this.items.put(i.getItemType(), i.getItemName());
                    draw();
                }
                catch (Throwable e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                   
                
            }
            else
            {
                try
                {
                    this.getBackpack().unequip(i);
                    this.items.put(i.getItemType(), i.getItemName());
                    draw();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }                
            }
        }
        
        return;
    }
    
    /**
     * Send item to backpack
     * @param i
     */
    public void sendItemToBackpack(Item i)
    {
        if (i != null)
        {
            if (this.items.containsKey(i.getItemType()))
            {
                String itemName = this.items.get(i.getItemType());
                if (!itemName.equals(i.getItemName()))
                {
                    System.out.println("Wrong item");
                    // existing object is being added to the list
                    return;
                }
                
                // if an existing item exists, try to equip it to backpack
                Item iObj = ItemJaxb.getItemFromXml(itemName);
                try
                {
                    this.getBackpack().equip(i);
                    
                    // if equip is successful, then remove that item from character
                    removeItem(i);
                }
                catch (Throwable e)
                {
                    System.out.println(e.getMessage());
                }
            }
        }
        
        return;
    }
    
    /**
     * Remove an item from characters list
     * @param i
     */
    public void removeItem(Item i)
    {
        if (i != null)
        {
            if (this.items.containsKey(i.getItemType()))
            {
                this.items.remove(i.getItemType(), i.getItemName());
                System.out.println(items);
            }
        }
        draw();
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
     * Calculate hit score
     * 
     * @throws Exception
     */
    private void calculateHitScore() throws Exception
    {
        // if no class is set for the character set hit score to 0
        if (this.getCharacterClass() == null || this.getCharacterClass().isEmpty()){
            this.hitScore = 0;
            return;
        }
        
        CharacterClass cClass;

        /*
         * http://rpg.stackexchange.com/questions/48156/is-con-modifier-%C3%97-
         * level-added-to-hp-every-level-up
         */
        cClass = new CharacterClass(this.getCharacterClass(), this);
        CharacterClassStructure cClassVal = cClass.get();

        cClass.calculateHitScore(
                new Dice(cClassVal.getNumberOfRolls(), cClassVal.getDiceSides(), cClassVal.getNumberOfRolls()));

        // add hit score to existing hit score, useful when it's increment level
        this.hitScore += cClass.getHitScore();
    }

    /**
     * builds and initializes the character this method is run just once in a
     * characters lifetime and calculates - hit score, based on characters class
     */
    private void build()
    {
        if (!this.isBuilt)
        {
            // Build characters class and get hit score

            try
            {
                // modify ability scores with class type modifiers
                
                
                // calculate hit score from the character class
                this.calculateHitScore();

                // build a bucket for the character
                this.createBackpack();

                this.isBuilt = true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    /**
     * check if a character has backpack
     * 
     * @return true if backpack exists else false
     */
    public boolean hasBackpack()
    {
        return this.backpackFileName != null;
    }

    /**
     * If a backpack doesnt yet exist for the character, creates a new backpack
     */
    private void createBackpack()
    {

        if (this.hasBackpack())
            return;
        
        Backpack backpack = new Backpack();
        try
        {
            backpack.save();
            this.backpackFileName = backpack.getFileName();
        }
        catch (IOException e)
        {
            this.backpackFileName = null;
        }
    }

    /**
     * @return backpack
     */
    public String getBackpackFileName()
    {
        return this.backpackFileName;
    }
    
    public Backpack getBackpack() throws IOException
    {
        if (!this.hasBackpack())
            this.createBackpack();
        
        if (this.backpack == null)
            this.backpack = Backpack.get(this.getBackpackFileName());
        
        return this.backpack;
    }
    
    /**
     * Saves backpack if it already exists, otherwise does nothing
     * @throws IOException 
     */
    public void saveBackpack() throws IOException{
        if (this.backpack != null)
            this.backpack.save();
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
        this.draw();
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
        xstream.omitField(Observable.class, "obs");
        xstream.omitField(Observable.class, "changed");
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
            
            // save backpack
            this.saveBackpack();
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
     * 
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
     * 
     * @param value true/false to set is friendly monster
     */
    public void setFriendlyMonsterFlag(boolean value)
    {
        isFriendlyMonster = value;
    }

    /**
     * Return true if the character is a player
     * 
     * @return the isPlayer
     */
    public boolean isPlayer()
    {
        return this.isPlayer;
    }

    /**
     * This method set is the character is a player or not
     * 
     * @param isPlayer the isPlayer to set
     */
    public void setPlayerFlag(boolean isPlayer)
    {
        this.isPlayer = isPlayer;
    }

    /**
     * This method clone this class object
     * 
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
     * 
     * @return isKeyCollected true if user has key
     */
    public boolean isKeyCollected()
    {
        return isKeyCollected;
    }

    /**
     * This method set if the player has key or not
     * 
     * @param status of user has key or not
     */
    public void setKeyCollectedFlag(boolean isKeyCollected)
    {
        this.isKeyCollected = isKeyCollected;
        this.draw();
    }
    
    /**
     * Get all items that character is wearing
     * 
     * @return hashmap containing all items that a character is wearing
     */
    public HashMap<String, String> getAllItems()
    {
        if (this.items == null)
            return new HashMap<String, String>();
        
        return this.items;
    }
    
    /**
     * Get inventory object for the character. This object contains all the items in backpack and items character is wearing.
     * It also implements an observer pattern so any new change will be immediately triggered
     * 
     * @return character inventory object
     */
    public Inventory getInventory()
    {
        if (this.inventory == null)
            this.inventory = Inventory.initialize(this);
        
        return this.inventory;
    }
}