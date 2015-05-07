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
import advancedresoucemenaging.tableSTuff.SubjectCellRenderer;
import advancedresoucemenaging.tableSTuff.JLableCellRenderer;
import advancedresoucemenaging.tableSTuff.TableControl;
import advancedresoucemenaging.tableSTuff.StudentPlanTableModel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
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
public class StudentScheduleTab extends GradientPanel
{

    JComboBox<Object> classes;
    JButton print, makePlan, randomize;
    JTable table;
    JScrollPane sp;
    StudentPlanTableModel model;

    public StudentScheduleTab()
    {
        super(new MigLayout());

        JLabel classL = new JLabel(GlobalStrings.classString);
        add(classL, "gapleft 0.5cm,gaptop 0.5cm,split 5");

        classes = GUIElements.getComboField();
        classes.setPreferredSize(new Dimension(125, 35));
        classes.addActionListener((event) ->
        {
            if (classes.getSelectedIndex() != -1)
            {
                TableControl.selectedClass = classes.getSelectedItem().toString();
                model.fireTableDataChanged();
            }
        });
        add(classes, "gaptop 0.5cm");

        print = GUIElements.getButton(GlobalStrings.saveToPdfString);
        print.setBounds(225, 25, 150, 35);
        print.addActionListener((e) ->
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
                    Logger.getLogger(StudentScheduleTab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        add(print, "gaptop 0.5cm");

        makePlan = GUIElements.getButton(GlobalStrings.makeString);
        makePlan.setBounds(385, 25, 125, 35);
        makePlan.addActionListener((ActionEvent event) ->
        {
            new Thread(new Runnable()
            {

                @Override
                public void run()
                {
                    GlobalSpace.setUpController();
                    GlobalSpace.makePlan();
                    model.fireTableDataChanged();
                    table.repaint();

                }
            }).start();
            new Thread(new Runnable()
            {

                @Override
                public void run()
                {
                    while (!GlobalSpace.ready)
                    {
                        try
                        {
                            GlobalSpace.updateClassController();
                            model.fireTableDataChanged();
                            table.repaint();
                            Thread.sleep(50);
                        } catch (InterruptedException ex)
                        {
                            Logger.getLogger(StudentScheduleTab.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
            }).start();
        });
        add(makePlan, "gapleft 0.5cm");

        randomize = GUIElements.getButton("Разбъркай");
        randomize.addActionListener((e) ->
        {
            GlobalSpace.classController.randomize();
            TableControl.selectedClass = classes.getSelectedItem().toString();
            model.fireTableDataChanged();
        });
        add(randomize, "gapleft 0.5cm , wrap 0.5cm");

        model = new StudentPlanTableModel();
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

        sp = new JScrollPane(table);
        sp.setPreferredSize(new Dimension(736, 456));
        add(sp, "gapleft 0.5cm");
    }

    public void refresh()
    {
        classes.removeAllItems();
        for (advancedresoucemenaging.dataHandling.Class clas : GlobalSpace.classController.getClasses().values())
        {
            classes.addItem(clas.getName());
        }
    }
}
