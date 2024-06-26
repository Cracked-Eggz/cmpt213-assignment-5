package CoursePlanner.Model;

import java.util.*;
import java.util.stream.Collectors;

public class Course {
    private final long courseId;
    private final String department;
    private final String catalogNumber;
    private final List<Offering> offerings;



    public Course(long courseId, long offeringId, ArrayList<String> courseDetails) {
        assert (courseDetails.size() == 8);
        this.courseId = courseId;
        this.offerings = new ArrayList<>();

        this.department = courseDetails.get(1);
        this.catalogNumber = courseDetails.get(2);
        offerings.add(new Offering(offeringId, courseDetails));
    }

    public void getTotalEnrollmentPerSemester(Map<Integer, Integer> totalEnrollments) {
        Map<Integer, Integer> newEnrollments = offerings.stream()
                .collect(Collectors.groupingBy(Offering::getSemesterCode,
                        Collectors.summingInt(Offering::getOfferingTotal)));

        for (Map.Entry<Integer, Integer> entry : newEnrollments.entrySet()) {
            int semesterCode = entry.getKey();
            int newEnrollment = entry.getValue();

            if (totalEnrollments.containsKey(semesterCode)) {
                totalEnrollments.put(semesterCode, totalEnrollments.get(semesterCode) + newEnrollment);
            } else {
                totalEnrollments.put(semesterCode, newEnrollment);
            }
        }
    }

    public long getCourseId() {
        return courseId;
    }

    public String getDepartment() {
        return department;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public Offering getOffering(long id) {
        for (Offering offering : offerings) {
            if (offering.getOfferingId() == id) {
                return offering;
            }
        }
        return null;
    }

    public List<Offering> getOfferings() {
        return offerings;
    }

    public boolean merge(Course course) {
        // there is only one offering in the new course
        Offering newOffering = course.getOfferings().get(0);

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

        offerings.sort(Comparator.comparingInt(Offering::getSemester));
        return merged;
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
