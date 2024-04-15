package CoursePlanner.Model;

import java.util.ArrayList;

public class Section {
    private int enrollCap;
    private int enrollTotal;
    private final String componentCode;

    public Section(ArrayList<String> courseDetails) {
        assert (courseDetails.size() == 8);

        this.enrollCap = Integer.parseInt(courseDetails.get(4));
        this.enrollTotal = Integer.parseInt(courseDetails.get(5));
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

    public void merge(Section section) {
        this.enrollCap += section.getEnrollCap();
        this.enrollTotal += section.getEnrollTotal();
    }

    public void print() {
        System.out.printf("%8sType=%s, Enrollment=%d/%d%n", " ", componentCode, enrollTotal, enrollCap);
    }

    @Override
    public boolean equals(Object aClass) {
        if (!(aClass instanceof Section)) {
            return false;
        } else {
            return getComponentCode().equals(((Section) aClass).getComponentCode());
        }
    }
}
