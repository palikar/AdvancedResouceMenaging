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

import advancedresoucemenaging.algStuff.Controller;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Stanisalv
 */
public class GlobalSpace {

    static public ClassHandler classController;
    static public TeacherHandler teacherController;
    static public SubjectHandler subjectController;
    static public String school = "", principle = "";
    static public Map<Object, Object> params;
    static public Controller algControll;
    static public File openeFile = null;
    public static boolean ready = false;
    public static BufferedImage iconImage = null;

    static {
        classController = new ClassHandler();
        teacherController = new TeacherHandler();
        subjectController = new SubjectHandler();
        params = new HashMap<>();
    }

    public static void setParam(Object param, Object value) {
        params.put(param, value);
    }

    public static void addParam(Object param) {
        if (!params.containsKey(param)) {
            params.put(param, "");
        }
    }

    public static void setUpController() {
        algControll = new Controller();
        classController.setUpController(algControll);
    }

    public static void makePlan() {
        algControll.make();
        ready = true;
        classController.getReadySchedule(algControll);
    }

    public static void deleteEverything() {
        classController = new ClassHandler();
        teacherController = new TeacherHandler();
        subjectController = new SubjectHandler();
        algControll = new Controller();
        school = "default";
        principle = "default";
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int type, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return resizedImage;
    }

}