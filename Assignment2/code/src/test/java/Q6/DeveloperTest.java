package Q6;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DeveloperTest {

	@Test
	public void softwareDeveloperTest() {
		Developer developer = new SoftwareDeveloper(20, 40);
		String jobDescription = developer.getJobDescription();
		Double salary = developer.calculateSalary();
		assertEquals(jobDescription, "This is software developer job");
		assertEquals(salary, 3200);
	}
	
	@Test
	public void backendDeveloperTest() {
		Developer developer = new BackendDeveloper(20, 40, new BackendTask(1000));
		String jobDescription = developer.getJobDescription();
		Double salary = developer.calculateSalary();
		assertEquals(jobDescription, "This is backend developer job");
		assertEquals(salary, 3250);
	}
	
	@Test
	public void frontendDeveloperTest() {
		Developer developer = new FrontendDeveloper(20, 40, new FrontendTask(20));
		String jobDescription = developer.getJobDescription();
		Double salary = developer.calculateSalary();
		assertEquals(jobDescription, "This is frontend developer job");
		assertEquals(salary, 3300);
	}
}
