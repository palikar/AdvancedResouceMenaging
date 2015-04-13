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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Stanisalv
 */
public class ClassHandler extends Handler {

    public ArrayList<String> classes;
    public Map<String, ArrayList<SubjectPlaceHolder>> subjects;
    public Map<String, Map<SubjectPlaceHolder, Integer>> subjectPlan;
    public Map<String, ArrayList<ConditionDescription>> conditions;
    public Map<String, SubjectPlaceHolder[][]> schedules;
    public static final SubjectPlaceHolder emptySub = new SubjectPlaceHolder();

    public ClassHandler() {
        classes = new ArrayList<>();
        subjectPlan = new HashMap<>();
        conditions = new HashMap<>();
        schedules = new HashMap<>();
        subjects = new HashMap<>();
    }

    @Override
    public void add(String name) {
        classes.add(name);
        subjectPlan.put(name, new HashMap<SubjectPlaceHolder, Integer>());
        conditions.put(name, new ArrayList<ConditionDescription>());
        schedules.put(name, new SubjectPlaceHolder[5][7]);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                schedules.get(name)[i][j] = new SubjectPlaceHolder();
            }
        }
        subjects.put(name, new ArrayList<SubjectPlaceHolder>());

    }

    @Override
    public void remove(String name) {
        classes.remove(name);
        subjectPlan.remove(name);
        conditions.remove(name);
        schedules.remove(name);
    }

    public void clearAllSchedules() {
        for (int i = 0; i < classes.size(); i++) {
            clearShcedule(classes.get(i));
        }

    }

    public void clearShcedule(String clas) {
        SubjectPlaceHolder sub[][] = schedules.get(clas);
        for (int j = 0; j < sub.length; j++) {
            for (int k = 0; k < sub[0].length; k++) {
                if (!sub[j][k].equals(emptySub)) {
                    sub[j][k] = new SubjectPlaceHolder();
                }
            }
        }
    }

    public void addSuject(String clas, SubjectPlaceHolder sub, int times) {
        subjectPlan.get(clas).put(sub, times);
        subjects.get(clas).add(sub);

    }

    public void addCondition(String clas, ConditionDescription con) {
        conditions.get(clas).add(con);
    }

    public void addCondition(String clas, String line) {
        conditions.get(clas).add(new ConditionDescription(clas).loadFromString(line));
    }

    public void setUpController(Controller controll) {
        for (int i = 0; i < classes.size(); i++) {
            String name = classes.get(i);
            ClassNode node = new ClassNode(name);
            Map<SubjectPlaceHolder, Integer> schedule = subjectPlan.get(name);
            Object keys[] = schedule.keySet().toArray();
            for (int j = 0; j < keys.length; j++) {
                int times = schedule.get(keys[j]).intValue();
                node.addSubject((SubjectPlaceHolder) keys[j], times);
            }

            ArrayList<ConditionDescription> listCon = conditions.get(name);
            for (int j = 0; j < listCon.size(); j++) {
                node.addCondition(listCon.get(j));
            }
            controll.addClass(node);
        }
        controll.setUpClasses();
        for (int i = 0; i < classes.size(); i++) {
            SubjectPlaceHolder[][] schedule = schedules.get(classes.get(i));
            for (int j = 0; j < schedule.length; j++) {
                for (int k = 0; k < schedule[j].length; k++) {
                    SubjectPlaceHolder subjectPlaceHolder = schedule[j][k];
                    if (subjectPlaceHolder != null && !subjectPlaceHolder.equals(emptySub)) {
                        controll.classes.get(i).days.get(j).getHours().get(k).setManual(subjectPlaceHolder);
                    }
                }
            }
        }
    }

    public void getReadySchedule(Controller controll) {
        for (int i = 0; i < controll.classes.size(); i++) {
            ClassNode classNode = controll.classes.get(i);
            SubjectPlaceHolder[][] schedule = schedules.get(classNode.name);
            for (int j = 0; j < classNode.days.size(); j++) {
                DayNode dayNode = classNode.days.get(j);
                for (int k = 0; k < dayNode.getHours().size(); k++) {
                    HourNode hourNode = dayNode.getHours().get(k);
                    schedule[j][k].setSubject(hourNode.placeHolder.getSubject());
                    schedule[j][k].setTeacher(hourNode.placeHolder.getTeacher());

                }
            }
        }
    }

    public String getTeacher(String clas, String sub) {
        for (SubjectPlaceHolder subject : subjects.get(clas)) {
            if (subject.getSubject().equals(sub)) {
                return subject.getTeacher();
            }
        }
        return null;
    }

    @Override
    public boolean contains(String name) {
        return classes.contains(name);
    }

    public String setSubject(String clas, int day, int hour, SubjectPlaceHolder selectedValue) {
        if (selectedValue.equals(emptySub)) {
            schedules.get(clas)[day][hour] = selectedValue;
            return null;
        }
        SubjectPlaceHolder schedule[][] = schedules.get(clas);
        int cnt = 1;
        for (int i = 0; i < schedule.length; i++) {
            for (int j = 0; j < schedule[0].length; j++) {
                SubjectPlaceHolder subjectPlaceHolder = schedule[i][j];
                if (subjectPlaceHolder.equals(selectedValue)) {
                    cnt++;
                }
            }
        }
        if (cnt > subjectPlan.get(clas).get(selectedValue).intValue()) {
            return GlobalStrings.tooManySameSubsInWeek;
        }
        for (int i = 0; i < classes.size(); i++) {
            SubjectPlaceHolder sub = schedules.get(classes.get(i))[day][hour];
            if (!sub.equals(emptySub) && sub.equals(selectedValue)) {
                return GlobalStrings.sameSubInAnotherClass;
            }
        }

        schedules.get(clas)[day][hour] = selectedValue;
        return null;
    }
}
