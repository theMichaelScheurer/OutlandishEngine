package graphics;
/**
 * A VertexArray with a texture.
 * 
 * @author Michael
 *
 */
public class TexturedModel implements RenderObject
{
	private Texture texture;
	private VertexArray vertexArray;
	
	public TexturedModel(float[] vertices, int[] indices, float[] textureCoordinates, String imagePath){
		this.texture = new Texture(imagePath);
		this.vertexArray = new VertexArray(vertices, indices, textureCoordinates);
	}
	
	public TexturedModel(Texture texture, VertexArray vertexArray)
	{
		this.texture = texture;
		this.vertexArray = vertexArray;
	}
	
	public Texture getTexture()
	{
		return texture;
	}
	public VertexArray getVertexArray()
	{
		return vertexArray;
	}
	
	/**
	 * Binds the VertexArray(mesh) and texture of the model
	 */
	public void bind()
	{
		texture.bind();
		vertexArray.bind();
	}
	
	/**
	 * Unbinds the VertexArray(mesh) and texture of the model
	 */
	public void unbind()
	{
		texture.unbind();
		vertexArray.unbind();
	}
	
	/**
	 * Draws the model
	 */
	public void draw()
	{
		vertexArray.draw();
	}
	
}
