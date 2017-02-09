package character.wearables.weapons;

/**
 * Longsword is a Melee Weapon which has a score of
 *
 * @author Supreet Singh (s_supree)
 * @since 1.0.0
 */
public class Longsword implements WeaponsInterface{

    private WeaponsInterface.type type = WeaponsInterface.type.valueOf("melee");
    private int diceSides = 0;
    private int numberOfRolls = 0;
    
	@Override
	public int getDiceSides() {
		return this.diceSides;
	}

	@Override
	public int getNumberOfRolls() {
		return this.numberOfRolls;
	}
}
