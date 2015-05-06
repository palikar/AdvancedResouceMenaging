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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Stanisalv
 */
public class SubjectHandler implements Handler {

    private Map<String, Subject> subjectsMap;
    private ArrayList<Subject> subjects;

    public SubjectHandler() {
        subjectsMap = new HashMap<>();
        subjects = new ArrayList<>();
    }

    @Override
    public void add(String name) {
        Subject newSub = new Subject(name);
        subjects.add(newSub);
        subjectsMap.put(name, newSub);
    }

    @Override
    public void remove(String name) {
        subjects.remove(subjectsMap.get(name));
        subjectsMap.remove(name);
    }

    @Override
    public boolean contains(String name) {
        return subjectsMap.containsKey(name);
    }

    public Map<String, Subject> getSubjectsMap() {
        return subjectsMap;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }
    
    
}
