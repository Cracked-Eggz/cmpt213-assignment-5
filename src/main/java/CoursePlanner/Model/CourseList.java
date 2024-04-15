package CoursePlanner.Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class CourseList {
    private static final Logger logger = Logger.getLogger(CourseList.class.getName());
    private static final AtomicInteger departmentIdCounter = new AtomicInteger(1);
    private static final AtomicInteger courseIdCounter = new AtomicInteger(1);
    private static final AtomicInteger offeringIdCounter = new AtomicInteger(1);
    // tree maps automatically maintain sorted order
    private TreeMap<String, Department> departments;

    public CourseList(String csvFileName) {
        this.departments = new TreeMap<>();

        String courseStr;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFileName))) {
            br.readLine(); // To skip reading the headers
            while ((courseStr = br.readLine()) != null) {
                addCourse(courseStr);
            }
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

    public void addCourse(String courseStr) {
        ArrayList<String> courseDetails = new CourseDataParser(courseStr).getCourseDetailsList();
        assert (courseDetails.size() == 8);
        Course newCourse = new Course(courseIdCounter.getAndIncrement(),
                offeringIdCounter.getAndIncrement(), courseDetails);

        if (departments.get(newCourse.getDepartment()) == null) {
            departments.put(newCourse.getDepartment(),
                    new Department(departmentIdCounter.getAndIncrement(), newCourse.getDepartment(), newCourse));
        } else {
            Department courseDepartment = departments.get(newCourse.getDepartment());
            if (courseDepartment.addCourse(newCourse)){
                // if the course was merged, then it is not a new course with unique id
                courseIdCounter.decrementAndGet();
                offeringIdCounter.decrementAndGet();
            }
        }
    }

    public Department getDepartment(String name) {
        return departments.get(name);
    }

    public void print() {
        departments.forEach((deptName, department) -> department.print());
    }
}
