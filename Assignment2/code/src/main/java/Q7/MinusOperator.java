package Q7;

public class MinusOperator implements Operator
{
	public MinusOperator()
	{
		
	}
	
	public String getName()
	{
		return "-";
	}
	
	public double evaluate(double a, double b)
	{
		return a - b;
	}
}