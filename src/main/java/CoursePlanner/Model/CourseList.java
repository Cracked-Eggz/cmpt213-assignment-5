package CoursePlanner.Model;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

public class CourseList {
    List<Course> courses;
    private static final Logger logger = Logger.getLogger(CourseList.class.getName());

    public CourseList(String csvFileName) {
        this.courses = new ArrayList<>();
        String courseStr;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFileName))) {
            br.readLine(); // To skip reading the headers
            while ((courseStr = br.readLine()) != null) {
                ArrayList<String> courseDetails = new CourseParser(courseStr).getCourseDetailsList();
                assert (courseDetails.size() == 8);
                Course newCourse = new Course(courseDetails);

                boolean merged = false;
                for (Course course : courses) {
                    if (course.equals(newCourse)) {
                        course.merge(newCourse);
                        merged = true;
                        break;
                    }
                }
                if (!merged) {
                    courses.add(newCourse);
                }
            }
            courses.sort(new Course.CourseComparator());
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + csvFileName);
            System.exit(1); // abnormal status
        } catch (IOException e) {
            logger.severe("An unaccounted for error occurred: " + e.getMessage());
            logger.severe("Stack trace: ");
            for (StackTraceElement element : e.getStackTrace()) {
                logger.severe(element.toString());
            }
            System.exit(1); // abnormal status
        }
    }

    // hard-coded factory method for local csv files
    public static CourseList hardCodedCreate() {
        // return new CourseList("data/small_data.csv");
        return new CourseList("data/course_data_2018.csv");
        // return new CourseList("data/course_data_2022.csv");
    }

    public void printAllCourses() {
        for (Course course : courses) {
            course.print();
        }
    }
}
