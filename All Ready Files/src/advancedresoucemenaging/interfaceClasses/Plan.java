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
import advancedresoucemenaging.tableSTuff.CustomCellRenderer;
import advancedresoucemenaging.tableSTuff.JTableLableRenderer;
import advancedresoucemenaging.tableSTuff.TableControl;
import advancedresoucemenaging.tableSTuff.PlanTableModel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Stanisalv
 */
public class Plan extends GradientPanel {

    JComboBox<Object> classes;
    JButton print, makePlan;
    JTable table;
    JScrollPane sp;
    PlanTableModel model;

    public Plan() {
        super(new MigLayout());

        JLabel classL = new JLabel(GlobalStrings.classString);
        add(classL, "gapleft 0.5cm,gaptop 0.5cm,split 4");

        classes = GUIElements.getComboField();
        classes.setPreferredSize(new Dimension(125, 35));
        classes.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (classes.getSelectedIndex() != -1) {
                    TableControl.selectedClass = classes.getSelectedItem().toString();
                    model.fireTableDataChanged();
                }
            }
        });
        add(classes, "gaptop 0.5cm");

        print = GUIElements.getButton(GlobalStrings.printString);
        print.setBounds(225, 25, 150, 35);
        add(print, "gaptop 0.5cm");

        makePlan = GUIElements.getButton(GlobalStrings.makeString);
        makePlan.setBounds(385, 25, 125, 35);
        makePlan.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        GlobalSpace.setUpController();
                        GlobalSpace.makePlan();
                        model.fireTableDataChanged();
                        table.repaint();

                    }
                }).start();

            }
        });
        add(makePlan, "wrap 0.5cm");

        model = new PlanTableModel();
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

        table.getColumnModel().getColumn(0).setCellRenderer(new JTableLableRenderer());
        table.getColumnModel().getColumn(1).setCellRenderer(new CustomCellRenderer());
        table.getColumnModel().getColumn(2).setCellRenderer(new CustomCellRenderer());
        table.getColumnModel().getColumn(3).setCellRenderer(new CustomCellRenderer());
        table.getColumnModel().getColumn(4).setCellRenderer(new CustomCellRenderer());
        table.getColumnModel().getColumn(5).setCellRenderer(new CustomCellRenderer());

        sp = new JScrollPane(table);
        sp.setPreferredSize(new Dimension(736, 456));
        add(sp, "gapleft 0.5cm");
    }

    public void refresh() {
        classes.removeAllItems();
        for (int i = 0; i < GlobalSpace.classController.classes.size(); i++) {
            classes.addItem(GlobalSpace.classController.classes.get(i));
        }
    }
}
