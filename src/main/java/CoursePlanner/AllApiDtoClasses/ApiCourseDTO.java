package CoursePlanner.AllApiDtoClasses;

import CoursePlanner.Model.Course;

public class ApiCourseDTO {
    public long courseId;
    public String catalogNumber;

    public ApiCourseDTO(Course course) {
        this.courseId = course.getCourseId();
        this.catalogNumber = course.getCatalogNumber();
    }
}
