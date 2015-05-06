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
package advancedresoucemenaging.GUIClasses;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author Stanisalv
 */
public class GUISizeController {

    private static final Dimension intendedSize = new Dimension(1075, 625);
    private static final Dimension intendedScreenSize = new Dimension(1366, 768);
    private static Dimension prefferedSize;
    public static double heightRatio = 1, widthRatio = 1;

    public static Dimension getPrefferedSize() {
        return prefferedSize == null ? calculatePrefferedSize() : prefferedSize;
    }

    private static Dimension calculatePrefferedSize() {
        Dimension screenMaxSize = Toolkit.getDefaultToolkit().getScreenSize();
        prefferedSize = new Dimension(intendedSize);

      //  if (screenMaxSize.height < intendedScreenSize.height) {
        //       prefferedSize.height = (int) Math.round((double) intendedSize.height * ((double) screenMaxSize.height / (double) intendedScreenSize.height));
        //        heightRatio = (double) prefferedSize.height / (double) intendedSize.height;
        // } else {
        prefferedSize.height = intendedSize.height;
       // }

        //if (screenMaxSize.width < intendedScreenSize.width) {
        //   prefferedSize.width = (int) Math.round((double) intendedSize.width * ((double) screenMaxSize.width / (double) intendedScreenSize.width));
        //  widthRatio = (double) prefferedSize.width / (double) intendedSize.width;
        //} else {
        prefferedSize.width = intendedSize.width;
        // }
        return prefferedSize;
    }
}
