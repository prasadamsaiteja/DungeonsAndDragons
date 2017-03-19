package game.views.jdialogs.viewmodels;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import game.model.Item;
import game.model.character.Backpack;
import game.model.character.Character;
import game.model.character.CharactersList;

/**
 * Updates character view
 * @author Supreet Singh (s_supree)
 * 
 */
public class UpdateCharacterPanelInfo implements Observer
{
    private StyledDocument doc;
    private Character c;
    
    /**
     * @param doc styled document object
     */
    public UpdateCharacterPanelInfo(StyledDocument doc)
    {
        this.doc = doc;  
        addStylesToDocument(this.doc);
    }
    
    /**
     * add styles to a style doc
     * 
     * @param doc
     */
    private void addStylesToDocument(StyledDocument doc)
    {
        // Initialize some styles.
        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

        Style regular = doc.addStyle("regular", def);
        StyleConstants.setFontFamily(def, "SansSerif");

        Style s = doc.addStyle("italic", regular);
        StyleConstants.setItalic(s, true);

        s = doc.addStyle("bold", regular);
        StyleConstants.setBold(s, true);

        s = doc.addStyle("small", regular);
        StyleConstants.setFontSize(s, 10);

        s = doc.addStyle("large", regular);
        StyleConstants.setFontSize(s, 16);
    }
    
