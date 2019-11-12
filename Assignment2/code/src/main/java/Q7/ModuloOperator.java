package Q7;

public class ModuloOperator implements Operator
{
	public ModuloOperator()
	{
		
	}
	
	public String getName()
	{
		return "%";
	}
	
	public double evaluate(double a, double b)
	{
		if ((int)b != b)
		{
			throw new IllegalArgumentException();
		}
		
		return a % b;
	}
}