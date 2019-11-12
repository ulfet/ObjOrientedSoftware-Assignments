package Q7;

public class ConstantNode implements Node
{
	double value;
	
	public ConstantNode(double value)
	{
		this.value = value;
	}
	
	public double evaluate()
	{
		return value;
	}
}