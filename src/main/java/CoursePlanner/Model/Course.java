package CoursePlanner.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Objects;

public class Course {
    private final String subject;
    private final String catalogNumber;
    private final List<Offering> offerings;

    public Course(ArrayList<String> courseDetails) {
        this.offerings = new ArrayList<>();
        for (String dataCol: courseDetails) {
            if (dataCol == null) {
                System.out.print("Incorrect array size passed in Course constructor");
                System.exit(1); // abnormal status
            }
        }

        this.subject = courseDetails.get(1);
        this.catalogNumber = courseDetails.get(2);
        offerings.add(new Offering(courseDetails));
    }

    public String getSubject() {
        return subject;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public List<Offering> getOfferings() {
        return offerings;
    }

    public void merge(Course course) {
        List<Offering> offeringsToRemove = new ArrayList<>();

        for (Offering offering : offerings) {
            for (Offering newOffering : course.getOfferings()) {
                if (offering.equals(newOffering)) {
                    offering.merge(newOffering);
                    offeringsToRemove.add(newOffering);
                }
            }
        }
        course.getOfferings().removeAll(offeringsToRemove);
        offerings.addAll(course.getOfferings());
        offerings.sort(Comparator.comparingInt(Offering::getSemester));
    }

    public void print() {
        System.out.println(subject + " " + catalogNumber);
        for (Offering offering : offerings) {
            offering.print();
        }
    }

    @Override
    public boolean equals(Object course) {
        if (!(course instanceof Course)) {
            return false;
        } else {
            return Objects.equals(subject, ((Course) course).getSubject()) &&
                    Objects.equals(catalogNumber, ((Course) course).getCatalogNumber());
        }
    }

    // Compare subjects first, and if subjects are equal, then compare catalog numbers
    public static class CourseComparator implements Comparator<Course> {
        @Override
        public int compare(Course c1, Course c2) {
            int subjectComparison = c1.getSubject().compareTo(c2.getSubject());
            if (subjectComparison != 0) {
                return subjectComparison;
            } else {
                return c1.getCatalogNumber().compareTo(c2.getCatalogNumber());
            }
        }
    }
}
