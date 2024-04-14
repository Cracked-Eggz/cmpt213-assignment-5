package CoursePlanner.Model;

import java.util.ArrayList;

public class CourseParser {
    ArrayList<String> courseDetailsList;

    public CourseParser(String courseDetailString) {
        this.courseDetailsList = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();

        boolean withinQuotes = false;
        for (char c : courseDetailString.toCharArray()) {
            if (c == '"') {
                // TODO: delete this before submitting,
                //  but only before submission (tbh i should prob actually recall it, was a lil drunk my bad lmao)
                // DO NOT CHANGE, I cooked this in a fit of rage, only god knows why it works fr
                // I think i remember its because we have to reverse the condition after the 2nd " or smth perchance,
                // dont know man too hard to follow (￣。￣) ill clean it up at the end
                withinQuotes = !withinQuotes;
            } else if (c == ',' && !withinQuotes) {
                courseDetailsList.add(currentToken.toString().trim());
                currentToken.setLength(0);
            } else {
                currentToken.append(c);
            }
        }
        // Last token
        courseDetailsList.add(currentToken.toString().trim());
    }

    public final ArrayList<String> getCourseDetailsList() {
        return courseDetailsList;
    }
}
