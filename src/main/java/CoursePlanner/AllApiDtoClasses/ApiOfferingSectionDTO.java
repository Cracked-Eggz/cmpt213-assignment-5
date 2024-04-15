package CoursePlanner.AllApiDtoClasses;

import CoursePlanner.Model.Section;

public class ApiOfferingSectionDTO {
    public String type;
    public int enrollmentCap;
    public int enrollmentTotal;

    public ApiOfferingSectionDTO(Section section) {
        this.type = section.getComponentCode();
        this.enrollmentCap = section.getEnrollCap();
        this.enrollmentTotal = section.getEnrollTotal();
    }
}
