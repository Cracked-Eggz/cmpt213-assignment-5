package CoursePlanner.Model;

import CoursePlanner.AllApiDtoClasses.ApiWatcherCreateDTO;

import java.util.ArrayList;
import java.util.List;

public class Watcher {
    private final long watcherId;
    private final Department department;
    private final Course course;
    private final List<String> events;

    public Watcher(long id, CourseList courseList, ApiWatcherCreateDTO watcherCreate) {
        this.watcherId = id;
        this.department = courseList.getDepartment(watcherCreate.deptId);
        this.course = courseList.getDepartment(watcherCreate.deptId).getCourse(watcherCreate.courseId);
        events = new ArrayList<>();
    }

    public long getWatcherId() {
        return watcherId;
    }

    public Department getDepartment() {
        return department;
    }

    public String getDepartmentName() {
        return department.getName();
    }

    public Course getCourse() {
        return course;
    }

    public String getCourseNumber() {
        return course.getCatalogNumber();
    }

    public List<String> getEvents() {
        return events;
    }

    public void addEvent(String event) {
        events.add(event);
    }
}
