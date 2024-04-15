package CoursePlanner.Model;

import java.util.ArrayList;
import java.util.List;

public class Department {
    List<Course> courses;

    public Department(Course course) {
        courses = new ArrayList<>();
        courses.add(course);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        boolean merged = false;
        for (Course aCourse : courses) {
            if (aCourse.equals(course)) {
                aCourse.merge(course);
                merged = true;
                break;
            }
        }
        if (!merged) {
            courses.add(course);
        }
        sort();
    }

    public void sort() {
        courses.sort(new Course.CourseComparator());
    }

    public void print() {
        for (Course course : courses) {
            course.print();
        }
    }
}
