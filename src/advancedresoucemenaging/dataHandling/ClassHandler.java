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
package advancedresoucemenaging.dataHandling;

import advancedresoucemenaging.conditionClasses.ConditionDescription;
import advancedresoucemenaging.algStuff.ClassNode;
import advancedresoucemenaging.algStuff.Controller;
import advancedresoucemenaging.algStuff.DayNode;
import advancedresoucemenaging.algStuff.HourNode;
import advancedresoucemenaging.algStuff.SubjectPlaceHolder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Stanisalv
 */
public class ClassHandler extends Handler
{

    public Map<String, Class> classes;
    public static final SubjectPlaceHolder emptySub = new SubjectPlaceHolder();

    public ClassHandler()
    {
        classes = new HashMap<>();

    }

    @Override
    public void add(String name)
    {
        classes.put(name, new Class(name));
    }

    @Override
    public void remove(String name)
    {
        classes.remove(name);

    }

    public void clearAllSchedules()
    {
        for (Class clas : classes.values())
        {
            clas.clearSchedule();
        }
    }

    public void clearShcedule(String clas)
    {
        classes.get(clas).clearSchedule();

    }

    public void addSuject(String clas, SubjectPlaceHolder sub, int times)
    {
        classes.get(clas).addSubject(sub, times);

    }

    public void addCondition(String clas, ConditionDescription con)
    {

    }

    public void addCondition(String clas, String line)
    {
    }

    public void setUpController(Controller controll)
    {

        for (Class clas : classes.values())
        {

            String name = clas.name;
            ClassNode node = new ClassNode(name);
            for (Map.Entry<SubjectPlaceHolder, Integer> subject : clas.subjectPlan.entrySet())
            {
                node.addSubject(subject.getKey(), subject.getValue());
            }

            ArrayList<ConditionDescription> listCon = clas.conditions;
            listCon.forEach((element) ->
            {
                node.addCondition(element);
            });
            controll.addClass(node);
        }
        controll.setUpClasses();
        Class[] classArray = new Class[classes.values().size()];
        classes.values().toArray(classArray);
        for (int i = 0; i < classes.values().size(); i++)
        {
            SubjectPlaceHolder[][] schedule = classArray[i].schedule;
            for (int j = 0; j < schedule.length; j++)
            {
                for (int k = 0; k < schedule[j].length; k++)
                {
                    SubjectPlaceHolder subjectPlaceHolder = schedule[j][k];
                    if (subjectPlaceHolder != null && !subjectPlaceHolder.equals(emptySub))
                    {
                        controll.getClasses().get(i).getDays().get(j).getHours().get(k).setManual(subjectPlaceHolder);
                    }
                }
            }
        }
    }

    public void getReadySchedule(Controller controll)
    {
        for (int i = 0; i < controll.getClasses().size(); i++)
        {
            ClassNode classNode = controll.getClasses().get(i);
            SubjectPlaceHolder[][] schedule = classes.get(classNode.getName()).schedule;
            for (int j = 0; j < classNode.getDays().size(); j++)
            {
                DayNode dayNode = classNode.getDays().get(j);
                for (int k = 0; k < dayNode.getHours().size(); k++)
                {
                    HourNode hourNode = dayNode.getHours().get(k);
                    schedule[j][k].setSubject(hourNode.getPlaceHolder().getSubject());
                    schedule[j][k].setTeacher(hourNode.getPlaceHolder().getTeacher());

                }
            }
        }
    }

    public String getTeacher(String clas, String sub)
    {
        for (SubjectPlaceHolder subject : classes.get(clas).subjects)
        {
            if (subject.getSubject().equals(sub))
            {
                return subject.getTeacher();
            }
        }
        return null;
    }

    @Override
    public boolean contains(String name)
    {
        return classes.containsValue(name);
    }

    public String setSubject(String clas, int day, int hour, SubjectPlaceHolder selectedValue)
    {
        if (selectedValue.equals(emptySub))
        {
            classes.get(clas).schedule[day][hour] = selectedValue;
            return null;
        }
        SubjectPlaceHolder schedule[][] = classes.get(clas).schedule;
        int cnt = 1;
        for (int i = 0; i < schedule.length; i++)
        {
            for (int j = 0; j < schedule[0].length; j++)
            {
                SubjectPlaceHolder subjectPlaceHolder = schedule[i][j];
                if (subjectPlaceHolder.equals(selectedValue))
                {
                    cnt++;
                }
            }
        }
        if (cnt > classes.get(clas).subjectPlan.get(selectedValue))
        {
            return GlobalStrings.tooManySameSubsInWeek;
        }
        for (int i = 0; i < classes.size(); i++)
        {
            SubjectPlaceHolder sub = classes.get(clas).schedule[day][hour];
            if (!sub.equals(emptySub) && sub.equals(selectedValue))
            {
                return GlobalStrings.sameSubInAnotherClass;
            }
        }

        classes.get(clas).schedule[day][hour] = selectedValue;
        return null;
    }
}
