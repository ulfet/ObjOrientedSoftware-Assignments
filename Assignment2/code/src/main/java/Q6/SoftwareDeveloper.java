package Q6;

public class SoftwareDeveloper implements Developer {
	
	private final double hourlyRate;
	private final double hoursPerWeek;

	public SoftwareDeveloper(double hourlyRate, double hoursPerWeek) {
		super();
		this.hourlyRate = hourlyRate;
		this.hoursPerWeek = hoursPerWeek;
	}

	@Override
	public String getJobDescription() {
		return "This is software developer job";
	}

	@Override
	public Double calculateSalary() {
		return hourlyRate*hoursPerWeek*4;
	}

	
}
