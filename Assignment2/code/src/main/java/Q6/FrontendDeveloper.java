package Q6;

public class FrontendDeveloper extends SoftwareDeveloper {

	private final FrontendTask completedTask;
	
	private final double BONUS_PER_PAGE = 5;
	
	
	public FrontendDeveloper(double hourlyRate, double hoursPerWeek, FrontendTask completedTask) {
		super(hourlyRate, hoursPerWeek);
		this.completedTask = completedTask;
	}

	@Override
	public String getJobDescription() {
		return "This is frontend developer job";
	}

	@Override
	public Double calculateSalary() {
		return super.calculateSalary() + calculateBonus();
	}
	
	private Double calculateBonus() {
		return completedTask.getNumberOfPages()*BONUS_PER_PAGE;
	}
}
