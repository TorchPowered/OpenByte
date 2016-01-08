/*
 * Created by JFormDesigner on Fri Jan 08 15:48:02 ICT 2016
 */

package net.openbyte.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.swing.*;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rtextarea.*;
import org.gradle.tooling.GradleConnector;

/**
 * @author Gary Lee
 */
public class WorkFrame extends JFrame {
    private File workDirectory;

    public WorkFrame(File workDirectory) {
        this.workDirectory = workDirectory; initComponents();
    }

    private void menuItem1ActionPerformed(ActionEvent e) {
        textArea1.setText("");
        ByteArrayOutputStream logOutput = new ByteArrayOutputStream();
        GradleConnector.newConnector().forProjectDirectory(workDirectory).connect().newBuild().forTasks("runClient").setStandardOutput(logOutput).setStandardError(logOutput).run();
        textArea1.append("" + logOutput);
    }

    private void menuItem2ActionPerformed(ActionEvent e) {
        textArea1.setText("");
        ByteArrayOutputStream logOutput = new ByteArrayOutputStream();
        GradleConnector.newConnector().forProjectDirectory(workDirectory).connect().newBuild().forTasks("runServer").setStandardOutput(logOutput).setStandardError(logOutput).run();
        textArea1.append("" + logOutput);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Gary Lee
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menuItem3 = new JMenuItem();
        scrollPane1 = new JScrollPane();
        list1 = new JList();
        scrollPane2 = new JScrollPane();
        textArea1 = new JTextArea();
        label1 = new JLabel();
        label2 = new JLabel();
        rTextScrollPane1 = new RTextScrollPane();
        rSyntaxTextArea1 = new RSyntaxTextArea();
        label3 = new JLabel();

        //======== this ========
        setTitle("Project Workspace");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("Gradle");

                //---- menuItem1 ----
                menuItem1.setText("Run Client");
                menuItem1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        menuItem1ActionPerformed(e);
                    }
                });
                menu1.add(menuItem1);

                //---- menuItem2 ----
                menuItem2.setText("Run Server");
                menuItem2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        menuItem2ActionPerformed(e);
                    }
                });
                menu1.add(menuItem2);

                //---- menuItem3 ----
                menuItem3.setText("Build Mod JAR");
                menu1.add(menuItem3);
            }
            menuBar1.add(menu1);
        }
        setJMenuBar(menuBar1);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(list1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(0, 20, 160, 585);

        //======== scrollPane2 ========
        {

            //---- textArea1 ----
            textArea1.setEditable(false);
            scrollPane2.setViewportView(textArea1);
        }
        contentPane.add(scrollPane2);
        scrollPane2.setBounds(165, 520, 745, 80);

        //---- label1 ----
        label1.setText("Output");
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(165, 500), label1.getPreferredSize()));

        //---- label2 ----
        label2.setText("File Manager");
        contentPane.add(label2);
        label2.setBounds(new Rectangle(new Point(45, 5), label2.getPreferredSize()));

        //======== rTextScrollPane1 ========
        {

            //---- rSyntaxTextArea1 ----
            rSyntaxTextArea1.setSyntaxEditingStyle("text/java");
            rTextScrollPane1.setViewportView(rSyntaxTextArea1);
        }
        contentPane.add(rTextScrollPane1);
        rTextScrollPane1.setBounds(165, 20, 745, 470);

        //---- label3 ----
        label3.setText("Code Editor");
        contentPane.add(label3);
        label3.setBounds(new Rectangle(new Point(165, 5), label3.getPreferredSize()));

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
        setSize(935, 665);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Gary Lee
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JScrollPane scrollPane1;
    private JList list1;
    private JScrollPane scrollPane2;
    private JTextArea textArea1;
    private JLabel label1;
    private JLabel label2;
    private RTextScrollPane rTextScrollPane1;
    private RSyntaxTextArea rSyntaxTextArea1;
    private JLabel label3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
