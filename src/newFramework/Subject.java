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

import java.util.ArrayList;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Subject
{

    public static final int LOW_HARDNESS = 1;
    public static final int MEDIUM_HARDNESS = 2;
    public static final int HIGH_HARDNESS = 3;

    private final ArrayList<Teacher> teachers;
    private int hardNess;
    private final int id;
    private final String name;

    public Subject(int hardNess, int id, String name)
    {
        this.hardNess = hardNess;
        this.id = id;
        this.name = name;
        teachers = new ArrayList<>();
    }

    public void setHardNess(int hardNess)
    {
        this.hardNess = hardNess;
    }

    public ArrayList<Teacher> getTeachers()
    {
        return teachers;
    }

    public int getHardNess()
    {
        return hardNess;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

}
