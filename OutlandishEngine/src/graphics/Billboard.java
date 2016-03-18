package graphics;

import math.Vector3f;
/**
 * Quad that always faces the camera. Since mesh and texture coordinates are
 * always the same it only has a texture and scale.
 * 
 * @author Michael
 *
 */
public class Billboard implements RenderObject {
	private static final float[] vertices = { 0.5f, 0, 0,
											 -0.5f, 0, 0, 
											 -0.5f, 1,0,
											  0.5f, 1, 0 };
	private static final float[] texcoords = { 1, 1, 
											   0, 1, 
											   0, 0, 
											   1, 0 };
	private static final int[] indices = { 0, 1, 2, 
										   2, 3, 0 };
	public static final VertexArray vertexArray = new VertexArray(vertices,
			indices, texcoords);

	private float scale;
	private Texture texture;

	/**
	 * Creates a billboard with the defined image and scale
	 * 
	 * @param texturepath path and name of the image
	 * @param scale size to which the billboard should be scaled (from 1x1)
	 */
	public Billboard(String texturepath, float scale) {
		this.scale = scale;

		texture = new Texture(texturepath);
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public static VertexArray getVertexarray() {
		return vertexArray;
	}

	public Texture getTexture() {
		return texture;
	}

	public void bind() {
		texture.bind();
	}

	public void unbind() {
		vertexArray.unbind();
		texture.unbind();
	}

	public void draw() {
		vertexArray.draw();
	}

}
