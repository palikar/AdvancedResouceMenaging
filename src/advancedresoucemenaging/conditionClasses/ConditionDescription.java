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

import advancedresoucemenaging.dataHandling.GlobalSpace;
import advancedresoucemenaging.algStuff.SubjectPlaceHolder;
import advancedresoucemenaging.dataHandling.Subject;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 *
 * @author Stanisalv
 */
public class ConditionDescription
{

    int code;
    Map<String, String> params;
    ArrayList<SubjectPlaceHolder> subs;
    int[][] daysEnebled;
    JPanel UI;
    String clas;

    public ConditionDescription(int code, Map<String, String> params, ArrayList<SubjectPlaceHolder> subs)
    {
        this.code = code;
        this.params = params;
        daysEnebled = new int[5][7];
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                daysEnebled[i][j] = 0;
            }
        }
        this.subs = subs;
        createUI();

    }

    public ConditionDescription(String clas)
    {
        this.clas = clas;
        params = new HashMap<>();
        subs = new ArrayList<>();
        daysEnebled = new int[5][7];
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                daysEnebled[i][j] = 0;
            }
        }
    }

    private void createUI()
    {
        switch (code)
        {
            case 0:
                UI = ConditionUI.empty();
                break;
            case ConditionCodeList.mustBeConsecitiveConditionCode:
                UI = ConditionUI.getMustBeConsecitiveConditionUI(this);
        }
    }

    public String getSaveString()
    {
        StringBuilder builder = new StringBuilder("");
        builder.append(code).append("&");
        for (int i = 0; i < subs.size(); i++)
        {
            SubjectPlaceHolder sub = subs.get(i);
            builder.append(sub.getSubject() + "+" + sub.getTeacher()).append("|");

        }
        builder.append("&");
        Object[] keys = params.keySet().toArray();
        for (int i = 0; i < keys.length; i++)
        {
            builder.append(keys[i] + ":" + params.get(keys[i])).append("|");
        }
        builder.append("&");
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                builder.append(daysEnebled[i][j]);
            }
            builder.append("|");
        }
        builder.append("&");

        return builder.toString();
    }

    public ConditionDescription loadFromString(String str)
    {
        String firstPartition[] = str.split("\\&");
        this.code = Integer.parseInt(firstPartition[0]);
        String secondPartition[] = firstPartition[1].split("\\|");
        for (int i = 0; i < secondPartition.length; i++)
        {
            if (secondPartition[i].length() == 0)
            {
                continue;
            }
            String thirdPartition[] = secondPartition[i].split("\\+");
            subs.add(new SubjectPlaceHolder(thirdPartition[0], thirdPartition[1]));
        }
        System.out.println(firstPartition[2]);
        String secondPartition2[] = firstPartition[2].split("\\|");
        for (int i = 0; i < secondPartition2.length; i++)
        {
            if (secondPartition2[i].length() == 0)
            {
                continue;
            }
            String thirdPartition[] = secondPartition2[i].split("\\:");
            params.put(thirdPartition[0], thirdPartition[1]);
        }
        String secondPartition3[] = firstPartition[3].split("\\|");
        for (int i = 0; i < secondPartition3.length; i++)
        {
            String s = secondPartition3[i];
            for (int j = 0; j < s.length(); j++)
            {
                if (s.charAt(j) == '1')
                {
                    daysEnebled[i][j] = 1;
                }
            }
        }
        createUI();
        return this;
    }

    public JPanel refreshUI()
    {
        if (UI == null)
        {
            createUI();
        }
        refresh(UI);
        return UI;

    }

    public void refresh(final Container c)
    {
        Component[] comps = c.getComponents();
        for (Component comp : comps)
        {
            if (comp instanceof JComboBox)
            {
                int index = ((JComboBox) comp).getSelectedIndex();
                JComboBox box = ((JComboBox) comp);
                box.removeAllItems();
                for (Subject subject : GlobalSpace.subjectController.subjects)
                {
                    box.addItem(subject.getName());
                }
                if (index < ((JComboBox) comp).getItemCount())
                {
                    box.setSelectedIndex(index);
                } else
                {
                    box.setSelectedIndex(box.getItemCount() - 1);
                }

            }
            if (comp instanceof Container)
            {
                refresh((Container) comp);
            }
        }

    }

    public JPanel getUI()
    {
        if (UI == null)
        {
            createUI();
        }
        return UI;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public int[][] getDaysEnebled()
    {
        return daysEnebled;
    }

    public String getClas()
    {
        return clas;
    }

    public void setClas(String clas)
    {
        this.clas = clas;
    }

    public Map<String, String> getParams()
    {
        return params;
    }

    public ArrayList<SubjectPlaceHolder> getSubs()
    {
        return subs;
    }

}
