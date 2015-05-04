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

import advancedresoucemenaging.conditionClasses.MustBeConsecitiveCondition;
import advancedresoucemenaging.conditionClasses.Condition;
import advancedresoucemenaging.dataHandling.GlobalSpace;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Stanisalv
 */
public class HourNode {

    SubjectPlaceHolder placeHolder;
    ArrayList<HourNode> sameTimeConnection;
    ArrayList<HourNode> specialConnection;
    ArrayList<SubjectPlaceHolder> domain;
    DayNode sameDayConnection, nextDayConnection, prevDayConnection;
    HourNode nextHour, prevHour;
    ClassNode nextClass, prevClass, sameClass;
    ArrayList<Condition> con;
    ArrayList<SubjectPlaceHolder> removed;
    ArrayList<HourNode> conditionConection;
    ArrayList<HourNode> setSingeltones;
    Map<HourNode, ArrayList<SubjectPlaceHolder>> classMap, dayMap, weekMap;
    ArrayList<Point> weekReductions;
    boolean set = false;
    int day, hour;
    Point weekPoint;

    public HourNode(int day, int hour) {
        placeHolder = new SubjectPlaceHolder();
        sameTimeConnection = new ArrayList<>();
        specialConnection = new ArrayList<>();
        domain = new ArrayList<>();
        con = new ArrayList<>();
        removed = new ArrayList<>();
        setSingeltones = new ArrayList<>();
        weekReductions = new ArrayList<>();
        this.day = day;
        this.hour = hour;
        weekPoint = new Point(day, hour);

    }

    public HourNode(SubjectPlaceHolder placeHolder, int day, int hour) {
        this(day, hour);
        this.placeHolder = placeHolder;
    }

    public void setConsecitiveCondition(SubjectPlaceHolder subject) {
        con.add(new MustBeConsecitiveCondition(this, subject));
    }

