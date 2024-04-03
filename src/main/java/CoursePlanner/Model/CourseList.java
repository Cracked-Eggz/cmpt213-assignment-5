package CoursePlanner.Model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CourseList {
    List<Course> courses;

    public CourseList(String csvFileName) {
        this.courses = new ArrayList<>();
        String courseStr;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFileName))) {
            br.readLine(); // To skip reading the headers
            while ((courseStr = br.readLine()) != null) {
                String[] courseDetails = courseStr.split(",");
                Course newCourse = new Course(courseDetails);

                for (Course course : courses) {
                    if (course.equals(newCourse)) {
                        course.merge(newCourse);
                        return;
                    }
                }
                courses.add(newCourse);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + csvFileName);
        } catch (IOException e) {
            System.out.println("Unaccounted exception found.");
            e.printStackTrace();
            System.exit(1); // abnormal status
        }
    }

    // hard-coded factory method for course_data_2018.csv
    public static CourseList createTEST() {
        return new CourseList("data/course_data_2018.csv");
    }

    public void printAllCourses() {
        for (Course course : courses) {
            course.print();
        }
    }
}
