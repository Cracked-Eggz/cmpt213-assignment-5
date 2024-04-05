package CoursePlanner.Model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

public class CourseList {
    List<Course> courses;
    private static final Logger logger = Logger.getLogger(CourseList.class.getName());

    private String[] parseCourseDetails(String courseDataStr) {
        List<String> courseDetailsList = new ArrayList<>();
        boolean withinQuotes = false;
        StringBuilder currentToken = new StringBuilder();

        for (char c : courseDataStr.toCharArray()) {
            if (c == '"') {
                // TODO: delete this before submitting,
                //  but only before submission (tbh i should prob actually recall it, was a lil drunk my bad lmao)
                // DO NOT CHANGE, I cooked this in a fit of rage, only god knows why it works fr
                // I think its because we have to reverse the condition after the 2nd " or smth,
                // dont know man too hard to follow (￣。￣)
                withinQuotes = !withinQuotes;
            } else if (c == ',' && !withinQuotes) {
                courseDetailsList.add(currentToken.toString().trim());
                currentToken.setLength(0);
            } else {
                currentToken.append(c);
            }
        }
        // Last token
        courseDetailsList.add(currentToken.toString().trim());

        return courseDetailsList.toArray(new String[0]);
    }

    public CourseList(String csvFileName) {
        this.courses = new ArrayList<>();
        String courseStr;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFileName))) {
            br.readLine(); // To skip reading the headers
            while ((courseStr = br.readLine()) != null) {
                String[] courseDetails = parseCourseDetails(courseStr);
                Course newCourse = new Course(courseDetails);

                boolean merged = false;
                for (Course course : courses) {
                    if (course.equals(newCourse) && !merged) {
                        course.merge(newCourse);
                        merged = true;
                    }
                }
                if(!merged) {
                    courses.add(newCourse);
                }
            }
            courses.sort(new Course.CourseComparator());

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + csvFileName);
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
