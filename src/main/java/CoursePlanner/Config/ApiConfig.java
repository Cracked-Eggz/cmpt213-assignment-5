package CoursePlanner.Config;

import CoursePlanner.Model.CourseList;
import CoursePlanner.Model.WatcherList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Bean
    public CourseList courseList() {
        return CourseList.hardCodedCreate();
    }

    @Bean
    public WatcherList watcherList() {
        return WatcherList.makeWatcherList();
    }
}
