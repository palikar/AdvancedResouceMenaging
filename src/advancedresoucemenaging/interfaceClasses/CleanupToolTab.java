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
import advancedresoucemenaging.tableSTuff.JLableCellRenderer;
import advancedresoucemenaging.tableSTuff.StudentPlanTableModel;
import advancedresoucemenaging.tableSTuff.SubjectCellRenderer;
import advancedresoucemenaging.tableSTuff.WeekTable;
import com.alee.laf.menu.WebCheckBoxMenuItem;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
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
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Stanisalv
 */
public class CleanupToolTab extends GradientPanel
{

    DefaultListModel<Object> classesModel;
    JList<Object> classes;
    JButton set, swap, remove, showFull;
    WeekTable simpleTable;
    JLabel teacher, subject;
    JFrame newFrame;
    boolean fullOpened = false;
    private StudentPlanTableModel model;

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
            simpleTable.unselectAll();
            if (fullOpened)
            {
                int index = classes.getSelectedIndex();
                if (index == -1)
                {
                    return;
                }
                advancedresoucemenaging.dataHandling.Class clas = GlobalSpace.classController.getClasses().get(classes.getSelectedValue());
                model.setSelected(clas.getName());
                model.fireTableDataChanged();
            }
        });

        JScrollPane sp2 = new JScrollPane(classes);
        sp2.setPreferredSize(new Dimension(230, 400));
        add(sp2, "gapleft 0.5cm,spany 4");

        simpleTable = new WeekTable();
        add(simpleTable, "gapleft 0.5cm,wrap 0.25cm");

        showFull = GUIElements.getButton("Покажи цяла");
        showFull.addActionListener(e ->
        {
            showFullTable();
        });
        add(showFull, "gapleft 0.5cm,align right,aligny top,wrap 0.5cm");

        add(subject = new JLabel(GlobalStrings.subjectString), "gaptop 0.5cm, gapleft 0.5cm, wrap 0.25cm,aligny top");
        add(teacher = new JLabel(GlobalStrings.teacherString), "gapleft 0.5cm,wrap 0.25cm,aligny top");

        set = GUIElements.getButton("Постави");
        add(set, "gapleft 0.5cm,skip,split");
        swap = GUIElements.getButton("Размени");
        add(swap, "gapleft 0.5cm");
        remove = GUIElements.getButton("Премахни");
        remove.addActionListener((e) ->
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

        });
        add(remove, "gapleft 0.5cm");

        simpleTable.setValueChangeAction(() ->
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
                return;
            }
            teacher.setText(GlobalStrings.teacherString + sub.getTeacher());
            subject.setText(GlobalStrings.subjectString + sub.getSubject());

        });

        refresh();

    }

    private void showFullTable()
    {
        int index = classes.getSelectedIndex();
        if (index == -1)
        {
            return;
        }
        advancedresoucemenaging.dataHandling.Class clas = GlobalSpace.classController.getClasses().get(classes.getSelectedValue());
        newFrame = new JFrame("Програма на " + clas.getName());
        model = new StudentPlanTableModel();
        model.setSelected(clas.getName());
        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowSelectionAllowed(true);
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowHeight(60);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 17));
        table.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setBorder(GUIElements.defaultBorder);
        table.setGridColor(Colors.tableGridColor);
        table.setSelectionBackground(Colors.tableSelectionBackGround);
        table.getTableHeader().setForeground(Colors.tableHeaderColor);

        table.setCellSelectionEnabled(true);

        table.getColumnModel().getColumn(0).setPreferredWidth(45);
        table.getColumnModel().getColumn(1).setPreferredWidth(137);
        table.getColumnModel().getColumn(2).setPreferredWidth(138);
        table.getColumnModel().getColumn(3).setPreferredWidth(138);
        table.getColumnModel().getColumn(4).setPreferredWidth(138);
        table.getColumnModel().getColumn(5).setPreferredWidth(138);

        table.getColumnModel().getColumn(0).setCellRenderer(new JLableCellRenderer());
        table.getColumnModel().getColumn(1).setCellRenderer(new SubjectCellRenderer());
        table.getColumnModel().getColumn(2).setCellRenderer(new SubjectCellRenderer());
        table.getColumnModel().getColumn(3).setCellRenderer(new SubjectCellRenderer());
        table.getColumnModel().getColumn(4).setCellRenderer(new SubjectCellRenderer());
        table.getColumnModel().getColumn(5).setCellRenderer(new SubjectCellRenderer());

        JScrollPane sp = new JScrollPane(table);
        sp.setPreferredSize(new Dimension(736, 456));
        newFrame.add(sp);
        newFrame.pack();
        newFrame.setResizable(false);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
