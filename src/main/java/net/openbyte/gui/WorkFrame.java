/*
 * Created by JFormDesigner on Fri Jan 08 15:48:02 ICT 2016
 */

package net.openbyte.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import net.openbyte.gui.logger.Consumer;
import net.openbyte.gui.logger.StreamCapturer;
import net.openbyte.model.FileSystemModel;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.fife.rsta.ac.java.JavaLanguageSupport;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rtextarea.*;
import org.gradle.tooling.GradleConnector;

/**
 * @author Gary Lee
 */
public class WorkFrame extends JFrame implements Consumer {
    private File workDirectory;
    private File selectedFile;
    private File selectedDirectory;

    public WorkFrame(File workDirectory) {
        this.workDirectory = workDirectory;
        initComponents();
        KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
        menuItem4.setAccelerator(keyStroke);
        tree1.setModel(new FileSystemModel(this.workDirectory));
        JavaLanguageSupport support = new JavaLanguageSupport();
        try {
            support.getJarManager().addCurrentJreClassFileSource();
        } catch (IOException e) {
            e.printStackTrace();
        }
        support.setAutoActivationDelay(0);
        support.setAutoCompleteEnabled(true);
        support.setParameterAssistanceEnabled(true);
        support.install(rSyntaxTextArea1);
        PrintStream out = System.out;
        System.setOut(new PrintStream(new StreamCapturer("OpenByte", this, out)));
    }

    private void menuItem1ActionPerformed(ActionEvent e) {
        textArea1.setText("Starting client... \n");
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                GradleConnector.newConnector().forProjectDirectory(workDirectory).connect().newBuild().forTasks("runClient").run();
                return null;
            }
        };
        worker.execute();
    }

    private void menuItem2ActionPerformed(ActionEvent e) {
        textArea1.setText("Starting server... \n");
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                GradleConnector.newConnector().forProjectDirectory(workDirectory).connect().newBuild().forTasks("runServer").run();
                return null;
            }
        };
        worker.execute();
    }

    private void menuItem3ActionPerformed(ActionEvent e) {
        textArea1.setText("Building modification JAR... \n");
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                GradleConnector.newConnector().forProjectDirectory(workDirectory).connect().newBuild().forTasks("build").run();
                return null;
            }
        };
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
        if(!(selectedFile == null)){
            selectedFile.delete();
            return;
        }
        if(selectedDirectory == null){
            JOptionPane.showMessageDialog(this, "You cannot delete a file that you have not selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        selectedDirectory.delete();
        tree1.updateUI();
        selectedDirectory = null;
    }

    private void menuItem8ActionPerformed(ActionEvent e) {
        File src = new File(workDirectory, "src");
        File main = new File(src, "main");
        File java = new File(main, "java");
        ClassCreationFrame classCreationFrame = new ClassCreationFrame(tree1, java);
        classCreationFrame.setVisible(true);
    }

    private void menuItem9ActionPerformed(ActionEvent e) {
        menuItem9.setEnabled(false);
        try {
            Git.init().setDirectory(this.workDirectory).call();
        } catch (GitAPIException e1) {
            e1.printStackTrace();
        }
        menu5.setEnabled(true);
    }

    private void menuItem10ActionPerformed(ActionEvent e) {
        try {
            Git
                    .open(this.workDirectory)
                    .add()
                    .addFilepattern(".")
                    .call();
            Git
                    .open(this.workDirectory)
                    .commit()
                    .setMessage("Merge repository with local changes.")
                    .call();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Gary Lee
        menuBar1 = new JMenuBar();
        menu2 = new JMenu();
        menuItem8 = new JMenuItem();
        menuItem6 = new JMenuItem();
        menuItem4 = new JMenuItem();
        menuItem5 = new JMenuItem();
        menu3 = new JMenu();
        menuItem7 = new JMenuItem();
        menu1 = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menuItem3 = new JMenuItem();
        menu4 = new JMenu();
        menuItem9 = new JMenuItem();
        menu5 = new JMenu();
        menuItem10 = new JMenuItem();
        scrollPane2 = new JScrollPane();
        textArea1 = new JTextArea();
        rTextScrollPane1 = new RTextScrollPane();
        rSyntaxTextArea1 = new RSyntaxTextArea();
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

                //---- menuItem8 ----
                menuItem8.setText("Add Class");
                menuItem8.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        menuItem8ActionPerformed(e);
                    }
                });
                menu2.add(menuItem8);

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

            //======== menu4 ========
            {
                menu4.setText("Git");

                //---- menuItem9 ----
                menuItem9.setText("Import into Git");
                menuItem9.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        menuItem9ActionPerformed(e);
                        menuItem9ActionPerformed(e);
                    }
                });
                menu4.add(menuItem9);

                //======== menu5 ========
                {
                    menu5.setText("Options");
                    menu5.setEnabled(false);

                    //---- menuItem10 ----
                    menuItem10.setText("Commit");
                    menuItem10.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            menuItem10ActionPerformed(e);
                        }
                    });
                    menu5.add(menuItem10);
                }
                menu4.add(menu5);
            }
            menuBar1.add(menu4);
        }
        setJMenuBar(menuBar1);

        //======== scrollPane2 ========
        {
            scrollPane2.setBorder(null);

            //---- textArea1 ----
            textArea1.setEditable(false);
            textArea1.setBorder(new TitledBorder(LineBorder.createGrayLineBorder(), "Output"));
            textArea1.setBackground(new Color(240, 240, 240));
            scrollPane2.setViewportView(textArea1);
        }
        contentPane.add(scrollPane2);
        scrollPane2.setBounds(355, 610, 760, 100);

        //======== rTextScrollPane1 ========
        {
            rTextScrollPane1.setBorder(new TitledBorder(LineBorder.createGrayLineBorder(), "Code Editor"));

            //---- rSyntaxTextArea1 ----
            rSyntaxTextArea1.setSyntaxEditingStyle("text/java");
            rSyntaxTextArea1.setBackground(Color.white);
            rTextScrollPane1.setViewportView(rSyntaxTextArea1);
        }
        contentPane.add(rTextScrollPane1);
        rTextScrollPane1.setBounds(355, 10, 760, 590);

        //======== scrollPane3 ========
        {
            scrollPane3.setBorder(null);

            //---- tree1 ----
            tree1.setBorder(new TitledBorder(LineBorder.createGrayLineBorder(), "File Manager"));
            tree1.setBackground(new Color(240, 240, 240));
            tree1.addTreeSelectionListener(new TreeSelectionListener() {
                @Override
                public void valueChanged(TreeSelectionEvent e) {
                    tree1ValueChanged(e);
                }
            });
            scrollPane3.setViewportView(tree1);
        }
        contentPane.add(scrollPane3);
        scrollPane3.setBounds(5, 10, 340, 705);

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
        setSize(1230, 785);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Gary Lee
    private JMenuBar menuBar1;
    private JMenu menu2;
    private JMenuItem menuItem8;
    private JMenuItem menuItem6;
    private JMenuItem menuItem4;
    private JMenuItem menuItem5;
    private JMenu menu3;
    private JMenuItem menuItem7;
    private JMenu menu1;
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JMenu menu4;
    private JMenuItem menuItem9;
    private JMenu menu5;
    private JMenuItem menuItem10;
    private JScrollPane scrollPane2;
    private JTextArea textArea1;
    private RTextScrollPane rTextScrollPane1;
    private RSyntaxTextArea rSyntaxTextArea1;
    private JScrollPane scrollPane3;
    private JTree tree1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    @Override
    public void appendText(String text) {

    }
}
