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

import advancedresoucemenaging.GUIClasses.Colors;
import advancedresoucemenaging.GUIClasses.GUIElements;
import advancedresoucemenaging.GUIClasses.GradientPanel;
import advancedresoucemenaging.algStuff.SubjectPlaceHolder;
import advancedresoucemenaging.dataHandling.GlobalSpace;
import advancedresoucemenaging.dataHandling.GlobalStrings;
import advancedresoucemenaging.dataHandling.ScheduleFabrik;
import advancedresoucemenaging.tableSTuff.JLableCellRenderer;
import advancedresoucemenaging.tableSTuff.StudentPlanTableModel;
import advancedresoucemenaging.tableSTuff.SubjectCellRenderer;
import advancedresoucemenaging.tableSTuff.WeekTable;
import com.alee.laf.menu.WebCheckBoxMenuItem;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Stanisalv
 */
public class CleanupToolTab extends GradientPanel
{

    DefaultListModel<Object> classesModel;
    JList<Object> classes;
    JButton set, swap, remove, showFull, clear;
    WeekTable simpleTable;
    JLabel teacher, subject;
    JFrame newFrame;
    boolean fullOpened = false;
    private StudentPlanTableModel model = null;
    private JTable fullTable = null;

    public CleanupToolTab()
    {
        super(new MigLayout());

        add(new JLabel(GlobalStrings.classesString), "gaptop 0.5cm, gapleft 0.5cm");
        add(new JLabel(GlobalStrings.simpleTablePlanSting), "gapleft 0.5cm,wrap 0.25cm");
        //add(new JLabel(GlobalStrings.parametarsString), "gapleft 0.5cm, wrap 0.25cm");

        classesModel = new DefaultListModel<>();
        classes = GUIElements.getList(classesModel);
        classes.addListSelectionListener(e ->
        {
            changeTables();
        });

        JScrollPane sp2 = new JScrollPane(classes);
        sp2.setPreferredSize(new Dimension(230, 400));
        add(sp2, "gapleft 0.5cm,spany 5");

        simpleTable = new WeekTable();
        simpleTable.setToolTipText(GlobalStrings.simpleWeekTableDesc);
        add(simpleTable, "gapleft 0.5cm,wrap 0.25cm");

        showFull = GUIElements.getButton("Покажи цяла");
        showFull.addActionListener(e ->
        {
            showFullTable();
        });
        add(showFull, "gapleft 0.5cm,align left,aligny top,split 2");

        clear = GUIElements.getButton("Изчисти");
        clear.addActionListener((e) ->
        {
            simpleTable.unselectAll();
        });
        add(clear, "gapleft 0.5cm,align left,aligny top,wrap 0.5cm");

        add(subject = new JLabel(GlobalStrings.subjectString), "gaptop 0.5cm, gapleft 0.5cm, wrap 0.25cm,aligny top");
        add(teacher = new JLabel(GlobalStrings.teacherString), "gapleft 0.5cm,wrap 0.25cm,aligny top");

        set = GUIElements.getButton("Постави");
        set.addActionListener((e) ->
        {
            setSubject();

        });
        add(set, "gapleft 0.5cm,skip,split");
        swap = GUIElements.getButton("Размени");
        swap.addActionListener((e) ->
        {
            swapSubjects();
        });
        add(swap, "gapleft 0.5cm");
        remove = GUIElements.getButton("Премахни");
        remove.addActionListener((e) ->
        {
            removeSubject();

        });
        add(remove, "gapleft 0.5cm");

        simpleTable.setValueChangeAction(() ->
        {

            changeLables();

        });

        refresh();

    }

    private void changeTables()
    {
        simpleTable.unselectAll();
        int index = classes.getSelectedIndex();
        if (index == -1)
        {
            return;
        }
        advancedresoucemenaging.dataHandling.Class clas = GlobalSpace.classController.getClasses().get(classes.getSelectedValue());

        if (fullOpened)
        {

            model.setSelected(clas.getName());
            model.fireTableDataChanged();
        }

        updateMistakes(clas.getName());

    }

