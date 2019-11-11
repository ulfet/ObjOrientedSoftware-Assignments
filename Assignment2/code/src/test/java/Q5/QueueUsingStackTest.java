package Q5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QueueUsingStackTest {
	private IQueue q;
	
	@BeforeEach
	public void init() {
		q = (IQueue) new QueueUsingStack();
	}
	
	
	@Test
	public void frontTest() {
		Integer e = 10;
		
		q.insert(e);
		
		Integer f = q.front();
		assertEquals(e, f);
	}
	
	@Test
	public void removeTest() {
		Integer e = 10;
		
		q.insert(e);
		
		Integer r = q.remove();
		assertEquals(e, r);
		assertEquals(true, q.isEmpty());
	}
	
	@Test
	public void insertTest() {
		int testSize = 10;
		for(int i=testSize-1; i>= 0; i--) {
			q.insert(i);
		}
		
		for(int i=0; i<testSize; i++) {
			Integer e = q.remove();
			assertEquals(i, e);
		}
	}
	
	@Test
	public void getSizeTest() {
		int testSize = 10;
		for(int i=0; i<testSize; i++) {
			q.insert(i);
		}
		
		assertEquals(testSize, q.getSize());
	}
	
	@Test
	public void isEmptyTest() {
		assertEquals(true, q.isEmpty());
		
		int testSize = 10;
		for(int i=0; i<testSize; i++) {
			q.insert(i);
		}
		
		for(int i=0; i<testSize; i++) {
			q.remove();
		}
		
		assertEquals(true, q.isEmpty());
	}
}
