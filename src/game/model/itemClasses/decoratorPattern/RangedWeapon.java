package game.model.itemClasses.decoratorPattern;

import game.components.Dice;
import game.model.character.Character;

public class RangedWeapon extends Weapon {

    @Override
    public int damagePoints(Character character) {
        return (new Dice(1, 8, 1)).getRollSum();
    }

}
