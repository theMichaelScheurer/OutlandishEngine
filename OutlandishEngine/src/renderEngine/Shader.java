package renderEngine;

import java.util.HashMap;
import java.util.Map;

import math.Matrix4f;
import math.Vector3f;
import math.Vector4f;
import utils.ShaderUtils;
import static org.lwjgl.opengl.GL20.*;

public class Shader
{
	public static final int VERTEX_ATTRIB = 0;
	public static final int TCOORD_ATTRIB = 1;
	public static final int NORMAL_ATTRIB = 2;
	
	
	public static Shader BILLBOARD, TEXTUREDMODEL, GUI;
	private final int ID;
	private Map<String, Integer> locationCache = new HashMap<String, Integer>();
	
	private boolean enabled = false;
	
	public Shader(String vertex, String fragment)
	{
		ID = ShaderUtils.load(vertex, fragment);
	}
	
	public static void loadAll()
	{
		BILLBOARD = new Shader("shaders/billboard.vert", "shaders/billboard.frag");
		TEXTUREDMODEL = new Shader("shaders/entity.vert", "shaders/entity.frag");
		GUI = new Shader("shaders/gui.vert", "shaders/gui.frag");
	}
	
	public int getUniform(String name)
	{
		if(locationCache.containsKey(name))
		{
			return locationCache.get(name);
		}
		
		int result = glGetUniformLocation(ID, name);
		if(result == -1)
		{
			System.err.println("Could not find uniform variable " + name);
		}
		else
		{
			locationCache.put(name, result);
		}
		return result;
	
	}
	
	public void setUniform1i(String name, int value)
	{
		if(!enabled) enable();
		glUniform1i(getUniform(name), value);
	}
	
	public void setUniform1f(String name, float value)
	{
		if(!enabled) enable();
		glUniform1f(getUniform(name), value);
	}
	
	public void setUniform2f(String name, float x, float y)
	{
		if(!enabled) enable();
		glUniform2f(getUniform(name), x, y);
	}
	
	public void setUniform3f(String name, Vector3f vector)
	{
		if(!enabled) enable();
		glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
	}
	
	public void setUniform4f(String name, Vector4f vector)
	{
		if(!enabled) enable();
		glUniform4f(getUniform(name), vector.x, vector.y, vector.z, vector.w);
	}
	
	public void setUniformMat4f(String name, Matrix4f matrix)
	{
		if(!enabled) enable();
		glUniformMatrix4fv(getUniform(name), false, matrix.toFloatBuffer());
	}
	
	public void bindAttribute(int attribute, String varName)
	{
		glBindAttribLocation(ID, attribute, varName);
	}
	
	public void enable()
	{
		glUseProgram(ID);
		enabled = true;
	}
	
	public void disable()
	{
		glUseProgram(0);
		enabled = false;
	}
}
