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
public class Proparties extends GradientPanel implements ListSelectionListener, ActionListener
{

    DefaultListModel<Object> classesModel, propartiesModel;
    JList<Object> classes, proparties;
    JButton add, remove;
    JPanel params;
    Graphics g;
    ConditionDescription emptyDesc = new ConditionDescription(0, null, null);
    ConditionDescription currentDesc = emptyDesc;
    ArrayList<Boolean> allClasses;

    public Proparties()
    {
        super(new MigLayout());

        add(new JLabel(GlobalStrings.classesString), "gaptop 0.5cm, gapleft 0.5cm, span 2");
        add(new JLabel(GlobalStrings.propartiesSting), "gapleft 0.5cm");
        add(new JLabel(GlobalStrings.parametarsString), "gapleft 0.5cm, wrap 0.25cm");

        classesModel = new DefaultListModel<>();
        classes = GUIElements.getList(classesModel);
        classes.addListSelectionListener(this);
        JScrollPane sp2 = new JScrollPane(classes);
        sp2.setPreferredSize(new Dimension(150, 350));
        add(sp2, "gapleft 0.5cm");

        propartiesModel = new DefaultListModel<>();
        proparties = GUIElements.getList(propartiesModel);
        JScrollPane sp1 = new JScrollPane(proparties);
        proparties.addListSelectionListener(this);
        sp1.setPreferredSize(new Dimension(200, 350));
        add(sp1, "gapleft 0.5cm, span 2 ,wrap 0.25cm");

        params = currentDesc.getUI();
        add(params, "gapleft 0.5cm,wrap 0.25cm,span 1 3");

        add = GUIElements.getButton(GlobalStrings.addString);
        add.addActionListener(this);
        add(add, "center,gaptop 0.5cm,wrap 0.25cm");

        remove = GUIElements.getButton(GlobalStrings.removeString);
        remove.addActionListener(this);
        add(remove, "center");
    }

    void refresh()
    {
        classesModel.clear();
        for (int i = 0; i < GlobalSpace.classController.classes.size(); i++)
        {
            classesModel.addElement(GlobalSpace.classController.classes.get(i));
        }
        refreshConditionUI();
    }

    void refreshPorparties()
    {
        propartiesModel.clear();
        if (classes.getSelectedIndex() == -1)
        {
            return;
        }
        for (ConditionDescription c : GlobalSpace.classController.classes.get(classes.getSelectedValue()).conditions)
        {
            propartiesModel.addElement(ConditionCodeList.getName(c.getCode()));
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        if (e.getSource().equals(classes))
        {
            propartiesModel.clear();
            if (classes.getSelectedIndex() != -1)
            {
                for (ConditionDescription desc : GlobalSpace.classController.classes.get(classes.getSelectedValue()).conditions)
                {
                    propartiesModel.addElement(ConditionCodeList.getName(desc.getCode()));
                }
            }
            currentDesc = emptyDesc;
            refreshConditionUI();
        } else if (e.getSource().equals(proparties))
        {
            if (classes.getSelectedIndex() == -1 || proparties.getSelectedIndex() == -1)
            {
                currentDesc = emptyDesc;
            } else
            {
                currentDesc = GlobalSpace.classController.classes.get(classes.getSelectedValue()).conditions.get(proparties.getSelectedIndex());
            }
            refreshConditionUI();
        }
    }

    private void refreshConditionUI()
    {
        remove(params);
        params = currentDesc.refreshUI();
        add(params, "cell 3 1,span 1 3,wrap 0.25cm");
        repaint();

    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(add))
        {
            JComboBox<Object> type = new JComboBox<Object>()
            {
                {
                    for (int code : ConditionCodeList.codes)
                    {
                        addItem(ConditionCodeList.getName(code));
                    }
                }
            };
            Object input[] =
            {
                new JLabel(GlobalStrings.typeString), type
            };
            if (classes.getSelectedIndex() == -1)
            {
                JOptionPane.showMessageDialog(this, GlobalStrings.noSelectedClass, GlobalStrings.errorSting, JOptionPane.WARNING_MESSAGE);
                return;

            }
            if (JOptionPane.showConfirmDialog(this, input, GlobalStrings.newPorpartyString, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.CANCEL_OPTION)
            {
                return;
            }
            ConditionDescription newDesc = new ConditionDescription(classes.getSelectedValue().toString());
            newDesc.setCode(type.getSelectedIndex() + 1);
            GlobalSpace.classController.classes.get(classes.getSelectedValue()).conditions.add(newDesc);
            propartiesModel.addElement(ConditionCodeList.getName(newDesc.getCode()));
            proparties.setSelectedIndex(propartiesModel.getSize() - 1);
            currentDesc = newDesc;
            refreshConditionUI();

        } else if (e.getSource().equals(remove))
        {
            if (proparties.getSelectedIndex() == -1 || classes.getSelectedIndex() == -1)
            {
                return;
            }
            GlobalSpace.classController.classes.get(classes.getSelectedValue()).conditions.remove(proparties.getSelectedIndex());
            refreshPorparties();
        }
    }
}
