public class Singleton<T> {
	public T getInstance() {
			if (instance == null)
				instance = new Singleton<T>();
			return instance;
		}
	private T instance = null;
	
}
