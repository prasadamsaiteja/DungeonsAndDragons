package tests.game.model.character.character;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import game.components.GameMechanics;
import game.model.character.strategyPattern.strategies.FreezingStrategy;
import game.model.character.strategyPattern.strategies.FriendlyNPC;
import game.views.jpanels.GamePlayScreen;

public class strategyTesting {

    private static GamePlayScreen gamePlayScreen;
    
    @Before
    public void init(){
        gamePlayScreen = new GamePlayScreen("campaign 1", "saitej", true, true);
    }
    
    @Test
    public void testFreezingStrategy(){
        
        gamePlayScreen.character.setMomentStrategy(new FreezingStrategy(gamePlayScreen, gamePlayScreen.character, 3));
        int[] playerPositionBeforeTurn = GameMechanics.getPlayerPosition(gamePlayScreen.currentMap);
        
        gamePlayScreen.character.getMomentStrategy().playTurn();
        int[] playerPositionAfterTurn = GameMechanics.getPlayerPosition(gamePlayScreen.currentMap);
        
        assertEquals(playerPositionBeforeTurn[0], playerPositionAfterTurn[0]);
        assertEquals(playerPositionBeforeTurn[1], playerPositionAfterTurn[1]);
    }
    
    @Test
    public void testFriendlyStrategy(){
        
        gamePlayScreen.character.popMomentStrategy();
        gamePlayScreen.character.setMomentStrategy(new FriendlyNPC(gamePlayScreen, gamePlayScreen.character));        
        int[] playerPositionBeforeTurn = GameMechanics.getPlayerPosition(gamePlayScreen.currentMap);
        
        gamePlayScreen.character.getMomentStrategy().playTurn();
        int[] playerPositionAfterTurn = GameMechanics.getPlayerPosition(gamePlayScreen.currentMap);
        
        assertNotEquals(playerPositionBeforeTurn[0], playerPositionAfterTurn[0]);
        assertNotEquals(playerPositionBeforeTurn[1], playerPositionAfterTurn[1]);
    }
    
    
    
}
