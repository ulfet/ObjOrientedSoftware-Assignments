public final class genericsAlgorithm{
	public static <T extends Comparable<T>> T max(T x, T y) {
		return x.compareTo(y) > 0 ? x : y;
	}
}
