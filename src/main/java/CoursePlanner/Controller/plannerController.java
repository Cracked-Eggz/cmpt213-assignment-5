package CoursePlanner.Controller;
import CoursePlanner.Model.Course;
import CoursePlanner.Model.CourseList;
import org.springframework.web.bind.annotation.*;
import CoursePlanner.AllApiDtoClasses.ApiAboutDTO;

@RestController
@RequestMapping("/api")
public class plannerController {

    @GetMapping("/about")
    public ApiAboutDTO getAbout() {
        return new ApiAboutDTO("TheBestCoursePlannerEver", "Deng Chen and Richard Xiong");
    }

    @GetMapping("/dump-model")
    public void getDumpModel() {

        CourseList courseList = CourseList.createTEST();
        courseList.printAllCourses();
    }


}
