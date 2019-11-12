package Q7;

public class DivisionOperator implements Operator
{
	public DivisionOperator()
	{
		
	}
	
	public String getName()
	{
		return "/";
	}
	
	public double evaluate(double a, double b)
	{
		return a / b;
	}
}