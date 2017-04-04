package game.model.itemClasses.decoratorPattern.enchantments;

import game.model.character.Character;
import game.model.itemClasses.decoratorPattern.Weapon;
import game.model.itemClasses.decoratorPattern.WeaponDecorator;

public class SlayingEnchantment extends WeaponDecorator{

    public SlayingEnchantment(Weapon decoratedWeapon){
        super(decoratedWeapon);
    }
    
    @Override
    public int damagePoints(Character character) {
        // TODO Auto-generated method stub
        return 0;
    }

}