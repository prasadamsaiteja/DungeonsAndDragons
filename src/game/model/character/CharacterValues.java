package game.model.character;
import java.util.HashMap;

/**
 * Build a new character
 * 
 * @author Supreet Singh (s_supree)
 *
 */
public class CharacterValues
{

    private int level = 1;
    private int strength;
    private int dexterity;
    private int constitution;
    private int hitScore = 0;
    private HashMap<String, String> items;
    private String characterClass;
    private String characterType;
    private String name;
    private String fname;

    /**
     * @return
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * @return
     */
    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getHitScore() {
        return hitScore;
    }

    public void setHitScore(int hitScore) {
        this.hitScore = hitScore;
    }

    public HashMap<String, String> getItems() {
        return items;
    }

    public void setItems(HashMap<String, String> items) {
        this.items = items;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }

    public String getCharacterType() {
        return characterType;
    }

    public void setCharacterType(String characterType) {
        this.characterType = characterType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getBackpackFileName() {
        return backpackFileName;
    }

    public void setBackpackFileName(String backpackFileName) {
        this.backpackFileName = backpackFileName;
    }

    /**
     * @return if the character is built or not
     */
    public boolean isBuilt() {
        return isBuilt;
    }

    public void setBuilt(boolean built) {
        isBuilt = built;
    }

    private String backpackFileName;
    private boolean isBuilt = false;
}