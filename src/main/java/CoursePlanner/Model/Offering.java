package CoursePlanner.Model;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Offering {
    private final Semester semester;
    private final String location;
    private final List<String> instructors;
    private final List<Class> classes;

    public Offering(ArrayList<String> courseDetails) {
        this.classes = new ArrayList<>();
        for (String dataCol : courseDetails) {
            if (dataCol == null) {
                System.out.print("Incorrect array size in Course(String[] courseDetails){...}");
                System.exit(1); // abnormal status
            }
        }

        this.semester = new Semester(courseDetails.get(0));
        this.location = courseDetails.get(3);
        this.instructors = Arrays.asList(courseDetails.get(6).split(","));
        classes.add(new Class(courseDetails));
    }

    public void merge(Offering offering) {
        for (String instructor : offering.getInstructors()) {
            if (!instructors.contains(instructor)) {
                instructors.add(instructor);
            }
        }
        List<Class> classesToAdd = new ArrayList<>();
        for (Class newClass : offering.getClasses()) {
            boolean exists = false;
            for (Class aClass : classes) {
                if (aClass.equals(newClass)) {
                    aClass.merge(newClass);
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                classesToAdd.add(newClass);
            }
        }
        classes.addAll(classesToAdd);
        classes.sort(Comparator.comparing(Class::getComponentCode));
    }

    public int getSemester() {
        return semester.getAsInt();
    }

    public String getLocation() {
        return location;
    }

    public List<String> getInstructors() {
        return instructors;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void print() {
        System.out.printf("%8s in %s by %s%n", semester, location, String.join(", ", instructors));
        assert !classes.isEmpty();
        for (Class aclass : classes) {
            aclass.print();
        }
    }

    @Override
    public boolean equals(Object offering) {
        if (!(offering instanceof Offering)) {
            return false;
        } else if (semester.getAsInt() != ((Offering) offering).getSemester()) {
            return false;
        } else {
            return location.equals(((Offering) offering).getLocation());
        }
    }
}
