/*
 * Created by JFormDesigner on Tue Jan 05 19:21:12 ICT 2016
 */

package net.openbyte.gui;

import net.lingala.zip4j.core.ZipFile;
import net.openbyte.Launch;
import net.openbyte.data.Files;
import net.openbyte.data.file.OpenProjectSolution;
import net.openbyte.enums.MinecraftVersion;
import net.openbyte.enums.ModificationAPI;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.gradle.tooling.GradleConnector;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import javax.swing.*;

/**
 * @author Gary Lee
 */
public class CreateProjectFrame extends JFrame {
    private JFrame previousFrame;
    private File projectFolder;
    private String projectName;
    private MinecraftVersion version;
    private ModificationAPI api;

    public CreateProjectFrame(JFrame previousFrame) {
        this.previousFrame = previousFrame;
        initComponents();
    }

    private void button2ActionPerformed(ActionEvent e) {
        setVisible(false);
        previousFrame.setVisible(true);
    }

    private void button1ActionPerformed(ActionEvent e) {
        if(!(Launch.nameToSolution.get(textField1.getText()) == null)){
            JOptionPane.showMessageDialog(this, "Project has already been created.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        MinecraftVersion minecraftVersion = MinecraftVersion.BOUNTIFUL_UPDATE;
        if(comboBox1.getSelectedIndex() == 1){
            minecraftVersion = MinecraftVersion.THE_UPDATE_THAT_CHANGED_THE_WORLD;
        }
        ModificationAPI modificationAPI = ModificationAPI.MINECRAFT_FORGE;
        this.version = minecraftVersion;
        this.api = modificationAPI;
        setVisible(false);
        JOptionPane.showMessageDialog(this, "We're working on setting up the project. When we are ready, a window will show up.", "Working on project creation", JOptionPane.INFORMATION_MESSAGE);
        this.projectName = textField1.getText();
        doOperations();
    }

    public void doOperations(){
        createProjectFolder();
        createSolutionFile();
        cloneAPI();
        GradleConnector.newConnector().forProjectDirectory(projectFolder).connect().newBuild().setJvmArguments("-XX:-UseGCOverheadLimit").forTasks("setupDecompWorkspace").run();
        WelcomeFrame welcomeFrame = new WelcomeFrame();
        welcomeFrame.setVisible(true);
    }
    private void createProjectFolder(){
        this.projectFolder = new File(Files.WORKSPACE_DIRECTORY, projectName);
        projectFolder.mkdir();
    }

    private void createSolutionFile(){
        File solutionFile = new File(Files.WORKSPACE_DIRECTORY, projectName + ".openproj");
        Files.createNewFile(projectName + ".openproj", Files.WORKSPACE_DIRECTORY);
        OpenProjectSolution solution = OpenProjectSolution.getProjectSolutionFromFile(solutionFile);
        solution.setProjectFolder(this.projectFolder);
        solution.setProjectName(projectName);
    }

    private void cloneAPI(){
        if(this.api == ModificationAPI.MINECRAFT_FORGE){
            String link;
            if(this.version == MinecraftVersion.BOUNTIFUL_UPDATE){
                link = "http://files.minecraftforge.net/maven/net/minecraftforge/forge/1.8.9-11.15.0.1684/forge-1.8.9-11.15.0.1684-mdk.zip";
            } else {
                link = "http://files.minecraftforge.net/maven/net/minecraftforge/forge/1.7.10-10.13.4.1614-1.7.10/forge-1.7.10-10.13.4.1614-1.7.10-src.zip";
            }
            File forgeZip = new File(projectFolder, "forge.zip");
            try{
                URL url = new URL(link);
                FileUtils.copyURLToFile(url, forgeZip);
                ZipFile zipFile = new ZipFile(forgeZip);
                zipFile.extractAll(projectFolder.getAbsolutePath());
                forgeZip.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Gary Lee
        button1 = new JButton();
        textField1 = new JTextField();
        label1 = new JLabel();
        comboBox1 = new JComboBox<>();
        label2 = new JLabel();
        comboBox2 = new JComboBox<>();
        label3 = new JLabel();
        button2 = new JButton();

        //======== this ========
        setTitle("Project Creation");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- button1 ----
        button1.setText("Create project");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button1ActionPerformed(e);
            }
        });
        contentPane.add(button1);
        button1.setBounds(new Rectangle(new Point(340, 170), button1.getPreferredSize()));
        contentPane.add(textField1);
        textField1.setBounds(10, 25, 245, textField1.getPreferredSize().height);

        //---- label1 ----
        label1.setText("Project Name");
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(10, 5), label1.getPreferredSize()));

        //---- comboBox1 ----
        comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
            "Minecraft Forge"
        }));
        contentPane.add(comboBox1);
        comboBox1.setBounds(10, 70, 245, comboBox1.getPreferredSize().height);

        //---- label2 ----
        label2.setText("Modification API");
        contentPane.add(label2);
        label2.setBounds(new Rectangle(new Point(10, 50), label2.getPreferredSize()));

        //---- comboBox2 ----
        comboBox2.setModel(new DefaultComboBoxModel<>(new String[] {
            "1.8.9",
            "1.7.10"
        }));
        contentPane.add(comboBox2);
        comboBox2.setBounds(10, 120, 245, comboBox2.getPreferredSize().height);

        //---- label3 ----
        label3.setText("Minecraft Version");
        contentPane.add(label3);
        label3.setBounds(new Rectangle(new Point(10, 100), label3.getPreferredSize()));

        //---- button2 ----
        button2.setText("Cancel");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button2ActionPerformed(e);
            }
        });
        contentPane.add(button2);
        button2.setBounds(new Rectangle(new Point(10, 170), button2.getPreferredSize()));

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
        setSize(465, 240);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Gary Lee
    private JButton button1;
    private JTextField textField1;
    private JLabel label1;
    private JComboBox<String> comboBox1;
    private JLabel label2;
    private JComboBox<String> comboBox2;
    private JLabel label3;
    private JButton button2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
