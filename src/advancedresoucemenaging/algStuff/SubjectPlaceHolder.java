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

import java.util.Objects;

/**
 *
 * @author Stanisalv
 */
public class SubjectPlaceHolder {

    String subject;
    String teacher;

    public SubjectPlaceHolder() {
        subject = "unknown";
        teacher = "unknown";
    }

    public SubjectPlaceHolder(String subject, String teacher) {
        this.subject = subject;
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object obj) {
        return ((SubjectPlaceHolder) obj).teacher.equals(teacher)
                && ((SubjectPlaceHolder) obj).subject.equals(subject);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.subject);
        hash = 29 * hash + Objects.hashCode(this.teacher);
        return hash;
    }
}
