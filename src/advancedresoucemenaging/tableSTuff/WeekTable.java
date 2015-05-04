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
package advancedresoucemenaging.tableSTuff;

import advancedresoucemenaging.GUIClasses.Colors;
import advancedresoucemenaging.GUIClasses.GUIElements;
import advancedresoucemenaging.conditionClasses.ConditionDescription;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Stanisalv
 */
public class WeekTable extends JTable
{

    Color[][] customSelection = new Color[5][7];

    public WeekTable()
    {
        super();

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                customSelection[i][j] = Colors.weekTableDisabledColor;
            }
        }

        final int rowHeght = 30;
        final int colWidth = 50;
        setModel(new TableModel(customSelection));

        setDefaultRenderer(Object.class, new ColorRenderer());
        setCellSelectionEnabled(false);
        setRowHeight(rowHeght);
        getColumnModel().getColumn(0).setPreferredWidth(colWidth);
        getColumnModel().getColumn(1).setPreferredWidth(colWidth);
        getColumnModel().getColumn(2).setPreferredWidth(colWidth);
        getColumnModel().getColumn(3).setPreferredWidth(colWidth);
        getColumnModel().getColumn(4).setPreferredWidth(colWidth);
        setBorder(GUIElements.defaultBorder);
        setGridColor(Colors.tableGridColor);
        addMouseListener(new MouseListener()
        {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON3)
                {
                    changeValue(e.getX() / colWidth, e.getY() / rowHeght, Colors.weekTableDisabledColor);
                } else
                {
                    changeValue(e.getX() / colWidth, e.getY() / rowHeght, Colors.weekTableEnabledColor);
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
        });
        addMouseMotionListener(new MouseMotionListener()
        {

            @Override
            public void mouseDragged(MouseEvent e)
            {
                if (e.getModifiersEx() == MouseEvent.BUTTON3_DOWN_MASK)
                {
                    changeValue(e.getX() / colWidth, e.getY() / rowHeght, Colors.weekTableDisabledColor);
                } else
                {
                    changeValue(e.getX() / colWidth, e.getY() / rowHeght, Colors.weekTableEnabledColor);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
            }
        });
        setPreferredSize(new Dimension(colWidth * getColumnCount(), rowHeght * getRowCount()));

    }

    public void changeValue(int day, int hour, Color value)
    {
        if (day >= 5 || hour >= 7 || day < 0 || hour < 0)
        {
            return;
        }

        customSelection[day][hour] = value;

        tableChanged(new TableModelEvent(getModel(), hour));
    }

}

class ColorRenderer extends DefaultTableCellRenderer
{

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        return new JPanel()
        {
            {
                if (value instanceof Color)
                {
                    setBackground((Color) value);
                }
            }
        };
    }
}

class TableModel extends AbstractTableModel
{

    Object[][] daysEnebled;
    private String[] COLUMN_NAMES = new String[]
    {
        "",
        "Пон.",
        "Вт.",
        "Ср.",
        "Ч.к",
        "П."
    };

    public TableModel(Object[][] daysEnebled)
    {
        this.daysEnebled = daysEnebled;
    }

    @Override
    public int getRowCount()
    {
        return 7;
    }

    @Override
    public int getColumnCount()
    {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        return daysEnebled[columnIndex][rowIndex];
    }

    @Override
    public String getColumnName(int column)
    {
        return COLUMN_NAMES[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }

}
