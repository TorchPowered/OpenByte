/*
 * Created by JFormDesigner on Sun Feb 14 18:20:40 ICT 2016
 */

package net.openbyte.gui;

import net.openbyte.data.Files;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

/**
 * @author Gary Lee
 */
public class SettingsFrame extends JDialog {
    public static boolean useSystem = true;

    public SettingsFrame(Dialog owner) {
        super(owner);
        initComponents();
        this.label2.setText("Current Workspace Location: " + Files.WORKSPACE_DIRECTORY.getAbsolutePath());
        if(useSystem) {
            radioButton2.setSelected(true);
            radioButton3.setEnabled(false);
        } else {
            radioButton3.setSelected(true);
            radioButton2.setEnabled(false);
        }
    }

    private void radioButton2ItemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.SELECTED) {
            radioButton3.setEnabled(false);
        }
        if(e.getStateChange() == ItemEvent.DESELECTED) {
            radioButton3.setEnabled(true);
        }
    }

    private void radioButton3ItemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.SELECTED) {
            radioButton2.setEnabled(false);
        }
        if(e.getStateChange() == ItemEvent.DESELECTED) {
            radioButton2.setEnabled(true);
        }
    }

    private void thisWindowClosing(WindowEvent e) {
        boolean newSystem = radioButton2.isSelected();
        if(newSystem) {
            useSystem = true;
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            useSystem = false;
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        WelcomeFrame welcomeFrame = new WelcomeFrame();
        welcomeFrame.setVisible(true);
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void button1ActionPerformed(ActionEvent e) {
        if(textField1.getText().equals("")){
            showErrorDialog("No directory path specified in the given field!");
            return;
        }
        File file = new File(textField1.getText());
        if(!file.exists()) {
            showErrorDialog("Directory path specified doesn't exists.");
            return;
        }
        if(!file.isDirectory()) {
            showErrorDialog("Directory path specified is not a directory!");
            return;
        }
        Files.WORKSPACE_DIRECTORY = file;
        this.label2.setText("Current Workspace Location: " + Files.WORKSPACE_DIRECTORY.getAbsolutePath());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Gary Lee
        tabbedPane1 = new JTabbedPane();
        panel1 = new JPanel();
        label1 = new JLabel();
        textField1 = new JTextField();
        button1 = new JButton();
        label2 = new JLabel();
        radioButton2 = new JRadioButton();
        radioButton3 = new JRadioButton();

        //======== this ========
        setTitle("Preferences");
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== tabbedPane1 ========
        {

            //======== panel1 ========
            {

                // JFormDesigner evaluation mark
                panel1.setBorder(new javax.swing.border.CompoundBorder(
                    new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                        "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                        javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                        java.awt.Color.red), panel1.getBorder())); panel1.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

                panel1.setLayout(null);

                //---- label1 ----
                label1.setText("IDE Theme:");
                panel1.add(label1);
                label1.setBounds(5, 45, 580, label1.getPreferredSize().height);
                panel1.add(textField1);
                textField1.setBounds(5, 20, 405, textField1.getPreferredSize().height);

                //---- button1 ----
                button1.setText("Change Workspace Location");
                button1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        button1ActionPerformed(e);
                    }
                });
                panel1.add(button1);
                button1.setBounds(415, 20, 175, button1.getPreferredSize().height);

                //---- label2 ----
                label2.setText("Current Workspace Location: ");
                panel1.add(label2);
                label2.setBounds(5, 5, 580, 14);

                //---- radioButton2 ----
                radioButton2.setText("System Swing Theme");
                radioButton2.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        radioButton2ItemStateChanged(e);
                    }
                });
                panel1.add(radioButton2);
                radioButton2.setBounds(5, 60, 195, radioButton2.getPreferredSize().height);

                //---- radioButton3 ----
                radioButton3.setText("Default Swing Theme");
                radioButton3.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        radioButton3ItemStateChanged(e);
                    }
                });
                panel1.add(radioButton3);
                radioButton3.setBounds(5, 80, 185, 23);

                { // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < panel1.getComponentCount(); i++) {
                        Rectangle bounds = panel1.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = panel1.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    panel1.setMinimumSize(preferredSize);
                    panel1.setPreferredSize(preferredSize);
                }
            }
            tabbedPane1.addTab("IDE", panel1);
        }
        contentPane.add(tabbedPane1, BorderLayout.CENTER);
        setSize(615, 210);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Gary Lee
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JLabel label1;
    private JTextField textField1;
    private JButton button1;
    private JLabel label2;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
