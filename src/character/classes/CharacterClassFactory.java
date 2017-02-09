package character.classes;

import java.util.ArrayList;

public class CharacterClassFactory {
	
	private static enum allowedClasses{Fighter}


	public static allowedClasses getValidParam(String className){
		return CharacterClassFactory.allowedClasses.valueOf(className);
	}
	
	public static ArrayList<String> getAllowedClasses(){
		ArrayList<String> classes = new ArrayList<String>();
		
		for (CharacterClassFactory.allowedClasses allowedClass: CharacterClassFactory.allowedClasses.values() ){	
			classes.add(allowedClass.toString());
		}
		
		return classes;
	}
	
	public static ClassInterface get(CharacterClassFactory.allowedClasses characterClass){
		ClassInterface classObj = null;		
		switch (characterClass){
		case Fighter:
			classObj = new Fighter();
			break;
		}		
		return classObj;
	}
}
