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

import advancedresoucemenaging.GUIClasses.GUIElements;
import advancedresoucemenaging.GUIClasses.GradientPanel;
import advancedresoucemenaging.dataHandling.GlobalSpace;
import advancedresoucemenaging.dataHandling.GlobalStrings;
import advancedresoucemenaging.dataLoading.SavingLoadingSystem;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Stanisalv
 */
public class ControlPanel extends GradientPanel implements ActionListener {

    JButton save, load;
    JTextField opened, school, diretor;
    private final JLabel classesL;
    private final JLabel teacherL;
    private final JLabel subsL;

    public ControlPanel() {
        super(new MigLayout());
        save = GUIElements.getButton(GlobalStrings.saveString);
        save.addActionListener(this);
        add(save, "gapleft 0.5cm, gaptop 0.5cm");

        load = GUIElements.getButton(GlobalStrings.loadString);
        load.addActionListener(this);
        add(load, "gapleft 0.5cm,split 3, gapright 0.5cm");

        JLabel openL = new JLabel(GlobalStrings.opendString);
        add(openL);

        opened = GUIElements.getTextField();
        opened.setEditable(false);
        opened.setPreferredSize(new Dimension(350, 35));
        add(opened, "split 3, wrap 0.5cm");

        JLabel schoolL = new JLabel(GlobalStrings.schoolString);
        schoolL.setBounds(25, 75, 125, 35);
        add(schoolL, "gapleft 0.5cm");

        school = GUIElements.getTextField();
        school.setPreferredSize(new Dimension(250, 35));
        school.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                GlobalSpace.school = school.getText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                GlobalSpace.school = school.getText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                GlobalSpace.school = school.getText();
            }
        });
        add(school, "wrap 0.5cm");

        JLabel directorL = new JLabel(GlobalStrings.principleString);
        directorL.setBounds(25, 130, 125, 35);
        add(directorL, "gapleft 0.5cm");

        diretor = GUIElements.getTextField();
        diretor.setPreferredSize(new Dimension(250, 35));
        diretor.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                GlobalSpace.principle = diretor.getText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                GlobalSpace.principle = diretor.getText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                GlobalSpace.principle = diretor.getText();
            }
        });
        add(diretor, "wrap 0.5cm");

        classesL = new JLabel(GlobalStrings.classesString);
        classesL.setBounds(25, 185, 125, 35);
        add(classesL, "gapleft 0.5cm");

        teacherL = new JLabel(GlobalStrings.teachersString);
        teacherL.setBounds(25, 240, 125, 35);
        add(teacherL, "split 2, gapleft 0.5cm");

        subsL = new JLabel(GlobalStrings.subjectsString);
        subsL.setBounds(25, 295, 125, 35);
        add(subsL, "gapleft 0.5cm");

        refresh();

    }

    public void refresh() {
        school.setText(GlobalSpace.school);
        diretor.setText(GlobalSpace.principle);
        classesL.setText(GlobalStrings.classesString + GlobalSpace.classController.classes.size());
        teacherL.setText(GlobalStrings.teachersString + GlobalSpace.teacherController.teachers.size());
        subsL.setText(GlobalStrings.subjectsString + GlobalSpace.subjectController.subjects.size());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(save)) {
            if (GlobalSpace.openeFile != null) {
                SavingLoadingSystem.saveGlobalSpace(GlobalSpace.openeFile);
                return;
            }
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new File("School_set_up_" + System.currentTimeMillis() / 1000 + ".rsr"));
            if (chooser.showSaveDialog(this) == chooser.APPROVE_OPTION) {
                SavingLoadingSystem.saveGlobalSpace(chooser.getSelectedFile());
            }

        } else if (e.getSource().equals(load)) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileFilter() {

                @Override
                public boolean accept(File f) {
                    return f.getAbsolutePath().endsWith(".rsr") || f.isDirectory();
                }

                @Override
                public String getDescription() {
                    return "";
                }
            });
            if (chooser.showOpenDialog(this) == chooser.APPROVE_OPTION) {
                SavingLoadingSystem.loadGlobalSpace(chooser.getSelectedFile());
                GlobalSpace.openeFile = chooser.getSelectedFile();
                opened.setText(GlobalSpace.openeFile.getName());
                refresh();
            }

        }
    }
}
