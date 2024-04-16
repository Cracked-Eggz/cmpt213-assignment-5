package CoursePlanner.Controller;

import CoursePlanner.Model.Department;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import CoursePlanner.AllApiDtoClasses.*;
import CoursePlanner.Model.CourseList;
import CoursePlanner.Model.WatcherList;
import CoursePlanner.Model.Watcher;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class plannerController {

    private final CourseList courseList;
    private final WatcherList watcherList;

    public plannerController(CourseList courseList, WatcherList watcherList) {
        this.courseList = courseList;
        this.watcherList = watcherList;
    }

    @GetMapping("/about")
    public ResponseEntity<?> getAbout() {
        return ResponseEntity.ok(new ApiAboutDTO(
                "TheBestCoursePlannerEver",
                "Richard Xiong and Deng Chen"
        ));
    }

    @GetMapping("/dump-model")
    public void getDumpModel() {
        courseList.print();
    }

    @GetMapping("/departments")
    public ResponseEntity<?> getDepartments() {
        return ResponseEntity.ok(courseList.getDepartments().stream()
                .map(ApiDepartmentDTO::new)
                .toList());
    }

    @GetMapping("/departments/{deptId}/courses")
    public ResponseEntity<?> getCoursesInDepartment(@PathVariable int deptId) {
        if (courseList.getDepartment(deptId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No department with ID: " + deptId);
        }

        return ResponseEntity.ok(courseList.getDepartment(deptId).getCourses().stream()
                .map(ApiCourseDTO::new)
                .toList());
    }

    @GetMapping("/departments/{deptId}/courses/{courseId}/offerings")
    public ResponseEntity<?> getOfferInCourse(@PathVariable int deptId, @PathVariable int courseId) {
        if (courseList.getDepartment(deptId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No department with ID: " + deptId);
        } else if (courseList.getDepartment(deptId).getCourse(courseId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No course with ID: " + courseId + " in department: " + deptId);
        }

        return ResponseEntity.ok(courseList.getDepartment(deptId).getCourse(courseId).getOfferings().stream()
                .map(ApiCourseOfferingDTO::new)
                .toList());
    }

    @GetMapping("/departments/{deptId}/courses/{courseId}/offerings/{offeringId}")
    public ResponseEntity<?> getOfferingById(@PathVariable int deptId,
                                             @PathVariable int courseId,
                                             @PathVariable int offeringId) {
        if (courseList.getDepartment(deptId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No department with ID: " + deptId);
        } else if (courseList.getDepartment(deptId).getCourse(courseId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No course with ID: " + courseId +
                            " in department: " + deptId);
        } else if (courseList.getDepartment(deptId).getCourse(courseId).getOffering(offeringId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No offering with ID: " + offeringId +
                            " in course: " + courseId +
                            " in department: " + deptId);
        }

        return ResponseEntity.ok(courseList.getDepartment(deptId).getCourse(courseId)
                .getOffering(offeringId).getSections()
                .stream()
                .map(ApiOfferingSectionDTO::new)
                .toList());
    }

    @GetMapping("/stats/students-per-semester")
    public ResponseEntity<?> studentsPerSemester(@RequestParam int deptId) {
        Department department = courseList.getDepartment(deptId);
        Map<Integer, Integer> enrollmentTotals = department.getTotalEnrollmentPerSemester();
        List<ApiGraphDataPointDTO> graphDataPoints = enrollmentTotals.entrySet().stream()
                .map(entry -> new ApiGraphDataPointDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(graphDataPoints);
    }

    @PostMapping("/addoffering")
    public ResponseEntity<?> addOffering(@RequestBody ApiOfferingDataDTO newOffering) {
        Watcher watcher = watcherList.getWatcher(newOffering.subjectName, newOffering.catalogNumber);
        if (watcher != null) {
            watcher.addEvent(courseList.addCourse(newOffering));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("New offering added successfully.");
    }

    @GetMapping("/watchers")
    public ResponseEntity<?> getWatchers() {
        return ResponseEntity.ok(watcherList.getWatchers().stream()
                .map(ApiWatcherDTO::new)
                .toList());
    }

    @PostMapping("/watchers")
    public ResponseEntity<?> addWatcher(@RequestBody ApiWatcherCreateDTO watcher) {
        watcherList.addWatcher(courseList, watcher);
        return ResponseEntity.status(HttpStatus.CREATED).body("New watcher added successfully.");
    }

    @GetMapping("/watchers/{watcherId}")
    public ResponseEntity<?> getWatcher(@PathVariable int watcherId) {
        if (watcherList.getWatcher(watcherId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No watcher with ID: " + watcherId);
        }

        return ResponseEntity.ok(watcherList.getWatcher(watcherId).getEvents());
    }

    @DeleteMapping("/watchers/{watcherId}")
    public ResponseEntity<?> deleteWatcher(@PathVariable int watcherId) {
        if (watcherList.deleteWatcher(watcherId)) {
            return ResponseEntity.status(HttpStatus.OK).body("Deleted offering with id " + watcherId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No watcher with ID: " + watcherId);
        }
    }
}
