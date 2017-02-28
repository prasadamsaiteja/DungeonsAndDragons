package tests.game.model.character.character;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Observable;
import java.util.Observer;

import org.junit.Before;
import org.junit.Test;

import game.model.character.Character;

public class DrawTest
{
    private DummyObserver d1;
    private Character c1;

    private class DummyObserver implements Observer
    {
        private int dummyParam = 0;

        @Override
        public void update(Observable o, Object arg)
        {
            dummyParam = 1;
        }

        public int getDummyParam()
        {
            return dummyParam;
        }
    }

    @Before
    public void createVars()
    {
        d1 = new DummyObserver();
        c1 = new Character();
        c1.addObserver(d1);
    }

    @Test
    public void characterNotBuilt_noTriggerToObserver()
    {
        c1.setWeaponName("Longsword");
        assertEquals(0, d1.getDummyParam());
    }

    @Test
    public void characterBuilt_TriggerObserver()
    {
        try
        {
            Field f1 = Character.class.getDeclaredField("isBuilt");
            f1.setAccessible(true);
            f1.set(c1, true);
            c1.setWeaponName("Longsword");
            assertEquals(1, d1.getDummyParam());
        }
        catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

}
