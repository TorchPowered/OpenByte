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
 * Created by JFormDesigner on Fri Mar 11 18:04:55 WIB 2016
 */

package net.openbyte.gui.plugin;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import net.openbyte.Launch;
import net.openbyte.data.file.PluginDescriptionFile;
import net.openbyte.plugin.AbstractPlugin;
import net.openbyte.plugin.PluginManager;
import org.jdesktop.swingx.*;

/**
 * @author Gary Lee
 */
public class PluginListingFrame extends JDialog {
    public static DefaultListModel plugins = new DefaultListModel();

    public PluginListingFrame(Frame owner)
    {
        super(owner);
        plugins.clear();
        for (AbstractPlugin plugin : PluginManager.getAbstractPlugins()) {
            plugins.addElement(plugin.name());
        }
        initComponents();
        list1.setModel(plugins);
    }

    private void list1ValueChanged(ListSelectionEvent e) {
        String currentPluginSelectedName = (String) list1.getSelectedValue();
        AbstractPlugin plugin = PluginManager.getPlugin(currentPluginSelectedName);
        PluginDescriptionFile pdf = PluginManager.getPluginDescriptionFile(plugin);
        try {
            xImagePanel1.setImage(ImageIO.read(new URL(pdf.getLogoURL())));
        } catch (IOException e1) {
            Launch.logger.error("Failed to set logo image of plugin listing, " + plugin.name() + "!");
        }
        label2.setText(plugin.name());
        textArea1.setText(pdf.getDescription());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Gary Lee
        list1 = new JList();
        label2 = new JLabel();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();
        scrollPane2 = new JScrollPane();
        xImagePanel1 = new JXImagePanel();

        //======== this ========
        setTitle("Plugins");
        setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- list1 ----
        list1.setBackground(new Color(238, 238, 238));
        list1.setBorder(new TitledBorder(LineBorder.createGrayLineBorder(), "Plugins"));
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                list1ValueChanged(e);
            }
        });
        contentPane.add(list1);
        list1.setBounds(5, 5, 175, 470);

        //---- label2 ----
        label2.setText("No plugin listing has been selected!");
        label2.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        contentPane.add(label2);
        label2.setBounds(185, 185, 405, 65);

        //======== scrollPane1 ========
        {
            scrollPane1.setBorder(null);

            //---- textArea1 ----
            textArea1.setBorder(null);
            textArea1.setLineWrap(true);
            textArea1.setEditable(false);
            textArea1.setBackground(new Color(238, 238, 238));
            scrollPane1.setViewportView(textArea1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(185, 240, 405, 230);

        //======== scrollPane2 ========
        {
            scrollPane2.setViewportView(xImagePanel1);
        }
        contentPane.add(scrollPane2);
        scrollPane2.setBounds(185, 15, 405, 185);

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
        setSize(600, 510);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Gary Lee
    private JList list1;
    private JLabel label2;
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    private JScrollPane scrollPane2;
    private JXImagePanel xImagePanel1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
