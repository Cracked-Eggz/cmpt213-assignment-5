package CoursePlanner.Controller;

import CoursePlanner.Model.Department;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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

    @ModelAttribute("courseList")
    public CourseList getCourseList(Model model) {
        if (model.containsAttribute("courseList")) {
            return (CourseList) model.getAttribute("courseList");
        }
        return CourseList.hardCodedCreate();
    }

    @ModelAttribute("watcherList")
    public WatcherList getWatcherList(Model model) {
        if (model.containsAttribute("watcherList")) {
            return (WatcherList) model.getAttribute("watcherList");
        }
        return new WatcherList();
    }

    @GetMapping("/about")
    public ResponseEntity<?> getAbout() {
        return ResponseEntity.ok(new ApiAboutDTO(
                "TheBestCoursePlannerEver",
                "Richard Xiong and Deng Chen"
        ));
    }

    @GetMapping("/dump-model")
    public void getDumpModel(CourseList courseList) {
        courseList.print();
    }

    @GetMapping("/departments")
    public ResponseEntity<?> getDepartments(CourseList courseList) {
        return ResponseEntity.ok(courseList.getDepartments().stream()
                .map(ApiDepartmentDTO::new)
                .toList());
    }

    @GetMapping("/departments/{deptId}/courses")
    public ResponseEntity<?> getCoursesInDepartment(@PathVariable int deptId, CourseList courseList) {
        if (courseList.getDepartment(deptId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No department with ID: " + deptId);
        }

        return ResponseEntity.ok(courseList.getDepartment(deptId).getCourses().stream()
                .map(ApiCourseDTO::new)
                .toList());
    }

    @GetMapping("/departments/{deptId}/courses/{courseId}/offerings")
    public ResponseEntity<?> getOfferInCourse(@PathVariable int deptId, @PathVariable int courseId,
                                              CourseList courseList) {
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
    public ResponseEntity<?> getOfferingById(@PathVariable int deptId, @PathVariable int courseId,
                                             @PathVariable int offeringId, CourseList courseList) {
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
    public ResponseEntity<?> studentsPerSemester(@RequestParam int deptId, CourseList courseList) {
        Department department = courseList.getDepartment(deptId);
        Map<Integer, Integer> enrollmentTotals = department.getTotalEnrollmentPerSemester();
        List<ApiGraphDataPointDTO> graphDataPoints = enrollmentTotals.entrySet().stream()
                .map(entry -> new ApiGraphDataPointDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(graphDataPoints);
    }

    @PostMapping("/addoffering")
    public ResponseEntity<?> addOffering(@RequestBody ApiOfferingDataDTO newOffering,
                                         CourseList courseList, WatcherList watcherList) {
        Watcher watcher = watcherList.getWatcher(newOffering.subjectName, newOffering.catalogNumber);
        if (watcher != null) {
            watcher.addEvent(courseList.addCourse(newOffering));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("New offering added successfully.");
    }

    @GetMapping("/watchers")
    public ResponseEntity<?> getWatchers(WatcherList watcherList) {
        return ResponseEntity.ok(watcherList.getWatchers().stream()
                .map(ApiWatcherDTO::new)
                .toList());
    }

    @PostMapping("/watchers")
    public ResponseEntity<?> addWatcher(@RequestBody ApiWatcherCreateDTO watcher,
                                        CourseList courseList, WatcherList watcherList) {
        watcherList.addWatcher(courseList, watcher);
        return ResponseEntity.status(HttpStatus.CREATED).body("New offering added successfully.");
    }

    @GetMapping("/watchers/{watcherId}")
    public ResponseEntity<?> getWatcher(@PathVariable int watcherId, WatcherList watcherList) {
        if (watcherList.getWatcher(watcherId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No watcher with ID: " + watcherId);
        }

        return ResponseEntity.ok(watcherList.getWatcher(watcherId).getEvents());
    }

    @DeleteMapping("/watchers/{watcherId}")
    public ResponseEntity<?> deleteWatcher(@PathVariable int watcherId, WatcherList watcherList) {
        if (watcherList.deleteWatcher(watcherId)) {
            return ResponseEntity.status(HttpStatus.OK).body("Deleted offering with id " + watcherId);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No watcher with ID: " + watcherId);
        }
    }
}
