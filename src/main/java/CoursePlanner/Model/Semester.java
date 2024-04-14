package CoursePlanner.Model;

public class Semester {
    private final String year;
    private final String semester;

    public Semester(String termCode) {
        assert (termCode.length() == 4);
        this.year = termCode.substring(0, 3);
        this.semester = termCode.substring(termCode.length() - 1);
    }

    // Ex. For semester XYZA:
    // Year = 1900 + 100*X + 10*Y + 1*Z;
    // Semester (A) = {1=Spring, 4=Summer, 7=Fall}
    // Ex: Term code 1147 = 2014 Fall
    public int getYear() {
        return 1900 +
                100 * Integer.parseInt(year.substring(0, 1)) +
                10 * Integer.parseInt(year.substring(1, 2)) +
                Integer.parseInt(year.substring(2));
    }

    public String getSemester() {
        return switch (semester) {
            case "1" -> "Spring";
            case "4" -> "Summer";
            case "7" -> "Fall";
            default -> "Illegal Semester";
        };
    }

    public int getAsInt() {
        String semsterString = year + semester;
        return Integer.parseInt(semsterString);
    }

    @Override
    public String toString() {
        return year + semester;
    }
}
