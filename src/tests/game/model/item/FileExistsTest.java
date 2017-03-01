package tests.game.model.item;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.junit.Before;

import game.model.jaxb.*;
import game.model.Item;
import game.model.Item.*;

/**
 * This test case is to test the creation of item
 * 
 * @author Priyanka A
 * @author Shikha Jhamb
 * @version 1.0
 * @since 2/28/2017
 *
 */

public class FileExistsTest
{

    private static Item item;
    private static String name;

    @Before
    public void beforeEachTest()
    {
        name = "Dagger";
        Item itemObj = new Item(name, "Weapon", "melee", 1);
        ItemJaxb.convertItemObjectToXml(itemObj);
    }

    @Test
    public void createItem()
    {
        item = ItemJaxb.getItemFromXml(name);
        assertEquals(name, item.getItemName());
    }
    
    @After
    public void destroyCreatedObjects()
    {
        ItemJaxb.deleteItemXml(name);
    }

}
