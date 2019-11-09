package Q5;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

public class Stack {
	List<Integer> list;
	
	Stack() {
		list = new ArrayList<Integer>();
	}
	
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
} 
