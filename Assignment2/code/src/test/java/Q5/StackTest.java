package Q5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StackTest{
	private Stack s;
	
	@BeforeEach
	public void init() {
		s = new Stack();
	}
	
	@Test
	public void topTest() {
		Integer e = 10;
		s.push(e);
		
		Integer t = s.top();
		assertEquals(t, e);
	}
	
	@Test
	public void popTest() {
		Integer e = 10;
		s.push(e);
		
		Integer t = s.pop();
		assertEquals(t, e);
		assertEquals(true, s.isEmpty());
	}
	
	@Test
	public void pushTest() {
		int testSize = 10;
		for (int i=0; i<testSize; i++)
			s.push(i);
		
		for (int i=testSize-1; i>=0; i--) {
			Integer e = s.pop();
			assertEquals(i, e);
		}
	}
	
	@Test
	public void isEmptyTest() {
		int testSize = 10;
		for (int i=0; i<testSize; i++)
			s.push(i);
		
		for (int i=0; i<testSize; i++)
			s.pop();
		
		assertEquals(true, s.isEmpty());
	}
	
	@Test
	public void getSizeTest() {
		int testSize = 10;
		for (int i=0; i<testSize; i++)
			s.push(i);
		
		assertEquals(testSize, s.getSize());
	}
}
