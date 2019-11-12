package Q7;

public class PlusOperator implements Operator
{
	public PlusOperator()
	{
		
	}
	
	public String getName()
	{
		return "+";
	}
	
	public double evaluate(double a, double b)
	{
		return a + b;
	}
}