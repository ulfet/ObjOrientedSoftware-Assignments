package Q8;

import Q7.ConstantNode;
import Q7.Node;
import Q7.OperatorNode;
import Q7.PlusOperator;

public class NodeFactory {
	public Node createNewNodeInstanceFromToken(String token)
	{
		if (token == "+")
			return new OperatorNode(new PlusOperator(), null, null);
		else if (token == "42") {
			return new ConstantNode(42);
		}
		// and so on...
		
		return new ConstantNode(0);
	}
}
