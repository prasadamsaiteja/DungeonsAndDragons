package tests.game.model.character.character;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import game.model.character.Character;
import game.model.itemClasses.Item;
import game.model.jaxb.ItemJaxb;

/**
 * Tests for Armor Class and worn items influence on character abilities
 * @author supreet (s_supree)
 * @version 1.0.0
 */
public class ArmorClassTest
{
    private String nonExistingArmor;
    private String existingArmor;
    private String nonExistingRing;
    private String existingRing;
    private String nonExistingShield;
    private String existingShield;
    private String nonExistingBoots;
    private String existingBoots;
    private String nonExistingHelmet;
    private String existingHelmet;

    private int itemLevel = 1;
    private int defaultArmorClass = 10;

    /**
     * Set default values and create dummy items
     */
    @Before
    public void createVars()
    {
        nonExistingArmor = "nonExistingItem";
        existingArmor = "existingArmor";

        nonExistingRing = "nonExistingRing";
        existingRing = "existingRing";

        nonExistingShield = "nonExistingShield";
        existingShield = "existingShield";

        nonExistingBoots = "nonExistingBoots";
        existingBoots = "existingBoots";

        nonExistingHelmet = "nonExistingHelmet";
        existingHelmet = "existingHelmet";

        // create dummy armor
        createDummyItem(existingArmor, "Armor", "Light", itemLevel);

        // create dummy ring
        createDummyItem(existingRing, "Ring", "ArmorClass", itemLevel);

        // create dummy shield
        createDummyItem(existingShield, "Shield", "ArmorClass", itemLevel);

        // create dummy helmet
        createDummyItem(existingHelmet, "Helmet", "ArmorClass", itemLevel);

        // create dummy boots
        createDummyItem(existingBoots, "Boots", "ArmorClass", itemLevel);

    }

    /**
     * Creation of dummy item
     * 
     * @param itemName name of item
     * @param itemType type of item
     * @param itemClass class of item
     * @param armorLevel armor level of character
     */
    private void createDummyItem(String itemName, String itemType, String itemClass, int armorLevel)
    {
        Item itemObj = new Item(itemName, itemType, itemClass, armorLevel);
        ItemJaxb.convertItemObjectToXml(itemObj);
    }

    /**
     * No armor set should through exception when getting armor object
     */
    @Test
    public void getArmorObject_NoItemSet_throwsException()
    {
        Character c1 = new Character();
        assertNull(c1.getArmorObject());
    }

    /**
     * Non existing armor should throw exception when getting armor object
     */
    @Test
    public void getArmorObject_NonExistentArmor_throwsException()
    {
        Character c1 = new Character();
        c1.setArmor(nonExistingArmor);
        assertNull(c1.getArmorObject());
    }

    /**
     * Existing armor should throw no exception when getting armor object
     */
    @Test
    public void getArmorObject_ExistentArmor_noExceptionThrown()
    {
        Character c1 = new Character();
        c1.setArmor(existingArmor);
        assertNotNull(c1.getArmorObject());
    }

