package CoursePlanner.Model;

import java.util.List;
import java.util.ArrayList;

public class Offering {
    private final int semester;
    private final String location;
    private final List<String> instructors;
    private final List<Class> classes;

    public Offering(String[] courseDetails) {
        this.classes = new ArrayList<>();
        this.instructors = new ArrayList<>();
        for (String dataCol: courseDetails) {
            if (dataCol == null) {
                System.out.print("Incorrect array size in Course(String[] courseDetails){...}");
                System.exit(1); // abnormal status
            }
        }

        this.semester = Integer.parseInt(courseDetails[0]);
        this.location = courseDetails[3];
        instructors.add(courseDetails[6]);
        classes.add(new Class(courseDetails));
    }

public void merge(Offering offering) {
    for (String instructor : offering.getInstructors()) {
        if (!this.instructors.contains(instructor)) {
            this.instructors.add(instructor);
        }
    }
    List<Class> classesToAdd = new ArrayList<>();
    for (Class newClass : offering.getClasses()) {
        boolean exists = false;
        for (Class aClass : this.classes) {
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
    this.classes.addAll(classesToAdd);
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
        System.out.println("    "+ semester + " in " + location + " by " + String.join(", ",instructors));
        assert !classes.isEmpty();
        for(Class aclass: classes) {
            aclass.print();
        }
    }

    @Override
    public boolean equals(Object offering) {
        if (!(offering instanceof Offering)) {
            return false;
        } else {
            return location.equals(((Offering) offering).getLocation());
        }
    }
}
