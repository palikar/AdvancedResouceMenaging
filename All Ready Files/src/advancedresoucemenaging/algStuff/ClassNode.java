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
package advancedresoucemenaging.algStuff;

import advancedresoucemenaging.conditionClasses.ConditionCodeList;
import advancedresoucemenaging.conditionClasses.ConditionDescription;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Stanisalv
 */
public class ClassNode
{

    ArrayList<DayNode> days;
    String name;
    Map<SubjectPlaceHolder, Integer> subjectsPlan;
    Map<SubjectPlaceHolder, Integer> currentOccurence;
    Map<String, Object> properties;

    public ClassNode(String name)
    {
        days = new ArrayList<>();
        subjectsPlan = new HashMap<>();
        currentOccurence = new HashMap<>();
        properties = new HashMap<>();
        this.name = name;

        for (int i = 0; i < 5; i++)
        {
            days.add(new DayNode(i));
        }
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                if (i != 4)
                {
                    days.get(i).hours.get(j).nextDayConnection = days.get(i + 1);
                }
                if (i != 0)
                {
                    days.get(i).hours.get(j).prevDayConnection = days.get(i - 1);
                }
                days.get(i).hours.get(j).sameDayConnection = days.get(i);
            }
        }
    }

    public void addOccurence(SubjectPlaceHolder sub)
    {
        currentOccurence.put(sub, currentOccurence.get(sub).intValue() + 1);
    }

    void removeOccurence(SubjectPlaceHolder sub)
    {
        currentOccurence.put(sub, currentOccurence.get(sub).intValue() - 1);

    }

    public boolean complateConditons(HourNode node)
    {

        return true;
    }

    public boolean isReady()
    {
        Object keys[] = subjectsPlan.keySet().toArray();
        for (int i = 0; i < keys.length; i++)
        {
            if (subjectsPlan.get(keys[i]).intValue() != currentOccurence.get(keys[i]))
            {
                return false;
            }
        }
        return true;
    }

    public int getOccurance(SubjectPlaceHolder sub)
    {
        return currentOccurence.get(sub).intValue();
    }

    public int getSubjectPlan(SubjectPlaceHolder sub)
    {
        return subjectsPlan.get(sub).intValue();
    }

    public void addSubject(SubjectPlaceHolder sub, int times)
    {
        subjectsPlan.put(sub, times);
        currentOccurence.put(sub, 0);
        for (int j = 0; j < days.size(); j++)
        {
            for (int k = 0; k < days.get(j).hours.size(); k++)
            {
                days.get(j).hours.get(k).domain.add(sub);

            }
        }
    }

    public void addCondition(ConditionDescription con)
    {
        switch (con.getCode())
        {
            case ConditionCodeList.mustBeConsecitiveConditionCode:
                for (int i = 0; i < 5; i++)
                {
                    for (int j = 0; j < 7; j++)
                    {
                        if (con.getDaysEnebled()[i][j] == 1)
                        {
                            days.get(i).getHours().get(j).setConsecitiveCondition(con.getSubs().get(0));
                        }
                    }
                }
                break;
        }
    }

    public ArrayList<DayNode> getDays()
    {
        return days;
    }

    public String getName()
    {
        return name;
    }

    public Map<SubjectPlaceHolder, Integer> getSubjectsPlan()
    {
        return subjectsPlan;
    }

    public Map<SubjectPlaceHolder, Integer> getCurrentOccurence()
    {
        return currentOccurence;
    }

    public Map<String, Object> getProperties()
    {
        return properties;
    }

    @Override
    public boolean equals(Object obj)
    {
        return ((ClassNode) obj).name.equals(this.name);
    }

    public ArrayList<HourNode> getHours()
    {
        ArrayList<HourNode> list = new ArrayList<>();
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                list.add(days.get(j).hours.get(i));
            }
        }
        return list;
    }

}
