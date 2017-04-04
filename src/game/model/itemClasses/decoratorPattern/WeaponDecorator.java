package game.model.itemClasses.decoratorPattern;

import game.model.character.Character;

public class WeaponDecorator implements Weapon {

    protected final Weapon weapon;
    
    public WeaponDecorator(Weapon weapon){
        this.weapon = weapon;
    }
    
    @Override
    public int damagePoints(Character character) {
        return weapon.damagePoints(character);
    }
}