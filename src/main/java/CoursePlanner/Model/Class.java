package CoursePlanner.Model;

import java.util.ArrayList;

public class Class {
    private int enrollCap;
    private int enrollTotal;
    private final String componentCode;

    public Class(ArrayList<String> courseDetails) {
        assert (courseDetails.size() == 8);

        this.enrollCap = Integer.parseInt(courseDetails.get(5));
        this.enrollTotal = Integer.parseInt(courseDetails.get(6));
        this.componentCode = courseDetails.get(7);
    }

    public int getEnrollCap() {
        return enrollCap;
    }

    public int getEnrollTotal() {
        return enrollTotal;
    }

    public String getComponentCode() {
        return componentCode;
    }

    public void merge(Class aClass) {
        this.enrollCap += aClass.getEnrollCap();
        this.enrollTotal += aClass.getEnrollTotal();
    }

    public void print() {
        System.out.printf("%8sType=%s, Enrollment=%d/%d%n", " ", componentCode, enrollTotal, enrollCap);
    }

    @Override
    public boolean equals(Object aClass) {
        if (!(aClass instanceof Class)) {
            return false;
        } else {
            return getComponentCode().equals(((Class) aClass).getComponentCode());
        }
    }
}
