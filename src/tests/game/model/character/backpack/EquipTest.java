package tests.game.model.character.backpack;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import game.model.Item;
import game.model.character.Backpack;

public class EquipTest
{
    private Backpack backpack;
    private Item existingItem;

    @Before
    public void setVars()
    {
        this.backpack = new Backpack();
        Item existingItem = new Item("existingItem", "Weapon", "Constitution", 1);
    }
    
    @Test
    public void EquipItem_ValidTest()
    {
        Item i = new Item("nonExistingItem", "Weapon", "Constitution", 1);
        try
        {
            this.backpack.equip(i);
            assertTrue(true);
        }
        catch (Throwable e)
        {
            fail("Exception not expected");
        }
    }
    
    /**
     * 
     */
    @Test
    public void EquipItem_ExistingItem()
    {
        Item i = new Item("existingItem", "Weapon", "Constitution", 1);
        
        try
        {
            this.backpack.equip(existingItem);
            this.backpack.equip(i);
            fail("Exception expected");
        }
        catch (AssertionError e)
        {
            fail(e.getMessage());
        }
        catch (Throwable e)
        {
            assertTrue(true);
        }
            
        
    }
    
    @Test
    public void EquipManyItems_ThrowException(){
        try
        {
            Item dummy = null;
            for (int i = 0; i < 11; i++)
            {
                dummy = new Item("newItem"+i, "Weapon", "Constitution", 1);
                this.backpack.equip(dummy);
            }
            fail("Exception expected");
        }
        catch (AssertionError e)
        {
            fail(e.getMessage());
        }
        catch (Throwable e)
        {
            System.out.println(e.getMessage());
        }
    }
}