    private void updateMistakes(String clas)
    {
        simpleTable.resetColors();
        if (ScheduleFabrik.holes.containsKey(clas))
        {
            ArrayList<Point> holes = ScheduleFabrik.holes.get(clas);
            holes.forEach((Point p) ->
            {
                simpleTable.changeColor(p.x, p.y, Color.red);
            });
        }
        if (ScheduleFabrik.loners.containsKey(clas))
        {
            ArrayList<Point> loners = ScheduleFabrik.loners.get(clas);
            loners.forEach((Point p) ->
            {
                simpleTable.changeColor(p.x, p.y, Color.green);
            });
        }
    }

    private void swapSubjects()
    {
        int index = classes.getSelectedIndex();
        if (index == -1)
        {
            return;
        }
        advancedresoucemenaging.dataHandling.Class clas = GlobalSpace.classController.getClasses().get(classes.getSelectedValue());

        ArrayList<Point> selection = simpleTable.getSelected();
        if (selection.size() != 2)
        {
            simpleTable.unselectAll();
            return;
        }

        GlobalSpace.classController.swap(clas, selection.get(0), selection.get(1));

        if (fullOpened)
        {
            model.fireTableDataChanged();
        }
        GlobalSpace.runMistakeChecker();
        updateMistakes(classes.getSelectedValue().toString());
    }

    private void changeLables()
    {
        int index = classes.getSelectedIndex();
        if (index == -1)
        {
            return;
        }
        SubjectPlaceHolder sub = GlobalSpace.classController.getClasses().get(classes.getSelectedValue())
                .getSchedule()[simpleTable.getLastSelected().x][simpleTable.getLastSelected().y];
        if (sub.equals(SubjectPlaceHolder.empty))
        {
            teacher.setText(GlobalStrings.teacherString);
            subject.setText(GlobalStrings.subjectString);

        } else
        {
            teacher.setText(GlobalStrings.teacherString + sub.getTeacher());
            subject.setText(GlobalStrings.subjectString + sub.getSubject());
        }
        if (fullOpened)
        {
            fullTable.changeSelection(simpleTable.getLastSelected().y, simpleTable.getLastSelected().x + 1, false, false);
        }
    }

    private void removeSubject()
    {
        int index = classes.getSelectedIndex();
        if (index == -1)
        {
            return;
        }
        ArrayList<Point> selected = simpleTable.getSelected();
        selected.forEach((Point p) ->
        {
            GlobalSpace.classController.getClasses().get(classes.getSelectedValue())
                    .getSchedule()[p.x][p.y] = SubjectPlaceHolder.empty;

        });
        if (fullOpened)
        {
            model.fireTableDataChanged();
        }
        teacher.setText(GlobalStrings.teacherString);
        subject.setText(GlobalStrings.subjectString);
        simpleTable.unselectAll();
        GlobalSpace.runMistakeChecker();
        updateMistakes(classes.getSelectedValue().toString());

    }

