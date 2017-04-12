package tests.game.model.character;

import game.components.GameMechanics;
import game.views.jpanels.GamePlayScreen;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CombatAttackRollTest {

    private GamePlayScreen gamePlayScreen;
    public static int dice = 18;
    @Before
    public void init(){
        gamePlayScreen = new GamePlayScreen("campaign 1", "saitej", true, true);
    }
    
    /**
     * This method test combat attack
     */
    @Test
    public void testAttackBonus(){
                
        int hp = GameMechanics.getAHostileMonster(gamePlayScreen.currentMap).getHitScore();
        
        for(int i = 0; i < 3 ; i++)
            gamePlayScreen.playerMomentMechanics.new UP_PRESSED().actionPerformed(null);
        
        for(int i = 0; i < 17 ; i++)
            gamePlayScreen.playerMomentMechanics.new RIGHT_PRESSED().actionPerformed(null);               
        
        if(dice + gamePlayScreen.character.getAttackBonus() >= GameMechanics.getAHostileMonster(gamePlayScreen.currentMap).getArmorClass()){
            gamePlayScreen.playerMomentMechanics.new RIGHT_PRESSED().actionPerformed(null);
            assertTrue(GameMechanics.getAHostileMonster(gamePlayScreen.currentMap).getHitScore() < hp);
        }        
    }
    
    /**
     * This method test the range of melee weapon
     */
    @Test
    public void testMeleeWeapon(){
        
        gamePlayScreen.character.setWeaponName("plain_melee_weapon");
        int hp = GameMechanics.getAHostileMonster(gamePlayScreen.currentMap).getHitScore();
        
        for(int i = 0; i < 3 ; i++)
            gamePlayScreen.playerMomentMechanics.new UP_PRESSED().actionPerformed(null);
        
        for(int i = 0; i < 18 ; i++)
            gamePlayScreen.playerMomentMechanics.new RIGHT_PRESSED().actionPerformed(null);               
        
        assertTrue(GameMechanics.getAHostileMonster(gamePlayScreen.currentMap).getHitScore() < hp);        
    }
    
    /**
     * This method test the range of ranged weapon
     */
    @Test
    public void testRangedWeapon(){
        
        gamePlayScreen.character.setWeaponName("plain_ranged_weapon");
        int hp = GameMechanics.getAHostileMonster(gamePlayScreen.currentMap).getHitScore();
        
        for(int i = 0; i < 2 ; i++)
            gamePlayScreen.playerMomentMechanics.new UP_PRESSED().actionPerformed(null);
        
        for(int i = 0; i < 18 ; i++)
            gamePlayScreen.playerMomentMechanics.new RIGHT_PRESSED().actionPerformed(null);               
        
        assertTrue(GameMechanics.getAHostileMonster(gamePlayScreen.currentMap).getHitScore() < hp);        
    }
    
}
