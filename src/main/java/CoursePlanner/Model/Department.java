package CoursePlanner.Model;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private final int deptId;
    private final String name;
    List<Course> courses;

    public Department(int id, String name, Course course) {
        this.deptId = id;
        this.name = name;
        courses = new ArrayList<>();
        courses.add(course);
    }

    public int getDeptId() {
        return deptId;
    }

    public String getName() {
        return name;
    }

    public Course getCourse(int id) {
        for (Course course : courses) {
            if (course.getCourseId() == id) {
                return course;
            }
        }
        return null;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public boolean addCourse(Course course) {
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
        return merged;
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
