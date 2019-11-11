package Q5;
import javax.management.RuntimeErrorException;

import Q5.Stack;

public class QueueUsingStack implements IQueue{
	
	private Stack mainStack;
	private Stack helperStack;
	
	QueueUsingStack() {
		mainStack = new Stack();
		//helperStack = new Stack();
	}

	@Override
	public Integer front() {
		if (mainStack.getSize() == 0)
			throw new RuntimeErrorException(null);
		
		helperStack = new Stack();
		while (mainStack.getSize() != 1) {
			helperStack.push(mainStack.pop());
		}
		
		// not removed, only retrieved
		Integer frontElem = mainStack.top();
		
		while (! helperStack.isEmpty()) {
			mainStack.push(helperStack.pop());
		}
		
		return frontElem;
	}

	@Override
	public Integer remove() {
		if (mainStack.getSize() == 0)
			throw new RuntimeErrorException(null);
		
		helperStack = new Stack();
		while (mainStack.getSize() != 1) {
			helperStack.push(mainStack.pop());
		}
		
		// REMOVED element
		Integer frontElem = mainStack.pop();
		
		while (! helperStack.isEmpty()) {
			mainStack.push(helperStack.pop());
		}
		
		return frontElem;
	}

	@Override
	public void insert(Integer e) {
		helperStack = new Stack();
		
		while( ! mainStack.isEmpty()) {
			helperStack.push(mainStack.pop());
		}
		
		mainStack.push(e);
		
		while(! helperStack.isEmpty()) {
			mainStack.push(helperStack.pop());
		}
	}

	@Override
	public Integer getSize() {
		return mainStack.getSize();
	}

	@Override
	public boolean isEmpty() {
		return (mainStack.isEmpty());
	}
	
}
