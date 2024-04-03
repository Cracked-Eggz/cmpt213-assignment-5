package Controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class plannerController {
    @PostMapping("/hello")
    public String getAuthorName() {
        return "Deng Chen";
    }
}
