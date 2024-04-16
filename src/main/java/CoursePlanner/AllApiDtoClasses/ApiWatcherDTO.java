package CoursePlanner.AllApiDtoClasses;

import CoursePlanner.Model.Watcher;

import java.util.List;

public class ApiWatcherDTO {
    public long id;
    public ApiDepartmentDTO department;
    public ApiCourseDTO course;
    public List<String> events;

    public ApiWatcherDTO(Watcher watcher) {
        this.id = watcher.getWatcherId();
        this.department = new ApiDepartmentDTO(watcher.getDepartment());
        this.course = new ApiCourseDTO(watcher.getCourse());
        this.events = watcher.getEvents();
    }
}
