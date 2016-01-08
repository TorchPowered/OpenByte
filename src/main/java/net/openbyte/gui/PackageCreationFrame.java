/*
 * Created by JFormDesigner on Fri Jan 08 16:22:41 ICT 2016
 */

package net.openbyte.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;

/**
 * @author Gary Lee
 */
public class PackageCreationFrame extends JFrame {
    private File location;
    private JTree tree;

    public PackageCreationFrame(JTree tree, File createPackageLocation)
    {
        this.location = createPackageLocation;
        initComponents();
        this.tree = tree;
    }

    private void button2ActionPerformed(ActionEvent e) {
        setVisible(false);
    }

    private void button1ActionPerformed(ActionEvent e) {
        if(textField1.getText() == null || textField1.getText().equals("")){
            JOptionPane.showMessageDialog(this, "You have not input a package name inside the text field. Please do.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] splittedName = textField1.getText().split("\\.");
        if(splittedName.length > 3){
            JOptionPane.showMessageDialog(this, "You cannot input a package with more than 3 directory separators.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(splittedName.length == 3){
            File mainFolder = new File(location, splittedName[0]);
            File secondFolder = new File(mainFolder, splittedName[1]);
            File thirdFolder = new File(secondFolder, splittedName[2]);
            if(!mainFolder.exists()){
                mainFolder.mkdir();
            }
            if(!secondFolder.exists()){
                mainFolder.mkdir();
            }
            if(!thirdFolder.exists()){
                thirdFolder.mkdir();
            }
            setVisible(false);
            tree.updateUI();
            return;
        }
        if(splittedName.length == 2){
            File mainFolder = new File(location, splittedName[0]);
            File secondFolder = new File(mainFolder, splittedName[1]);
            if(!mainFolder.exists()){
                mainFolder.mkdir();
            }
            if(!secondFolder.exists()){
                secondFolder.mkdir();
            }
            setVisible(false);
            tree.updateUI();
            return;
        }
        File mainFolder = new File(location, splittedName[0]);
        if(!mainFolder.exists()){
            mainFolder.mkdir();
        }
        setVisible(false);
        tree.updateUI();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Gary Lee
        textField1 = new JTextField();
        label1 = new JLabel();
        button1 = new JButton();
        button2 = new JButton();

        //======== this ========
        setResizable(false);
        setTitle("Create package");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.add(textField1);
        textField1.setBounds(70, 25, 435, textField1.getPreferredSize().height);

        //---- label1 ----
        label1.setText("Enter new package name:");
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(70, 10), label1.getPreferredSize()));

        //---- button1 ----
        button1.setText("OK");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button1ActionPerformed(e);
            }
        });
        contentPane.add(button1);
        button1.setBounds(490, 65, 80, button1.getPreferredSize().height);

        //---- button2 ----
        button2.setText("Cancel");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button2ActionPerformed(e);
            }
        });
        contentPane.add(button2);
        button2.setBounds(5, 65, 80, 23);

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        setSize(590, 135);
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Gary Lee
    private JTextField textField1;
    private JLabel label1;
    private JButton button1;
    private JButton button2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
