/*
 * Copyright (C) 2014 Stanisalv
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package advancedresoucemenaging.interfaceClasses;

import advancedresoucemenaging.GUIClasses.AquaBarTabbedPaneUI;
import advancedresoucemenaging.GUIClasses.GUIControll;
import advancedresoucemenaging.GUIClasses.GUIElements;
import advancedresoucemenaging.GUIClasses.GUISizeController;
import advancedresoucemenaging.dataHandling.GlobalSpace;
import advancedresoucemenaging.dataHandling.GlobalStrings;
import advancedresoucemenaging.dataHandling.Settings;
import advancedresoucemenaging.dataLoading.SavingLoadingSystem;
import com.alee.laf.menu.WebCheckBoxMenuItem;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.separator.WebSeparator;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Stanisalv
 */
public class MainFrame extends JFrame implements ActionListener
{

    JTabbedPane tabs;
    private final JMenu fileMenu;
    private final JMenuItem save;
    private final JMenuItem saveAs;
    private final JMenuItem saveToPdf;
    private final JMenuItem laod;
    private final JMenuItem exit;
    private final WebCheckBoxMenuItem shuffle;
    private final WebCheckBoxMenuItem image;
    private final WebCheckBoxMenuItem gradient;
    private final WebCheckBoxMenuItem harndesSort;
    private final WebCheckBoxMenuItem complateGeneration;
    private final WebCheckBoxMenuItem mistakeCheckerSkip;

