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
import java.io.File;

/**
 *
 * @author Stanisalv
 */
public class GlobalSpace {

    static public ClassHandler classController;
    static public TeacherHandler teacherController;
    static public SubjectHandler subjectController;
    static public String school = "default", principle = "default";
    static public Controller algControll;
    static public File openeFile = null;
    public static boolean ready = false;

    static {
        classController = new ClassHandler();
        teacherController = new TeacherHandler();
        subjectController = new SubjectHandler();
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
}
