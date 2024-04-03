package CoursePlanner.Controller;
import org.springframework.web.bind.annotation.*;
import CoursePlanner.AllApiDtoClasses.ApiAboutDTO;

@RestController
@RequestMapping("/api")
public class plannerController {

    @GetMapping("/about")
    public ApiAboutDTO haha() {
        return new ApiAboutDTO("TheBestCoursePlannerEver", "Deng Chen and Richard Xiong");


    }

}
