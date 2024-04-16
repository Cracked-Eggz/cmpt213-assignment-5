package CoursePlanner.Model;

import CoursePlanner.AllApiDtoClasses.ApiWatcherCreateDTO;

import java.util.ArrayList;
import java.util.List;

public class Watcher {
    public long watcherId;
    public Department department;
    public Course course;
    public List<String> events;

    public Watcher(long id, CourseList courseList, ApiWatcherCreateDTO watcherCreate) {
        this.watcherId = id;
        this.department = courseList.getDepartment(watcherCreate.deptId);
        this.course = courseList.getDepartment(watcherCreate.deptId).getCourse(watcherCreate.courseId);
        events = new ArrayList<>();
    }

    public void addEvent(String event) {
        events.add(event);
    }
}
