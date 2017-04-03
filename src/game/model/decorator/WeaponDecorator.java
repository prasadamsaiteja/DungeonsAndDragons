package game.model.decorator;

public class WeaponDecorator extends SimpleWeapon {

	public SimpleWeapon decoratedWeapon;
	
	public WeaponDecorator(String name, String type, String itemclass, int level) {
		super(name, type, itemclass, level);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getEnchantmentType() {
		
		return null;
	}

	@Override
	public String getWeaponType() {

		return null;
	}

	@Override
	public int getTargetDamage(Character Targetcharacter) {

		return 0;
	}

	@Override
	public void setEffect() {
		
	}

	@Override
	public int getRange() {
		return 0;
	}

}
