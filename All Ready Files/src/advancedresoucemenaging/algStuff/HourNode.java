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

import advancedresoucemenaging.conditionClasses.mustBeConsecitiveCondition;
import advancedresoucemenaging.conditionClasses.Condition;
import advancedresoucemenaging.dataHandling.GlobalSpace;
import java.util.ArrayList;
import java.util.Collections;
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
    boolean set = false;

    public HourNode() {
        placeHolder = new SubjectPlaceHolder();
        sameTimeConnection = new ArrayList<>();
        specialConnection = new ArrayList<>();
        domain = new ArrayList<>();
        con = new ArrayList<>();
        removed = new ArrayList<>();
        setSingeltones = new ArrayList<>();

    }

    public HourNode(SubjectPlaceHolder placeHolder) {
        this();
        this.placeHolder = placeHolder;
    }

    public void setConsecitiveCondition(SubjectPlaceHolder subject) {
        con.add(new mustBeConsecitiveCondition(this, subject));
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

    public boolean set(SubjectPlaceHolder sub) {
        this.placeHolder = sub;
        this.sameClass.addOccurence(sub);
        set = true;
        if (this.sameClass.getOccurance(sub) > this.sameClass.getSubjectPlan(sub)) {
            return false;
        }
        boolean suc = true;
        for (int i = 0; i < con.size(); i++) {
            Condition condition = con.get(i);
            suc = condition.complate();
            if (!suc) {
                return false;
            }
        }
        propagateThroughClassesAndReduceDomain();
        propagateThroughDayAndReduceDomain();
        suc = propagetThroughSingeltoneDomains();

        return suc;
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
        set = false;
        sameClass.removeOccurence(placeHolder);
        for (int i = 0; i < con.size(); i++) {
            Condition condition = con.get(i);
            condition.unComplate();
        }

        restoreClassesDomain();
        restoreDayDomain();
        restoreSingeltoneDomains();
        setSingeltones.clear();
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
                if (!node.set(node.domain.get(0))) {
                    return false;
                }
            }

        }
        return true;
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

    private void restoreDayDomain() {
        ArrayList<HourNode> nodesOfDay = this.sameDayConnection.hours;
        for (HourNode node : nodesOfDay) {
            if (!node.isSet() && node != this) {
                node.domain.add(this.placeHolder);
            }
        }
    }

    public HourNode getNext(HourNode node) {
        if (node.nextHour != null) {
            return node.nextHour;
        } else if (node.nextDayConnection != null) {
            return node.nextDayConnection.hours.get(0);
        } else {
            return null;
        }
    }

    void shuffle() {
        Collections.shuffle(domain);
    }

    public boolean isSingeltoneDomain() {
        return domain.size() == 1;
    }

    public SubjectPlaceHolder getPlaceHolder() {
        return placeHolder;
    }

}
