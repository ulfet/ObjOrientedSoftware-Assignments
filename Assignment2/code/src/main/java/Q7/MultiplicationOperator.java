package Q7;

public class MultiplicationOperator implements Operator
{
	public MultiplicationOperator()
	{
		
	}
	
	public String getName()
	{
		return "*";
	}
	
	public double evaluate(double a, double b)
	{
		return a * b;
	}
}