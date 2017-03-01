package game.views.jdialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import game.GameLauncher;
import game.views.jpanels.MapDesigner;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;

/**
 * This class create a dialog which prompts user to enter map details such as
 * map name, width, height
 * 
 * @author saiteja prasdam
 * @version 1.0.0
 * @since 1/26/2017
 */
@SuppressWarnings("serial")
class NewMapDialog extends JDialog
{

    private JDialog parent;

    /**
     * This is a class constructor which creates dialog which helps creation of
     * maps
     * 
     * @param jdialog Parent JDilaog reference which is required to dispose
     *        parent dialog if needed.
     */
    NewMapDialog(JDialog jdialog)
    {

        this.parent = jdialog;
        DialogHelper.setDialogProperties(this, "New map", new Rectangle(100, 100, 250, 150));
        initComponents();
    }

    /**
     * This method initializes UI components
     */
    private void initComponents()
    {

        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        JTextField map_name_text_field = new JTextField();
        JFormattedTextField map_dimesions_y;
        JFormattedTextField map_dimesions_x;

        // Number formatter for dimensions
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMaximum(80);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

        { // Map name label
            JLabel map_name_label = new JLabel("Map name");
            map_name_label.setBounds(5, 5, 66, 34);
            contentPanel.add(map_name_label);
        }

        { // Map name text field
            map_name_text_field.setBounds(71, 11, 153, 23);
            contentPanel.add(map_name_text_field);
            map_name_text_field.setColumns(10);
        }

        { // Map dimension label
            JLabel lblNewLabel_1 = new JLabel("Map dimensions");
            lblNewLabel_1.setBounds(5, 39, 83, 34);
            contentPanel.add(lblNewLabel_1);
        }

        { // Map dimension text field width
            map_dimesions_x = new JFormattedTextField(formatter);
            map_dimesions_x.setBounds(110, 45, 32, 23);
            map_dimesions_x.setHorizontalAlignment(SwingConstants.CENTER);
            contentPanel.add(map_dimesions_x);
            map_dimesions_x.setColumns(10);
        }

        { // Map dimension text field height
            map_dimesions_y = new JFormattedTextField(formatter);
            map_dimesions_y.setColumns(10);
            map_dimesions_y.setHorizontalAlignment(SwingConstants.CENTER);
            map_dimesions_y.setBounds(179, 45, 32, 23);
            contentPanel.add(map_dimesions_y);
        }

        { // X label and next button
            JLabel x = new JLabel("x");
            x.setBounds(156, 45, 15, 20);
            contentPanel.add(x);
            {
                JPanel buttonPane = new JPanel();
                buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
                getContentPane().add(buttonPane, BorderLayout.SOUTH);
                {
                    JButton okButton = new JButton("Next");
                    okButton.addActionListener(new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            if (map_name_text_field.getText().length() < 4)
                            {
                                DialogHelper.showBasicDialog("Map name should be atleast 5 characters");
                                return;
                            }

                            try
                            {
                                if (Integer.parseInt(map_dimesions_x.getText()) < 4
                                    || Integer.parseInt(map_dimesions_y.getText()) < 4)
                                {
                                    DialogHelper.showBasicDialog("Map dimension should be atleast 5x5 matrix");
                                    return;
                                }
                            }

                            catch (Exception ex)
                            {
                                DialogHelper.showBasicDialog("Map dimension should be atleast 5x5 matrix");
                                return;
                            }

                            if (parent != null)
                                parent.dispose();
                            dispose();
                            GameLauncher.mainFrameObject
                                    .replaceJPanel(new MapDesigner(map_name_text_field.getText(),
                                                                   Integer.parseInt(map_dimesions_x.getText()), Integer
                                                                           .parseInt(map_dimesions_y.getText())));
                        }
                    });
                    okButton.setActionCommand("OK");
                    buttonPane.add(okButton);
                    getRootPane().setDefaultButton(okButton);
                }
            }
        }
    }

}