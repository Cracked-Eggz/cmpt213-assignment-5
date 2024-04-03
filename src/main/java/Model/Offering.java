package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Offering {
    private final int semester;
    private final String location;
    private List<String> instructors;
    private List<Class> classes;

    public Offering(String[] courseDetails) {
        this.classes = new ArrayList<>();
        for (String dataCol: courseDetails) {
            if (dataCol == null) {
                System.out.print("Incorrect array size in Course(String[] courseDetails){...}");
                System.exit(1); // abnormal status
            }
        }
        this.semester = Integer.parseInt(courseDetails[0]);
        this.location = courseDetails[3];
        this.instructors = Collections.singletonList(courseDetails[6]);
    }

    public void merge(Offering offering){
        for (String instructor : instructors) {
            offering.getInstructors().removeIf(instructor::equals);
        }
        instructors.addAll(offering.getInstructors());

        for (Class aClass : classes) {
            for (Class newClass : offering.getClasses()) {
                if (aClass.equals(newClass)) {
                    aClass.merge(newClass);
                    offering.getClasses().remove(newClass);
                }
            }
        }
        classes.addAll(offering.getClasses());
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
