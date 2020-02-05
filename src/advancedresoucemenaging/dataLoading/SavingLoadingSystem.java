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
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import advancedresoucemenaging.dataHandling.Class;

/**
 *
 * @author Stanisalv
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
                String split[] = line.split(":");
                String key = split[0];
                String value = split[1];
                GlobalSpace.setParam(key, value);
            }

            while (!(line = bufferedReader.readLine()).equals("end!")) {
                GlobalSpace.classController.add(line);
            }
            while (!(line = bufferedReader.readLine()).equals("end!")) {
                GlobalSpace.teacherController.add(line);
            }
            while (!(line = bufferedReader.readLine()).equals("end!")) {
                String parts[] = line.split("\\|");
                GlobalSpace.subjectController.add(parts[0]);
                GlobalSpace.subjectController.getSubjects().get(GlobalSpace.subjectController.getSubjects().size() - 1).setHardness(Integer.parseInt(parts[1]));
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
                        GlobalSpace.classController.getClasses().get(clas).getSchedule()[i][index] = subjectPlaceHolder;
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

            Iterator<Object> paramKeys = GlobalSpace.params.keySet().iterator();
            while (paramKeys.hasNext()) {
                Object key = paramKeys.next();
                bufferedWriter.write(key + ":" + GlobalSpace.params.get(key));
                bufferedWriter.newLine();
            }
            bufferedWriter.write("end!");
            bufferedWriter.newLine();

            for (advancedresoucemenaging.dataHandling.Class clas : GlobalSpace.classController.getClasses().values()) {
                bufferedWriter.write(clas.getName());
                bufferedWriter.newLine();
            }
            bufferedWriter.write("end!");
            bufferedWriter.newLine();
            for (int i = 0; i < GlobalSpace.teacherController.getTeachers().size(); i++) {
                bufferedWriter.write(GlobalSpace.teacherController.getTeachers().get(i));
                bufferedWriter.newLine();
            }
            bufferedWriter.write("end!");
            bufferedWriter.newLine();
            for (int i = 0; i < GlobalSpace.subjectController.getSubjects().size(); i++) {
                bufferedWriter.write(GlobalSpace.subjectController.getSubjects().get(i).getName());
                bufferedWriter.write("|" + GlobalSpace.subjectController.getSubjects().get(i).getHardness());
                bufferedWriter.newLine();
            }
            bufferedWriter.write("end!");
            bufferedWriter.newLine();

            
            for (Entry<String, Class> clas : GlobalSpace.classController.getClasses().entrySet()) {
              bufferedWriter.write(clas.getKey());
              bufferedWriter.newLine();
              for (Entry<SubjectPlaceHolder, Integer> subject : clas.getValue().getSubjectPlan().entrySet()) {
                bufferedWriter.write(subject.getKey().getSubject()
                                     + "|" + subject.getKey().getTeacher() + "|" + subject.getValue());
                bufferedWriter.newLine();
              }
              bufferedWriter.write("newClass!");
              bufferedWriter.newLine();
              
            }

            bufferedWriter.write("end!");
            bufferedWriter.newLine();

            for (Entry<String, Class> clas : GlobalSpace.classController.getClasses().entrySet()) {
                bufferedWriter.write(clas.getKey());
                bufferedWriter.newLine();

                ArrayList<ConditionDescription> cons = clas.getValue().getConditions();
                for (int j = 0; j < cons.size(); j++) {
                    bufferedWriter.write(cons.get(j).getSaveString());
                    bufferedWriter.newLine();
                }
                bufferedWriter.write("newClass!");
                bufferedWriter.newLine();

            }
            bufferedWriter.write("end!");
            bufferedWriter.newLine();

            for (Entry<String, Class> clas : GlobalSpace.classController.getClasses().entrySet()) {

                bufferedWriter.write(clas.getKey());
                bufferedWriter.newLine();

                SubjectPlaceHolder[][] schedule = clas.getValue().getSchedule();
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

    public static void saveClassesScheduleToPdf(File f) {
        try {
            Document d = new Document();
            PdfWriter.getInstance(d, new FileOutputStream(f));
            BaseFont baseFont = BaseFont.createFont("c:\\\\windows\\\\fonts\\\\Times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            d.open();
            Paragraph p1 = new Paragraph("Седмично разписание класовете от " + GlobalSpace.school, new Font(baseFont, 20, Font.BOLD));
            p1.setAlignment(Element.ALIGN_CENTER);
            d.add(p1);
            int[] widths = new int[]{
                1, 3, 3, 3, 3, 3,};
            int entiesOnPage = 0, tablesPerPage = 2;
            for (Entry<String, Class> clas : GlobalSpace.classController.getClasses().entrySet()) {
                entiesOnPage++;
                Paragraph p = new Paragraph("Програма на  " + clas.getKey(), new Font(baseFont, 17, Font.ITALIC));
                d.add(p);
                PdfPTable table = new PdfPTable(6);
                table.setSpacingBefore(2.5f);
                table.setWidthPercentage(100);
                table.setWidths(widths);
                PdfPCell cell0 = new PdfPCell(new Paragraph("Час", new Font(baseFont, 15, Font.BOLD)));
                PdfPCell cell1 = new PdfPCell(new Paragraph("Понеделник", new Font(baseFont, 15, Font.BOLD)));
                PdfPCell cell2 = new PdfPCell(new Paragraph("Вторник", new Font(baseFont, 15, Font.BOLD)));
                PdfPCell cell3 = new PdfPCell(new Paragraph("Сряда", new Font(baseFont, 15, Font.BOLD)));
                PdfPCell cell4 = new PdfPCell(new Paragraph("Четвъртък", new Font(baseFont, 15, Font.BOLD)));
                PdfPCell cell5 = new PdfPCell(new Paragraph("Петък", new Font(baseFont, 15, Font.BOLD)));
                table.addCell(cell0);
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
                table.addCell(cell5);

                SubjectPlaceHolder[][] schedule = clas.getValue().getSchedule();
                for (int i = 0; i < 7; i++) {
                    PdfPCell placeHolderCell = new PdfPCell();
                    placeHolderCell.addElement(new Paragraph((i + 1) + "", new Font(baseFont)));
                    table.addCell(placeHolderCell);
                    for (int j = 0; j < 5; j++) {
                        placeHolderCell = new PdfPCell();
                        placeHolderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        placeHolderCell.setVerticalAlignment(Element.ALIGN_CENTER);
                        if (schedule[j][i] != null && !schedule[j][i].getSubject().equals("unknown") && !schedule[j][i].getTeacher().equals("unknown")) {

                            placeHolderCell.addElement(new Paragraph(schedule[j][i].getSubject()));
                            placeHolderCell.addElement(new Paragraph(schedule[j][i].getTeacher()));

                        } else {
                            placeHolderCell.addElement(Chunk.NEWLINE);
                            placeHolderCell.addElement(Chunk.NEWLINE);
                        }
                        table.addCell(placeHolderCell);
                    }
                    table.completeRow();
                }
                d.add(table);
                d.add(Chunk.NEWLINE);
                if (entiesOnPage % tablesPerPage == 0) {
                    entiesOnPage = 0;
                    d.newPage();
                }
            }
            d.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SavingLoadingSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(SavingLoadingSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SavingLoadingSystem.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void saveTeachersScheduleToPdf(File f) {
        try {
            Document d = new Document();
            PdfWriter.getInstance(d, new FileOutputStream(f));
            BaseFont baseFont = BaseFont.createFont("c:\\\\windows\\\\fonts\\\\Times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            d.open();
            Paragraph p1 = new Paragraph("Седмично разписание на учителите от " + GlobalSpace.school, new Font(baseFont, 20, Font.BOLD));
            p1.setAlignment(Element.ALIGN_CENTER);
            d.add(p1);
            int[] widths = new int[]{
                1, 3, 3, 3, 3, 3,};
            int entiesOnPage = 0, tablesPerPage = 2;
            for (String teacher : GlobalSpace.teacherController.getTeachers()) {
                entiesOnPage++;
                Paragraph p = new Paragraph("Програма на  " + teacher, new Font(baseFont, 17, Font.ITALIC));
                d.add(p);
                PdfPTable table = new PdfPTable(6);
                table.setSpacingBefore(2.5f);
                table.setWidthPercentage(100);
                table.setWidths(widths);
                PdfPCell cell0 = new PdfPCell(new Paragraph("Час", new Font(baseFont, 15, Font.BOLD)));
                PdfPCell cell1 = new PdfPCell(new Paragraph("Понеделник", new Font(baseFont, 15, Font.BOLD)));
                PdfPCell cell2 = new PdfPCell(new Paragraph("Вторник", new Font(baseFont, 15, Font.BOLD)));
                PdfPCell cell3 = new PdfPCell(new Paragraph("Сряда", new Font(baseFont, 15, Font.BOLD)));
                PdfPCell cell4 = new PdfPCell(new Paragraph("Четвъртък", new Font(baseFont, 15, Font.BOLD)));
                PdfPCell cell5 = new PdfPCell(new Paragraph("Петък", new Font(baseFont, 15, Font.BOLD)));
                table.addCell(cell0);
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
                table.addCell(cell5);

                for (int i = 0; i < 7; i++) {
                    PdfPCell placeHolderCell = new PdfPCell();
                    placeHolderCell.addElement(new Paragraph((i + 1) + "", new Font(baseFont)));
                    table.addCell(placeHolderCell);
                    for (int j = 0; j < 5; j++) {
                        placeHolderCell = new PdfPCell();
                        placeHolderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        placeHolderCell.setVerticalAlignment(Element.ALIGN_CENTER);
                        String className = null;

                        for (Entry<String, Class> clas : GlobalSpace.classController.getClasses().entrySet()) {
                            if (clas.getValue().getSchedule()[j][i] == null || clas.getValue().getSchedule()[j][i].getSubject().endsWith("unknown")) {
                                continue;
                            }
                            if (clas.getValue().getSchedule()[j][i].getTeacher().equals(teacher)) {
                                className = clas.getKey();
                            }

                        }
                        if (className != null) {
                            placeHolderCell.addElement(new Paragraph(className, new Font(baseFont)));
                            placeHolderCell.addElement(Chunk.NEWLINE);

                        } else {
                            placeHolderCell.addElement(Chunk.NEWLINE);
                            placeHolderCell.addElement(Chunk.NEWLINE);
                        }
                        table.addCell(placeHolderCell);
                    }
                    table.completeRow();
                }
                d.add(table);
                d.add(Chunk.NEWLINE);
                if (entiesOnPage % tablesPerPage == 0) {
                    entiesOnPage = 0;
                    d.newPage();
                }
            }
            d.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SavingLoadingSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(SavingLoadingSystem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SavingLoadingSystem.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
