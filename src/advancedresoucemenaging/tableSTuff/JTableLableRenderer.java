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
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Stanisalv
 */
public class JTableLableRenderer extends DefaultTableCellRenderer {

    public void fillColor(JTable t, JLabel l, boolean isSelected) {

        if (isSelected) {
            l.setBackground(t.getSelectionBackground());
        } else {
            l.setBackground(t.getBackground());
        }

    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        if (value instanceof JLabel) {
            JLabel jer = (JLabel) value;
            jer.setFont(new Font("SansSerif", Font.ITALIC, 18));
            jer.setForeground(Colors.tableLableRenderedFontColor);
            jer.setOpaque(true);
            jer.setHorizontalAlignment(CENTER);
            jer.setBounds(table.getCellRect(row, column, false));
            setHorizontalAlignment(CENTER);
            fillColor(table, jer, isSelected);
            return jer;
        } else {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }

    }
}
