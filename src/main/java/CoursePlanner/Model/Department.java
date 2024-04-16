package CoursePlanner.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Department {
    private final long deptId;
    private final String name;
    List<Course> courses;

    public Map<Integer, Integer> getTotalEnrollmentPerSemester() {
        // Use TreeMap to automatically sort by semester code
        Map<Integer, Integer> totalEnrollments = new TreeMap<>();
        for (Course course : courses) {
            Map<Integer, Integer> enrollments = course.getMapSemesterToEnrollmentNumbers();
            for (Map.Entry<Integer, Integer> entry : enrollments.entrySet()) {
                totalEnrollments.merge(entry.getKey(), entry.getValue(), Integer::sum);
            }
        }
        return totalEnrollments;
    }

    public Department(long id, String name, Course course) {
        this.deptId = id;
        this.name = name;
        courses = new ArrayList<>();
        courses.add(course);
    }

    public long getDeptId() {
        return deptId;
    }

    public String getName() {
        return name;
    }

    public Course getCourse(long id) {
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

    public List<Boolean> addCourse(Course course) {
        boolean courseMerged = false;
        boolean offerMerged = false;

        for (Course aCourse : courses) {
            if (aCourse.equals(course)) {
                offerMerged = aCourse.merge(course);
                courseMerged = true;
                break;
            }
        }
        if (!courseMerged) {
            courses.add(course);
        }
        courses.sort(new Course.CourseComparator());
        List<Boolean> result = new ArrayList<>();
        result.add(courseMerged);
        result.add(offerMerged);
        return result;
    }

    public void print() {
        for (Course course : courses) {
            course.print();
        }
    }
}
