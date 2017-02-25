package game.model.character.wearables.weapons;

abstract public class WeaponAbstract implements WeaponsInterface {	
	WeaponsInterface.type type;
	protected int diceSides = 0;
    protected int numberOfRolls = 0;
    protected int weaponLevel = 1;
    
    
	@Override
	public int getDiceSides() {
		return this.diceSides;
	}

	@Override
	public int getNumberOfRolls() {
		return this.numberOfRolls;
	}
	
	@Override
	public int getWeaponLevel(){
		return this.weaponLevel;
	};
	
	@Override
	public WeaponsInterface.type getType(){
		return this.type;
	}
	
	protected void setType(String type){
		this.type = WeaponsInterface.type.valueOf(type);
	}
}
