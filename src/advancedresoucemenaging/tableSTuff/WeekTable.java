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

import advancedresoucemenaging.Action;
import advancedresoucemenaging.GUIClasses.Colors;
import advancedresoucemenaging.GUIClasses.GUIElements;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
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

    private Color[][] customColors = new Color[5][7];
    private boolean[][] customSelection = new boolean[5][7];
    private Point lastSelected;
    private Action valueChanged;

    public WeekTable()
    {
        super();
        lastSelected = new Point();

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                customColors[i][j] = Colors.weekTableDisabledColor;
                customSelection[i][j] = false;
            }
        }

        final int rowHeght = 30;
        final int colWidth = 50;
        setModel(new TableModel(customColors));

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
                    if (changeValue(e.getX() / colWidth, e.getY() / rowHeght, Colors.weekTableEnabledColor))
                    {
                        lastSelected.setLocation(e.getX() / colWidth, e.getY() / rowHeght);
                        if (valueChanged != null)
                        {
                            valueChanged.perform();
                        }
                    }
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
                    if (changeValue(e.getX() / colWidth, e.getY() / rowHeght, Colors.weekTableEnabledColor))
                    {
                        lastSelected.setLocation(e.getX() / colWidth, e.getY() / rowHeght);
                        if (valueChanged != null)
                        {
                            valueChanged.perform();
                        }
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
            }
        });
        setPreferredSize(new Dimension(colWidth * getColumnCount(), rowHeght * getRowCount()));

    }

    public boolean changeValue(int day, int hour, Color value)
    {
        if (day >= 5 || hour >= 7 || day < 0 || hour < 0)
        {
            return false;
        }

        if (customColors[day][hour].getRGB() == value.getRGB())
        {
            return false;
        }

        customColors[day][hour] = value;
        customSelection[day][hour] = !customSelection[day][hour];

        tableChanged(new TableModelEvent(getModel(), hour));
        return true;
    }

    public Point getLastSelected()
    {
        return lastSelected;
    }

    public void setValueChangeAction(Action ac)
    {
        this.valueChanged = ac;
    }

    public ArrayList<Point> getSelected()
    {
        ArrayList<Point> list = new ArrayList<>();
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                if (customSelection[i][j])
                {
                    list.add(new Point(i, j));
                }
            }
        }
        return list;
    }

    public void unselectAll()
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                customSelection[i][j] = false;
                customColors[i][j] = Colors.weekTableDisabledColor;
            }
        }
        tableChanged(new TableModelEvent(getModel()));
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
