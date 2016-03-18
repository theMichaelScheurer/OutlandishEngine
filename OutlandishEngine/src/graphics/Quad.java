package graphics;

public class Quad
{
	private static final float[] vertices = {0.5f,-0.5f,0,  -0.5f,-0.5f,0,  -0.5f,0.5f,0,  0.5f,0.5f,0 };
	private static final float[]texcoords = {1,1,  0,1,  0,0,  1,0 };
	private static final int[] indices = {0,1,2,  2,3,0 };
	public static final VertexArray vertexArray = new VertexArray(vertices, indices, texcoords);
}
