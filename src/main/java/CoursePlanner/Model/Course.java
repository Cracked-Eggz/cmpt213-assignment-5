package CoursePlanner.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Objects;

public class Course {
    private final String department;
    private final String catalogNumber;
    private final List<Offering> offerings;

    public Course(ArrayList<String> courseDetails) {
        assert (courseDetails.size() == 8);
        this.offerings = new ArrayList<>();

        this.department = courseDetails.get(1);
        this.catalogNumber = courseDetails.get(2);
        offerings.add(new Offering(courseDetails));
    }

    public String getDepartment() {
        return department;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public List<Offering> getOfferings() {
        return offerings;
    }

    public void merge(Course course) {
        for (Offering newOffering : course.getOfferings()) {
            boolean merged = false;
            for (Offering offering : offerings) {
                if (offering.equals(newOffering)) {
                    offering.merge(newOffering);
                    merged = true;
                    break;
                }
            }
            if (!merged) {
                offerings.add(newOffering);
            }
        }
        offerings.sort(Comparator.comparingInt(Offering::getSemesterCode));
    }

    public void print() {
        System.out.println(department + " " + catalogNumber);
        for (Offering offering : offerings) {
            offering.print();
        }
    }

    @Override
    public boolean equals(Object course) {
        if (!(course instanceof Course)) {
            return false;
        } else {
            return Objects.equals(department, ((Course) course).getDepartment()) &&
                    Objects.equals(catalogNumber, ((Course) course).getCatalogNumber());
        }
    }

    // Compare subjects first, and if subjects are equal, then compare catalog numbers
    public static class CourseComparator implements Comparator<Course> {
        @Override
        public int compare(Course c1, Course c2) {
            int subjectComparison = c1.getDepartment().compareTo(c2.getDepartment());
            if (subjectComparison != 0) {
                return subjectComparison;
            } else {
                return c1.getCatalogNumber().compareTo(c2.getCatalogNumber());
            }
        }
    }
}