    /**
     * Existing armor should modify armor class
     */
    @Test
    public void getArmorObject_ExistentArmor_modifiedArmorClass()
    {
        Character c1 = new Character();
        c1.setArmor(existingArmor);
        try
        {
            int expectedArmorClass = (defaultArmorClass + (int) Math.ceil((double) itemLevel / (double) 4)) + c1.getDexterityModifier();
            assertEquals(expectedArmorClass, c1.getArmorClass());
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    /**
     * If no ring present, exception should be thrown when fetching ring object
     */
    @Test
    public void getRingObject_NoItemSet_throwsException()
    {
        Character c1 = new Character();
        assertNull(c1.getArmorObject());
    }

    /**
     * Non-Existing ring should throw an exception when getting ring object
     */
    @Test
    public void getRingObject_NonExistentItem_throwsException()
    {
        Character c1 = new Character();
        c1.setRingName(nonExistingRing);
        assertNull(c1.getRingObject());
    }

    /**
     * Existing ring should throw no exception when getting ring object
     */
    @Test
    public void getRingObject_ExistingItem_noExceptionThrown()
    {
        Character c1 = new Character();
        c1.setRingName(existingRing);
        try
        {
            c1.getRingObject();
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    /**
     * Existing ring should affect and enhance armor class
     */
    @Test
    public void getRingObject_ExistingItem_modifiedArmorClass()
    {
        Character c1 = new Character();
        c1.setRingName(existingRing);
        try
        {
            int expectedArmorClass = 10 + (int) Math.ceil((double) itemLevel / (double) 4);
            assertEquals(expectedArmorClass, c1.getArmorClass());
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    /**
     * If shield not set, exception should be thrown when getting shield object
     */
    @Test
    public void getShieldObject_NoItemSet_throwsException()
    {
        Character c1 = new Character();
        assertNull(c1.getArmorObject());
    }

    /**
     * Non-Existing shield should throw exception when getting shield object
     */
    @Test
    public void getShieldObject_NonExistentItem_throwsException()
    {
        Character c1 = new Character();
        c1.setRingName(nonExistingShield);
        assertNull(c1.getRingObject());
    }

    /**
     * Existing shield should throw no exception when getting shield object
     */
    @Test
    public void getShieldObject_ExistingItem_noExceptionThrown()
    {
        Character c1 = new Character();
        c1.setShield(existingShield);
        assertNotNull(c1.getShieldObject());
    }

    /**
     * Existing shield should affect and enhance armor class
     */
    @Test
    public void getShieldObject_ExistingItem_modifiedArmorClass()
    {
        Character c1 = new Character();
        c1.setShield(existingShield);
        try
        {
            int expectedArmorClass = defaultArmorClass + (int) Math.ceil((double) itemLevel / (double) 4);
            assertEquals(expectedArmorClass, c1.getArmorClass());
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    /**
     * If Helmet not set, exception should be thrown when fetching helmet object
     */
    @Test
    public void getHelmetObject_NoItemSet_throwsException()
    {
        Character c1 = new Character();
        assertNull(c1.getHelmetObject());
    }

    /**
     * Non-Existing helmet should throw exception when getting helmet object
     */
    @Test
    public void getHelmetObject_NonExistentItem_throwsException()
    {
        Character c1 = new Character();
        c1.setHelmet(nonExistingHelmet);
        assertNull(c1.getHelmetObject());
    }

    /**
     * Existing helmet should throw no exception when getting helmet object
     */
    @Test
    public void getHelmetObject_ExistingItem_noExceptionThrown()
    {
        Character c1 = new Character();
        c1.setHelmet(existingHelmet);
        try
        {
            c1.getHelmetObject();
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    /**
     * Existing helmet should affect and enhance armor class
     */
    @Test
    public void getHelmetObject_ExistingItem_modifiedArmorClass()
    {
        Character c1 = new Character();
        c1.setHelmet(existingHelmet);
        try
        {
            int expectedArmorClass = defaultArmorClass + (int) Math.ceil((double) itemLevel / (double) 4);
            assertEquals(expectedArmorClass, c1.getArmorClass());
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    /**
     * If not set, boots object should throw an exception when trying to fetch
     */
    @Test
    public void getBootsObject_NoItemSet_throwsException()
    {
        Character c1 = new Character();
        assertNull(c1.getArmorObject());
    }

    /**
     * Non-Existing boots should throw exception when getting boots object
     */
    @Test
    public void getBootsObject_NonExistentItem_throwsException()
    {
        Character c1 = new Character();
        c1.setBoots(nonExistingBoots);
        assertNull(c1.getBootsObject());
    }

    /**
     * Existing boots should throw no exception when getting boots object
     */
    @Test
    public void getBootsObject_ExistingItem_noExceptionThrown()
    {
        Character c1 = new Character();
        c1.setBoots(existingBoots);
        assertNotNull(c1.getBootsObject());
    }

    /**
     * Existing boots should affect and enhance armor class
     */
    @Test
    public void getBootsObject_ExistingItem_modifiedArmorClass()
    {
        Character c1 = new Character();
        c1.setBoots(existingBoots);        
        try
        {
            int expectedArmorClass = defaultArmorClass + (int) Math.ceil((double) itemLevel / (double) 4);
            assertEquals(expectedArmorClass, c1.getArmorClass());
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    /**
     * Destroy all the dynamically created items
     */
    @After
    public void destroyCreatedObjects()
    {
        ItemJaxb.deleteItemXml(existingArmor);
        ItemJaxb.deleteItemXml(existingRing);
        ItemJaxb.deleteItemXml(existingShield);
        ItemJaxb.deleteItemXml(existingHelmet);
        ItemJaxb.deleteItemXml(existingBoots);
    }
}
