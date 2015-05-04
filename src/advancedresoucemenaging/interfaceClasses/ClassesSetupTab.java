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
import advancedresoucemenaging.algStuff.SubjectPlaceHolder;
import advancedresoucemenaging.dataHandling.Subject;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Stanisalv
 */
public class ClassesSetupTab extends GradientPanel implements ActionListener, ListSelectionListener {

    DefaultListModel<Object> classesModel;
    DefaultListModel<SubjectPlaceHolder> subjectsModel;
    JList<Object> classes;
    JList<SubjectPlaceHolder> subjects;
    JComboBox<Object> subs, teachers;
    JTextField timesPerWeek, times;
    TitledBorder addingSub;
    JButton add, remove;
    Map<SubjectPlaceHolder, Integer> currentMap;
    JLabel timesL;

    public ClassesSetupTab() {
        super(new MigLayout());

        JLabel clasL = new JLabel(GlobalStrings.classesString);
        add(clasL, "gaptop 0.5cm, gapleft 0.5cm, span 3");

        JLabel subsL = new JLabel(GlobalStrings.subsOfClassString);
        add(subsL, "gapleft 0.5cm, wrap 0.25cm");

        classesModel = new DefaultListModel<>();
        classes = GUIElements.getList(classesModel);
        JScrollPane sp2 = new JScrollPane(classes);
        sp2.setPreferredSize(new Dimension(200, 270));
        classes.addListSelectionListener(this);
        add(sp2, "gapleft 0.5cm,span 3");

        subjectsModel = new DefaultListModel<>();
        subjects = new JList<SubjectPlaceHolder>(subjectsModel);
        subjects.setCellRenderer(new ListCellRenderer<Object>() {

            @Override
            public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof SubjectPlaceHolder) {
                    JLabel lable;
                    SubjectPlaceHolder sub = (SubjectPlaceHolder) value;
                    lable = new JLabel();
                    lable.setText("<html><strong>" + sub.getSubject() + "</strong><br>" + sub.getTeacher() + "</html>");
                    lable.setFont(new Font("SansSerif", Font.PLAIN, 15));
                    lable.setOpaque(true);
                    if (isSelected) {
                        lable.setBackground(subjects.getSelectionBackground());
                        lable.setForeground(subjects.getSelectionForeground());
                    } else {
                        lable.setBackground(subjects.getBackground());
                        lable.setForeground(subjects.getForeground());

                    }
                    return lable;
                }
                return null;
            }
        });
        JScrollPane sp1 = new JScrollPane(subjects);
        sp1.setPreferredSize(new Dimension(200, 270));
        subjects.addListSelectionListener(this);
        add(sp1, "gapleft 0.5cm, wrap 0.25cm");

        JLabel addingLable = new JLabel(GlobalStrings.addingSubjectToClass);
        add(addingLable, "gapleft 0.5cm");

        timesL = new JLabel(GlobalStrings.timesPerWeekInClassSetUpPanel);
        add(timesL, " gapleft 0.5cm, cell 3 2,split");

        times = GUIElements.getTextField();
        times.setPreferredSize(new Dimension(50, 25));
        times.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                if (subjects.getSelectedIndex() != -1 && !times.getText().replace(" ", "").equals("")) {

                    int newTimes = Integer.parseInt(times.getText());
                    SubjectPlaceHolder sub = subjectsModel.get(subjects.getSelectedIndex());
                    GlobalSpace.classController.getClasses().get(classes.getSelectedValue().toString()).getSubjectPlan().put(sub, newTimes);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (subjects.getSelectedIndex() != -1 && !times.getText().replace(" ", "").equals("")) {

                    int newTimes = Integer.parseInt(times.getText());
                    SubjectPlaceHolder sub = subjectsModel.get(subjects.getSelectedIndex());
                    GlobalSpace.classController.getClasses().get(classes.getSelectedValue().toString()).getSubjectPlan().put(sub, newTimes);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

                if (subjects.getSelectedIndex() != -1 && !times.getText().replace(" ", "").equals("")) {

                    int newTimes = Integer.parseInt(times.getText());
                    SubjectPlaceHolder sub = subjectsModel.get(subjects.getSelectedIndex());
                    GlobalSpace.classController.getClasses().get(classes.getSelectedValue().toString()).getSubjectPlan().put(sub, newTimes);
                }

            }
        });
        add(times, " wrap 0.25cm, gapleft 0.5cm, split");

        JLabel subsCL = new JLabel(GlobalStrings.subjectString);
        add(subsCL, "gapleft 0.5cm, split 2");

        subs = GUIElements.getComboField();
        subs.setPreferredSize(new Dimension(135, 25));
        add(subs, "wrap 0.25cm");

        JLabel teachL = new JLabel(GlobalStrings.teacherString);
        teachL.setBounds(25, 405, 135, 35);
        add(teachL, "gapleft 0.5cm, split 2");

        teachers = GUIElements.getComboField();
        teachers.setPreferredSize(new Dimension(135, 25));
        teachers.setFont(new Font("SansSerif", Font.ITALIC, 17));
        add(teachers, "wrap 0.25cm");

        JLabel temesPerWeekLable = new JLabel(GlobalStrings.classesPerWeek);
        add(temesPerWeekLable, "gapleft 0.5cm,split 2");

        timesPerWeek = GUIElements.getTextField();
        timesPerWeek.setPreferredSize(new Dimension(75, 25));
        add(timesPerWeek, "wrap 0.25cm");

        add = GUIElements.getButton(GlobalStrings.addString);
        add.setBounds(25, 485, 135, 25);
        add.addActionListener(this);
        add(add, "gapleft 0.5cm,split 2 ");

        remove = GUIElements.getButton(GlobalStrings.removeString);
        remove.setBounds(180, 485, 135, 25);
        remove.addActionListener(this);

        add(remove);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    void refresh() {
        classesModel.clear();
        for (advancedresoucemenaging.dataHandling.Class clas : GlobalSpace.classController.getClasses().values()) {
            classesModel.addElement(clas.getName());
        }
        subs.removeAllItems();
        for (int i = 0; i < GlobalSpace.subjectController.getSubjects().size(); i++) {
            Subject object = GlobalSpace.subjectController.getSubjects().get(i);
            subs.addItem(object.getName());
        }
        teachers.removeAllItems();
        for (int i = 0; i < GlobalSpace.teacherController.getTeachers().size(); i++) {
            String object = GlobalSpace.teacherController.getTeachers().get(i);
            teachers.addItem(object);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(add) || e.getSource().equals(timesPerWeek)) {
            if (classes.getSelectedIndex() != -1
                    && teachers.getSelectedIndex() != -1
                    && subs.getSelectedIndex() != -1) {
                SubjectPlaceHolder sub = new SubjectPlaceHolder(subs.getSelectedItem().toString(), teachers.getSelectedItem().toString());
                int timesPerWeekInt = Integer.parseInt(timesPerWeek.getText().replaceAll(" ", ""));
                GlobalSpace.classController.getClasses().get(classes.getSelectedValue().toString()).getSubjectPlan().put(sub, timesPerWeekInt);
            }
            refreshSubjectsOfClass();
        } else if (e.getSource().equals(remove)) {
            if (classes.getSelectedIndex() != -1
                    && subjects.getSelectedIndex() != -1) {
                GlobalSpace.classController.getClasses().get(classes.getSelectedValue()).getSubjectPlan().remove(subjects.getSelectedValue());
                subjectsModel.remove(subjects.getSelectedIndex());
            }
        }
    }

    private void refreshSubjectsOfClass() {
        if (classes.getSelectedIndex() != -1) {
            Map<SubjectPlaceHolder, Integer> map = GlobalSpace.classController.getClasses().get(classes.getSelectedValue()).getSubjectPlan();
            currentMap = map;
            subjectsModel.clear();
            for (SubjectPlaceHolder key : map.keySet()) {
                subjectsModel.addElement(key);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource().equals(classes)) {
            refreshSubjectsOfClass();
            timesL.setText(GlobalStrings.timesPerWeekInClassSetUpPanel);
        } else if (e.getSource().equals(subjects)) {
            if (subjects.getSelectedIndex() != -1) {
                times.setText("" + currentMap.get(subjectsModel.get(subjects.getSelectedIndex())));
            }
        }
    }
}
