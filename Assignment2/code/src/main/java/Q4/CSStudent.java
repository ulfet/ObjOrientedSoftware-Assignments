package Q4;

public class CSStudent extends CourseStudent
{
    private int grade = 1;
    private String course = "CSStudent";

    public void setGrade(int g)
    {
        grade = g;
    }

    public int getGrade()
    {
        return grade;
    }

    public void setCourse(String c)
    {
        course = c;
    }

    public String getCourse()
    {
        return course;
    }
}
