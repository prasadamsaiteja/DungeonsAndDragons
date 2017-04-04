package tests.game.model.item;

import static org.junit.Assert.*;

import org.junit.Test;

import game.model.itemClasses.Item;

import org.junit.Before;

/**
 * This is used to test various methods used in Item class
 * 
 * @author Priyanka A
 * @version 1.0
 * @since 2/28/2017
 *
 */

public class ClassIntegrityTest
{
    public String name1, name2;
    public String type1, type2;
    public int level;
    public String classtype1, classtype2;
    Item obj1;
    Item obj2;

    /**
     * Initialisation of variables
     */
    @Before
    public void beforeEachTest()
    {
        name1 = "Dagger";
        name2 = "MyArmor";
        type1 = "Weapon";
        type2 = "Armor";
        level = 1;
        classtype1 = "Melee";
        classtype2 = "Heavy";
        obj1 = new Item(name1, type1, classtype1, level);
        obj2 = new Item(name2, type2, classtype2, level);
    }

    /**
     * Tests the item name, type, modifier
     */
    @Test
    public void testMethods()
    {
        assertEquals(name1, obj1.getItemName());
        assertEquals(type1, obj1.getItemType());
        assertEquals(15, obj2.getModifier());
    }

}
