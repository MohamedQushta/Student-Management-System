public interface CourseStrategy {
    public void addCourse(String courseId, String userEmail);
    public void showAvailableCourses(String userEmail);
    public void removeCourse(String userEmail, String courseId);
    public void viewCurrCourses(String userEmail);
}
