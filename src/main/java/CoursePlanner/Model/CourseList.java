package CoursePlanner.Model;

import CoursePlanner.AllApiDtoClasses.ApiOfferingDataDTO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class CourseList {
    private final AtomicLong departmentIdCounter = new AtomicLong(1);
    private final AtomicLong courseIdCounter = new AtomicLong(1);
    private final AtomicLong offeringIdCounter = new AtomicLong(1);
    // tree maps automatically maintain sorted order
    private final TreeMap<String, Department> departments;

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
            Logger logger = Logger.getLogger(CourseList.class.getName());
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

    public void addCourse(ApiOfferingDataDTO dataDTO) {
        String result = dataDTO.semester + ", " +
                dataDTO.subjectName + ", " +
                dataDTO.catalogNumber + ", " +
                dataDTO.location + ", " +
                dataDTO.enrollmentCap + ", " +
                dataDTO.enrollmentTotal + ", " +
                dataDTO.instructor + ", " +
                dataDTO.component;
        addCourse(result);
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
            List<Boolean> merged = courseDepartment.addCourse(newCourse);
            Boolean courseMerged = merged.get(0);
            Boolean offerMerged = merged.get(1);
            if (courseMerged) {
                // if the course was merged, then it is not a new course with unique id
                courseIdCounter.decrementAndGet();
                if (offerMerged) {
                    // if the offering also merged, then it is not a new offering with unique id
                    offeringIdCounter.decrementAndGet();
                }
            }
        }
    }

    public Department getDepartment(long deptId) {
        for (Department department : departments.values()) {
            if (department.getDeptId() == deptId) {
                return department;
            }
        }
        return null;
    }

    public List<Department> getDepartments() {
        return new ArrayList<>(departments.values());
    }

    public void print() {
        departments.forEach((deptName, department) -> department.print());
    }
}
