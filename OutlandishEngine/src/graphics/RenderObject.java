package graphics;

/**
 * Interface with the methods an object needs to be renderable
 * 
 * @author Michael
 *
 */
public abstract interface RenderObject {
	public abstract void bind();

	public abstract void unbind();

	public abstract void draw();

	/**
	 * binds, draws and unbinds the object
	 */
	public default void render() {
		bind();
		draw();
		unbind();
	}

}
