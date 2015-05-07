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

import advancedresoucemenaging.algStuff.SubjectPlaceHolder;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class ClassChecker
{

    private Map<String, Class> classesOld;
    private Map<String, Class> classesNew;
    private ArrayList<String> classesNameOrder;
    private SubjectPlaceHolder[][][] subsArray;
    private SubjectPlaceHolder empty = new SubjectPlaceHolder("unknown", "unknown");

    public ClassChecker()
    {
    }

    public void findMistakes(Map<String, Class> classes)
    {
        this.classesOld = classes;
        int numClasses = classes.values().size();
        subsArray = new SubjectPlaceHolder[numClasses][5][7];
        classesNameOrder = new ArrayList<>();
        ArrayList<Class> classesList = new ArrayList<>(classes.values());
        for (int i = 0; i < classesList.size(); i++)
        {
            classesNameOrder.add(classesList.get(i).getName());
            for (int j = 0; j < 5; j++)
            {
                for (int k = 0; k < 7; k++)
                {
                    subsArray[i][j][k] = new SubjectPlaceHolder(classesList.get(i).getSchedule()[j][k].getSubject(),
                            classesList.get(i).getSchedule()[j][k].getTeacher());
                }
            }

        }
        ScheduleFabrik.holes.putAll(checkForHoles());

    }

    private Map<String, ArrayList<Point>> checkForHoles()
    {
        ScheduleFabrik.holes.clear();
        Map<String, ArrayList<Point>> holes = new HashMap<>();

        for (int i = 0; i < classesNameOrder.size(); i++)
        {
            for (int j = 0; j < 5; j++)
            {
                for (int k = 0; k < 7 - 1; k++)
                {
                    if (subsArray[i][j][k].equals(empty))
                    {
                        if (k - 1 < 0 || !subsArray[i][j][k + 1].equals(empty))
                        {
                            if (!holes.containsKey(classesNameOrder.get(i)))
                            {
                                holes.put(classesNameOrder.get(i), new ArrayList<>());
                            }
                            holes.get(classesNameOrder.get(i)).add(new Point(j, k));
                            continue;
                        }

                        if (!subsArray[i][j][k + 1].equals(empty)
                                && !subsArray[i][j][k - 1].equals(empty))
                        {
                            if (!holes.containsKey(classesNameOrder.get(i)))
                            {
                                holes.put(classesNameOrder.get(i), new ArrayList<>());
                            }
                            holes.get(classesNameOrder.get(i)).add(new Point(j, k));

                        }

                    }
                }
            }
        }

        return holes;
    }

    public class SubjectLocation
    {

        public int clas, day, hour;

        public SubjectLocation(int clas, int day, int hour)
        {
            this.clas = clas;
            this.day = day;
            this.hour = hour;
        }

    }
}
