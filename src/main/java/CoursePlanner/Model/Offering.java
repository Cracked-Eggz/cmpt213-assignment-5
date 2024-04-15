package CoursePlanner.Model;

import java.util.*;

public class Offering {
    private int offeringId;
    private final Semester semester;
    private final String location;
    private final Set<String> instructors;
    private final List<Section> sections;

    public Offering(int id, ArrayList<String> courseDetails) {
        assert (courseDetails.size() == 8);
        this.offeringId = id;
        this.sections = new ArrayList<>();

        this.semester = new Semester(courseDetails.get(0));
        this.location = courseDetails.get(3);
        List<String> instructors = Arrays.asList(courseDetails.get(6).split(","));
        this.instructors = new HashSet<>(instructors);
        sections.add(new Section(courseDetails));
    }

    public int getOfferingId() {
        return offeringId;
    }

    public int getSemester() {
        return semester.getAsInt();
    }

    public int getYear() {
        return semester.getYear();
    }

    public String getTerm() {
        return semester.getTerm();
    }

    public String getLocation() {
        return location;
    }

    public Set<String> getInstructors() {
        return instructors;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void merge(Offering offering) {
        // sets automatically maintain uniqueness
        instructors.addAll(offering.getInstructors());

        for (Section newSection : offering.getSections()) {
            boolean merged = false;
            for (Section section : sections) {
                if (section.equals(newSection)) {
                    section.merge(newSection);
                    merged = true;
                    break;
                }
            }
            if (!merged) {
                sections.add(newSection);
            }
        }
        sections.sort(Comparator.comparing(Section::getComponentCode));
    }

    public void print() {
        System.out.printf("%8s in %s by %s%n", semester, location, String.join(", ", instructors));
        assert !sections.isEmpty();
        for (Section aSection : sections) {
            aSection.print();
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
