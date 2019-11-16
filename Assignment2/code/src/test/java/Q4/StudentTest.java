package Q4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentTest
{
	private EnglishStudent e;
	private GermanStudent g;
	private CSStudent c;
	private SSEStudent s;
	
	@BeforeEach
	public void init() {
		e = new EnglishStudent();
		g = new GermanStudent();
		c = new CSStudent();
		s = new SSEStudent();
	}

	@Test
	public void uniGerman()
	{
		g.setUni("RWTH");
		assertEquals(g.getUni(), "RWTH", "Uni not set.");
	}

	@Test
	public void idEnglish()
	{
		e.setIdentifier(123);
		assertEquals(e.getIdentifier(), 123, "Id not set.");
	}

	@Test
	public void uniCS()
	{
		c.setUni("RW TH");
		assertEquals(c.getUni(), "RW TH", "Uni not set.");
	}

	@Test
	public void idSSE()
	{
		s.setIdentifier(123);
		assertEquals(s.getIdentifier(), 123, "Id not set.");
	}

	@Test
	public void langGerman()
	{
		g.setLanguageClassesNumber(12);
		assertEquals(g.getLanguageClassesNumber(), 12, "Classes not set.");
	}

	@Test
	public void langEnglish()
	{
		e.setLanguageClassesNumber(12);
		assertEquals(e.getLanguageClassesNumber(), 12, "Classes not set.");
	}

	@Test
	public void courseCS()
	{
		c.setNumberOfStudents(50);
		assertEquals(c.getNumberOfStudents(), 50, "Student number not set.");
	}

	@Test
	public void courseSSE()
	{
		s.setNumberOfStudents(50);
		assertEquals(s.getNumberOfStudents(), 50, "Student number not set.");
	}

	@Test
	public void German()
	{
		g.setLevel("C",1);
		assertEquals(g.getLevel(), "C1", "Level not set.");
	}

	@Test
	public void English()
	{
		e.setLevel("B", 2);
		assertEquals(e.getLevel(), "B2", "Level not set.");
	}

	@Test
	public void CS()
	{
		c.setCourse("CSStudent");
		c.setGrade(1);
		assertEquals(c.getCourse(), "CSStudent", "CourseStudent not set.");
		assertEquals(c.getGrade(), 1, "Grade not set.");
	}

	@Test
	public void SSE()
	{
		s.setCourse("SSEStudent");
		s.setGrade(2);
		assertEquals(s.getCourse(), "SSEStudent", "CourseStudent not set.");
		assertEquals(s.getGrade(), 2, "Grade not set.");
	}

	@Test
	public void changeCourse()
	{
		s.setNumberOfStudents(50);
		assertEquals(c.getNumberOfStudents(), 0, "Student number set.");
		assertEquals(s.getNumberOfStudents(), 50, "Student number not set.");
	}

	@Test
	public void changeUni()
	{
		g.setUni("RWTH");
		assertEquals(c.getUni(), "", "Uni set.");
		assertEquals(g.getUni(), "RWTH", "Uni not set.");
	}
}
