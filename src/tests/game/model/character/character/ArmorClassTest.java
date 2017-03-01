package tests.game.model.character.character;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import game.model.Item;
import game.model.character.Character;
import game.model.jaxb.ItemJaxb;

/**
 * @author supreet (s_supree)
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

    private int itemLevel;

    @Before
    public void createVars()
    {
        nonExistingArmor = "nonExistingItem";
        existingArmor = "ExistingArmor";

        nonExistingRing = "nonExistingRing";
        existingRing = "existingRing";

        nonExistingShield = "nonExistingShield";
        existingShield = "existingShield";

        nonExistingBoots = "nonExistingBoots";
        existingBoots = "existingBoots";

        nonExistingHelmet = "nonExistingHelmet";
        existingHelmet = "existingHelmet";

        itemLevel = 5;

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

    private void createDummyItem(String itemName, String itemType, String itemClass, int armorLevel)
    {
        Item itemObj = new Item(itemName, itemType, itemClass, armorLevel);
        ItemJaxb.convertItemObjectToXml(itemObj);
    }

    @Test
    public void getArmorObject_NoItemSet_throwsException()
    {
        Character c1 = new Character();
        try
        {
            Item armorObjectItemNotSet = c1.getArmorObject();
            fail("Exception didn't throw");
        }
        catch (Exception e)
        {
        }
    }

    @Test
    public void getArmorObject_NonExistentArmor_throwsException()
    {
        Character c1 = new Character();
        c1.setArmor(nonExistingArmor);
        try
        {
            Item armorObjectNonExistentItem = c1.getArmorObject();
            fail("Exception didn't throw");
        }
        catch (Exception e)
        {
        }
    }

    @Test
    public void getArmorObject_ExistentArmor_noExceptionThrown()
    {
        Character c1 = new Character();
        c1.setArmor(existingArmor);
        try
        {
            Item armorObjectExistentArmor = c1.getArmorObject();
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void getArmorObject_ExistentArmor_modifiedArmorClass()
    {
        Character c1 = new Character();
        c1.setArmor(existingArmor);
        try
        {
            int expectedArmorClass = (int) Math.ceil((double) itemLevel / (double) 4);
            assertEquals(expectedArmorClass, c1.getArmorClass());
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void getRingObject_NoItemSet_throwsException()
    {
        Character c1 = new Character();
        try
        {
            Item ringObjectItemNotSet = c1.getArmorObject();
            fail("Exception didn't throw");
        }
        catch (Exception e)
        {
        }
    }

    @Test
    public void getRingObject_NonExistentItem_throwsException()
    {
        Character c1 = new Character();
        c1.setRingName(nonExistingRing);
        try
        {
            Item ringObjectNonExistentItem = c1.getRingObject();
            fail("Exception didn't throw");
        }
        catch (Exception e)
        {
        }
    }

    @Test
    public void getRingObject_ExistingItem_noExceptionThrown()
    {
        Character c1 = new Character();
        c1.setRingName(existingRing);
        try
        {
            Item ringObjectExistingItem = c1.getRingObject();
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void getRingObject_ExistingItem_modifiedArmorClass()
    {
        Character c1 = new Character();
        c1.setRingName(existingRing);
        try
        {
            int expectedArmorClass = (int) Math.ceil((double) itemLevel / (double) 4);
            assertEquals(expectedArmorClass, c1.getArmorClass());
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void getShieldObject_NoItemSet_throwsException()
    {
        Character c1 = new Character();
        try
        {
            Item shieldObjectItemNotSet = c1.getArmorObject();
            fail("Exception didn't throw");
        }
        catch (Exception e)
        {
        }
    }

    @Test
    public void getShieldObject_NonExistentItem_throwsException()
    {
        Character c1 = new Character();
        c1.setRingName(nonExistingShield);
        try
        {
            Item shieldObjectNonExistentItem = c1.getRingObject();
            fail("Exception didn't throw");
        }
        catch (Exception e)
        {
        }
    }

    @Test
    public void getShieldObject_ExistingItem_noExceptionThrown()
    {
        Character c1 = new Character();
        c1.setShield(existingShield);
        try
        {
            Item shieldObjectExistingItem = c1.getShieldObject();
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void getShieldObject_ExistingItem_modifiedArmorClass()
    {
        Character c1 = new Character();
        c1.setShield(existingShield);
        try
        {
            int expectedArmorClass = (int) Math.ceil((double) itemLevel / (double) 4);
            assertEquals(expectedArmorClass, c1.getArmorClass());
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void getHelmetObject_NoItemSet_throwsException()
    {
        Character c1 = new Character();
        try
        {
            Item helmetObjectItemNotSet = c1.getArmorObject();
            fail("Exception didn't throw");
        }
        catch (Exception e)
        {
        }
    }

    @Test
    public void getHelmetObject_NonExistentItem_throwsException()
    {
        Character c1 = new Character();
        c1.setHelmet(nonExistingHelmet);
        try
        {
            Item helmetObjectNonExistentItem = c1.getHelmetObject();
            fail("Exception didn't throw");
        }
        catch (Exception e)
        {
        }
    }

    @Test
    public void getHelmetObject_ExistingItem_noExceptionThrown()
    {
        Character c1 = new Character();
        c1.setHelmet(existingHelmet);
        try
        {
            Item helmetObjectExistingItem = c1.getHelmetObject();
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void getHelmetObject_ExistingItem_modifiedArmorClass()
    {
        Character c1 = new Character();
        c1.setHelmet(existingHelmet);
        try
        {
            int expectedArmorClass = (int) Math.ceil((double) itemLevel / (double) 4);
            assertEquals(expectedArmorClass, c1.getArmorClass());
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void getBootsObject_NoItemSet_throwsException()
    {
        Character c1 = new Character();
        try
        {
            Item bootsObjectItemNotSet = c1.getArmorObject();
            fail("Exception didn't throw");
        }
        catch (Exception e)
        {
        }
    }

    @Test
    public void getBootsObject_NonExistentItem_throwsException()
    {
        Character c1 = new Character();
        c1.setBoots(nonExistingBoots);
        try
        {
            Item bootsObjectNonExistentItem = c1.getBootsObject();
            fail("Exception didn't throw");
        }
        catch (Exception e)
        {
        }
    }

    @Test
    public void getBootsObject_ExistingItem_noExceptionThrown()
    {
        Character c1 = new Character();
        c1.setBoots(existingBoots);
        try
        {
            Item bootsObjectExistingItem = c1.getBootsObject();
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void getBootsObject_ExistingItem_modifiedArmorClass()
    {
        Character c1 = new Character();
        c1.setBoots(existingBoots);
        try
        {
            int expectedArmorClass = (int) Math.ceil((double) itemLevel / (double) 4);
            assertEquals(expectedArmorClass, c1.getArmorClass());
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

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
