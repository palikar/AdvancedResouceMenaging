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
import advancedresoucemenaging.algStuff.SubjectPlaceHolder;

/**
 *
 * @author Stanisalv
 */
public class mustBeConsecitiveCondition extends Condition {

    public SubjectPlaceHolder sub;
    public static final int CODE = 1;

    public mustBeConsecitiveCondition(HourNode node, SubjectPlaceHolder sub) {
        super(node);
        this.sub = sub;
    }

    @Override
    public boolean complate() {

        return true;

    }

    @Override
    public boolean unComplate() {
        return true;
    }
}
