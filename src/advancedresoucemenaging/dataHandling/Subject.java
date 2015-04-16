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

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Subject
{

    public static final int EASY = 1;
    public static final int MEDIUM = 2;
    public static final int HARD = 3;

    private String name;
    private int hardness;

    public Subject(String name)
    {
        this(name, EASY);
    }

    public Subject(String name, int hardness)
    {
        this.name = name;
        this.hardness = hardness;
    }

    public String getName()
    {
        return name;
    }

    public int getHardness()
    {
        return hardness;
    }

    public void setHardness(int hardness)
    {
        this.hardness = hardness;
    }

}
