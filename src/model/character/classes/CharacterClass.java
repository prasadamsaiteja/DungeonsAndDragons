package model.character.classes;

import java.util.ArrayList;
import java.util.HashMap;

import javax.management.RuntimeErrorException;

import GameComponents.Dice;
import model.character.Character;

public class CharacterClass {

	private Character character;
	private int hitScore;
	private String name;
	// Store allowed character classes
	
	private HashMap<String, HashMap<String, Integer>> allowedCharacterList = new HashMap();
	
	public CharacterClass(String name, Character character){
		if (!CharacterClass.isClassAllowed(name)){
			throw new RuntimeErrorException(null);
		}
		
		this.name = name;
		this.character = character;
				
	}

	public static boolean isClassAllowed(String cClass){
		return false;
	}
	
	public void /* replace with a return type */ getAllowedClasses(){
		
	}
	
	public void calculateHitScore(){
		this.hitScore = 0;
	}
	
	public int getHitScore(Dice dice){
		int hitPoints = 0;
		
		hitPoints += this.character.getConstitution() * this.character.getLevel();
		
		if (this.character.getLevel() > 1){
			int diceRoll = dice.getRollSum();
			hitPoints += diceRoll; 
		}else{
			// hitPoints += this.getDiceSides();
		}
		
		System.out.println("calculated hit points: " + hitPoints);
		
		return hitPoints;
	}
	
}
