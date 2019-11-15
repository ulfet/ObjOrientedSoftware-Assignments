package Q6;

public class BackendDeveloper extends SoftwareDeveloper{

	private final BackendTask completedTask;
	
	private final double BONUS_PER_LOC = 0.05;
	
	
	public BackendDeveloper(double hourlyRate, double hoursPerWeek, BackendTask completedTask) {
		super(hourlyRate, hoursPerWeek);
		this.completedTask = completedTask;
	}

	@Override
	public String getJobDescription() {
		return "This is backend developer job";
	}

	@Override
	public Double calculateSalary() {
		return super.calculateSalary() + calculateBonus();
	}
	
	private Double calculateBonus() {
		return completedTask.getLineOfCode()*BONUS_PER_LOC;
	}

}
