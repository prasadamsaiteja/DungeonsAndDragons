package tests.game.model;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import game.components.GameMechanics;
import game.model.Map;
import game.views.jpanels.GamePlayScreen;

/**
 * This class is used to test game play
 * 
 * @author saiteja prasadam
 * @version 1.0.0
 * @since 2/28/2017
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GamePlayScreenTest {

    private static GamePlayScreen gameplayScreen;
    
    /**
     * This static method initalises map object variable to perform test operations
     */
    @BeforeClass
    public static void initVariables(){
        gameplayScreen = new GamePlayScreen("campaign 1", "saitej", true, true);                
    }
    
    /**
     * Tests Game Play Character
     */
    @Test
    public void test1GamePlayCharacter(){
        assertTrue(gameplayScreen.character.isPlayer());
    }
    
    /**
     * Tests Whether campaign has maps
     */
    @Test
    public void test2Campaign(){
        assertTrue(gameplayScreen.campaign.getMapList().size() > 0);
    }
    
    /**
     * Tests Map Validation
     */
    @Test
    public void test3MapValidation(){
        for (Map map : gameplayScreen.campaign.getMapList()) 
            assertNotNull(map);        
    }
    
    /**
     *  Tests Character Item Kind
     */
    @Test
    public void test4CharacterItemKind(){
        
        Set<String> itemsTypes = gameplayScreen.character.items.keySet();
        
        Set<String> names = new HashSet<String>();
        for (String itemType: itemsTypes) {            
          if (names.contains(itemType))
             fail("Duplicate item found");
          else 
            names.add(itemType);          
        }               
    }
   
    /**
     * Tests Character Forward Moments
     */
    @Test
    public void test5CharacterForwardMoments(){

        int[] previousPlayerPosition = GameMechanics.getPlayerPosition(gameplayScreen.currentMap);
        gameplayScreen.playerMomentMechanics.new UP_PRESSED().actionPerformed(null);                
        
        int[] presentPlayerPosition = GameMechanics.getPlayerPosition(gameplayScreen.currentMap);
        System.out.println("Present location" + previousPlayerPosition[0] + " " + previousPlayerPosition[1]  + "Present location"  + presentPlayerPosition[0] + " " + presentPlayerPosition[1]);

        assertNotEquals(previousPlayerPosition[0] + " " + previousPlayerPosition[1], presentPlayerPosition[0] + " " + presentPlayerPosition[1]);
    }
    
    /**
     * Tests Character Back Moments
     */
    @Test
    public void test8CharacterBackMoments(){

        int[] previousPlayerPosition = GameMechanics.getPlayerPosition(gameplayScreen.currentMap);
        gameplayScreen.playerMomentMechanics.new DOWN_PRESSED().actionPerformed(null);                
        
        int[] presentPlayerPosition = GameMechanics.getPlayerPosition(gameplayScreen.currentMap);
        assertNotEquals(previousPlayerPosition[0] + " " + previousPlayerPosition[1], presentPlayerPosition[0] + " " + presentPlayerPosition[1]);
    }
    
    /**
     * Tests Character Left Moments
     */
    @Test
    public void test7CharacterLeftMoments(){

        int[] previousPlayerPosition = GameMechanics.getPlayerPosition(gameplayScreen.currentMap);
        gameplayScreen.playerMomentMechanics.new LEFT_PRESSED().actionPerformed(null);                
        
        int[] presentPlayerPosition = GameMechanics.getPlayerPosition(gameplayScreen.currentMap);
        assertNotEquals(previousPlayerPosition[0] + " " + previousPlayerPosition[1], presentPlayerPosition[0] + " " + presentPlayerPosition[1]);
    }
    
    /**
     * Tests Character Right Moments
     */
    @Test
    public void test6CharacterRightMoments(){

        int[] previousPlayerPosition = GameMechanics.getPlayerPosition(gameplayScreen.currentMap);
        gameplayScreen.playerMomentMechanics.new RIGHT_PRESSED().actionPerformed(null);                
        
        int[] presentPlayerPosition = GameMechanics.getPlayerPosition(gameplayScreen.currentMap);
        assertNotEquals(previousPlayerPosition[0] + " " + previousPlayerPosition[1], presentPlayerPosition[0] + " " + presentPlayerPosition[1]);
    }

    /**
     * Tests Character Moments through wall
     */
    @Test
    public void testMapWall(){
        int[] previousPlayerPosition = GameMechanics.getPlayerPosition(gameplayScreen.currentMap);
        gameplayScreen.playerMomentMechanics.new DOWN_PRESSED().actionPerformed(null);                
        
        int[] presentPlayerPosition = GameMechanics.getPlayerPosition(gameplayScreen.currentMap);
        assertEquals(previousPlayerPosition[0] + " " + previousPlayerPosition[1], presentPlayerPosition[0] + " " + presentPlayerPosition[1]);
    }

    /**
     * /**
     * Tests Character looting a chest
     */
    @Test
    public void test_a_ChestLooting(){
        
        int previousBackpackCount = gameplayScreen.character.backpack.backpackItems.size();
        
        for(int index = 1; index <= 16; index++)
            gameplayScreen.playerMomentMechanics.new UP_PRESSED().actionPerformed(null);
        
        for(int index = 1; index <= 5; index++)
            gameplayScreen.playerMomentMechanics.new RIGHT_PRESSED().actionPerformed(null);
                
        assertTrue(gameplayScreen.character.backpack.backpackItems.size() > previousBackpackCount);
    }
    
    /**
     * Tests Loading a map
     */
    @Test
    public void test_b_MapLoading(){
         Map firstMap = gameplayScreen.currentMap;
         gameplayScreen.character.getMomentStrategy().moveToNextMap();
         Map nextMap = gameplayScreen.currentMap;
         assertNotSame(firstMap, nextMap);
    }
}