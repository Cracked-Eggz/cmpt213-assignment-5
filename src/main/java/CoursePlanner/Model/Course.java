package CoursePlanner.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course {
    private final String subject;
    private final String catalogNumber;
    private List<Offering> offerings;

    public Course(String[] courseDetails) {
        this.offerings = new ArrayList<>();
        for (String dataCol: courseDetails) {
            if (dataCol == null) {
                System.out.print("Incorrect array size passed in Course constructor");
                System.exit(1); // abnormal status
            }
        }
        this.subject = courseDetails[1];
        this.catalogNumber = courseDetails[2];

        //this.catalogNumber = Integer.parseInt(courseDetails[2]);
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

//    public void merge(Course course)  {
//        for (Offering offering : offerings) {
//            for (Offering newOffering : course.getOfferings()) {
//                if (offering.equals(newOffering)) {
//                    offering.merge(newOffering);
//                    course.getOfferings().remove(newOffering);
//                }
//            }
//        }
//        offerings.addAll(course.getOfferings());
//    }

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
    }

    public void print() {
        System.out.println(subject + catalogNumber);
        for (Offering offering : offerings) {
            offering.print();
        }
    }

    @Override
    public boolean equals(Object course) {
        if (!(course instanceof Course)) {
            return false;
        } else {
            return Objects.equals(getSubject(), ((Course) course).getSubject()) &&
                    Objects.equals(getCatalogNumber(), ((Course) course).getCatalogNumber());
        }
    }
}
