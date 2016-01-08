/*
 * Created by JFormDesigner on Fri Jan 08 15:48:02 ICT 2016
 */

package net.openbyte.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import net.openbyte.model.FileTreeModel;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rtextarea.*;
import org.gradle.tooling.GradleConnector;

/**
 * @author Gary Lee
 */
public class WorkFrame extends JFrame {
    private File workDirectory;
    private File selectedFile;
    private File selectedDirectory;

    public WorkFrame(File workDirectory) {
        this.workDirectory = workDirectory;
        initComponents();
        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
        menuItem4.setAccelerator(keyStroke);
        tree1.setModel(new FileTreeModel(this.workDirectory));
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

    private void menuItem3ActionPerformed(ActionEvent e) {
        textArea1.setText("");
        ByteArrayOutputStream logOutput = new ByteArrayOutputStream();
        GradleConnector.newConnector().forProjectDirectory(workDirectory).connect().newBuild().forTasks("build").setStandardOutput(logOutput).setStandardError(logOutput).run();
        textArea1.append("" + logOutput);
    }

    private void createUIComponents() {
        // TODO: add custom component creation code here
    }

    private void menuItem4ActionPerformed(ActionEvent e) {
        if(selectedFile == null){
            JOptionPane.showMessageDialog(this, "You cannot save right now, because you have not selected a file inside the code editor.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        byte[] textBytes = rSyntaxTextArea1.getText().getBytes();
        try {
            FileOutputStream stream = new FileOutputStream(selectedFile);
            stream.write(textBytes);
            stream.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void menuItem5ActionPerformed(ActionEvent e) {
        setVisible(false);
        WelcomeFrame welcomeFrame = new WelcomeFrame();
        welcomeFrame.setVisible(true);
    }

    private void tree1ValueChanged(TreeSelectionEvent e) {
        File node = (File) tree1.getLastSelectedPathComponent();
        if(node.isDirectory()){
            selectedDirectory = node;
        }
        if(node == null || node.isDirectory()){
            return;
        }
        selectedFile = node;
        BufferedReader br = null;
        StringBuilder builder = new StringBuilder();

        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader(node));

            while ((sCurrentLine = br.readLine()) != null) {
                builder.append(sCurrentLine + "\n");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        rSyntaxTextArea1.setText(builder.toString());
    }

    private void menuItem6ActionPerformed(ActionEvent e) {
        File src = new File(workDirectory, "src");
        File main = new File(src, "main");
        File java = new File(main, "java");
        PackageCreationFrame packageCreationFrame = new PackageCreationFrame(tree1, java);
        packageCreationFrame.setVisible(true);
    }

    private void menuItem7ActionPerformed(ActionEvent e) {
        if(selectedDirectory == null){
            JOptionPane.showMessageDialog(this, "You cannot delete a file that you have not selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        selectedDirectory.delete();
        tree1.updateUI();
        selectedDirectory = null;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Gary Lee
        menuBar1 = new JMenuBar();
        menu2 = new JMenu();
        menuItem6 = new JMenuItem();
        menuItem4 = new JMenuItem();
        menuItem5 = new JMenuItem();
        menu3 = new JMenu();
        menuItem7 = new JMenuItem();
        menu1 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menuItem3 = new JMenuItem();
        scrollPane2 = new JScrollPane();
        textArea1 = new JTextArea();
        label1 = new JLabel();
        label2 = new JLabel();
        rTextScrollPane1 = new RTextScrollPane();
        rSyntaxTextArea1 = new RSyntaxTextArea();
        label3 = new JLabel();
        scrollPane3 = new JScrollPane();
        tree1 = new JTree();

        //======== this ========
        setTitle("Project Workspace");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== menuBar1 ========
        {

            //======== menu2 ========
            {
                menu2.setText("File");

                //---- menuItem6 ----
                menuItem6.setText("Add Package");
                menuItem6.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        menuItem6ActionPerformed(e);
                    }
                });
                menu2.add(menuItem6);

                //---- menuItem4 ----
                menuItem4.setText("Save");
                menuItem4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        menuItem4ActionPerformed(e);
                    }
                });
                menu2.add(menuItem4);

                //---- menuItem5 ----
                menuItem5.setText("Close Project");
                menuItem5.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        menuItem5ActionPerformed(e);
                    }
                });
                menu2.add(menuItem5);
            }
            menuBar1.add(menu2);

            //======== menu3 ========
            {
                menu3.setText("Edit");

                //---- menuItem7 ----
                menuItem7.setText("Delete");
                menuItem7.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        menuItem7ActionPerformed(e);
                    }
                });
                menu3.add(menuItem7);
            }
            menuBar1.add(menu3);

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
                menuItem3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        menuItem3ActionPerformed(e);
                    }
                });
                menu1.add(menuItem3);
            }
            menuBar1.add(menu1);
        }
        setJMenuBar(menuBar1);

        //======== scrollPane2 ========
        {

            //---- textArea1 ----
            textArea1.setEditable(false);
            scrollPane2.setViewportView(textArea1);
        }
        contentPane.add(scrollPane2);
        scrollPane2.setBounds(175, 520, 735, 80);

        //---- label1 ----
        label1.setText("Output");
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(175, 500), label1.getPreferredSize()));

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
        rTextScrollPane1.setBounds(175, 20, 735, 470);

        //---- label3 ----
        label3.setText("Code Editor");
        contentPane.add(label3);
        label3.setBounds(new Rectangle(new Point(175, 5), label3.getPreferredSize()));

        //======== scrollPane3 ========
        {

            //---- tree1 ----
            tree1.addTreeSelectionListener(new TreeSelectionListener() {
                @Override
                public void valueChanged(TreeSelectionEvent e) {
                    tree1ValueChanged(e);
                }
            });
            scrollPane3.setViewportView(tree1);
        }
        contentPane.add(scrollPane3);
        scrollPane3.setBounds(5, 20, 165, 580);

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
    private JMenu menu2;
    private JMenuItem menuItem6;
    private JMenuItem menuItem4;
    private JMenuItem menuItem5;
    private JMenu menu3;
    private JMenuItem menuItem7;
    private JMenu menu1;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JScrollPane scrollPane2;
    private JTextArea textArea1;
    private JLabel label1;
    private JLabel label2;
    private RTextScrollPane rTextScrollPane1;
    private RSyntaxTextArea rSyntaxTextArea1;
    private JLabel label3;
    private JScrollPane scrollPane3;
    private JTree tree1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
