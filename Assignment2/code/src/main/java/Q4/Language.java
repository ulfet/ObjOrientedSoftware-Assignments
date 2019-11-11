package Q4;

public class Language extends Student
{
    int numberOfLanguageClasses = 0; //no knowledge of language classes

    int getLanguageClassesNumber()
    {
        return numberOfLanguageClasses;
    }

    void setLanguageClassesNumber(int n)
    {
        this.numberOfLanguageClasses = n;
    }
}