    private void showFullTable()
    {
        if (fullOpened)
        {
            return;
        }
        int index = classes.getSelectedIndex();
        if (index == -1)
        {
            return;
        }
        advancedresoucemenaging.dataHandling.Class clas = GlobalSpace.classController.getClasses().get(classes.getSelectedValue());
        newFrame = new JFrame("Програма на " + clas.getName());
        model = new StudentPlanTableModel();
        model.setSelected(clas.getName());
        fullTable = new JTable(model);
        fullTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        fullTable.setRowSelectionAllowed(true);
        fullTable.setFillsViewportHeight(true);
        fullTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        fullTable.setRowHeight(60);
        fullTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 17));
        fullTable.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        fullTable.getTableHeader().setReorderingAllowed(false);
        fullTable.getTableHeader().setResizingAllowed(false);
        fullTable.setBorder(GUIElements.defaultBorder);
        fullTable.setGridColor(Colors.tableGridColor);
        fullTable.setSelectionBackground(Colors.tableSelectionBackGround);
        fullTable.getTableHeader().setForeground(Colors.tableHeaderColor);

        fullTable.setCellSelectionEnabled(true);

        fullTable.getColumnModel().getColumn(0).setPreferredWidth(45);
        fullTable.getColumnModel().getColumn(1).setPreferredWidth(137);
        fullTable.getColumnModel().getColumn(2).setPreferredWidth(138);
        fullTable.getColumnModel().getColumn(3).setPreferredWidth(138);
        fullTable.getColumnModel().getColumn(4).setPreferredWidth(138);
        fullTable.getColumnModel().getColumn(5).setPreferredWidth(138);

        fullTable.getColumnModel().getColumn(0).setCellRenderer(new JLableCellRenderer());
        fullTable.getColumnModel().getColumn(1).setCellRenderer(new SubjectCellRenderer());
        fullTable.getColumnModel().getColumn(2).setCellRenderer(new SubjectCellRenderer());
        fullTable.getColumnModel().getColumn(3).setCellRenderer(new SubjectCellRenderer());
        fullTable.getColumnModel().getColumn(4).setCellRenderer(new SubjectCellRenderer());
        fullTable.getColumnModel().getColumn(5).setCellRenderer(new SubjectCellRenderer());

        JScrollPane sp = new JScrollPane(fullTable);
        sp.setPreferredSize(new Dimension(736, 456));
        newFrame.add(sp);
        newFrame.pack();
        newFrame.setResizable(false);
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JMenuBar bar = new WebMenuBar()
        //<editor-fold defaultstate="collapsed" desc="Dont look">
        {
            {
                add(new WebMenu(GlobalStrings.settingsString)
                {
                    {
                        add(new WebCheckBoxMenuItem(GlobalStrings.alwaysOnTopString)
                        {
                            {
                                setSelected(true);
                                addActionListener((e) ->
                                {
                                    newFrame.setAlwaysOnTop(this.isSelected());
                                });
                            }
                        });
                    }
                });
            }
        };
//</editor-fold>
        newFrame.setJMenuBar(bar);
        newFrame.setAlwaysOnTop(true);
        newFrame.setVisible(true);
        fullOpened = true;

        newFrame.addWindowListener(new WindowAdapter()
        {

            @Override
            public void windowClosed(WindowEvent e)
            {
                super.windowClosed(e); //To change body of generated methods, choose Tools | Templates.
                fullOpened = false;
                fullTable = null;
                model = null;
            }

        });

    }

    private void setSubject()
    {

        int index = classes.getSelectedIndex();
        if (index == -1)
        {
            return;
        }
        if (simpleTable.getSelected().isEmpty())
        {
            return;
        }
        simpleTable.unselectAll();
        simpleTable.select(simpleTable.getLastSelected());
        SetSubjectFrame settingFrame = new SetSubjectFrame(GlobalSpace.classController.getClasses().get(classes.getSelectedValue()).getSubjects().toArray(),
                simpleTable.getLastSelected().x, simpleTable.getLastSelected().y,
                classes.getSelectedValue().toString());
        settingFrame.overrideLimitations();
        settingFrame.addWindowListener(new WindowAdapter()
        {

            @Override
            public void windowClosed(WindowEvent e)
            {
                super.windowClosed(e); //To change body of generated methods, choose Tools | Templates.

                SubjectPlaceHolder sub = GlobalSpace.classController.getClasses().get(classes.getSelectedValue())
                        .getSchedule()[simpleTable.getLastSelected().x][simpleTable.getLastSelected().y];
                if (sub.equals(SubjectPlaceHolder.empty))
                {
                    teacher.setText(GlobalStrings.teacherString);
                    subject.setText(GlobalStrings.subjectString);
                    return;
                }
                teacher.setText(GlobalStrings.teacherString + sub.getTeacher());
                subject.setText(GlobalStrings.subjectString + sub.getSubject());
                if (fullOpened)
                {
                    fullTable.tableChanged(new TableModelEvent(fullTable.getModel()));
                }
                GlobalSpace.runMistakeChecker();
                updateMistakes(classes.getSelectedValue().toString());
            }

        });

    }

    void refresh()
    {
        classesModel.clear();
        GlobalSpace.classController.getClasses().values().forEach((clas) ->
        {
            classesModel.addElement(clas.getName());

        });
    }
}
