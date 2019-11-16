package Q4;

public class LanguageStudent extends Student
{
    private int numberOfLanguageClasses = 0; //no knowledge of language classes

    public int getLanguageClassesNumber()
    {
        return numberOfLanguageClasses;
    }

    public void setLanguageClassesNumber(int n)
    {
        this.numberOfLanguageClasses = n;
    }
}
