/*
 * Created by JFormDesigner on Fri Jan 08 16:22:41 ICT 2016
 */

package net.openbyte.gui;

import java.awt.*;
import java.io.File;
import javax.swing.*;

/**
 * @author Gary Lee
 */
public class PackageCreationFrame extends JFrame {
    private File location;

    public PackageCreationFrame(File createPackageLocation)
    {
        this.location = createPackageLocation;
        initComponents();
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
        contentPane.add(button1);
        button1.setBounds(490, 65, 80, button1.getPreferredSize().height);

        //---- button2 ----
        button2.setText("Cancel");
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
