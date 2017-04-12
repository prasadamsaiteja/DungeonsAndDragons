package tests.game.model.character;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import game.components.GameMechanics;
import game.model.character.strategyPattern.strategies.AggresiveNPC;
import game.model.character.strategyPattern.strategies.ComputerPlayer;
import game.model.character.strategyPattern.strategies.FreezingStrategy;
import game.model.character.strategyPattern.strategies.FriendlyNPC;
import game.model.character.strategyPattern.strategies.FrighteningStrategy;
import game.model.character.Character;
import game.views.jpanels.GamePlayScreen;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StrategyTesting {

    private static GamePlayScreen gamePlayScreen;
    
    @Before
    public void init(){
        gamePlayScreen = new GamePlayScreen("campaign 1", "saitej", true, true);
    }
    
    @Test
    public void test1FreezingStrategy(){
        
        gamePlayScreen.character.setMomentStrategy(new FreezingStrategy(gamePlayScreen, gamePlayScreen.character, 3));
        int[] playerPositionBeforeTurn = GameMechanics.getPlayerPosition(gamePlayScreen.currentMap);
        
        gamePlayScreen.character.getMomentStrategy().playTurn();
        int[] playerPositionAfterTurn = GameMechanics.getPlayerPosition(gamePlayScreen.currentMap);
        
        assertEquals(playerPositionBeforeTurn[0], playerPositionAfterTurn[0]);
        assertEquals(playerPositionBeforeTurn[1], playerPositionAfterTurn[1]);
    }
    
    @Test
    public void testFriendlyStrategy(){
        
        Character friendlyMonster = GameMechanics.getAFriendlyMonster(gamePlayScreen.currentMap);
        
        friendlyMonster.setMomentStrategy(new FriendlyNPC(gamePlayScreen, friendlyMonster));        
        int[] playerPositionBeforeTurn = GameMechanics.getCharacterPosition(gamePlayScreen.currentMap, friendlyMonster);
                
        friendlyMonster.getMomentStrategy().playTurn();
        int[] playerPositionAfterTurn = GameMechanics.getCharacterPosition(gamePlayScreen.currentMap, friendlyMonster);
        
        assertTrue(playerPositionBeforeTurn[0] != playerPositionAfterTurn[0] || playerPositionBeforeTurn[1] != playerPositionAfterTurn[1]);
    }
    
    @Test
    public void test2MonsterStrategy(){
     
        Character monsterMonster = GameMechanics.getAHostileMonster(gamePlayScreen.currentMap);
        
        monsterMonster.setMomentStrategy(new AggresiveNPC(gamePlayScreen, monsterMonster));        
        int[] monsterPositionBeforeTurn = GameMechanics.getCharacterPosition(gamePlayScreen.currentMap, monsterMonster);
                
        monsterMonster.getMomentStrategy().playTurn();
        int[] monsterPositionAfterTurn = GameMechanics.getCharacterPosition(gamePlayScreen.currentMap, monsterMonster);
        int[] playerPosition = GameMechanics.getPlayerPosition(gamePlayScreen.currentMap);
        
        assertTrue(playerPosition[0] - monsterPositionBeforeTurn[0] < playerPosition[0] - monsterPositionAfterTurn[0] || playerPosition[1] - monsterPositionBeforeTurn[1] < playerPosition[1] - monsterPositionAfterTurn[1]);        
    }
    
    @Test
    public void test3FrightenedStrategy(){
     
        Character monsterMonster = GameMechanics.getAHostileMonster(gamePlayScreen.currentMap);
        
        monsterMonster.setMomentStrategy(new FrighteningStrategy(gamePlayScreen, monsterMonster, gamePlayScreen.character, 3));        
        int[] monsterPositionBeforeTurn = GameMechanics.getCharacterPosition(gamePlayScreen.currentMap, monsterMonster);
                
        monsterMonster.getMomentStrategy().playTurn();
        int[] monsterPositionAfterTurn = GameMechanics.getCharacterPosition(gamePlayScreen.currentMap, monsterMonster);
        int[] playerPosition = GameMechanics.getPlayerPosition(gamePlayScreen.currentMap);
        
        assertTrue(playerPosition[0] - monsterPositionBeforeTurn[0] > playerPosition[0] - monsterPositionAfterTurn[0] || playerPosition[1] - monsterPositionBeforeTurn[1] > playerPosition[1] - monsterPositionAfterTurn[1]);        
    }
    
    @Test
    public void test4ComputerStrategy(){
     
        gamePlayScreen.character.setMomentStrategy(new ComputerPlayer(gamePlayScreen));
        int[] playerPositionBeforeTurn = GameMechanics.getPlayerPosition(gamePlayScreen.currentMap);
        
        gamePlayScreen.character.getMomentStrategy().playTurn();
        int[] playerPositionAfterTurn = GameMechanics.getPlayerPosition(gamePlayScreen.currentMap);
        int[] monsterPosition = GameMechanics.getCharacterPosition(gamePlayScreen.currentMap, GameMechanics.getAHostileMonster(gamePlayScreen.currentMap));

        assertTrue(monsterPosition[0] - playerPositionBeforeTurn[0] > monsterPosition[0] - playerPositionAfterTurn[0] || monsterPosition[1] - playerPositionBeforeTurn[1] > monsterPosition[1] - playerPositionAfterTurn[1]);        
    }
    
}