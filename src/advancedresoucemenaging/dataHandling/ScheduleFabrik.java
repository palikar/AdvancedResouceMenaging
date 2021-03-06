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

import advancedresoucemenaging.algStuff.Controller;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class ScheduleFabrik
{

    static public Controller algControll;
    static public ClassChecker mistakeFinder;
    static public Map<String, ArrayList<Point>> holes;
    static public Map<String, ArrayList<Point>> loners;

    static
    {
        mistakeFinder = new ClassChecker();
        holes = new HashMap<>();
        loners = new HashMap<>();
    }

}
