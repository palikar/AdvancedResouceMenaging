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
package advancedresoucemenaging.conditionClasses;

import advancedresoucemenaging.algStuff.HourNode;

/**
 *
 * @author Stanisalv
 */
public abstract class Condition {

    HourNode node;

    public Condition(HourNode node) {
        this.node = node;
    }

    public abstract boolean complate();

    public abstract boolean unComplate();

    public HourNode getNode() {
        return node;
    }

    public void setNode(HourNode node) {
        this.node = node;
    }

}
