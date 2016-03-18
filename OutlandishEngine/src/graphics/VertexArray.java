package graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import renderEngine.Shader;
import utils.BufferUtils;

/**
 * Represents a 3D mesh consisting of vertices, connected based on indices.
 * Every vertex has a texture coordinate.
 * Needs to be bind before it can be drawn.
 * 
 * @author Michael Scheurer
 *
 */
public class VertexArray {
	private int vertexArrayObject, vertexBufferObject, indexBufferObject, textureBufferObject;
	private int count;

	public VertexArray(int count) {
		this.count = count;
		vertexArrayObject = glGenVertexArrays();
	}

	/**
	 * Creates a vertexArray using
	 * 
	 * @param vertices
	 *            vertices of the mesh; 3 floats in a row define 1 vertex
	 * @param indices
	 *            the indices define the way the vertices are connected to
	 *            triangles; refers to verts by their position in the vertices
	 *            array; 3 values in a row make one triangle
	 * @param textureCoordinates
	 *            assigns every vert a texturecoordinate; the coordinate needs
	 *            to be in the same position as the vert in the verticesarray
	 */
	public VertexArray(float[] vertices, int[] indices,
			float[] textureCoordinates) {
		count = indices.length;

		vertexArrayObject = glGenVertexArrays();
		glBindVertexArray(vertexArrayObject);

		vertexBufferObject = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObject);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices),
				GL_STATIC_DRAW);
		glVertexAttribPointer(Shader.VERTEX_ATTRIB, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(Shader.VERTEX_ATTRIB);

		textureBufferObject = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, textureBufferObject);
		glBufferData(GL_ARRAY_BUFFER,
				BufferUtils.createFloatBuffer(textureCoordinates),
				GL_STATIC_DRAW);
		glVertexAttribPointer(Shader.TCOORD_ATTRIB, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(Shader.TCOORD_ATTRIB);

		indexBufferObject = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufferObject);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER,
				BufferUtils.createIntBuffer(indices), GL_STATIC_DRAW);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindVertexArray(0);

	}
	
	/**
	 * Binds the VertexArray
	 */
	public void bind() {
		glBindVertexArray(vertexArrayObject);
		if (indexBufferObject > 0) {
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufferObject);
		}
	}

	/**
	 * Unbinds the VertexArray
	 */
	public void unbind() {
		glBindVertexArray(0);
		if (indexBufferObject > 0) {
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		}
	}

	/**
	 * Draws the VertexArray by calling gl
	 */
	public void draw() {
		if (indexBufferObject > 0) {
			glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
		} else {
			glDrawArrays(GL_TRIANGLES, 0, count);
		}
	}

	/**
	 * Binds and draws the element
	 */
	public void render() {
		bind();
		draw();
	}

}
