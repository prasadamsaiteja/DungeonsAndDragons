package game.views.jdialogs;

import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import javax.swing.BorderFactory;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Set;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import game.GameLauncher;
import game.components.ExtensionMethods;
import game.components.SharedVariables;
import game.model.Map;
import game.model.character.Backpack;
import game.model.character.Character;
import game.model.character.CharactersList;
import game.model.jaxb.CampaignJaxb;
import game.model.jaxb.ItemJaxb;
import game.model.jaxb.MapJaxb;
import game.views.jdialogs.campaignDialog.CampaignNameDialog;
import game.views.jdialogs.campaignDialog.NewCampaignInfoDialog;
import game.views.jdialogs.characterDialog.CreateCharacterDialog;
import game.views.jdialogs.viewmodels.CharactersListModel;
import game.views.jpanels.MapDesigner;

import java.awt.Font;
import java.awt.GridLayout;

/**
 * This class is a JDialog which helps user to create new campaign, map or
 * character.
 * 
 * @author saiteja prasdam
 * @version 1.0
 * @since 1/29/2017
 */

@SuppressWarnings("serial")
public class CreateStuffDialog extends JDialog
{

    private SpringLayout sl_mapsPanel;
    private SpringLayout sl_characterPanel;
    private int defaultTab = 0;
    private String lastCreateMapName;

    /**
     * This class is a JDialog which helps user to create new campaign, map or
     * character.
     * 
     * @param mapName
     */
    public CreateStuffDialog()
    {
        DialogHelper.setDialogProperties(this, "Create stuff", new Rectangle(100, 100, 640, 390));
        getContentPane().setLayout(null);
        initComponents();
    }

