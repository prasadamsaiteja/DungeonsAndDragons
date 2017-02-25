package game.model.character.wearables.weapons;

import game.interfaces.DiceImplementationInterface;

public interface WeaponsInterface extends DiceImplementationInterface {
	public enum type{melee,ranged};
	public int getWeaponLevel();
	public WeaponsInterface.type getType();
}
