package Q5;
import javax.management.RuntimeErrorException;
import Q5.Stack;

public class QueueUsingStack extends Stack implements IQueue {
	
	private Stack helperStack;
	
	QueueUsingStack() {
		super();
	}
	
	/* START REDEFINING
	 * note that, here, we are REDEFINING functions of Stack, just for the sake of Implementation inheritance
	 */
	
	public Integer top() {
		if (list.size() == 0)
			throw new RuntimeErrorException(null);
		return list.get(list.size() - 1);
	}
	
	public Integer pop() {
		if (list.size() == 0)
			throw new RuntimeErrorException(null);
		Integer topElement = this.top();
		list.remove(list.size() - 1);
		return topElement;
	}
	
	public void push(Integer e) {
		list.add(e);
	}
	
	public boolean isEmpty() {
		return (list.size() == 0);
	}
	
	public Integer getSize() {
		return list.size();
	}
	/* END REDEFINING
	 * although those are the same definitions, this is the example we come up with
	 */

	@Override
	public Integer front() {
		if (this.getSize() == 0)
			throw new RuntimeErrorException(null);
		helperStack = new Stack();
		while (this.getSize() != 1) {
			helperStack.push(this.pop());
		}
		
		// not removed, only retrieved
		Integer frontElem = this.top();
		while (! helperStack.isEmpty()) {
			this.push(helperStack.pop());
		}
		return frontElem;
	}

	@Override
	public Integer remove() {
		if (this.getSize() == 0)
			throw new RuntimeErrorException(null);
		helperStack = new Stack();
		while (this.getSize() != 1) {
			helperStack.push(this.pop());
		}
		// REMOVED element
		Integer frontElem = this.pop();
		while (! helperStack.isEmpty()) {
			this.push(helperStack.pop());
		}
		return frontElem;
	}

	@Override
	public void insert(Integer e) {
		helperStack = new Stack();
		while( ! this.isEmpty()) {
			helperStack.push(this.pop());
		}
		this.push(e);
		while(! helperStack.isEmpty()) {
			this.push(helperStack.pop());
		}
	}
}
