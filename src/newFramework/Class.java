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
package newFramework;

import java.util.HashMap;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Class
{

    private final int grade;
    private final String letter;
    private final int id;
    private final HashMap<Subject, Integer> subjectsPerWeek;
    private final HashMap<Subject, Teacher> subjectsTeachers;

    public Class(int grade, String letter, int id)
    {
        this.grade = grade;
        this.letter = letter;
        this.id = id;
        this.subjectsPerWeek = new HashMap<>();
        this.subjectsTeachers = new HashMap<>();
    }

    @Override
    public String toString()
    {
        return grade + "" + letter;
    }

    public int getGrade()
    {
        return grade;
    }

    public String getLetter()
    {
        return letter;
    }

    public int getId()
    {
        return id;
    }

    public HashMap<Subject, Integer> getSubjectsPerWeek()
    {
        return subjectsPerWeek;
    }

    public HashMap<Subject, Teacher> getSubjectsTeachers()
    {
        return subjectsTeachers;
    }

}
