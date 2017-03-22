package game.views.jdialogs;

import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import java.awt.Color;

import game.components.ExtensionMethods;
import game.model.character.Character;

/**
 * This class is for inventory view and its related operations that is a dialog
 * 
 * @author SaiTeja
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class InventoryViewDialog extends JDialog implements ItemListener, Observer
{
    
    private Character character;
    private JPanel panel;
    
    /**
     * Constructor for assigning character
     * @param characterObject object
     */
    public InventoryViewDialog(Character characterObject){
        character = characterObject;
        this.draw();
    }
    
    /**
     * Sets the basic properties and initializes components
     */
    private void draw(){
        
        DialogHelper.setDialogProperties(this, "Backpack", new Rectangle(430, 350));
        getContentPane().setLayout(null);
        panel = new JPanel();
        initComponents();          
    }

    /**
     * This method initializes the UI components of Inventory dialog
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void initComponents() {                
        
        panel.removeAll();
        panel.setBackground(Color.WHITE);
        panel.setBounds(10, 11, 408, 298);
        getContentPane().add(panel);
        panel.setLayout(null);
        
        JLabel lblHelmet = new JLabel("Helmet");
        lblHelmet.setBounds(10, 21, 84, 14);
        panel.add(lblHelmet);
        
        DefaultComboBoxModel<String> helmetComboBoxModel = new DefaultComboBoxModel(character.getAllHelmets().toArray());
        JComboBox<String> helmetComboBox = new JComboBox<String>(helmetComboBoxModel);
        helmetComboBox.addItemListener(this);
        helmetComboBox.setBounds(104, 18, 294, 20);
        panel.add(helmetComboBox);
        
        JLabel lblArmor = new JLabel("Armor");
        lblArmor.setBounds(10, 59, 84, 14);
        panel.add(lblArmor);        
        
        DefaultComboBoxModel<String> armorComboBoxModel = new DefaultComboBoxModel(character.getAllArmor().toArray());
        JComboBox<String> armorComboBox = new JComboBox<String>(armorComboBoxModel);
        armorComboBox.addItemListener(this);
        armorComboBox.setBounds(104, 56, 294, 20);
        panel.add(armorComboBox);
        
        JLabel lblShield = new JLabel("Shield");
        lblShield.setBounds(10, 101, 84, 14);
        panel.add(lblShield);
        
        DefaultComboBoxModel<String> shieldComboBoxModel = new DefaultComboBoxModel(character.getAllShield().toArray());
        JComboBox<String> shieldcomboBox = new JComboBox<String>(shieldComboBoxModel);
        shieldcomboBox.addItemListener(this);
        shieldcomboBox.setBounds(104, 98, 294, 20);
        panel.add(shieldcomboBox);
        
        JLabel lblRing = new JLabel("Ring");
        lblRing.setBounds(10, 142, 84, 14);
        panel.add(lblRing);
        
        DefaultComboBoxModel<String> ringComboBoxModel = new DefaultComboBoxModel(character.getAllRing().toArray());
        JComboBox<String> ringComboBox = new JComboBox<String>(ringComboBoxModel);
        ringComboBox.addItemListener(this);
        ringComboBox.setBounds(104, 139, 294, 20);
        panel.add(ringComboBox);
        
        JLabel lblBelt = new JLabel("Belt");
        lblBelt.setBounds(10, 184, 84, 14);
        panel.add(lblBelt);
        
        DefaultComboBoxModel<String> beltComboBoxModel = new DefaultComboBoxModel(character.getAllBelts().toArray());
        JComboBox<String> beltComboBox = new JComboBox<String>(beltComboBoxModel);
        beltComboBox.addItemListener(this);
        beltComboBox.setBounds(104, 181, 294, 20);
        panel.add(beltComboBox);
        
        JLabel lblBoots = new JLabel("Boots");
        lblBoots.setBounds(10, 227, 84, 14);
        panel.add(lblBoots);
        
        DefaultComboBoxModel<String> bootsComboBoxModel = new DefaultComboBoxModel(character.getAllBoots().toArray());
        JComboBox<String> bootsComboBox = new JComboBox<String>(bootsComboBoxModel);
        bootsComboBox.addItemListener(this);
        bootsComboBox.setBounds(104, 224, 294, 20);
        panel.add(bootsComboBox);
        
        JLabel lblWeapon = new JLabel("Weapon");
        lblWeapon.setBounds(10, 270, 84, 14);
        panel.add(lblWeapon);
        
        DefaultComboBoxModel<String> weaponComboBoxModel = new DefaultComboBoxModel(character.getAllWeapons().toArray());
        JComboBox<String> weaponComboBox = new JComboBox<>(weaponComboBoxModel);
        weaponComboBox.addItemListener(this);
        weaponComboBox.setBounds(104, 267, 294, 20);
        panel.add(weaponComboBox);        
        
        panel.repaint();
        panel.revalidate();
    }

    /**
     * This overridden Method is called whenever Combo box item changes
     * @param event ItemEvent
     */
    @Override
    public void itemStateChanged(ItemEvent event) {
        
        if (event.getStateChange() == ItemEvent.SELECTED && character.isPlayer()) {
            
            String selectedItem = event.getItem().toString();
            
            if(ExtensionMethods.getByValue(character.getAllItems(), selectedItem) == null){
                Entry<String, String> equipItem = ExtensionMethods.getByValue(character.backpack.backpackItems, selectedItem);
                String unequipItem = character.items.get(equipItem.getKey());
                
                character.items.put(equipItem.getKey(), equipItem.getValue());
                character.backpack.backpackItems.put(equipItem.getKey(), unequipItem);
            }
                
        }
    }

    /**
     * This method updates the inventory dialog 
     * @param o Observable
     * @param arg Object
     */
    @Override
    public void update(Observable o, Object arg) {       
        character = (Character) o;        
        this.initComponents();
    }
}
