package game.views.jdialogs;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import game.components.ExtensionMethods;

import javax.swing.JLabel;
import java.awt.Font;

@SuppressWarnings("serial")
class BackpackDialog extends JDialog {
    
    public BackpackDialog(){
        DialogHelper.setDialogProperties(this, "Backpack", new Rectangle(480, 350));
        getContentPane().setLayout(null);
        initComponents();        
    }

    private void initComponents() {
        
        JButton btnSaveButton = new JButton("Save");
        btnSaveButton.setBounds(332, 286, 89, 23);
        getContentPane().add(btnSaveButton);
        
        JPanel backpackPanel = new JPanel();
        backpackPanel.setBackground(Color.WHITE);
        backpackPanel.setBounds(10, 11, 459, 264);
        getContentPane().add(backpackPanel);
        backpackPanel.setLayout(null);
        
        JList<String> ItemsList = new JList<String>();        
        ItemsList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, Color.LIGHT_GRAY));
        ItemsList.setBounds(10, 33, 169, 220);
        backpackPanel.add(ItemsList);
        
        JLabel lblItemslist = new JLabel("Items ");
        lblItemslist.setBounds(72, 8, 46, 14);
        backpackPanel.add(lblItemslist);
        
        DefaultListModel<String> backpackItemsListModel = new DefaultListModel<String>();
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
                    backpackItemsListModel.addElement(ItemsList.getSelectedValue());
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
                    int index = backpackItemsList.getSelectedIndex();
                    backpackItemsListModel.remove(index);
                    backpackItemsList.setSelectedIndex(index - 1);
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
