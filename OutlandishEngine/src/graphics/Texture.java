package graphics;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import utils.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL30.*;

/**
 * Represents a texture. Needs to be bind before it can be drawn
 * 
 * @author Michael
 *
 */
public class Texture {
	private int width, height;
	private int texture;
	private boolean transparency = false;

	/**
	 * Loads a image to a texture from the path using fixed min- and mag-filters
	 * 
	 * @param path location and name of the picture
	 */
	public Texture(String path) {
		texture = load(path, GL_NEAREST_MIPMAP_LINEAR, GL_NEAREST);
	}

	/**
	 * Loads a image to a texture from the path using the selected min- and mag-filters.
	 * 
	 * @param path
	 * @param minParameter
	 * @param magParameter
	 */
	public Texture(String path, int minParameter, int magParameter) {
		texture = load(path, minParameter, magParameter);
	}

	private int load(String path, int minParameter, int magParameter) {
		int[] pixels = null;
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int[] data = new int[width * height];
		for (int i = 0; i < width * height; i++) {
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			data[i] = a << 24 | b << 16 | g << 8 | r;
		}

		int tex = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, tex);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA,
				GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
		glGenerateMipmap(GL_TEXTURE_2D);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magParameter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minParameter);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_LOD_BIAS, 0);
		glBindTexture(GL_TEXTURE_2D, 0);

		return tex;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getID() {
		return texture;
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, texture);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public boolean hasTransparency() {
		return transparency;
	}

	public void setTransparency(boolean trancperancy) {
		this.transparency = trancperancy;
	}
}
