/*
 * Copyright (C) 2015 Sammy Guergachi <sguergachi at gmail.com>
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
import advancedresoucemenaging.conditionClasses.ConditionDescription;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Class
{

    public ArrayList<SubjectPlaceHolder> subjects;
    public Map<SubjectPlaceHolder, Integer> subjectPlan;
    public ArrayList<ConditionDescription> conditions;
    public SubjectPlaceHolder[][] schedule;
    public String name;

    public Class(String name)
    {
        this.name = name;
        subjectPlan = new HashMap<>();
        subjects = new ArrayList<>();
        conditions = new ArrayList<>();
        schedule = new SubjectPlaceHolder[5][7];
        clearSchedule();
    }

    void clearSchedule()
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 7; j++)
            {
                schedule[i][j] = new SubjectPlaceHolder();
            }
        }
    }

    void addSubject(SubjectPlaceHolder sub, int times)
    {
        subjectPlan.put(sub, times);
        subjects.add(sub);
    }

}
