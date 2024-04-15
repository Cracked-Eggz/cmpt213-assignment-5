package CoursePlanner.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import CoursePlanner.Model.CourseList;
import CoursePlanner.AllApiDtoClasses.*;

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

    @GetMapping("/api/department")
    public ApiDepartmentDTO getDepartment() {
        return new ApiDepartmentDTO();
    }

    @PostMapping("/api/addoffering")
    public ResponseEntity<String> addOffering(@RequestBody String newOfferingCSV, CourseList courseList) {
        courseList.addCourse(newOfferingCSV);
        return ResponseEntity.status(HttpStatus.CREATED).body("New offering added successfully.");
    }
}
