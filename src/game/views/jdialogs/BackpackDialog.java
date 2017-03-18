package game.views.jdialogs;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import game.model.Item;
import game.model.character.Character;
import game.model.jaxb.ItemJaxb;
import game.views.jdialogs.viewmodels.BackpackDialogBackpackListModel;
import game.views.jdialogs.viewmodels.BackpackDialogCharacterListModel;
import javax.swing.JLabel;
import java.awt.Font;

@SuppressWarnings("serial")
class BackpackDialog extends JDialog {
    
    private Character c;
    
    public BackpackDialog(Character c){
        this.c = c;
        this.draw();
    }
    
    private void draw(){
        DialogHelper.setDialogProperties(this, "Backpack", new Rectangle(480, 350));
        getContentPane().setLayout(null);
        initComponents();  
    }

    private void initComponents() {
        
        JButton btnSaveButton = new JButton("Save");
        btnSaveButton.setBounds(332, 286, 89, 23);
        getContentPane().add(btnSaveButton);
        btnSaveButton.addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                c.save();
                dispose();
            }
        });
        
        JPanel backpackPanel = new JPanel();
        backpackPanel.setBackground(Color.WHITE);
        backpackPanel.setBounds(10, 11, 459, 264);
        getContentPane().add(backpackPanel);
        backpackPanel.setLayout(null);
        
        BackpackDialogCharacterListModel characterItemsListModel = new BackpackDialogCharacterListModel(this.c);
        JList<String> ItemsList = new JList<String>(characterItemsListModel);        
        ItemsList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.LIGHT_GRAY));
        ItemsList.setBounds(10, 33, 169, 220);
        backpackPanel.add(ItemsList);
        
        JLabel lblItemslist = new JLabel("Items ");
        lblItemslist.setBounds(72, 8, 46, 14);
        backpackPanel.add(lblItemslist);
        
        BackpackDialogBackpackListModel backpackItemsListModel = new BackpackDialogBackpackListModel(this.c);
        JList<String> backpackItemsList = new JList<String>(backpackItemsListModel);
        backpackItemsList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.LIGHT_GRAY));
        backpackItemsList.setBounds(278, 33, 163, 220);
        backpackPanel.add(backpackItemsList);
        
        JLabel lblBackpackItems = new JLabel("Backpack items");
        lblBackpackItems.setBounds(308, 8, 72, 14);
        backpackPanel.add(lblBackpackItems);
        
        JButton addItemButton = new JButton("+");
        addItemButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ItemsList.getSelectedValue() != null)
                {
                    Item i = ItemJaxb.getItemFromXml(ItemsList.getSelectedValue());
                    c.sendItemToBackpack(i);
                }
            }
        });
        addItemButton.setEnabled(false);
        addItemButton.setFont(new Font("Tahoma", Font.PLAIN, 8));
        addItemButton.setBounds(195, 87, 54, 39);
        backpackPanel.add(addItemButton);
        
        JButton removeItemButton = new JButton("-");
        removeItemButton.setEnabled(false);
        removeItemButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if(backpackItemsList.getSelectedValue() != null){
                    Item i = ItemJaxb.getItemFromXml(backpackItemsList.getSelectedValue());
                    c.getItemFromBackpack(i);    
                }
            }
        });
        removeItemButton.setFont(new Font("Tahoma", Font.PLAIN, 8));
        removeItemButton.setBounds(191, 138, 60, 39);
        backpackPanel.add(removeItemButton);
        
        ItemsList.addListSelectionListener(new ListSelectionListener() {
            
            @SuppressWarnings("rawtypes")
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (((JList) e.getSource()).getSelectedValue() == null)
                    addItemButton.setEnabled(false);

                else
                    addItemButton.setEnabled(true);
            }
        });
        
        backpackItemsList.addListSelectionListener(new ListSelectionListener() {
            
            @SuppressWarnings("rawtypes")
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (((JList) e.getSource()).getSelectedValue() == null)
                    removeItemButton.setEnabled(false);

                else
                    removeItemButton.setEnabled(true);
            }
        });
    }
}
