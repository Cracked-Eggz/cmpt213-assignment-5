package CoursePlanner.Controller;

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

    @GetMapping("/departments")
    public List<ApiDepartmentDTO> getDepartments(CourseList courseList) {
        return courseList.getDepartments().stream()
                .map(ApiDepartmentDTO::new)
                .toList();
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

    @GetMapping("/departments/{deptId}/courses/{courseId}/offerings/{offeringId}")
    public ApiOfferingDataDTO getOfferingById(@PathVariable int deptId, @PathVariable int courseId,
                                                              @PathVariable int offeringId, CourseList courseList) {

        return new ApiOfferingDataDTO();
    }

    @GetMapping("/stats/students-per-semester?deptId={deptId}")
    public List<ApiGraphDataPointDTO> studentsPerSemester(@PathVariable int deptId, CourseList courseList) {
        return new ArrayList<>();
    }

    @PostMapping("/addoffering")
    public ResponseEntity<String> addOffering(@RequestBody String newOfferingCSV, CourseList courseList) {
        courseList.addCourse(newOfferingCSV);
        return ResponseEntity.status(HttpStatus.CREATED).body("New offering added successfully.");
    }

    @GetMapping("/watchers")
    public List<ApiWatcherDTO> getWatchers(CourseList courseList) {
        return new ArrayList<>();
    }

    @PostMapping("/watchers")
    public ResponseEntity<String> addWatcher(@RequestBody ApiWatcherCreateDTO watcher, CourseList courseList) {
        return ResponseEntity.status(HttpStatus.CREATED).body("New offering added successfully.");
    }

    @GetMapping("/watchers/{watcherId}")
    public ApiWatcherDTO getWatcher(@PathVariable int watcherId, CourseList courseList) {
        return new ApiWatcherDTO();
    }

    @DeleteMapping("/watchers/{watcherId}")
    public ResponseEntity<String> deleteWatcher(@PathVariable int watcherId, CourseList courseList) {
        return ResponseEntity.status(HttpStatus.OK).body("Deleted offering with id " + watcherId);
    }
}
