package Q4;

public class GermanStudent extends LanguageStudent
{
    private String letter = "A";
    private int number = 1;

    public void setLevel(String l, int n)
    {
        letter = l;
        number = n;
    }

    public String getLevel()
    {
        return letter+number;
    }
}
