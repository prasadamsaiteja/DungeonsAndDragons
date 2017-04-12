package tests.game.model.character;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import game.views.jpanels.GamePlayScreen;

public class DamageModifiersTest {
	
    private GamePlayScreen gamePlayScreen;
    public static int staticDiesRoll = 5;
    
    @Before
    public void init(){                     
        gamePlayScreen = new GamePlayScreen("campaign 1", "saitej", true, true);
    }
    
    @Test
    public void meleeDamageModifier() {
        gamePlayScreen.character.setWeaponName("plain_melee_weapon");      
        int damagePoints = gamePlayScreen.character.calculateDamagePoints();
        assertEquals(staticDiesRoll + gamePlayScreen.character.getStrengthModifier(), damagePoints);
    }
    
    @Test
    public void rangedDamageModifier() {
        gamePlayScreen.character.setWeaponName("plain_ranged_weapon");
        int damagePoints = gamePlayScreen.character.calculateDamagePoints();
        assertEquals(staticDiesRoll, damagePoints);
    }

}
