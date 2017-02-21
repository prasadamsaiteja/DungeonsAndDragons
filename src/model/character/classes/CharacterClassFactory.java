package model.character.classes;

import java.util.ArrayList;

public class CharacterClassFactory {
	
	private static enum allowedClasses{Fighter,Rogue,Ninja}


	public static allowedClasses getValidParam(String className){
		System.out.println(className);
		return CharacterClassFactory.allowedClasses.valueOf(className);
	}
	
	public static ArrayList<String> getAllowedClasses(){
		ArrayList<String> classes = new ArrayList<String>();
		
		for (CharacterClassFactory.allowedClasses allowedClass: CharacterClassFactory.allowedClasses.values() ){	
			classes.add(allowedClass.toString());
		}
		
		return classes;
	}
	
	public static CharacterClassInterface get(CharacterClassFactory.allowedClasses characterClass){
		CharacterClassInterface classObj = null;		
		String characterClassName = "model.character.classes."+characterClass.toString();	
		try {
			classObj = (CharacterClassInterface) Class.forName(characterClassName).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return classObj;
	}
}
