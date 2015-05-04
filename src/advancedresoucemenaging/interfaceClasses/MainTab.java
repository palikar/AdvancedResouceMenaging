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

import advancedresoucemenaging.GUIClasses.GUIControll;
import advancedresoucemenaging.GUIClasses.GUIElements;
import advancedresoucemenaging.GUIClasses.GradientPanel;
import advancedresoucemenaging.dataHandling.GlobalSpace;
import advancedresoucemenaging.dataHandling.GlobalStrings;
import advancedresoucemenaging.dataLoading.SavingLoadingSystem;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Stanisalv
 */
public class MainTab extends GradientPanel implements ActionListener, ListSelectionListener {

    JButton save, load;
    JTextField opened, school, diretor;
    private final JLabel classesL;
    private final JLabel teacherL;
    private final JLabel subsL;
    JList<Object> params;
    DefaultListModel<Object> paramsModel;
    JTextField value;
    JButton addParam, removeParam;

    public MainTab() {
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
        add(subsL, "gapleft 0.5cm, wrap 0.5cm ");

        JLabel paramsLable = new JLabel(GlobalStrings.paramsString);
        add(paramsLable, "gapleft 0.5cm,wrap 0.25cm,span 2");

        paramsModel = new DefaultListModel<Object>();
        Iterator<Object> paramKeys = GlobalSpace.params.keySet().iterator();
        while (paramKeys.hasNext()) {
            paramsModel.addElement(paramKeys.next());
        }
        params = GUIElements.getList(paramsModel);
        params.addListSelectionListener(this);
        JScrollPane sp = new JScrollPane(params);
        sp.setPreferredSize(new Dimension(300, 200));
        add(sp, "gapleft 0.5cm,span 2,wrap 0.25cm");

        JLabel valueLable = new JLabel(GlobalStrings.valueString);
        add(valueLable, "gapleft 0.5cm,split 2,span 2");

        value = GUIElements.getTextField();
        value.setPreferredSize(new Dimension(150, 35));
        value.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                refreshParam();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                refreshParam();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                refreshParam();
            }
        });
        add(value, "gapleft 0.5cm,wrap 0.25cm");

        addParam = GUIElements.getButton(GlobalStrings.addString);
        addParam.addActionListener(this);
        add(addParam, "gapleft 0.5cm");

        removeParam = GUIElements.getButton(GlobalStrings.removeString);
        removeParam.addActionListener(this);
        add(removeParam, "gapleft 0.5cm");

        refresh();

    }

    public void refresh() {
        school.setText(GlobalSpace.school);
        diretor.setText(GlobalSpace.principle);
        classesL.setText(GlobalStrings.classesString + GlobalSpace.classController.classes.size());
        teacherL.setText(GlobalStrings.teachersString + GlobalSpace.teacherController.teachers.size());
        subsL.setText(GlobalStrings.subjectsString + GlobalSpace.subjectController.subjects.size());
        Iterator<Object> paramKeys = GlobalSpace.params.keySet().iterator();
        paramsModel.clear();
        while (paramKeys.hasNext()) {
            paramsModel.addElement(paramKeys.next());
        }
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

            }
            refresh();
            repaint();

        } else if (e.getSource().equals(addParam)) {
            addParam("Име на праметара", "Добавяне на параметар", paramsModel);
        } else if (e.getSource().equals(removeParam)) {
            if (params.getSelectedIndex() == -1) {
                return;
            }
            GlobalSpace.params.remove(params.getSelectedValue());
            paramsModel.remove(params.getSelectedIndex());
        }
    }

    public void addParam(String msg, String title, DefaultListModel model) {
        JLabel lable = new JLabel(msg);
        lable.setFont(new Font("SansSerif", Font.ITALIC, 20));
        JTextField textFied = new JTextField();
        textFied.setFont(new Font("SansSerif", Font.ITALIC, 20));
        Object obj[] = {lable, textFied};
        if (JOptionPane.showConfirmDialog(this, obj, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.CANCEL_OPTION) {
            return;
        }
        String name = textFied.getText().replaceAll(" ", "");
        if (!name.matches("[A-Za-z0-9А-Яа-я.]+")) {
            JOptionPane.showMessageDialog(this, GlobalStrings.invalidSynaxString, GlobalStrings.errorSting, JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (textFied.getText() != null && !name.equals("")) {
            GlobalSpace.params.put(name, "");
            model.addElement(name);
        }
    }

    private void refreshParam() {
        if (params.getSelectedIndex() != - 1) {
            GlobalSpace.params.put(params.getSelectedValue(), value.getText());
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (params.getSelectedIndex() != -1) {
            value.setText(GlobalSpace.params.get(params.getSelectedValue()).toString());
        } else {
            value.setText("");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        if (GUIControll.renderLogoImage) {
            g.drawImage(GlobalSpace.resizeImage(GlobalSpace.iconImage, GlobalSpace.iconImage.getType(), 250, 250), 450, 275, this);
        }
    }

}
