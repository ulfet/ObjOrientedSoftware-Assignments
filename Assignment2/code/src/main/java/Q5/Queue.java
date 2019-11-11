package Q5;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

public class Queue implements IQueue {
	
	List<Integer> list;
	
	public Queue() {
		list = new ArrayList<Integer>();
	}
	
	public Integer front() {
		if (list.size() == 0) {
			throw new RuntimeErrorException(null);
		}
		return list.get(0);
	}
	
	public Integer remove() {
		if (list.size() == 0) {
			throw new RuntimeErrorException(null);
		}
		
		Integer frontElement = list.get(0);
		list.remove(0);
		
		return frontElement;
	}
	
	public Integer getSize() {
		return list.size();
	}

	@Override
	public void insert(Integer e) {
		list.add(0, e);
	}
	
	public boolean isEmpty() {
		return (list.size() == 0);
	}
}
