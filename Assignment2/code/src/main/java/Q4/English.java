package Q4;

public class English extends Language
{
    String letter = "A";
    int number = 1;

    void setLevel(String l, int n)
    {
        letter = l;
        number = n;
    }

    String getLevel()
    {
        return letter+number;
    }
}