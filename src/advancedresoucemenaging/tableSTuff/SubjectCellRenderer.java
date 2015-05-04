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

import advancedresoucemenaging.algStuff.SubjectPlaceHolder;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**

 @author Stanisalv
 */
public class SubjectCellRenderer extends DefaultTableCellRenderer {

    JPanel panel;
    JLabel lable;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
	    boolean hasFocus, int row, int column) {
	if (value instanceof SubjectPlaceHolder) {
	    SubjectPlaceHolder sub = (SubjectPlaceHolder) value;
	    lable = new JLabel();
	    if (!sub.getSubject().equals("unknown")) {
		lable.setText("<html><strong>" + sub.getSubject() + "</strong><br>" + sub.getTeacher() + "</html>");
	    }else{
		lable.setText("");
	    }
	    lable.setFont(new Font("SansSerif", Font.PLAIN, 14));
	    lable.setHorizontalAlignment(CENTER);
	    lable.setBounds(table.getCellRect(row, column, false));
	    setHorizontalAlignment(CENTER);
	    panel = new JPanel(new BorderLayout());
	    panel.add(lable, BorderLayout.NORTH);
	    if (isSelected) {
		panel.setBackground(table.getSelectionBackground());
	    } else {
		panel.setBackground(table.getBackground());

	    }
	    return panel;
	}
	return null;
    }
}
