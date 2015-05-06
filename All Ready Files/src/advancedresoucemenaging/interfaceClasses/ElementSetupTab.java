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
import advancedresoucemenaging.dataHandling.Handler;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import static advancedresoucemenaging.GUIClasses.GUIElements.getButton;
import advancedresoucemenaging.dataHandling.GlobalStrings;
import advancedresoucemenaging.dataHandling.Subject;
import com.alee.laf.scroll.WebScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Stanisalv
 */
public class ElementSetupTab extends GradientPanel implements ActionListener
{

    JList<Object> classes, subjects, teachers;
    DefaultListModel<Object> classesModel, subjectsModel, teachersModel;
    JButton add1, add2, add3, remove1, remove2, remove3;
    JRadioButtonMenuItem easyButton, mediumButton, hardButton;

    public ElementSetupTab()
    {
        super(new MigLayout("", "", ""));

        JLabel clasL = new JLabel(GlobalStrings.classesString);
        add(clasL, "gaptop 0.5cm, gapleft 0.5cm, span 2");

        JLabel subsL = new JLabel(GlobalStrings.subjectsString);
        add(subsL, "span 2,gapleft 0.5cm");

        JLabel teachL = new JLabel(GlobalStrings.teachersString);
        add(teachL, "wrap 0.25cm,gapleft 0.5cm");

        classesModel = new DefaultListModel<>();
        classes = GUIElements.getList(classesModel);
        WebScrollPane sp1 = new WebScrollPane(classes);
        sp1.setPreferredSize(new Dimension(200, 300));
        classes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(sp1, "gapleft 0.5cm,span 2");

        subjectsModel = new DefaultListModel<>();
        subjects = GUIElements.getList(subjectsModel);
        ButtonGroup group = new ButtonGroup();
        JPopupMenu men = new JPopupMenu("hello");
        easyButton = new JRadioButtonMenuItem("Easy");
        easyButton.addActionListener((e) ->
        {
            GlobalSpace.subjectController.getSubjects().get(subjects.getSelectedIndex()).setHardness(1);
        });
        mediumButton = new JRadioButtonMenuItem("Medium");
        mediumButton.addActionListener((e) ->
        {
            GlobalSpace.subjectController.getSubjects().get(subjects.getSelectedIndex()).setHardness(2);
        });
        hardButton = new JRadioButtonMenuItem("Hard");
        hardButton.addActionListener((e) ->
        {
            GlobalSpace.subjectController.getSubjects().get(subjects.getSelectedIndex()).setHardness(3);
        });
        men.add(easyButton);
        men.add(mediumButton);
        men.add(hardButton);
        group.add(easyButton);
        group.add(mediumButton);
        group.add(hardButton);
        subjects.add(men);
        subjects.addMouseListener(new MouseAdapter()
        {

            @Override
            public void mouseReleased(MouseEvent e)
            {

                boolean con = subjects.locationToIndex(e.getPoint()) == subjects.getSelectedIndex();

                if (!subjectsModel.isEmpty() && e.getButton() == MouseEvent.BUTTON3 && con)
                {

                    int hardnes = GlobalSpace.subjectController.getSubjects().get(subjects.getSelectedIndex()).getHardness();
                    switch (hardnes)
                    {
                        case Subject.EASY:
                            easyButton.setSelected(true);
                            break;
                        case Subject.MEDIUM:
                            mediumButton.setSelected(true);
                            break;
                        case Subject.HARD:
                            hardButton.setSelected(true);
                            break;

                    }
                    men.show(subjects,
                            e.getX(), e.getY());
                }
            }

        });

        subjects.addListSelectionListener((event) ->
        {

        });

        WebScrollPane sp2 = new WebScrollPane(subjects);
        sp2.setPreferredSize(new Dimension(200, 300));

        add(sp2, "span 2,gapleft 0.5cm");

        teachersModel = new DefaultListModel<>();
        teachers = GUIElements.getList(teachersModel);
        WebScrollPane sp3 = new WebScrollPane(teachers);
        sp3.setPreferredSize(new Dimension(200, 300));
        add(sp3, "wrap, gapleft 0.5cm");

        add1 = getButton(GlobalStrings.addString);
        add1.setBounds(55, 370, 125, 35);
        add1.addActionListener(this);
        add(add1, "span 2, gapleft 2cm,gaptop 0.5cm");

        add2 = getButton(GlobalStrings.addString);
        add2.setBounds(330, 370, 125, 35);
        add2.addActionListener(this);
        add(add2, "span 2, gapleft 2cm");

        add3 = getButton(GlobalStrings.addString);
        add3.setBounds(610, 370, 125, 35);
        add3.addActionListener(this);
        add(add3, "wrap, gapleft 2cm");

        remove1 = getButton(GlobalStrings.removeString);
        remove1.setBounds(55, 410, 125, 35);
        remove1.addActionListener(this);
        add(remove1, "span 2, gapleft 1.95cm,gaptop 0.125cm");

        remove2 = getButton(GlobalStrings.removeString);
        remove2.setBounds(330, 410, 125, 35);
        remove2.addActionListener(this);
        add(remove2, "span 2, gapleft 1.95cm");

        remove3 = getButton(GlobalStrings.removeString);
        remove3.setBounds(610, 410, 125, 35);
        remove3.addActionListener(this);
        add(remove3, "wrap, gapleft 1.95cm");

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(add1))
        {
            addElement(GlobalStrings.nameOfClassString, GlobalStrings.newClassString, classesModel, GlobalSpace.classController);
        } else if (e.getSource().equals(add2))
        {
            addElement(GlobalStrings.subjectString, GlobalStrings.newSubjectString, subjectsModel, GlobalSpace.subjectController);
        } else if (e.getSource().equals(add3))
        {
            addElement(GlobalStrings.nameOfTeacherString, GlobalStrings.newTeacherString, teachersModel, GlobalSpace.teacherController);
        } else if (e.getSource().equals(remove1))
        {
            removeElement(classes, classesModel, GlobalSpace.classController);
        } else if (e.getSource().equals(remove2))
        {
            removeElement(subjects, subjectsModel, GlobalSpace.subjectController);
        } else if (e.getSource().equals(remove3))
        {
            removeElement(teachers, teachersModel, GlobalSpace.teacherController);
        }
    }

    public void addElement(String msg, String title, DefaultListModel model, Handler conroller)
    {
        JLabel lable = new JLabel(msg);
        lable.setFont(new Font("SansSerif", Font.ITALIC, 20));
        JTextField textFied = new JTextField();
        textFied.setFont(new Font("SansSerif", Font.ITALIC, 20));
        Object obj[] =
        {
            lable, textFied
        };
        if (JOptionPane.showConfirmDialog(this, obj, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.CANCEL_OPTION)
        {
            return;
        }
        String name = textFied.getText().replaceAll(" ", "");
        if (!name.matches("[A-Za-z0-9А-Яа-я.]+"))
        {
            JOptionPane.showMessageDialog(this, GlobalStrings.invalidSynaxString, GlobalStrings.errorSting, JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (textFied.getText() != null && !name.equals("") && !conroller.contains(name))
        {
            model.addElement(name);
            conroller.add(name);
        }
    }

    public void removeElement(JList<Object> jList, DefaultListModel model, Handler conroller)
    {
        if (jList.getSelectedIndex() != -1)
        {
            conroller.remove(jList.getSelectedValue().toString());
            model.remove(jList.getSelectedIndex());
        }
    }

    public void refresh()
    {
        classesModel.clear();
        teachersModel.clear();
        subjectsModel.clear();
        for (advancedresoucemenaging.dataHandling.Class clas : GlobalSpace.classController.getClasses().values())
        {
            classesModel.addElement(clas.getName());
        }
        for (int i = 0; i < GlobalSpace.subjectController.getSubjects().size(); i++)
        {
            subjectsModel.addElement(GlobalSpace.subjectController.getSubjects().get(i).getName());
        }
        for (int i = 0; i < GlobalSpace.teacherController.getTeachers().size(); i++)
        {
            teachersModel.addElement(GlobalSpace.teacherController.getTeachers().get(i));
        }
    }
}
