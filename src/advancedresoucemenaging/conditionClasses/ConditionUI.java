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
package advancedresoucemenaging.conditionClasses;

import advancedresoucemenaging.GUIClasses.Colors;
import advancedresoucemenaging.GUIClasses.GUIElements;
import advancedresoucemenaging.algStuff.SubjectPlaceHolder;
import advancedresoucemenaging.conditionClasses.ConditionDescription;
import advancedresoucemenaging.dataHandling.GlobalSpace;
import advancedresoucemenaging.dataHandling.Subject;
import advancedresoucemenaging.tableSTuff.WeekTable;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Stanisalv
 */
public class ConditionUI {

    public static JPanel getMustBeConsecitiveConditionUI(ConditionDescription desc) {
        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        rootPanel.setPreferredSize(new Dimension(400, 600));
        rootPanel.setOpaque(false);
        rootPanel.setBorder(GUIElements.defaultBorder);

        JComboBox<Object> subject1 = new JComboBox<>();
        subject1.setPreferredSize(new Dimension(135, 35));
        for (Subject subject : GlobalSpace.subjectController.getSubjects()) {
            subject1.addItem(subject.getName());
        }
        if (desc.getSubs().size() == 0) {
            desc.getSubs().add(new SubjectPlaceHolder());

        }
        if (subject1.getSelectedIndex() != -1) {
            if (desc.getSubs().size() != 0) {
                subject1.setSelectedItem(desc.getSubs().get(0).getSubject());
            } else {
                desc.getSubs().set(0, new SubjectPlaceHolder(subject1.getItemAt(0).toString(),
                        GlobalSpace.classController.getTeacher(desc.getClas(), subject1.getItemAt(0).toString())));
            }
        }

        subject1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (subject1.getSelectedIndex() == -1) {
                    return;
                }
                desc.getSubs().set(0, new SubjectPlaceHolder(subject1.getSelectedItem().toString(),
                        GlobalSpace.classController.getTeacher(desc.getClas(), subject1.getSelectedItem().toString())));
            }
        });
        JPanel buttonsPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonsPanel1.add(new JLabel("Предмет"));
        buttonsPanel1.add(subject1);
        buttonsPanel1.setOpaque(false);
        rootPanel.add(buttonsPanel1);

        JLabel l = new JLabel("Валидност");
        l.setAlignmentX(2.5f);
        rootPanel.add(l);

        JPanel tablePanle = new JPanel();
        tablePanle.setLayout(new FlowLayout(FlowLayout.CENTER));
        tablePanle.setOpaque(false);
        tablePanle.add(new WeekTable(desc));
        rootPanel.add(tablePanle);

        rootPanel.validate();

        return rootPanel;
    }

    public static JPanel empty() {
        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        rootPanel.setPreferredSize(new Dimension(400, 600));
        rootPanel.setOpaque(false);
        rootPanel.setBorder(BorderFactory.createLineBorder(Colors.bordersColor, 1));
        rootPanel.validate();
        return rootPanel;

    }

}
