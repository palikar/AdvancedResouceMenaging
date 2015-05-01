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

import advancedresoucemenaging.dataHandling.Settings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

/**
 *
 * @author Stanisalv
 */
public final class Controller
{

    ArrayList<ClassNode> classes;
    ArrayList<HourNode> state;
    boolean shuffling = true;

    public Controller()
    {
        classes = new ArrayList<>();
        state = new ArrayList<>();
        shuffling = Settings.shuffling;
    }

    boolean allReady()
    {
        for (int i = 0; i < classes.size(); i++)
        {
            if (!classes.get(i).isReady())
            {
                return false;
            }
        }
        return true;
    }

    public boolean assign(ListIterator<HourNode> nodes)
    {
        //  print();
        if (allReady())
        {
            return true;
        }
        int index = nodes.nextIndex();
        if (index == state.size())
        {
            if (allReady())
            {
                return true;
            } else
            {
                return false;
            }
        }
        HourNode node = state.get(index);
        if (node.sameClass.isReady() && node.nextClass != null)
        {
            return assign(state.listIterator(state.indexOf(node.nextClass.days.get(0).hours.get(0))));
        }
        if (node.isSet())
        {
            return assign(state.listIterator(index + 1));
        }
        if (node.prevHour != null && !node.prevHour.isSet())
        {
            return false;
        }
        for (int i = 0; i < node.domain.size(); i++)
        {

            SubjectPlaceHolder sub = node.domain.get(i);
            node.set(sub);

            if (!node.sameClass.isReady())
            {
                if (assign(state.listIterator(index + 1)))
                {
                    return true;
                }
            } else
            {
                if (node.nextClass != null)
                {
                    if (assign(state.listIterator(state.indexOf(node.nextClass.days.get(0).hours.get(0)))))
                    {
                        return true;
                    }
                } else
                {
                    return true;
                }
            }
            node.unSet();
        }
        return false;
    }

    public void addClass(ClassNode clas)
    {
        classes.add(clas);
        if (classes.size() > 1)
        {
            int lastIndex = classes.size() - 1;
            for (int i = 0; i < lastIndex; i++)
            {
                for (int j = 0; j < 5; j++)
                {
                    for (int k = 0; k < 7; k++)
                    {
                        classes.get(i).days.get(j).hours.get(k).sameTimeConnection.add(
                                classes.get(lastIndex).days.get(j).hours.get(k));
                        classes.get(lastIndex).days.get(j).hours.get(k).sameTimeConnection.add(classes.get(i).days.get(j).hours.get(k));
                    }
                }
            }
        }

    }

    public ArrayList<SubjectPlaceHolder[][]> getData()
    {
        ArrayList<SubjectPlaceHolder[][]> data = new ArrayList<>();
        for (int i = 0; i < classes.size(); i++)
        {
            SubjectPlaceHolder week[][] = new SubjectPlaceHolder[7][5];
            for (int j = 0; j < 5; j++)
            {
                for (int k = 0; k < 7; k++)
                {
                    week[k][j] = classes.get(i).days.get(j).hours.get(k).placeHolder;
                }
            }
            data.add(week);
        }
        return data;
    }

    public void print()
    {
        ArrayList<SubjectPlaceHolder[][]> data = getData();
        for (int i = 0; i < data.size(); i++)
        {
            SubjectPlaceHolder[][] week = data.get(i);
            for (int j = 0; j < week.length; j++)
            {
                for (int k = 0; k < week[0].length; k++)
                {
                    Object object = week[j][k].subject;
                    System.out.print(object + "|");
                }
                System.out.println();
            }
            System.out.println("-----------------");
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");

    }

    public void setUpClasses()
    {
        for (int i = 0; i < classes.size(); i++)
        {
            for (int j = 0; j < 5; j++)
            {
                for (int k = 0; k < 7; k++)
                {
                    classes.get(i).days.get(j).hours.get(k).sameClass = classes.get(i);
                    if (i != classes.size() - 1)
                    {
                        classes.get(i).days.get(j).hours.get(k).nextClass = classes.get(i + 1);
                    }
                    if (i != 0)
                    {
                        classes.get(i).days.get(j).hours.get(k).prevClass = classes.get(1 - 1);
                    }
                }
            }
        }

    }

    public void make()
    {
        for (int i = 0; i < classes.size(); i++)
        {
            for (int j = 0; j < 7; j++)
            {
                for (int k = 0; k < 5; k++)
                {
                    state.add(classes.get(i).days.get(k).hours.get(j));
                    if (Settings.hardnesSort)
                    {
                        classes.get(i).days.get(k).hours.get(j).sortByHardness();
                    }
                    if (Settings.shuffling)
                    {
                        classes.get(i).days.get(k).hours.get(j).shuffelDomain(Settings.hardnesSort);
                    }
                }
            }
        }
        System.out.println("Es hat angefangen");
        if (!assign(state.listIterator()))
        {
            System.out.println("Es ist eine Scheisskoniguration");
        } else
        {
            System.out.println("Das Prozess ist fertig");
        }
    }

    private boolean checkForEmptyDomains(HourNode currentNode)
    {
        for (ClassNode classNode : classes)
        {
            for (DayNode dayNode : classNode.days)
            {

                if (dayNode.hours.get(0).aloneEmpyDomain())
                {
                    return true;
                }

            }
        }
        return false;
    }

    public ArrayList<ClassNode> getClasses()
    {
        return classes;
    }

    public ArrayList<HourNode> getState()
    {
        return state;
    }

}
