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
import advancedresoucemenaging.dataHandling.GlobalSpace;
import advancedresoucemenaging.algStuff.SubjectPlaceHolder;
import java.awt.Component;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Stanisalv
 */
public class SetSubjectFrame extends JFrame implements WindowFocusListener, WindowListener, ListSelectionListener
{

    JList<SubjectPlaceHolder> subs, teachers;
    DefaultListModel<SubjectPlaceHolder> subjectsModel, teachersModel;
    String clas;
    int day, hour;
    int hoveredIndex = -1;
    boolean skipConditions = false;

    public SetSubjectFrame(Object[] subjects, int day, int hour, String clas)
    {
        super("Поставяне на предмет");
        //<editor-fold defaultstate="collapsed" desc="Basic frame custumazation">
        setLayout(null);
        setSize(295, 175);
        setLocation(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);
        setVisible(true);
        //</editor-fold>

        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
        {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.clas = clas;
        this.day = day;
        this.hour = hour;
        SubjectPlaceHolder selected = GlobalSpace.classController.getClasses().get(clas).getSchedule()[day][hour];

        subjectsModel = new DefaultListModel<SubjectPlaceHolder>();
        subs = new JList<SubjectPlaceHolder>(subjectsModel);
        subs.setVisibleRowCount(-1);
        subs.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        JScrollPane sp2 = new JScrollPane(subs);
        sp2.setBounds(2, 2, 285, 150);
        subs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subs.setFont(new Font("SansSerif", Font.ITALIC, 20));
        subs.setCellRenderer(new ListCellRenderer<Object>()
        {

            @Override
            public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
            {
                if (value instanceof SubjectPlaceHolder)
                {
                    JLabel lable;
                    SubjectPlaceHolder sub = (SubjectPlaceHolder) value;
                    lable = new JLabel();
                    lable.setText("<html><strong>" + sub.getSubject() + "</strong><br>" + sub.getTeacher() + "</html>");
                    lable.setFont(new Font("SansSerif", Font.PLAIN, 15));
                    lable.setOpaque(true);
                    if (isSelected)
                    {
                        lable.setBackground(subs.getSelectionBackground());
                        lable.setForeground(subs.getSelectionForeground());
                    } else
                    {
                        lable.setBackground(subs.getBackground());
                        lable.setForeground(subs.getForeground());
                    }
                    if (index == hoveredIndex)
                    {
                        lable.setBackground(Colors.choosenSubject);

                    }
                    return lable;
                }
                return null;
            }
        });
        subs.addListSelectionListener(this);

        subs.addMouseMotionListener(new MouseAdapter()
        {
            public void mouseMoved(MouseEvent me)
            {
                Point p = new Point(me.getX(), me.getY());
                int index = subs.locationToIndex(p);
                if (index != hoveredIndex)
                {
                    hoveredIndex = index;
                    subs.repaint();
                }
            }
        });

        add(sp2);
        for (int i = 0; i < subjects.length; i++)
        {
            SubjectPlaceHolder sub = (SubjectPlaceHolder) subjects[i];
            subjectsModel.addElement(sub);
        }
        subjectsModel.addElement(new SubjectPlaceHolder());
        //subs.setSelectedValue(selected, true);

        addWindowFocusListener(this);
        addWindowListener(this);

    }

    public void overrideLimitations()
    {
        skipConditions = true;
    }

    @Override
    public void windowGainedFocus(WindowEvent e)
    {
    }

    @Override
    public void windowLostFocus(WindowEvent e)
    {
        if (e.getWindow().isShowing())
        {
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

        }

    }

    @Override
    public void windowOpened(WindowEvent e)
    {
    }

    @Override
    public void windowClosing(WindowEvent e)
    {
        this.dispose();
        if (subs.getSelectedIndex() == -1)
        {
            return;
        }
        if (skipConditions)
        {
            GlobalSpace.classController.setSubjectWithNoChecks(clas, day, hour, subs.getSelectedValue());
            return;
        }

        String msg = GlobalSpace.classController.setSubject(clas, day, hour, subs.getSelectedValue());;
        if (msg != null)
        {
            JOptionPane.showMessageDialog(this, msg, "Невъзможно добавяне", JOptionPane.WARNING_MESSAGE);
        }

    }

    @Override
    public void windowClosed(WindowEvent e)
    {
    }

    @Override
    public void windowIconified(WindowEvent e)
    {
    }

    @Override
    public void windowDeiconified(WindowEvent e)
    {
    }

    @Override
    public void windowActivated(WindowEvent e)
    {
    }

    @Override
    public void windowDeactivated(WindowEvent e)
    {
    }

    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
