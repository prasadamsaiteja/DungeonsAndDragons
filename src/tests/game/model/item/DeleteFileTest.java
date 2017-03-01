package tests.game.model.item;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import game.model.Item;
import game.model.jaxb.*;

/**
 * This class is used to test the deletion of an item
 * 
 * @author Priyanka A
 * @author Shikha Jhamb
 * @version 1.0
 * @since 2/28/2017
 *
 */

public class DeleteFileTest
{
    private static String name;

    /**
     * This is a before method
     */

    @Before
    public void beforeEachTest()
    {
        name = "Dagger";
        Item itemObj = new Item(name, "Weapon", "melee", 1);
        ItemJaxb.convertItemObjectToXml(itemObj);
    }

    /**
     * This is testing the deletion method
     *
     */

    @Test
    public void deleteItem()
    {
        assertEquals(true, ItemJaxb.deleteItemXml(name));
    }

}
