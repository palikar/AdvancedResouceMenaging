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
package advancedresoucemenaging.GUIClasses;

import com.alee.laf.button.WebButtonUI;
import com.alee.laf.combobox.WebComboBoxUI;
import com.alee.laf.text.WebTextFieldUI;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.plaf.FontUIResource;

/**
 *
 * @author Stanisalv
 */
public class GUIElements {

    public static final Border defaultBorder = BorderFactory.createLineBorder(Colors.bordersColor, 1);
    public static final Border listBorder = BorderFactory.createLineBorder(Color.lightGray, 1, true);
    public static FontUIResource defaultFont = new FontUIResource("Verdana", Font.ITALIC, 20);

    public static JButton getButton(String lable) {
        return new JButton(lable) {
            {
                setUI(new WebButtonUI() {
                    {
                        setRolloverEnabled(true);
                        setRolloverShine(true);
                        setRolloverShadeOnly(true);
                        setRolloverDarkBorderOnly(true);
                        setRolloverDarkBorderOnly(true);
                        setShineColor(new Color(220, 255, 255));
                        setShadeColor(Colors.bordersColor);
                        setRound(7);
                    }

                });
                setRolloverEnabled(true);
            }
        };
    }

    public static JTextField getTextField() {
        return new JTextField() {
            {
                setUI(new WebTextFieldUI() {
                    {
                        setRound(7);

                    }

                });
            }
        };
    }

    public static JList<Object> getList(DefaultListModel<Object> model) {
        return new JList<Object>(model) {
            {
                setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                //setBorder(listBorder);

            }
        };
    }

    public static JComboBox<Object> getComboField() {
        return new JComboBox<Object>() {
            {
                setUI(new WebComboBoxUI() {
                    {
                        setRound(7);
                        setForeground(Color.black);
                    }
                });
            }

        };

    }
}
