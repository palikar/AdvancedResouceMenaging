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
package advancedresoucemenaging.dataLoading;

import advancedresoucemenaging.conditionClasses.ConditionDescription;
import advancedresoucemenaging.dataHandling.GlobalSpace;
import advancedresoucemenaging.algStuff.SubjectPlaceHolder;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;

/**

 @author Stanisalv
 */
public class SavingLoadingSystem {

    public static void loadGlobalSpace(File file) {
	try {
	    FileReader fileReader = new FileReader(file.getAbsolutePath());
	    BufferedReader bufferedReader = new BufferedReader(fileReader);
	    String line = null;
	    GlobalSpace.deleteEverything();
	    GlobalSpace.school = bufferedReader.readLine();
	    GlobalSpace.principle = bufferedReader.readLine();
	    while (!(line = bufferedReader.readLine()).equals("end!")) {
		GlobalSpace.classController.add(line);
	    }
	    while (!(line = bufferedReader.readLine()).equals("end!")) {
		GlobalSpace.teacherController.add(line);
	    }
	    while (!(line = bufferedReader.readLine()).equals("end!")) {
		GlobalSpace.subjectController.add(line);
	    }
	    while (!(line = bufferedReader.readLine()).equals("end!")) {
		String clas = line;
		while (!(line = bufferedReader.readLine()).equals("newClass!")) {
		    String params[] = line.split("\\|");
		    GlobalSpace.classController.addSuject(clas,
			    new SubjectPlaceHolder(params[0], params[1]), Integer.parseInt(params[2]));
		}

	    }
	    while (!(line = bufferedReader.readLine()).equals("end!")) {
		String clas = line;
		while (!(line = bufferedReader.readLine()).equals("newClass!")) {
		    GlobalSpace.classController.addCondition(clas, line);
		}

	    }

	    while (!(line = bufferedReader.readLine()).equals("end!")) {
		String clas = line;
		int index = 0;
		while (!(line = bufferedReader.readLine()).equals("newClass!")) {
		    String subjects[] = line.split("\\+");
		    for (int i = 0; i < subjects.length; i++) {
			String sub[] = subjects[i].split("\\|");
			SubjectPlaceHolder subjectPlaceHolder = new SubjectPlaceHolder(sub[0], sub[1]);
			GlobalSpace.classController.schedules.get(clas)[i][index] = subjectPlaceHolder;
		    }
		    index++;
		}

	    }

	    bufferedReader.close();
	    fileReader.close();
	} catch (IOException ex) {
	}


    }

    public static void saveGlobalSpace(File file) {
	try {
	    FileWriter fileWriter = new FileWriter(file.getAbsolutePath());
	    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	    bufferedWriter.write("");
	    bufferedWriter.write(GlobalSpace.school);
	    bufferedWriter.newLine();
	    bufferedWriter.write(GlobalSpace.principle);
	    bufferedWriter.newLine();
	    for (int i = 0; i < GlobalSpace.classController.classes.size(); i++) {
		bufferedWriter.write(GlobalSpace.classController.classes.get(i));
		bufferedWriter.newLine();
	    }
	    bufferedWriter.write("end!");
	    bufferedWriter.newLine();
	    for (int i = 0; i < GlobalSpace.teacherController.teachers.size(); i++) {
		bufferedWriter.write(GlobalSpace.teacherController.teachers.get(i));
		bufferedWriter.newLine();
	    }
	    bufferedWriter.write("end!");
	    bufferedWriter.newLine();
	    for (int i = 0; i < GlobalSpace.subjectController.subjects.size(); i++) {
		bufferedWriter.write(GlobalSpace.subjectController.subjects.get(i));
		bufferedWriter.newLine();
	    }
	    bufferedWriter.write("end!");
	    bufferedWriter.newLine();


	    ArrayList<String> classes = GlobalSpace.classController.classes;
	    for (int i = 0; i < classes.size(); i++) {
		bufferedWriter.write(classes.get(i));
		bufferedWriter.newLine();
		Map<SubjectPlaceHolder, Integer> map = GlobalSpace.classController.subjectPlan.get(classes.get(i));
		Object keys[] = map.keySet().toArray();
		for (int j = 0; j < keys.length; j++) {
		    bufferedWriter.write(((SubjectPlaceHolder) keys[j]).getSubject()
			    + "|" + ((SubjectPlaceHolder) keys[j]).getTeacher() + "|" + map.get(keys[j]));
		    bufferedWriter.newLine();
		}
		bufferedWriter.write("newClass!");
		bufferedWriter.newLine();
	    }

	    bufferedWriter.write("end!");
	    bufferedWriter.newLine();

	    for (int i = 0; i < GlobalSpace.classController.classes.size(); i++) {
		String name = GlobalSpace.classController.classes.get(i);
		bufferedWriter.write(name);
		bufferedWriter.newLine();

		ArrayList<ConditionDescription> cons = GlobalSpace.classController.conditions.get(name);
		for (int j = 0; j < cons.size(); j++) {
		    bufferedWriter.write(cons.get(j).getSaveString());
		    bufferedWriter.newLine();
		}
		bufferedWriter.write("newClass!");
		bufferedWriter.newLine();

	    }
	    bufferedWriter.write("end!");
	    bufferedWriter.newLine();

	    for (int i = 0; i < GlobalSpace.classController.classes.size(); i++) {
		String name = GlobalSpace.classController.classes.get(i);
		bufferedWriter.write(name);
		bufferedWriter.newLine();

		SubjectPlaceHolder[][] schedule = GlobalSpace.classController.schedules.get(name);
		for (int j = 0; j < schedule[0].length; j++) {
		    for (int k = 0; k < schedule.length; k++) {
			SubjectPlaceHolder subjectPlaceHolder = schedule[k][j];
			bufferedWriter.write(subjectPlaceHolder.getSubject() + "|" + subjectPlaceHolder.getTeacher() + "+");
		    }
		    bufferedWriter.newLine();
		}


		bufferedWriter.write("newClass!");
		bufferedWriter.newLine();

	    }
	    bufferedWriter.write("end!");



	    bufferedWriter.close();
	    fileWriter.close();
	} catch (IOException ex) {
	}



    }
}