    /**
     * Set character name 
     * @param selectedValue
     * @throws BadLocationException
     */
    public void setCharacterName(String selectedValue) throws BadLocationException
    {
        if (this.c != null)
            this.c.deleteObserver(this);
        
        this.c = CharactersList.getByName(selectedValue);
        if (selectedValue != null)
        {
            this.c.addObserver(this);
            try
            {
                updateCharacterPreview();
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 
        }        
    }
    
    public void setCharacter(Character c) throws BadLocationException
    {
        if (this.c != null)
            this.c.deleteObserver(this);
        
        this.c = c;
        this.c.addObserver(this);
        try
        {
            updateCharacterPreview();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    private void updateCharacterPreview() throws Exception
    {
        if (c == null)
        {
            doc.remove(0, doc.getLength());
            doc.insertString(doc.getLength(), "Select a character to get his details",
                    doc.getStyle("bold"));
        }
        else
        {
            doc.remove(0, doc.getLength());
            doc.insertString(doc.getLength(), "Character Details:\n\n", doc.getStyle("bold"));
    
            doc.insertString(doc.getLength(), "Name: ", doc.getStyle("bold"));
            doc.insertString(doc.getLength(), c.getName(), doc.getStyle("italics"));
    
            doc.insertString(doc.getLength(), "\nClass: ", doc.getStyle("bold"));
            doc.insertString(doc.getLength(), c.getCharacterClass(), doc.getStyle("italics"));
    
            doc.insertString(doc.getLength(), "\nLevel: ", doc.getStyle("bold"));
            doc.insertString(doc.getLength(), String.valueOf(c.getLevel()), doc.getStyle("italics"));
    
            doc.insertString(doc.getLength(), "\n\nAbilities:\n ", doc.getStyle("bold"));
            doc.insertString(doc.getLength(), "\nStrength: ", doc.getStyle("bold"));
            doc.insertString(doc.getLength(), String.valueOf(c.getStrength()), doc.getStyle("italics"));
            
            doc.insertString(doc.getLength(), "\nStrength Modifier: ", doc.getStyle("bold"));
            doc.insertString(doc.getLength(), String.valueOf(c.getStrengthModifier()), doc.getStyle("italics"));
    
            doc.insertString(doc.getLength(), "\nDexterity: ", doc.getStyle("bold"));
            doc.insertString(doc.getLength(), String.valueOf(c.getDexterity()), doc.getStyle("italics"));
            
            doc.insertString(doc.getLength(), "\nDexterity Modifier: ", doc.getStyle("bold"));
            doc.insertString(doc.getLength(), String.valueOf(c.getDexterityModifier()), doc.getStyle("italics"));
    
            doc.insertString(doc.getLength(), "\nConstitution: ", doc.getStyle("bold"));
            doc.insertString(doc.getLength(), String.valueOf(c.getConstitution()), doc.getStyle("italics"));
            
            doc.insertString(doc.getLength(), "\nConstitution Modifier: ", doc.getStyle("bold"));
            doc.insertString(doc.getLength(), String.valueOf(c.getConstitutionModifier()), doc.getStyle("italics"));
    
            doc.insertString(doc.getLength(), "\nHit Score: ", doc.getStyle("bold"));
            doc.insertString(doc.getLength(), String.valueOf(c.getHitScore()), doc.getStyle("italics"));
    
            doc.insertString(doc.getLength(), "\nArmor Class: ", doc.getStyle("bold"));
            doc.insertString(doc.getLength(), String.valueOf(c.getArmorClass()), doc.getStyle("italics"));
    
            doc.insertString(doc.getLength(), "\nAttack Bonus: ", doc.getStyle("bold"));
            doc.insertString(doc.getLength(), String.valueOf(c.getAttackBonus()), doc.getStyle("italics"));
    
            doc.insertString(doc.getLength(), "\nDamage Bonus: ", doc.getStyle("bold"));
            doc.insertString(doc.getLength(), String.valueOf(c.getDamageBonus()), doc.getStyle("italics"));
    
            doc.insertString(doc.getLength(), "\n\nItems:\n ", doc.getStyle("bold"));
            doc.insertString(doc.getLength(), "\nWeapon: ", doc.getStyle("bold"));
            Item weaponObj = c.getWeaponObject();
            doc.insertString(doc.getLength(), String.valueOf(weaponObj.getItemName() + "("+weaponObj.getLevel()+")"), doc.getStyle("italics"));
    
            doc.insertString(doc.getLength(), "\nArmor: ", doc.getStyle("bold"));
            doc.insertString(doc.getLength(), String.valueOf(c.getArmor()), doc.getStyle("italics"));
    
            doc.insertString(doc.getLength(), "\nBoots: ", doc.getStyle("bold"));
            doc.insertString(doc.getLength(), String.valueOf(c.getBoots()), doc.getStyle("italics"));
    
            doc.insertString(doc.getLength(), "\nShield: ", doc.getStyle("bold"));
            doc.insertString(doc.getLength(), String.valueOf(c.getShield()), doc.getStyle("italics"));
    
            doc.insertString(doc.getLength(), "\nBelt: ", doc.getStyle("bold"));
            doc.insertString(doc.getLength(), String.valueOf(c.getBeltName()), doc.getStyle("italics"));
    
            doc.insertString(doc.getLength(), "\nRing: ", doc.getStyle("bold"));
            doc.insertString(doc.getLength(), String.valueOf(c.getRingName()), doc.getStyle("italics"));
    
            doc.insertString(doc.getLength(), "\nHelmet: ", doc.getStyle("bold"));
            doc.insertString(doc.getLength(), String.valueOf(c.getHelmet()), doc.getStyle("italics"));
            
            String backpackFileName = c.getBackpackFileName();
            doc.insertString(doc.getLength(), "\n\nBackpack Details:\n", doc.getStyle("bold"));
            try
            {
                Backpack b = Backpack.get(backpackFileName);
                Set<String> itemTypes = b.getItemTypes();
    
                if (itemTypes != null)
                {
                    for (String itemType : itemTypes)
                    {
                        doc.insertString(doc.getLength(), "\n" + itemType + "\n", doc.getStyle("bold"));
                        Iterator<String> i = b.getByType(itemType).iterator();
                        while (i.hasNext())
                        {
                            doc.insertString(doc.getLength(), "- " + i.next() + "\n", doc.getStyle("italic"));
                        }
                    }
                }
            }
            catch (Throwable e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void update(Observable o, Object arg)
    {
        try
        {
            System.out.println("notification from observable");
            updateCharacterPreview();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}