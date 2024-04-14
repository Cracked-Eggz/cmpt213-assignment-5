package CoursePlanner.Controller;

import org.springframework.web.bind.annotation.*;

import CoursePlanner.Model.CourseList;
import CoursePlanner.AllApiDtoClasses.*;

@RestController
@RequestMapping("/api")
public class plannerController {

    @GetMapping("/about")
    public ApiAboutDTO getAbout() {
        return new ApiAboutDTO("TheBestCoursePlannerEver", "Richard Xiong and Deng Chen");
    }

    @GetMapping("/dump-model")
    public void getDumpModel() {
        CourseList courseList = CourseList.hardCodedCreate();
        courseList.printAllCourses();
    }

    @GetMapping("/api/department")
    public ApiDepartmentDTO getDepartment() {
        return new ApiDepartmentDTO();
    }
}
