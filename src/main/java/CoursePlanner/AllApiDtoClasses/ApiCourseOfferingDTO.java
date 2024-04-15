package CoursePlanner.AllApiDtoClasses;

import CoursePlanner.Model.Offering;

public class ApiCourseOfferingDTO {
    public long courseOfferingId;
    public String location;
    public String instructors;
    public String term;
    public long semesterCode;
    public int year;

    public ApiCourseOfferingDTO(Offering offering) {
        this.courseOfferingId = offering.getOfferingId();
        this.location = offering.getLocation();
        this.instructors = String.join(", ", offering.getInstructors());
        this.term = offering.getTerm();
        this.semesterCode = offering.getSemester();
        this.year = offering.getYear();
    }
}
