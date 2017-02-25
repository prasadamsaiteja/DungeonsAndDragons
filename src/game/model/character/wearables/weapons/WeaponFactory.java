package game.model.character.wearables.weapons;

import java.util.ArrayList;

public class WeaponFactory {
	private static enum allowedWeapons{Longsword,Bow,Dagger}

	public static allowedWeapons getValidParam(String weaponName){
		return WeaponFactory.allowedWeapons.valueOf(weaponName);
	}
	
	public static ArrayList<String> getAllowedWeapons(int level){
		ArrayList<String> weapons = new ArrayList<String>();
		
		for (WeaponFactory.allowedWeapons allowedClass: WeaponFactory.allowedWeapons.values() ){	
			int weaponLevel = 0;	
			String weaponClassName = "model.character.wearables.weapons."+allowedClass.toString();
			
			try {
				WeaponsInterface weaponObj = (WeaponsInterface) Class.forName(weaponClassName).newInstance();
				weaponLevel = weaponObj.getWeaponLevel();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			
			if (weaponLevel <= level){
				weapons.add(allowedClass.toString());
			}
		}
		
		return weapons;
	}
	
	public static WeaponsInterface get(WeaponFactory.allowedWeapons weapon){
		WeaponsInterface weaponObj = null;			
		String weaponName = "model.character.wearables.weapons."+weapon.toString();	
		try {
			weaponObj = (WeaponsInterface) Class.forName(weaponName).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return weaponObj;
	}
}