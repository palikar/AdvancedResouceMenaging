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

import advancedresoucemenaging.dataHandling.GlobalSpace;
import advancedresoucemenaging.dataHandling.GlobalStrings;
import java.util.Map.Entry;
import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Stanisalv
 */
public class TeacherPlanModel extends AbstractTableModel
{

    private final String[] COLUMN_NAMES = new String[]
    {
        GlobalStrings.hourSting,
        GlobalStrings.mondayString,
        GlobalStrings.tuesdaySting,
        GlobalStrings.wendsdaySting,
        GlobalStrings.thusdayString,
        GlobalStrings.fridayString
    };

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return Object.class;
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

    @Override
    public int getRowCount()
    {
        return 7;
    }

    @Override
    public int getColumnCount()
    {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        if (columnIndex == 0)
        {
            switch (rowIndex)
            {
                case 0:
                    return new JLabel("I");
                case 1:
                    return new JLabel("II");
                case 2:
                    return new JLabel("III");
                case 3:
                    return new JLabel("IV");
                case 4:
                    return new JLabel("V");
                case 5:
                    return new JLabel("VI");
                case 6:
                    return new JLabel("VII");
            }
        } else
        {
            if (!TableControl.selectedTeacher.equals(""))
            {
                return new JLabel(getTeacher(rowIndex, columnIndex));

            }
        }
        return "";
    }

    private String getTeacher(int rowIndex, int columnIndex)
    {

        for (Entry<String, advancedresoucemenaging.dataHandling.Class> clas : GlobalSpace.classController.classes.entrySet())
        {
            if (clas.getValue().schedule[columnIndex - 1][rowIndex].getTeacher().equals(TableControl.selectedTeacher))
            {
                return clas.getKey();
            }
        }
        return "";
    }
}
