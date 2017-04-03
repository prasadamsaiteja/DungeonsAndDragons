package game.model.decorator;

public class SlayingEffect extends WeaponDecorator{

	public SlayingEffect(String name, String type, String itemclass, int level) {
		super(name, type, itemclass, level);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getTargetDamage(Character Targetcharacter) {
		
		return super.getTargetDamage(Targetcharacter);
	}
}
