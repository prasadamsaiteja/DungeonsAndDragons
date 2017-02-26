package game.model.character.wearables.weapons;

/**
 * Longsword is a Melee Weapon which has a score of
 *
 * @author Supreet Singh (s_supree)
 * @since 1.0.0
 */

public class Bow extends WeaponAbstract
{
    public Bow()
    {
        this.weaponLevel = 2;
        this.setType("melee");
    }
}
