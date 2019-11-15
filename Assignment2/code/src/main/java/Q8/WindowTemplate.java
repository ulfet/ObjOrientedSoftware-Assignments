package Q8;

public abstract class WindowTemplate {
	void resize(int width, int height)
	{
		// e.g. OS.resize(handle)...
	}
	
	abstract void close();
	
	abstract void render();
}
