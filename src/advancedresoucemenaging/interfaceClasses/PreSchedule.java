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
import advancedresoucemenaging.tableSTuff.PrePlanTableModel;
import advancedresoucemenaging.tableSTuff.TableControl;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Stanisalv
 */
public class PreSchedule extends GradientPanel implements MouseListener, ActionListener
{

    JTable table;
    JScrollPane sp;
    PrePlanTableModel model;
    JComboBox<Object> classes;
    JButton clearClass, clearAll;

    public PreSchedule()
    {
        super(new MigLayout());

        JLabel classL = new JLabel(GlobalStrings.classString);
        add(classL, "gapleft 0.5cm,gaptop 0.5cm,split 4");

        classes = GUIElements.getComboField();
        classes.setPreferredSize(new Dimension(125, 35));
        classes.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (classes.getSelectedIndex() != -1)
                {
                    TableControl.selectedClass = classes.getSelectedItem().toString();
                    model.fireTableDataChanged();
                }
            }
        });
        add(classes, "gaptop 0.5cm");

        clearAll = GUIElements.getButton(GlobalStrings.clearAllString);
        clearAll.addActionListener(this);
        add(clearAll);

        clearClass = GUIElements.getButton(GlobalStrings.clearClassString);
        clearClass.addActionListener(this);
        add(clearClass, "wrap 0.5cm");

        model = new PrePlanTableModel();
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

        table.addMouseListener(this);

        sp = new JScrollPane(table);
        sp.setPreferredSize(new Dimension(736, 456));
        add(sp, "gapleft 0.5cm");

    }

    public void refresh()
    {
        classes.removeAllItems();
        for (advancedresoucemenaging.dataHandling.Class clas : GlobalSpace.classController.classes.values())
        {
            classes.addItem(clas.name);
        }
    }
    JFrame ob = null;

    private void setSubject(int row, int col)
    {
        if (table.getSelectedColumn() == -1 || table.getSelectedRow() == -1 || ob != null || TableControl.selectedClass.equals(""))
        {
            return;
        }

        ob = new SetSubject(GlobalSpace.classController.classes.get(TableControl.selectedClass).subjectPlan.keySet().toArray(),
                col,
                row,
                TableControl.selectedClass)
                {

                    @Override
                    public void dispose()
                    {
                        super.dispose();
                        model.fireTableDataChanged();
                        ob = null;
                    }
                };
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        int row = table.rowAtPoint(e.getPoint());
        int col = table.columnAtPoint(e.getPoint());
        if (col != 0)
        {
            setSubject(row, col - 1);
        }
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(clearClass))
        {
            if (classes.getSelectedIndex() != -1)
            {
                GlobalSpace.classController.clearShcedule(classes.getSelectedItem().toString());
            }
            model.fireTableDataChanged();
        } else if (e.getSource().equals(clearAll))
        {
            GlobalSpace.classController.clearAllSchedules();
            model.fireTableDataChanged();
        }
    }
}
