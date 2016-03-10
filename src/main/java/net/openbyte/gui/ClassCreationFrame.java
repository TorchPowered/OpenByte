/*
 * This file is part of OpenByte IDE, licensed under the MIT License (MIT).
 *
 * Copyright (c) TorchPowered 2016
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

/*
 * Created by JFormDesigner on Fri Jan 08 19:43:34 ICT 2016
 */

package net.openbyte.gui;

import net.openbyte.FileUtil;
import net.openbyte.Formats;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

/**
 * @author Gary Lee
 */
public class ClassCreationFrame extends JFrame {
    private File src;
    private JTree tree;

    public ClassCreationFrame(JTree tree, File srcDir) {
        this.src = srcDir;
        this.tree = tree;
        initComponents();
    }

    private void button1ActionPerformed(ActionEvent e) {
        setVisible(false);
    }

    private void button2ActionPerformed(ActionEvent e) {
        if(textField1.getText() == null || textField1.getText().equals("")){
            JOptionPane.showMessageDialog(this, "You have not input a package name inside the text field. Please do.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] splittedName = textField1.getText().split("\\.");
        if(splittedName.length < 2){
            JOptionPane.showMessageDialog(this, "You need to put the class inside of a package to create a class.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(splittedName.length > 4){
            JOptionPane.showMessageDialog(this, "You cannot create a class inside of more than 3 packages.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(splittedName.length == 4){
            File mainPackageDir = new File(src, splittedName[0]);
            File secondPackageDir = new File(mainPackageDir, splittedName[1]);
            File thirdPackageDir = new File(secondPackageDir, splittedName[2]);
            File theClass = new File(thirdPackageDir, splittedName[3] + ".java");
            if(!mainPackageDir.exists()){
                mainPackageDir.mkdir();
            }
            if(!secondPackageDir.exists()){
                secondPackageDir.mkdir();
            }
            if(!theClass.exists()){
                try {
                    theClass.createNewFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            setVisible(false);
            tree.updateUI();
            if(checkBox1.isSelected()) {
                FileUtil.format(Formats.interfaceFormat(splittedName[0] + "." + splittedName[1] + "." + splittedName[2], splittedName[3]), theClass);
                return;
            }
            FileUtil.format(Formats.classFormat(splittedName[0] + "." + splittedName[1] + "." + splittedName[2], splittedName[3]), theClass);
            return;
        }
        if(splittedName.length == 3){
            File mainPackageDir = new File(src, splittedName[0]);
            File secondPackageDir = new File(mainPackageDir, splittedName[1]);
            File theClass = new File(secondPackageDir, splittedName[2] + ".java");
            if(!mainPackageDir.exists()){
                mainPackageDir.mkdir();
            }
            if(!secondPackageDir.exists()){
                secondPackageDir.mkdir();
            }
            if(!theClass.exists()){
                try {
                    theClass.createNewFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            setVisible(false);
            tree.updateUI();
            FileUtil.format(Formats.classFormat(splittedName[0] + "." + splittedName[1], splittedName[2]), theClass);
            return;
        }
        File mainPackageDir = new File(src, splittedName[0]);
        File theClass = new File(mainPackageDir, splittedName[1] + ".java");
        if(!mainPackageDir.exists()){
            mainPackageDir.mkdir();
        }
        if(!theClass.exists()){
            try {
                theClass.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        setVisible(false);
        tree.updateUI();
        FileUtil.format(Formats.classFormat(splittedName[0], splittedName[1]), theClass);
        return;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Gary Lee
        textField1 = new JTextField();
        button1 = new JButton();
        button2 = new JButton();
        label1 = new JLabel();
        checkBox1 = new JCheckBox();

        //======== this ========
        setTitle("Create class");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.add(textField1);
        textField1.setBounds(140, 50, 230, textField1.getPreferredSize().height);

        //---- button1 ----
        button1.setText("Cancel");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button1ActionPerformed(e);
            }
        });
        contentPane.add(button1);
        button1.setBounds(new Rectangle(new Point(5, 130), button1.getPreferredSize()));

        //---- button2 ----
        button2.setText("Create");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button2ActionPerformed(e);
            }
        });
        contentPane.add(button2);
        button2.setBounds(new Rectangle(new Point(430, 130), button2.getPreferredSize()));

        //---- label1 ----
        label1.setText("Name:");
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(140, 30), label1.getPreferredSize()));

        //---- checkBox1 ----
        checkBox1.setText("Interface");
        contentPane.add(checkBox1);
        checkBox1.setBounds(new Rectangle(new Point(425, 105), checkBox1.getPreferredSize()));

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
        setSize(515, 195);
        setLocationRelativeTo(null);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Gary Lee
    private JTextField textField1;
    private JButton button1;
    private JButton button2;
    private JLabel label1;
    private JCheckBox checkBox1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
