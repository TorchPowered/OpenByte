/*
 * Created by JFormDesigner on Tue Jan 05 18:01:53 ICT 2016
 */

package net.openbyte.gui;

import javax.imageio.ImageIO;
import javax.swing.border.*;
import javax.swing.event.*;
import net.openbyte.Launch;
import net.openbyte.data.Files;
import net.openbyte.data.file.OpenProjectSolution;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;
import org.jdesktop.swingx.*;

/**
 * @author Gary Lee
 */
public class WelcomeFrame extends JFrame {
    public static DefaultListModel listItems = new DefaultListModel();

    public WelcomeFrame() {
        listItems.clear();
        Launch.projectNames.clear();
        Launch.nameToSolution.clear();
        initComponents();
        list1.setModel(listItems);
        try {
            xImagePanel1.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("openbytelogo.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        File[] projectFiles = Files.WORKSPACE_DIRECTORY.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".openproj");
            }
        });
        for (File projectFile : projectFiles){
            OpenProjectSolution solution = OpenProjectSolution.getProjectSolutionFromFile(projectFile);
            Launch.nameToSolution.put(solution.getProjectName(), solution);
            Launch.projectNames.add(solution.getProjectName());
        }
        for (String projectName : Launch.projectNames){
            WelcomeFrame.listItems.addElement(projectName);
        }
        list1.addMouseListener(new MouseAdapter() {

            int lastSelectedIndex;

            public void mouseClicked(MouseEvent e) {

                int index = list1.locationToIndex(e.getPoint());

                if (index != -1 && index == lastSelectedIndex) {
                    list1.clearSelection();
                }

                lastSelectedIndex = list1.getSelectedIndex();
            }
        });
    }

    public void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void button2ActionPerformed(ActionEvent e) {
        openWebpage("http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-tools/2420242-craftbyte-mod-ide-the-ide-for-mod-developers");
    }

    private void button3ActionPerformed(ActionEvent e) {
        openWebpage("https://github.com/PizzaCrust/OpenByte");
    }

    private void button4ActionPerformed(ActionEvent e) {
        setVisible(false);
        CreateProjectFrame createProjectFrame = new CreateProjectFrame(this);
        createProjectFrame.setVisible(true);
    }

    private void list1ValueChanged(ListSelectionEvent e) {
        if(list1.getSelectedIndex() == -1){
            button5.setEnabled(false);
            button1.setEnabled(false);
            return;
        }
        button5.setEnabled(true);
        button1.setEnabled(true);
    }

    private void button5ActionPerformed(ActionEvent e) {
        String selectedText = (String) list1.getSelectedValue();
        listItems.remove(list1.getSelectedIndex());
        OpenProjectSolution solution = Launch.nameToSolution.get(selectedText);
        solution.getProjectFolder().delete();
        list1.clearSelection();
        solution.deleteSolution();
    }

    private void button1ActionPerformed(ActionEvent e) {
        setVisible(false);
        String selectedText = (String) list1.getSelectedValue();
        OpenProjectSolution solution = Launch.nameToSolution.get(selectedText);
        WorkFrame workFrame = new WorkFrame(solution.getProjectFolder());
        workFrame.setVisible(true);
    }

    private void button6ActionPerformed(ActionEvent e) {
        setVisible(false);
        SettingsFrame settingsFrame = new SettingsFrame((Dialog) null);
        settingsFrame.setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Gary Lee
        scrollPane1 = new JScrollPane();
        list1 = new JList();
        button1 = new JButton();
        label2 = new JLabel();
        button2 = new JButton();
        button3 = new JButton();
        label3 = new JLabel();
        button4 = new JButton();
        button5 = new JButton();
        label4 = new JLabel();
        scrollPane2 = new JScrollPane();
        xImagePanel1 = new JXImagePanel();
        button6 = new JButton();

        //======== this ========
        setTitle("Welcome to OpenByte");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== scrollPane1 ========
        {
            scrollPane1.setBorder(new TitledBorder(LineBorder.createGrayLineBorder(), "Recent Projects"));

            //---- list1 ----
            list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list1.setBackground(new Color(240, 240, 240));
            list1.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    list1ValueChanged(e);
                }
            });
            scrollPane1.setViewportView(list1);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(15, 10, 165, 340);

        //---- button1 ----
        button1.setText("Open Project");
        button1.setEnabled(false);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button1ActionPerformed(e);
            }
        });
        contentPane.add(button1);
        button1.setBounds(105, 355, 110, button1.getPreferredSize().height);

        //---- label2 ----
        label2.setText("Media");
        contentPane.add(label2);
        label2.setBounds(new Rectangle(new Point(615, 240), label2.getPreferredSize()));

        //---- button2 ----
        button2.setText("Minecraft Forums");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button2ActionPerformed(e);
            }
        });
        contentPane.add(button2);
        button2.setBounds(500, 260, 151, button2.getPreferredSize().height);

        //---- button3 ----
        button3.setText("GitHub");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button3ActionPerformed(e);
            }
        });
        contentPane.add(button3);
        button3.setBounds(500, 290, 150, button3.getPreferredSize().height);

        //---- label3 ----
        label3.setText("OpenByte v0.93 maintained by Swatcommader6.");
        contentPane.add(label3);
        label3.setBounds(new Rectangle(new Point(415, 350), label3.getPreferredSize()));

        //---- button4 ----
        button4.setText("+");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button4ActionPerformed(e);
            }
        });
        contentPane.add(button4);
        button4.setBounds(15, 355, 45, button4.getPreferredSize().height);

        //---- button5 ----
        button5.setText("-");
        button5.setEnabled(false);
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button5ActionPerformed(e);
            }
        });
        contentPane.add(button5);
        button5.setBounds(60, 355, 40, button5.getPreferredSize().height);

        //---- label4 ----
        label4.setText("Original idea was from TheVBGuyDaniel.");
        contentPane.add(label4);
        label4.setBounds(460, 365, 265, label4.getPreferredSize().height);

        //======== scrollPane2 ========
        {
            scrollPane2.setBorder(null);
            scrollPane2.setViewportView(xImagePanel1);
        }
        contentPane.add(scrollPane2);
        scrollPane2.setBounds(210, 25, 445, 160);

        //---- button6 ----
        button6.setText("Preferences");
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button6ActionPerformed(e);
            }
        });
        contentPane.add(button6);
        button6.setBounds(500, 320, 150, 23);

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
        setSize(675, 425);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Gary Lee
    private JScrollPane scrollPane1;
    private JList list1;
    private JButton button1;
    private JLabel label2;
    private JButton button2;
    private JButton button3;
    private JLabel label3;
    private JButton button4;
    private JButton button5;
    private JLabel label4;
    private JScrollPane scrollPane2;
    private JXImagePanel xImagePanel1;
    private JButton button6;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
