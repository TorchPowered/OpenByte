/*
 * Created by JFormDesigner on Sat Feb 13 17:08:17 ICT 2016
 */

package net.openbyte.gui;

import org.eclipse.jgit.api.Git;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

/**
 * @author Gary Lee
 */
public class GitCommitFrame extends JFrame {
    private File workDirectory;

    public GitCommitFrame(File workDirectory) {
        this.workDirectory = workDirectory; initComponents();
    }

    private void button1ActionPerformed(ActionEvent e) {
        setVisible(false);
    }

    private void button2ActionPerformed(ActionEvent e) {
        if(textField1.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Invalid commit message.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Git
                    .open(this.workDirectory)
                    .add()
                    .addFilepattern(".")
                    .call();
            Git
                    .open(this.workDirectory)
                    .commit()
                    .setMessage(textField1.getText())
                    .call();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Gary Lee
        textField1 = new JTextField();
        label1 = new JLabel();
        button1 = new JButton();
        button2 = new JButton();

        //======== this ========
        setTitle("Commit");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.add(textField1);
        textField1.setBounds(25, 50, 375, textField1.getPreferredSize().height);

        //---- label1 ----
        label1.setText("Commit Message");
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(25, 30), label1.getPreferredSize()));

        //---- button1 ----
        button1.setText("Cancel");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button1ActionPerformed(e);
            }
        });
        contentPane.add(button1);
        button1.setBounds(new Rectangle(new Point(5, 110), button1.getPreferredSize()));

        //---- button2 ----
        button2.setText("Commit");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button2ActionPerformed(e);
            }
        });
        contentPane.add(button2);
        button2.setBounds(new Rectangle(new Point(355, 110), button2.getPreferredSize()));

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
        pack();
        setLocationRelativeTo(getOwner());
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
