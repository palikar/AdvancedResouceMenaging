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
import advancedresoucemenaging.dataHandling.GlobalSpace;
import advancedresoucemenaging.dataHandling.GlobalStrings;
import advancedresoucemenaging.dataLoading.SavingLoadingSystem;
import advancedresoucemenaging.tableSTuff.JTableLableRenderer;
import advancedresoucemenaging.tableSTuff.TableControl;
import advancedresoucemenaging.tableSTuff.TeacherPlanModel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Stanisalv
 */
public class TeacherSchedule extends GradientPanel
{

    JComboBox<Object> teachers;
    JButton print;
    JTable table;
    JScrollPane sp;
    TeacherPlanModel model;

    public TeacherSchedule()
    {
        super(new MigLayout());

        JLabel classL = new JLabel(GlobalStrings.teacherString);
        add(classL, "gapleft 0.5cm,gaptop 0.5cm, split 3");

        teachers = GUIElements.getComboField();
        teachers.setPreferredSize(new Dimension(125, 35));
        teachers.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (teachers.getSelectedIndex() != -1)
                {
                    TableControl.selectedTeacher = teachers.getSelectedItem().toString();
                    model.fireTableDataChanged();
                }
            }
        });
        add(teachers, "gaptop 0.5cm");
        print = GUIElements.getButton(GlobalStrings.saveToPdfString);
        print.addActionListener((event) ->
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setFont(new Font("SansSerif", Font.ITALIC | Font.BOLD, 10));
            chooser.setSelectedFile(new File("Teacher_Schadule_" + System.currentTimeMillis() / 1000 + ".pdf"));
            if (chooser.showSaveDialog(this) == chooser.APPROVE_OPTION)
            {
                try
                {
                    SavingLoadingSystem.saveTeachersScheduleToPdf(
                            new File(chooser.getSelectedFile().getCanonicalPath() + ".pdf"));
                } catch (IOException ex)
                {
                    Logger.getLogger(Plan.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        add(print, "wrap 0.5cm,gaptop 0.5cm");

        model = new TeacherPlanModel();
        table = new JTable(model);
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

        table.setDefaultRenderer(Object.class, new JTableLableRenderer());

        table.getColumnModel().getColumn(0).setPreferredWidth(45);
        table.getColumnModel().getColumn(1).setPreferredWidth(137);
        table.getColumnModel().getColumn(2).setPreferredWidth(138);
        table.getColumnModel().getColumn(3).setPreferredWidth(138);
        table.getColumnModel().getColumn(4).setPreferredWidth(138);
        table.getColumnModel().getColumn(5).setPreferredWidth(138);

        sp = new JScrollPane(table);
        sp.setPreferredSize(new Dimension(736, 456));
        add(sp, "gapleft 0.5cm");
    }

    public void refresh()
    {
        teachers.removeAllItems();
        for (int i = 0; i < GlobalSpace.teacherController.teachers.size(); i++)
        {
            teachers.addItem(GlobalSpace.teacherController.teachers.get(i));
        }
    }
}
