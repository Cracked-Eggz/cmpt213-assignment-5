package CoursePlanner.Controller;

import CoursePlanner.Model.Department;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import CoursePlanner.Model.CourseList;
import CoursePlanner.AllApiDtoClasses.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class plannerController {

    @ModelAttribute("courseList")
    public CourseList getCourseList() {
        return CourseList.hardCodedCreate();
    }

    @GetMapping("/about")
    public ApiAboutDTO getAbout() {
        return new ApiAboutDTO("TheBestCoursePlannerEver", "Richard Xiong and Deng Chen");
    }

    @GetMapping("/dump-model")
    public void getDumpModel(CourseList courseList) {
        courseList.print();
    }

    @GetMapping("/api/departments")
    public List<Department> getDepartments(CourseList courseList) {
        return courseList.getDepartments();
    }

    @GetMapping("/departments/{deptId}/courses")
    public List<ApiCourseDTO> getCoursesInDepartment(@PathVariable int deptId, CourseList courseList) {
        return courseList.getDepartment(deptId).getCourses().stream()
                .map(ApiCourseDTO::new)
                .toList();
    }

    @GetMapping("/departments/{deptId}/courses/{courseId}/offer")
    public List<ApiCourseOfferingDTO> getOfferInCourse(@PathVariable int deptId, @PathVariable int courseId,
                                                   CourseList courseList) {
        return courseList.getDepartment(deptId).getCourse(courseId).getOfferings().stream()
                .map(ApiCourseOfferingDTO::new)
                .toList();
    }

        @PostMapping("/api/addoffering")
    public ResponseEntity<String> addOffering(@RequestBody String newOfferingCSV, CourseList courseList) {
        courseList.addCourse(newOfferingCSV);
        return ResponseEntity.status(HttpStatus.CREATED).body("New offering added successfully.");
    }
}