    public MainFrame()
    {
        //base frame settings
        super(GlobalStrings.titleString);//setting titel
        setLayout(new BorderLayout());//setting layout menager
        setSize(GUISizeController.getPrefferedSize());//deciding the size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setUIFont(GUIElements.defaultFont);//setting the font of the application
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("advancedresoucemenaging/Timetable.png")));//setting the icon image
        try
        {
            GlobalSpace.iconImage = ImageIO.read(getClass().getClassLoader().getResource("advancedresoucemenaging/Timetable.png"));
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | IOException ex)
        {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        UIManager.put("OptionPane.border", BorderFactory.createLineBorder(new Color(200, 200, 255), 1));

        //Tabs generation
        tabs = new JTabbedPane(JTabbedPane.LEFT, JTabbedPane.SCROLL_TAB_LAYOUT);
        tabs.setUI(new AquaBarTabbedPaneUI());
        tabs.setFont(new Font("SansSerif", Font.ITALIC | Font.BOLD, 15));
        tabs.add(new ControlPanel(), GlobalStrings.mainPanelString);
        tabs.add(new ElementsTab(), GlobalStrings.elementPanelString);
        tabs.add(new ClassesSetUp(), GlobalStrings.classesPanelString);
        tabs.add(new Plan(), GlobalStrings.planForStudPanelString);
        tabs.add(new TeacherSchedule(), GlobalStrings.planForTeachPanelString);
        tabs.add(new PreSchedule(), GlobalStrings.prePlanPanelString);
       // tabs.add(new Proparties(), GlobalStrings.propPanelString);

        //Tabs hot keys
        tabs.setMnemonicAt(0, KeyEvent.VK_M);
        tabs.setMnemonicAt(1, KeyEvent.VK_E);
        tabs.setMnemonicAt(2, KeyEvent.VK_C);
        tabs.setMnemonicAt(3, KeyEvent.VK_S);
        tabs.setMnemonicAt(4, KeyEvent.VK_T);
        tabs.setMnemonicAt(5, KeyEvent.VK_S);
        // tabs.setMnemonicAt(6, KeyEvent.VK_P);

        setupTabTraversalKeys(tabs);//Tabs rottation

        tabs.addChangeListener(new ChangeListener()
        {

            @Override
            public void stateChanged(ChangeEvent e)
            {
                refresh();
            }
        }); // refreshing certein componened, based on what is selected
        add(tabs);

        JMenuBar menuBar = new WebMenuBar();

        //File Menue Code
        fileMenu = new WebMenu(GlobalStrings.fileString);
        save = new WebMenuItem(GlobalStrings.saveString);
        save.addActionListener(this);
        save.setToolTipText(GlobalStrings.saveToolTipString);
        saveAs = new WebMenuItem(GlobalStrings.saveAsString);
        saveAs.addActionListener(this);
        saveAs.setToolTipText(GlobalStrings.saveAsToolTipString);
        saveToPdf = new WebMenuItem(GlobalStrings.saveToPdfString);
        saveToPdf.addActionListener(this);
        laod = new WebMenuItem(GlobalStrings.loadString);
        laod.addActionListener(this);
        laod.setToolTipText(GlobalStrings.loadToolTipString);
        exit = new WebMenuItem(GlobalStrings.exit);
        exit.addActionListener(this);
        exit.setToolTipText(GlobalStrings.exitTootllTipString);
        fileMenu.add(save);
        fileMenu.add(saveAs);
        fileMenu.add(laod);
        fileMenu.add(new WebSeparator());
        fileMenu.add(saveToPdf);
        fileMenu.add(new WebSeparator());
        fileMenu.add(exit);
        menuBar.add(fileMenu);

        //Settings Menue Code
        WebMenu settingsMenu = new WebMenu(GlobalStrings.settingsString);
        shuffle = new WebCheckBoxMenuItem(GlobalStrings.shuffleString);
        shuffle.addActionListener(event ->
        {
            Settings.shuffling = shuffle.isSelected();
        });
        shuffle.setToolTipText(GlobalStrings.shuffleToolTipString);
        settingsMenu.add(shuffle);
        harndesSort = new WebCheckBoxMenuItem(GlobalStrings.hardesSortString);
        harndesSort.addActionListener(event ->
        {
            Settings.hardnesSort = harndesSort.isSelected();
        });
        harndesSort.setToolTipText(GlobalStrings.hardesSortStringToolTipString);
        settingsMenu.add(harndesSort);

        complateGeneration = new WebCheckBoxMenuItem(GlobalStrings.complateGeneration);
        harndesSort.addActionListener(event ->
        {
            Settings.complateGeneration = complateGeneration.isSelected();
        });
        complateGeneration.setToolTipText(GlobalStrings.complateGenerationToolTipString);
        settingsMenu.add(complateGeneration);
     
        mistakeCheckerSkip = new WebCheckBoxMenuItem(GlobalStrings.mistakeCheckerSkip);
        mistakeCheckerSkip.addActionListener(event ->
        {
            Settings.mistakeCheckerSkip = mistakeCheckerSkip.isSelected();
        });
        mistakeCheckerSkip.setToolTipText(GlobalStrings.mistakeCheckerSkipToolTipString);
        settingsMenu.add(mistakeCheckerSkip);

        menuBar.add(settingsMenu);

        //GUI menue
        WebMenu guiMenu = new WebMenu(GlobalStrings.guiMenuString);
        image = new WebCheckBoxMenuItem(GlobalStrings.renderImageString);
        image.setSelected(GUIControll.renderLogoImage);
        image.addActionListener(this);
        gradient = new WebCheckBoxMenuItem(GlobalStrings.renderGredientString);
        gradient.setSelected(GUIControll.renderGredient);
        gradient.addActionListener(this);
        guiMenu.add(image);
        guiMenu.add(gradient);
        menuBar.add(guiMenu);

        setJMenuBar(menuBar);

        validate();

    }

    private static void setupTabTraversalKeys(JTabbedPane tabbedPane)
    {
        KeyStroke ctrlTab = KeyStroke.getKeyStroke("ctrl TAB");
        KeyStroke ctrlShiftTab = KeyStroke.getKeyStroke("ctrl shift TAB");

        // Remove ctrl-tab from normal focus traversal
        Set<AWTKeyStroke> forwardKeys = new HashSet<AWTKeyStroke>(tabbedPane.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        forwardKeys.remove(ctrlTab);
        tabbedPane.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);

        // Remove ctrl-shift-tab from normal focus traversal
        Set<AWTKeyStroke> backwardKeys = new HashSet<AWTKeyStroke>(tabbedPane.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
        backwardKeys.remove(ctrlShiftTab);
        tabbedPane.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardKeys);

        // Add keys to the tab's input map
        InputMap inputMap = tabbedPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(ctrlTab, "navigateNext");
        inputMap.put(ctrlShiftTab, "navigatePrevious");
    }

    private static void setUIFont(javax.swing.plaf.FontUIResource f)
    {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements())
        {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource)
            {
                UIManager.put(key, f);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(exit))
        {
            System.exit(0);
        } else if (e.getSource().equals(saveAs))
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setFont(new Font("SansSerif", Font.ITALIC | Font.BOLD, 10));
            chooser.setSelectedFile(new File("School_set_up_" + System.currentTimeMillis() / 1000 + ".rsr"));
            if (chooser.showSaveDialog(this) == chooser.APPROVE_OPTION)
            {
                SavingLoadingSystem.saveGlobalSpace(chooser.getSelectedFile());
            }
        } else if (e.getSource().equals(save))
        {
            if (GlobalSpace.openeFile != null)
            {
                SavingLoadingSystem.saveGlobalSpace(GlobalSpace.openeFile);
                return;
            }
        } else if (e.getSource().equals(laod))
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileFilter()
            {

                @Override
                public boolean accept(File f)
                {
                    return f.getAbsolutePath().endsWith(".rsr") || f.isDirectory();
                }

                @Override
                public String getDescription()
                {
                    return "";
                }
            });
            if (chooser.showOpenDialog(this) == chooser.APPROVE_OPTION)
            {
                SavingLoadingSystem.loadGlobalSpace(chooser.getSelectedFile());
                GlobalSpace.openeFile = chooser.getSelectedFile();
            }
            refresh();
            repaint();
        } else if (e.getSource().equals(image))
        {
            GUIControll.renderLogoImage = image.isSelected();
            repaint();
        } else if (e.getSource().equals(gradient))
        {
            GUIControll.renderGredient = gradient.isSelected();
            repaint();
        } else if (e.getSource().equals(saveToPdf))
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setFont(new Font("SansSerif", Font.ITALIC | Font.BOLD, 10));
            chooser.setSelectedFile(new File("School_Schadule_" + System.currentTimeMillis() / 1000 + ".pdf"));
            if (chooser.showSaveDialog(this) == chooser.APPROVE_OPTION)
            {
                try
                {
                    SavingLoadingSystem.saveClassesScheduleToPdf(
                            new File(chooser.getSelectedFile().getCanonicalPath() + ".pdf"));
                } catch (IOException ex)
                {
                    Logger.getLogger(Plan.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void refresh()
    {
        gradient.setSelected(GUIControll.renderGredient);
        image.setSelected(GUIControll.renderLogoImage);
        switch (tabs.getSelectedIndex())
        {
            case 0:
                ((ControlPanel) tabs.getSelectedComponent()).refresh();
                break;
            case 1:
                ((ElementsTab) tabs.getSelectedComponent()).refresh();
                break;
            case 2:
                ((ClassesSetUp) tabs.getSelectedComponent()).refresh();
                break;
            case 3:
                ((Plan) tabs.getSelectedComponent()).refresh();
                break;
            case 4:
                ((TeacherSchedule) tabs.getSelectedComponent()).refresh();
                break;
            case 5:
                ((PreSchedule) tabs.getSelectedComponent()).refresh();
                break;
            case 6:
                ((Proparties) tabs.getSelectedComponent()).refresh();
                break;
        }
    }

}