    public boolean complateCondition() {
        for (int i = 0; i < con.size(); i++) {
            if (!con.get(i).complate()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return placeHolder.subject;
    }

    boolean isSet() {
        return set;
    }

    public HourNode getNext() {
        if (this.nextHour != null) {
            return this.nextHour;
        } else if (this.nextDayConnection != null) {
            return this.nextDayConnection.hours.get(0);
        } else {
            return null;
        }
    }

    public void set(SubjectPlaceHolder sub) {
        if (this.sameClass.getOccurance(sub) == this.sameClass.getSubjectPlan(sub)) {
            System.out.println("asdsadasdsa");
            return;

        }
        this.placeHolder = sub;
        this.sameClass.addOccurence(sub);
        set = true;

        propagateThroughClassesAndReduceDomain();
        propagateThroughDayAndReduceDomain();
        propagateThroughWeekAndReduceDomain();
        propagetThroughSingeltoneDomains();
    }

    public void setManual(SubjectPlaceHolder sub) {
        this.placeHolder = sub;
        this.sameClass.addOccurence(sub);
        if (this.sameClass.getOccurance(sub) > this.sameClass.getSubjectPlan(sub)) {
            this.sameClass.removeOccurence(sub);
            this.placeHolder = new SubjectPlaceHolder("unknown", "unknown");
        }
        set = true;
        propagateThroughClassesAndReduceDomain();
        propagateThroughDayAndReduceDomain();
    }

    public void unSet() {
        if (!set) {
            return;
        }
        set = false;
        sameClass.removeOccurence(placeHolder);
        for (int i = 0; i < con.size(); i++) {
            Condition condition = con.get(i);
            condition.unComplate();
        }

        restoreClassesDomain();
        restoreDayDomain();
        restoreWeekDomain();
        restoreSingeltoneDomains();
        setSingeltones.clear();
        weekReductions.clear();
        placeHolder = new SubjectPlaceHolder("unknown", "unknown");

    }

    private void propagateThroughDayAndReduceDomain() {
        ArrayList<HourNode> nodesOfDay = this.sameDayConnection.hours;
        for (HourNode node : nodesOfDay) {
            if (!node.isSet()) {
                node.domain.remove(this.placeHolder);
            }
        }
    }

    private void propagateThroughClassesAndReduceDomain() {
        for (int i = 0; i < this.sameTimeConnection.size(); i++) {
            if (!this.sameTimeConnection.get(i).isSet()) {
                this.sameTimeConnection.get(i).domain.remove(placeHolder);
            }
        }
    }

    private boolean propagetThroughSingeltoneDomains() {
        ArrayList<HourNode> allNodes = GlobalSpace.algControll.state;
        for (HourNode node : allNodes) {

            if (node.isSingeltoneDomain() && !node.isSet() && !node.sameClass.isReady()) {
                setSingeltones.add(node);
                node.set(node.domain.get(0));
            }

        }
        return true;
    }

    private void propagateThroughWeekAndReduceDomain() {
        if (this.sameClass.getOccurance(placeHolder) == this.sameClass.getSubjectPlan(placeHolder)) {
            for (DayNode day : sameClass.days) {
                for (HourNode hour : day.hours) {
                    if (!hour.isSet()) {
                        if (hour.domain.remove(placeHolder)) {
                            weekReductions.add(hour.weekPoint);
                        }

                    }
                }
            }

        }
    }

    private void restoreWeekDomain() {
        if (this.sameClass.getOccurance(placeHolder) != this.sameClass.getSubjectPlan(placeHolder)) {
            for (DayNode day : sameClass.days) {
                for (HourNode hour : day.hours) {
                    if (weekReductions.contains(hour.weekPoint));
                    {
                        hour.domain.add(placeHolder);
                    }
                }

            }

        }
    }

    private void restoreSingeltoneDomains() {
        for (HourNode node : setSingeltones) {
            node.unSet();
        }
    }

    private void restoreClassesDomain() {
        for (int i = 0; i < this.sameTimeConnection.size(); i++) {
            if (!this.sameTimeConnection.get(i).isSet()) {
                this.sameTimeConnection.get(i).domain.add(placeHolder);
            }
        }
    }

    public ArrayList<SubjectPlaceHolder> getDomain() {

        return null;

    }

    private void restoreDayDomain() {
        ArrayList<HourNode> nodesOfDay = this.sameDayConnection.hours;
        for (HourNode node : nodesOfDay) {
            if (!node.isSet() && node != this) {
                node.domain.add(this.placeHolder);
            }
        }
    }

    void sortByHardness() {
        domain.sort((SubjectPlaceHolder o1, SubjectPlaceHolder o2) -> {
            int hard1 = GlobalSpace.subjectController.getSubjectsMap().get(o1.getSubject()).getHardness();
            int hard2 = GlobalSpace.subjectController.getSubjectsMap().get(o2.getSubject()).getHardness();
            if (hard1 > hard2) {
                return -1;
            } else if (hard1 < hard2) {
                return 1;
            }
            return 0;
        });
    }

    public void shuffelDomain(boolean sorted) {
        if (!sorted) {
            Collections.shuffle(domain);
        } else {
            shuffelSorted();
        }
    }

    private void shuffelSorted() {
        Map<Integer, ArrayList<SubjectPlaceHolder>> map = new HashMap<>();
        domain.forEach(element -> {
            int hardess = GlobalSpace.subjectController.getSubjectsMap().get(element.getSubject()).getHardness();
            if (!map.containsKey(hardess)) {
                map.put(hardess, new ArrayList<SubjectPlaceHolder>());
            }
            map.get(hardess).add(element);
        }
        );
        map.values().forEach(element -> {
            Collections.shuffle(element);
        });
        domain.clear();

        for (int i = 3; i >= 1; i--) {
            for (Map.Entry<Integer, ArrayList<SubjectPlaceHolder>> entry : map.entrySet()) {
                if (entry.getKey() == i) {
                    domain.addAll(entry.getValue());
                }

            }
        }
        domain.forEach(element -> {
            System.out.println(element.getSubject());
        });
    }

    public boolean isSingeltoneDomain() {
        return domain.size() == 1;
    }

    public SubjectPlaceHolder getPlaceHolder() {
        return placeHolder;
    }

    boolean aloneEmpyDomain() {
        int cnt = 0;
        for (HourNode node : sameDayConnection.hours) {
            if (node.domain.isEmpty()) {
                cnt++;
            }
        }
        if (cnt == 1) {
            return true;
        } else {
            return false;
        }
    }

}
