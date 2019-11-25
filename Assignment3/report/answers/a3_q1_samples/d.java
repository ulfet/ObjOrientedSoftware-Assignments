public static double sumOfListVar1(List<? extends Number> list) {
double s = 0.0;
	for (Number n : list)
		s += n.doubleValue();
	return s;
}

public static double sumOfListVar2(List<Number> list) {
	double s = 0.0;
	for (Number n : list)
		s += n.doubleValue();
	return s;
}
