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
import advancedresoucemenaging.conditionClasses.ConditionDescription;
import advancedresoucemenaging.GUIClasses.GradientPanel;
import advancedresoucemenaging.conditionClasses.ConditionCodeList;
import advancedresoucemenaging.dataHandling.GlobalSpace;
import advancedresoucemenaging.dataHandling.GlobalStrings;
import advancedresoucemenaging.tableSTuff.WeekTable;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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

    public CleanupToolTab()
    {
        super(new MigLayout());

        add(new JLabel(GlobalStrings.classesString), "gaptop 0.5cm, gapleft 0.5cm");
        add(new JLabel(GlobalStrings.simpleTablePlanSting), "gapleft 0.5cm,wrap 0.25cm");
        //add(new JLabel(GlobalStrings.parametarsString), "gapleft 0.5cm, wrap 0.25cm");

        classesModel = new DefaultListModel<>();
        classes = GUIElements.getList(classesModel);

        JScrollPane sp2 = new JScrollPane(classes);
        sp2.setPreferredSize(new Dimension(230, 400));
        add(sp2, "gapleft 0.5cm,spany 4");

        simpleTable = new WeekTable();
        add(simpleTable, "gapleft 0.5cm,wrap 0.25cm");

        showFull = GUIElements.getButton("Покажи цяла");
        add(showFull, "gapleft 0.5cm,align right,aligny top,wrap 0.5cm");

        add(new JLabel("Предмет:"), "gaptop 0.5cm, gapleft 0.5cm, wrap 0.25cm,aligny top");
        add(new JLabel("Учител:"), "gapleft 0.5cm,wrap 0.25cm,aligny top");

        set = GUIElements.getButton("Постави");
        add(set, "gapleft 0.5cm,skip,split");
        swap = GUIElements.getButton("Размени");
        add(swap, "gapleft 0.5cm");
        remove = GUIElements.getButton("Премахни");
        add(remove, "gapleft 0.5cm");

        refresh();
    }

    void refresh()
    {
        classesModel.clear();
        for (int i = 0; i < GlobalSpace.classController.getClasses().size(); i++)
        {
            classesModel.addElement(GlobalSpace.classController.getClasses().get(i));
        }
    }
}
