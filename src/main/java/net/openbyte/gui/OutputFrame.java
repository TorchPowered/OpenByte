/*
 * Created by JFormDesigner on Sat Feb 13 16:51:30 ICT 2016
 */

package net.openbyte.gui;

import net.openbyte.gui.logger.Consumer;

import java.awt.*;
import javax.swing.*;

/**
 * @author Gary Lee
 */
public class OutputFrame extends JDialog implements Consumer {
    public OutputFrame(Frame owner) {
        super(owner);
        initComponents();
    }

    public OutputFrame(Dialog owner) {
        super(owner);
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Gary Lee
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();

        //======== this ========
        setTitle("Output");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== scrollPane1 ========
        {

            //---- textArea1 ----
            textArea1.setEditable(false);
            scrollPane1.setViewportView(textArea1);
        }
        contentPane.add(scrollPane1, BorderLayout.CENTER);
        setSize(1000, 370);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    @Override
    public void appendText(String text) {
        this.textArea1.append(text);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Gary Lee
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
