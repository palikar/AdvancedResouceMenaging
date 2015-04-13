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

import java.util.ArrayList;

/**

 @author Stanisalv
 */
public class DayNode {

    ArrayList<HourNode> hours;

    public DayNode() {
	hours = new ArrayList<>();
	for (int i = 0; i < 7; i++) {
	    hours.add(new HourNode());
	}
	for (int i = 0; i < 7; i++) {
	    if (i != 6) {
		hours.get(i).nextHour = hours.get(i + 1);
	    }
	    if (i != 0) {
		hours.get(i).prevHour = hours.get(i - 1);
	    }

	}
    }

    public ArrayList<HourNode> getHours() {
	return hours;
    }
}
