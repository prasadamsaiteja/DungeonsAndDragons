package tests.game.model.item;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import game.components.GameMechanics;
import game.model.character.Character;
import game.views.jpanels.GamePlayScreen;

/**
 * This class is meant for testing weapon enchantment test
 * @author saiteja prasadam
 * @version 1.0.0
 */
public class WeaponEnchatmentTest {
    
    private GamePlayScreen gamePlayScreen;
    
    /**
     * This method init class objects
     */
    @Before
    public void init(){
        gamePlayScreen = new GamePlayScreen("campaign 1", "saitej", true, true);
    }
    
    /**
     * This method test slaying weapon enchantment
     */
    @Test
    public void testSlayingEnchantment(){
        
        gamePlayScreen.character.setWeaponName("custom_slaying_weapon");
        
        for(int i = 0; i < 3 ; i++)
            gamePlayScreen.playerMomentMechanics.new UP_PRESSED().actionPerformed(null);
        
        for(int i = 0; i < 18 ; i++)
            gamePlayScreen.playerMomentMechanics.new RIGHT_PRESSED().actionPerformed(null);
        
        assertEquals(0, GameMechanics.getAHostileMonster(gamePlayScreen.currentMap).getHitScore());
    }
    
    /**
     * This method test freezing weapon enchantment
     */
    @Test
    public void testFreezingEnchantment(){
        
        gamePlayScreen.character.setWeaponName("custom_freezing_weapon");        
        
        for(int i = 0; i < 3 ; i++)
            gamePlayScreen.playerMomentMechanics.new UP_PRESSED().actionPerformed(null);
        
        for(int i = 0; i < 18 ; i++)
            gamePlayScreen.playerMomentMechanics.new RIGHT_PRESSED().actionPerformed(null);
        
        int[] monsterPositionBeforeAttack = GameMechanics.getCharacterPosition(gamePlayScreen.currentMap, GameMechanics.getAHostileMonster(gamePlayScreen.currentMap));
        GameMechanics.getAHostileMonster(gamePlayScreen.currentMap).getMomentStrategy().playTurn();
        int[] monsterPositionAfterAttack = GameMechanics.getCharacterPosition(gamePlayScreen.currentMap, GameMechanics.getAHostileMonster(gamePlayScreen.currentMap));        
        
        assertEquals(monsterPositionBeforeAttack[0], monsterPositionAfterAttack[0]);
        assertEquals(monsterPositionBeforeAttack[1], monsterPositionAfterAttack[1]);
    }
    
    @Test
    public void testFrighteningEnchatment(){

        gamePlayScreen.character.setWeaponName("custom_frightening_weapon");        
        
        for(int i = 0; i < 3 ; i++)
            gamePlayScreen.playerMomentMechanics.new UP_PRESSED().actionPerformed(null);
        
        for(int i = 0; i < 18 ; i++)
            gamePlayScreen.playerMomentMechanics.new RIGHT_PRESSED().actionPerformed(null);
        
        int[] monsterPositionBeforeTurn = GameMechanics.getCharacterPosition(gamePlayScreen.currentMap, GameMechanics.getAHostileMonster(gamePlayScreen.currentMap));                
        GameMechanics.getAHostileMonster(gamePlayScreen.currentMap).getMomentStrategy().playTurn();
        int[] monsterPositionAfterTurn = GameMechanics.getCharacterPosition(gamePlayScreen.currentMap, GameMechanics.getAHostileMonster(gamePlayScreen.currentMap));
        
        int[] playerPosition = GameMechanics.getPlayerPosition(gamePlayScreen.currentMap);
        assertTrue(playerPosition[0] - monsterPositionBeforeTurn[0] < playerPosition[0] - monsterPositionAfterTurn[0] || playerPosition[1] - monsterPositionBeforeTurn[1] < playerPosition[1] - monsterPositionAfterTurn[1]);
    }
    
    @Test
    public void testPacifyingEnchatment(){

        gamePlayScreen.character.setWeaponName("custom_pacifying_weapon");  
        int[] monsterPosition = GameMechanics.getCharacterPosition(gamePlayScreen.currentMap, GameMechanics.getAHostileMonster(gamePlayScreen.currentMap));
        
        for(int i = 0; i < 3 ; i++)
            gamePlayScreen.playerMomentMechanics.new UP_PRESSED().actionPerformed(null);
        
        for(int i = 0; i < 18 ; i++)
            gamePlayScreen.playerMomentMechanics.new RIGHT_PRESSED().actionPerformed(null);
                     
        assertTrue(((Character) gamePlayScreen.currentMap.mapData[monsterPosition[0]][monsterPosition[1]]).getIsFriendlyMonster());
    }
    
    @Test
    public void testBurningEnchatment(){
        
        gamePlayScreen.character.setWeaponName("custom_burning_weapon");  
        int monsterHpBeforeAttack = GameMechanics.getAHostileMonster(gamePlayScreen.currentMap).getHitScore();
        
        for(int i = 0; i < 3 ; i++)
            gamePlayScreen.playerMomentMechanics.new UP_PRESSED().actionPerformed(null);
        
        for(int i = 0; i < 18 ; i++)
            gamePlayScreen.playerMomentMechanics.new RIGHT_PRESSED().actionPerformed(null);
        
        GameMechanics.getAHostileMonster(gamePlayScreen.currentMap).getMomentStrategy().playTurn();
        assertTrue(GameMechanics.getAHostileMonster(gamePlayScreen.currentMap).getHitScore() < monsterHpBeforeAttack);
    }

}
