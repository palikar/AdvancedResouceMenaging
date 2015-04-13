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

/**

 @author Stanisalv
 */
public class SubjectHandler extends Handler {

    public ArrayList<String> subjects;

    public SubjectHandler() {
	subjects = new ArrayList<>();
    }

    @Override
    public void add(String name) {
	subjects.add(name);
    }

    @Override
    public void remove(String name) {
	subjects.remove(name);
    }

    @Override
    public boolean contains(String name) {
	return subjects.contains(name);
    }
}
