package CoursePlanner.Model;

public class Semester {
    private final int year;
    private final int semester;
    // Only three cases possible for semester: 1 = Spring, 4=Summer, 7=Fall

    public Semester(String semesterString) {
        this.year = Integer.parseInt(semesterString.substring(0,3));
        this.semester = Integer.parseInt(semesterString.substring(semesterString.length() - 1));
    }

    public int getYear() {
        return year;
    }

    public int getSemester() {
        return semester;
    }

    public int getAsInt() {
        // String + int is already = String, so no need for both String + String
        String semsterString = String.valueOf(year) + semester;
        return Integer.parseInt(semsterString);
    }

    @Override
    public String toString() {
        return String.valueOf(year) + semester;
    }
}
