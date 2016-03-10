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
 * Created by JFormDesigner on Sat Feb 20 09:29:40 WIB 2016
 */

package net.openbyte.gui;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import net.openbyte.Launch;
import org.jdesktop.swingx.*;

/**
 * @author Gary Lee
 */
public class AboutFrame extends JFrame {
    public AboutFrame() {
        initComponents();
        try {
            xImagePanel1.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("openbytelogo.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        label2.setText("Build: OpenByte IDE v" + Launch.CURRENT_VERSION);
        label3.setText("Java Version: " + System.getProperty("java.version"));
        label4.setText("OS: " + System.getProperty("os.name"));
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Gary Lee
        scrollPane1 = new JScrollPane();
        xImagePanel1 = new JXImagePanel();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();

        //======== this ========
        setTitle("About OpenByte");
        setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== scrollPane1 ========
        {
            scrollPane1.setBorder(null);

            //---- xImagePanel1 ----
            xImagePanel1.setBorder(null);
            scrollPane1.setViewportView(xImagePanel1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(5, 0, 445, 140);

        //---- label1 ----
        label1.setText("Licensed under the MIT License.");
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(120, 250), label1.getPreferredSize()));

        //---- label2 ----
        label2.setText("Build: OpenByte IDE v1.0");
        contentPane.add(label2);
        label2.setBounds(new Rectangle(new Point(145, 145), label2.getPreferredSize()));

        //---- label3 ----
        label3.setText("Java Version:");
        contentPane.add(label3);
        label3.setBounds(110, 175, 200, label3.getPreferredSize().height);

        //---- label4 ----
        label4.setText("OS:");
        contentPane.add(label4);
        label4.setBounds(175, 195, 205, label4.getPreferredSize().height);

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
        setSize(465, 300);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Gary Lee
    private JScrollPane scrollPane1;
    private JXImagePanel xImagePanel1;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