    /**
     * This class is a JDialog which helps user to create new campaign, map or
     * character.
     */
    public CreateStuffDialog(int defaultTabIndex, String mapName)
    {
        DialogHelper.setDialogProperties(this, "Create stuff", new Rectangle(100, 100, 640, 390));
        getContentPane().setLayout(null);
        defaultTab = defaultTabIndex;
        lastCreateMapName = mapName;
        initComponents();
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
     * Initializes the UI components.
     */
    private void initComponents()
    {

        JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
        tabbedPane.setBounds(10, 11, 615, 312);
        getContentPane().add(tabbedPane);

        // Campaign panel
        tabbedPane.addTab("Campaigns", null, initCampaignPanel(), null);

        // Maps panel
        tabbedPane.addTab("Maps", null, initMapsPanel(), null);

        // Character panel
        tabbedPane.addTab("Character", null, initCharacterPanel(), null);

        // Items panel
        tabbedPane.addTab("Items", null, initItemsPanel(), null);

        if (defaultTab != 0)
            tabbedPane.setSelectedIndex(defaultTab - 1);

        { // Done button
            JButton btnDone = new JButton("Done");
            btnDone.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    dispose();
                }
            });
            btnDone.setBounds(536, 327, 89, 23);
            getContentPane().add(btnDone);
        }
    }

    /**
     * Initializes the Character panel.
     * 
     * @param characterPanel This contains reference character panel tab.
     * @return
     */
    private JPanel initCharacterPanel()
    {

        JPanel characterPanel = new JPanel();
        characterPanel.setBackground(Color.WHITE);

        sl_characterPanel = new SpringLayout();
        characterPanel.setLayout(sl_characterPanel);

        CharactersListModel characterList = new CharactersListModel();
        JList<String> characterJlist = new JList<String>(characterList);

        // JScrollPane scrollPane = new JScrollPane();
        // scrollPane.setViewportView(characterJlist);

        sl_characterPanel.putConstraint(SpringLayout.NORTH, characterJlist, 10, SpringLayout.NORTH, characterPanel);
        sl_characterPanel.putConstraint(SpringLayout.WEST, characterJlist, 10, SpringLayout.WEST, characterPanel);
        sl_characterPanel.putConstraint(SpringLayout.EAST, characterJlist, 600, SpringLayout.WEST, characterPanel);
        characterJlist.setFont(new Font("Tahoma", Font.BOLD, 12));
        characterJlist.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        characterPanel.add(characterJlist);

        JButton btnAdd = new JButton("Create");
        sl_characterPanel.putConstraint(SpringLayout.SOUTH, characterJlist, -6, SpringLayout.NORTH, btnAdd);
        btnAdd.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new CreateCharacterDialog(CreateStuffDialog.this);
            }
        });
        sl_characterPanel.putConstraint(SpringLayout.NORTH, btnAdd, 251, SpringLayout.NORTH, characterPanel);
        sl_characterPanel.putConstraint(SpringLayout.WEST, btnAdd, -112, SpringLayout.EAST, characterPanel);
        sl_characterPanel.putConstraint(SpringLayout.EAST, btnAdd, -10, SpringLayout.EAST, characterPanel);
        characterPanel.add(btnAdd);

        JButton btnEdit = new JButton("Edit");
        sl_characterPanel.putConstraint(SpringLayout.NORTH, btnEdit, 6, SpringLayout.SOUTH, characterJlist);
        sl_characterPanel.putConstraint(SpringLayout.WEST, btnEdit, 10, SpringLayout.WEST, characterPanel);
        btnEdit.setEnabled(false);
        btnEdit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new CreateCharacterDialog(CreateStuffDialog.this,
                                          CharactersList.getByName(characterJlist.getSelectedValue()));
            }
        });
        characterPanel.add(btnEdit);

        JButton btnRemove = new JButton("Remove");
        sl_characterPanel.putConstraint(SpringLayout.NORTH, btnRemove, 251, SpringLayout.NORTH, characterPanel);
        sl_characterPanel.putConstraint(SpringLayout.EAST, btnEdit, -266, SpringLayout.WEST, btnRemove);
        btnRemove.setEnabled(false);
        btnRemove.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                CharactersList.getByName(characterJlist.getSelectedValue()).delete();
            }
        });
        sl_characterPanel.putConstraint(SpringLayout.WEST, btnRemove, -114, SpringLayout.WEST, btnAdd);
        sl_characterPanel.putConstraint(SpringLayout.EAST, btnRemove, -12, SpringLayout.WEST, btnAdd);
        characterPanel.add(btnRemove);

        // Create a new panel for peviewing selected character
        JPanel charPreviewPanel = new JPanel();
        sl_characterPanel.putConstraint(SpringLayout.NORTH, charPreviewPanel, 10, SpringLayout.NORTH, characterPanel);
        sl_characterPanel.putConstraint(SpringLayout.WEST, charPreviewPanel, 599, SpringLayout.WEST, characterPanel);
        sl_characterPanel.putConstraint(SpringLayout.SOUTH, charPreviewPanel, -6, SpringLayout.NORTH, btnAdd);
        sl_characterPanel.putConstraint(SpringLayout.EAST, charPreviewPanel, -10, SpringLayout.EAST, characterPanel);
        charPreviewPanel.setVisible(false);
        charPreviewPanel.setBackground(Color.WHITE);

        // Create a text pane
        JTextPane textPane = new JTextPane();
        StyledDocument doc = textPane.getStyledDocument();
        addStylesToDocument(doc);
        textPane.setEditable(false);
        try
        {
            doc.insertString(doc.getLength(), "Select a character to get his details", doc.getStyle("bold"));
        }
        catch (BadLocationException e1)
        {
            e1.printStackTrace();
        }
        charPreviewPanel.setLayout(null);

        JScrollPane areaScrollPane = new JScrollPane(textPane);
        areaScrollPane.setBounds(0, 0, 274, 235);
        areaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        areaScrollPane.setPreferredSize(new Dimension(250, 200));
        charPreviewPanel.add(areaScrollPane);
        characterPanel.add(charPreviewPanel);
        
        JButton btnBagpack = new JButton("Bagpack");
        sl_characterPanel.putConstraint(SpringLayout.NORTH, btnBagpack, 251, SpringLayout.NORTH, characterPanel);
        sl_characterPanel.putConstraint(SpringLayout.EAST, btnBagpack, -7, SpringLayout.WEST, btnRemove);
        btnBagpack.setEnabled(false);
        btnBagpack.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
               /* try
                {
                    new BackpackDialog();

		    //updatePreview(doc);
                }
                catch (Exception ignored)
                {
                    
                }*/
            }

        });
        characterPanel.add(btnBagpack);

        characterJlist.addListSelectionListener(new ListSelectionListener()
        {
            @SuppressWarnings("rawtypes")
            @Override
            public void valueChanged(ListSelectionEvent e)
            {

                try
                {
                    if (((JList) e.getSource()).getSelectedValue() == null)
                    {
                        btnRemove.setEnabled(false);
                        btnEdit.setEnabled(false);
                        btnBagpack.setEnabled(false);
                        charPreviewPanel.setVisible(false);
                        sl_characterPanel.putConstraint(SpringLayout.EAST, characterJlist, 600, SpringLayout.WEST,
                                characterPanel);
                        sl_characterPanel.putConstraint(SpringLayout.WEST, charPreviewPanel, 599, SpringLayout.WEST,
                                characterPanel);
                        doc.remove(0, doc.getLength());
                        doc.insertString(doc.getLength(), "Select a character to get his details",
                                doc.getStyle("bold"));
                    }
                    else
                    {
                        btnRemove.setEnabled(true);
                        btnEdit.setEnabled(true);
                        btnBagpack.setEnabled(true);
                        charPreviewPanel.setVisible(true);
                        sl_characterPanel.putConstraint(SpringLayout.WEST, charPreviewPanel, 6, SpringLayout.EAST,
                                characterJlist);
                        sl_characterPanel.putConstraint(SpringLayout.EAST, characterJlist, 320, SpringLayout.WEST,
                                characterPanel);
                        updateCharacterPreview(doc, (String) ((JList) e.getSource()).getSelectedValue());
                    }
                }
                catch (BadLocationException e1)
                {
                    e1.printStackTrace();
                }

            }

            private void updateCharacterPreview(StyledDocument doc, String selectedValue) throws BadLocationException
            {
                // get character information
                Character c = CharactersList.getByName(selectedValue);
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
                doc.insertString(doc.getLength(), String.valueOf(c.getWeaponName()), doc.getStyle("italics"));

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
                System.out.println(backpackFileName);
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

        });

        return characterPanel;
    }

    /**
     * Initializes the Map panel.
     * 
     * @param mapsPanel This contains reference to Map panel tab.
     * @return
     */
    private JPanel initMapsPanel()
    {

        JPanel mapsPanel = new JPanel();
        mapsPanel.setBackground(Color.WHITE);

        sl_mapsPanel = new SpringLayout();
        mapsPanel.setLayout(sl_mapsPanel);

        DefaultListModel<String> mapsJlistModel = new DefaultListModel<>();
        JList<String> mapsJlist = new JList<String>(mapsJlistModel);
        // JScrollPane scrollPane = new JScrollPane();
        // scrollPane.setViewportView(mapsJlist);

        sl_mapsPanel.putConstraint(SpringLayout.NORTH, mapsJlist, 10, SpringLayout.NORTH, mapsPanel);
        sl_mapsPanel.putConstraint(SpringLayout.WEST, mapsJlist, 10, SpringLayout.WEST, mapsPanel);

        String[] mapsList = ExtensionMethods.getMapsList();
        for (String mapName : mapsList)
            mapsJlistModel.addElement(mapName);

        mapsJlist.setFont(new Font("Tahoma", Font.BOLD, 12));
        mapsJlist.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        mapsPanel.add(mapsJlist);

        JButton btnAdd = new JButton("Create");
        sl_mapsPanel.putConstraint(SpringLayout.EAST, mapsJlist, 0, SpringLayout.EAST, btnAdd);
        btnAdd.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                new NewMapDialog(CreateStuffDialog.this);
            }
        });
        sl_mapsPanel.putConstraint(SpringLayout.NORTH, btnAdd, 251, SpringLayout.NORTH, mapsPanel);
        sl_mapsPanel.putConstraint(SpringLayout.WEST, btnAdd, -112, SpringLayout.EAST, mapsPanel);
        sl_mapsPanel.putConstraint(SpringLayout.EAST, btnAdd, -10, SpringLayout.EAST, mapsPanel);
        mapsPanel.add(btnAdd);

        JButton btnEdit = new JButton("Edit");
        sl_mapsPanel.putConstraint(SpringLayout.NORTH, btnEdit, 251, SpringLayout.NORTH, mapsPanel);
        sl_mapsPanel.putConstraint(SpringLayout.SOUTH, mapsJlist, -6, SpringLayout.NORTH, btnEdit);
        sl_mapsPanel.putConstraint(SpringLayout.WEST, btnEdit, 10, SpringLayout.WEST, mapsPanel);
        btnEdit.setEnabled(false);
        btnEdit.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (mapsJlist.getSelectedValue() != null)
                {
                    GameLauncher.mainFrameObject.replaceJPanel(new MapDesigner(mapsJlist.getSelectedValue()));
                    dispose();
                }
            }

        });
        mapsPanel.add(btnEdit);

        JButton btnRemove = new JButton("Remove");
        btnRemove.setEnabled(false);
        sl_mapsPanel.putConstraint(SpringLayout.NORTH, btnRemove, 251, SpringLayout.NORTH, mapsPanel);
        sl_mapsPanel.putConstraint(SpringLayout.EAST, btnEdit, -282, SpringLayout.WEST, btnRemove);
        sl_mapsPanel.putConstraint(SpringLayout.WEST, btnRemove, -114, SpringLayout.WEST, btnAdd);
        sl_mapsPanel.putConstraint(SpringLayout.EAST, btnRemove, -12, SpringLayout.WEST, btnAdd);
        btnRemove.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (mapsJlist.getSelectedValue() != null)
                {
                    MapJaxb.deleteMapXml(mapsJlist.getSelectedValue());
                    mapsJlistModel.clear();

                    String[] mapsList = ExtensionMethods.getMapsList();
                    for (String mapName : mapsList)
                        mapsJlistModel.addElement(mapName);
                }
            }

        });
        mapsPanel.add(btnRemove);

        JPanel mapPreviewPanel = new JPanel();
        mapPreviewPanel.setBackground(Color.WHITE);
        sl_mapsPanel.putConstraint(SpringLayout.NORTH, mapPreviewPanel, 10, SpringLayout.NORTH, mapsPanel);
        sl_mapsPanel.putConstraint(SpringLayout.WEST, mapPreviewPanel, 6, SpringLayout.EAST, mapsJlist);
        sl_mapsPanel.putConstraint(SpringLayout.SOUTH, mapPreviewPanel, -6, SpringLayout.NORTH, btnAdd);
        sl_mapsPanel.putConstraint(SpringLayout.EAST, mapPreviewPanel, -10, SpringLayout.EAST, mapsPanel);
        mapsPanel.add(mapPreviewPanel);

        mapsJlist.addListSelectionListener(new ListSelectionListener()
        {

            @SuppressWarnings("rawtypes")
            @Override
            public void valueChanged(ListSelectionEvent evt)
            {

                if (!evt.getValueIsAdjusting())
                {

                    if (((JList) evt.getSource()).getSelectedValue() == null)
                    {
                        btnRemove.setEnabled(false);
                        btnEdit.setEnabled(false);
                        sl_mapsPanel.putConstraint(SpringLayout.EAST, mapsJlist, 0, SpringLayout.EAST, btnAdd);
                    }

                    else
                    {
                        btnRemove.setEnabled(true);
                        btnEdit.setEnabled(true);
                        updateMapPreview(mapPreviewPanel, (String) ((JList) evt.getSource()).getSelectedValue());
                        sl_mapsPanel.putConstraint(SpringLayout.EAST, mapsJlist, -228, SpringLayout.EAST, btnAdd);
                    }
                }

            }

            private void updateMapPreview(JPanel mapPreviewPanel, String mapName)
            {

                Map mapObject = MapJaxb.getMapFromXml(mapName);

                mapPreviewPanel.removeAll();
                mapPreviewPanel.setLayout(new GridLayout(mapObject.mapWidth, mapObject.mapHeight));
                JPanel[][] mapJPanelArray = new JPanel[mapObject.mapWidth][mapObject.mapHeight];

                for (int i = 0; i < mapObject.mapWidth; i++)
                    for (int j = 0; j < mapObject.mapHeight; j++)
                    {
                        mapJPanelArray[i][j] = new JPanel();
                        mapJPanelArray[i][j]
                                .setBackground(SharedVariables.mapCellHashMap.get(mapObject.mapCellValues[i][j]));
                        mapJPanelArray[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                        mapPreviewPanel.add(mapJPanelArray[i][j]);
                    }
                mapPreviewPanel.revalidate();
                mapPreviewPanel.repaint();
            }
        });

        if (defaultTab == 2)
            mapsJlist.setSelectedValue(lastCreateMapName, true);

        return mapsPanel;
    }

    /**
     * Initializes the Items panel.
     * 
     * @param itemsPanel This contains reference to Items panel tab.
     * @return
     */
    private JPanel initItemsPanel()
    {

        JPanel itemsPanel = new JPanel();
        itemsPanel.setBackground(Color.WHITE);

        SpringLayout sl_itemsPanel = new SpringLayout();
        itemsPanel.setLayout(sl_itemsPanel);

        DefaultListModel<String> itemJlistModel = new DefaultListModel<>();
        JList<String> itemJlist = new JList<String>(itemJlistModel);
        // JScrollPane scrollPane = new JScrollPane();
        // scrollPane.setViewportView(itemJlist);

        String[] itemsList = ExtensionMethods.getItemsList();
        for (String itemName : itemsList)
            itemJlistModel.addElement(itemName);

        itemJlist.setFont(new Font("Tahoma", Font.BOLD, 12));
        itemJlist.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        sl_itemsPanel.putConstraint(SpringLayout.NORTH, itemJlist, 10, SpringLayout.NORTH, itemsPanel);
        sl_itemsPanel.putConstraint(SpringLayout.WEST, itemJlist, 10, SpringLayout.WEST, itemsPanel);
        sl_itemsPanel.putConstraint(SpringLayout.EAST, itemJlist, 600, SpringLayout.WEST, itemsPanel);
        itemsPanel.add(itemJlist);

        JButton btnAdd = new JButton("Create");
        sl_itemsPanel.putConstraint(SpringLayout.NORTH, btnAdd, 251, SpringLayout.NORTH, itemsPanel);
        sl_itemsPanel.putConstraint(SpringLayout.SOUTH, itemJlist, -6, SpringLayout.NORTH, btnAdd);
        sl_itemsPanel.putConstraint(SpringLayout.WEST, btnAdd, -112, SpringLayout.EAST, itemsPanel);
        sl_itemsPanel.putConstraint(SpringLayout.EAST, btnAdd, -10, SpringLayout.EAST, itemsPanel);
        btnAdd.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                new NewItemDialog(CreateStuffDialog.this);
            }
        });
        itemsPanel.add(btnAdd);

        JButton btnEdit = new JButton("Edit");
        btnEdit.setEnabled(false);
        sl_itemsPanel.putConstraint(SpringLayout.EAST, btnEdit, 92, SpringLayout.WEST, itemJlist);
        sl_itemsPanel.putConstraint(SpringLayout.NORTH, btnEdit, 6, SpringLayout.SOUTH, itemJlist);
        sl_itemsPanel.putConstraint(SpringLayout.WEST, btnEdit, 0, SpringLayout.WEST, itemJlist);
        btnEdit.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                new NewItemDialog(ItemJaxb.getItemFromXml(itemJlist.getSelectedValue()), CreateStuffDialog.this);
            }
        });
        itemsPanel.add(btnEdit);

        JButton btnRemove = new JButton("Remove");
        btnRemove.setEnabled(false);
        sl_itemsPanel.putConstraint(SpringLayout.NORTH, btnRemove, 6, SpringLayout.SOUTH, itemJlist);
        sl_itemsPanel.putConstraint(SpringLayout.WEST, btnRemove, -114, SpringLayout.WEST, btnAdd);
        sl_itemsPanel.putConstraint(SpringLayout.EAST, btnRemove, -12, SpringLayout.WEST, btnAdd);
        btnRemove.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                ItemJaxb.deleteItemXml(itemJlist.getSelectedValue());

                itemJlistModel.clear();
                String[] itemsList = ExtensionMethods.getItemsList();
                for (String itemName : itemsList)
                    itemJlistModel.addElement(itemName);
            }
        });
        itemsPanel.add(btnRemove);

        itemJlist.addListSelectionListener(new ListSelectionListener()
        {
            @SuppressWarnings("rawtypes")
            @Override
            public void valueChanged(ListSelectionEvent e)
            {

                if (((JList) e.getSource()).getSelectedValue() == null)
                {
                    btnRemove.setEnabled(false);
                    btnEdit.setEnabled(false);
                }

                else
                {
                    btnRemove.setEnabled(true);
                    btnEdit.setEnabled(true);
                }

            }
        });

        if (defaultTab == 4)
            itemJlist.setSelectedValue(lastCreateMapName, true);

        return itemsPanel;
    }

    /**
     * Initializes the Campaign panel.
     * 
     * @param campaignPanel This contains reference to Campaign panel tab.
     * @return
     */
    private JPanel initCampaignPanel()
    {

        JPanel campaignPanel = new JPanel();
        campaignPanel.setBackground(Color.WHITE);

        SpringLayout sl_campaignPanel = new SpringLayout();
        campaignPanel.setLayout(sl_campaignPanel);

        DefaultListModel<String> campaignJlistModel = new DefaultListModel<>();
        JList<String> campaignJlist = new JList<String>(campaignJlistModel);

        String[] campaignsList = ExtensionMethods.getCampaignsList();
        for (String campaignName : campaignsList)
            campaignJlistModel.addElement(campaignName);

        campaignJlist.setFont(new Font("Tahoma", Font.BOLD, 12));
        campaignJlist.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        sl_campaignPanel.putConstraint(SpringLayout.NORTH, campaignJlist, 10, SpringLayout.NORTH, campaignPanel);
        sl_campaignPanel.putConstraint(SpringLayout.WEST, campaignJlist, 10, SpringLayout.WEST, campaignPanel);
        sl_campaignPanel.putConstraint(SpringLayout.EAST, campaignJlist, 600, SpringLayout.WEST, campaignPanel);
        campaignPanel.add(campaignJlist);

        JButton btnAdd = new JButton("Create");
        sl_campaignPanel.putConstraint(SpringLayout.NORTH, btnAdd, 251, SpringLayout.NORTH, campaignPanel);
        sl_campaignPanel.putConstraint(SpringLayout.SOUTH, campaignJlist, -6, SpringLayout.NORTH, btnAdd);
        sl_campaignPanel.putConstraint(SpringLayout.WEST, btnAdd, -112, SpringLayout.EAST, campaignPanel);
        sl_campaignPanel.putConstraint(SpringLayout.EAST, btnAdd, -10, SpringLayout.EAST, campaignPanel);
        campaignPanel.add(btnAdd);

        btnAdd.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                new CampaignNameDialog();
                dispose();

            }
        });

        JButton btnEdit = new JButton("Edit");
        btnEdit.setEnabled(false);
        sl_campaignPanel.putConstraint(SpringLayout.EAST, btnEdit, 92, SpringLayout.WEST, campaignJlist);
        sl_campaignPanel.putConstraint(SpringLayout.NORTH, btnEdit, 6, SpringLayout.SOUTH, campaignJlist);
        sl_campaignPanel.putConstraint(SpringLayout.WEST, btnEdit, 0, SpringLayout.WEST, campaignJlist);
        btnEdit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (campaignJlist.getSelectedValue() != null)
                {
                    new NewCampaignInfoDialog(CampaignJaxb.getCampaignFromXml(campaignJlist.getSelectedValue()));
                }
            }
        });

        campaignPanel.add(btnEdit);

        JButton btnRemove = new JButton("Remove");
        btnRemove.setEnabled(false);
        sl_campaignPanel.putConstraint(SpringLayout.NORTH, btnRemove, 6, SpringLayout.SOUTH, campaignJlist);
        sl_campaignPanel.putConstraint(SpringLayout.WEST, btnRemove, -114, SpringLayout.WEST, btnAdd);
        sl_campaignPanel.putConstraint(SpringLayout.EAST, btnRemove, -12, SpringLayout.WEST, btnAdd);
        campaignPanel.add(btnRemove);

        btnRemove.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (campaignJlist.getSelectedValue() != null)
                {
                    CampaignJaxb.deleteCampaignXml(campaignJlist.getSelectedValue());
                    campaignJlistModel.clear();
                    String[] campaignsList = ExtensionMethods.getCampaignsList();
                    for (String campaignName : campaignsList)
                        campaignJlistModel.addElement(campaignName);
                }
            }
        });

        campaignJlist.addListSelectionListener(new ListSelectionListener()
        {
            @SuppressWarnings("rawtypes")
            @Override
            public void valueChanged(ListSelectionEvent e)
            {

                if (((JList) e.getSource()).getSelectedValue() == null)
                {
                    btnRemove.setEnabled(false);
                    btnEdit.setEnabled(false);
                }

                else
                {
                    btnRemove.setEnabled(true);
                    btnEdit.setEnabled(true);
                }

            }
        });

        return campaignPanel;
    }
}
