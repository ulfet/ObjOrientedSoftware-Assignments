package Q7;

public class ParsingContext
{
	Operator[] operators;
	
	public ParsingContext(Operator[] operators)
	{
		this.operators = operators;
	}
	
	public Node parseInput(String input)
	{
		return new ConstantNode(42);
	}
}