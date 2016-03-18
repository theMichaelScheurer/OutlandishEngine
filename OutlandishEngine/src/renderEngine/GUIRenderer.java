package renderEngine;

import static org.lwjgl.opengl.GL11.*;
import graphics.GUIElement;
import graphics.GUITextbox;
import graphics.Quad;


import graphics.Texture;
import math.Matrix4f;
import math.Vector3f;
import math.Vector4f;

public class GUIRenderer
{
	private GUIElement rootElement;
	private Texture texture = new Texture("res/guiClear.png");
	private Texture font = new Texture("res/fontCentury.png", GL_LINEAR_MIPMAP_LINEAR, GL_LINEAR);
	
	public GUIRenderer(GUIElement rootElement)
	{
		this.rootElement = rootElement;
	}
	public void render()
	{
		glDisable(GL_DEPTH_TEST);
		Shader.GUI.enable();
		
		Quad.vertexArray.bind();
		Shader.GUI.setUniform1f("texWidth", 16);
		Shader.GUI.setUniform1f("texHeight", 16);
		
		drawChilds(rootElement);
		
		Shader.GUI.disable();
		glEnable(GL_DEPTH_TEST);
	}
	
	public void drawChilds(GUIElement element)
	{
		for(GUIElement child : element.childs)
		{
			if(child.isActive())
				drawElement(child);
		}
		for(GUIElement child : element.childs)
		{
			if(child.isActive())
				drawChilds(child);
		}
	}
	
	public void drawElement(GUIElement element)
	{
		texture.bind();
		Shader.GUI.setUniform1f("width", element.texWidth);
		Shader.GUI.setUniform1f("height", element.texHeight);
		Shader.GUI.setUniform1f("indexX", element.indexX);
		Shader.GUI.setUniform1f("indexY", element.indexY);
		Shader.GUI.setUniformMat4f("transform", Matrix4f.transform(new Vector3f(element.getPosX()+element.width/2f, element.getPosY()+element.height/2f, 0), new Vector3f(0), new Vector3f(element.width, element.height,1)));
		Shader.GUI.setUniform4f("color", element.color);
		Quad.vertexArray.draw();
		
		if(element.getClass()==GUITextbox.class)
		{
			String s = ((GUITextbox)element).text;
			font.bind();
			Shader.GUI.setUniform1f("width", 1);
			Shader.GUI.setUniform1f("height", 1);
			Shader.GUI.setUniform4f("color", new Vector4f(0));
			
			float size = ((GUITextbox)element).fontSize;
			float posX = 0;
			float posY = element.height-size;
			int i = 0;
			while(posY>0  && i<s.length())
			{
				while(posX<element.width-size && i<s.length())
				{
					Shader.GUI.setUniformMat4f("transform", Matrix4f.transform(new Vector3f(element.getPosX()+posX+0.5f*size, element.getPosY()+posY+0.5f*size, 0), new Vector3f(0), new Vector3f(size, size,1)));
					Shader.GUI.setUniform1f("indexX", ((int)s.charAt(i))%16 );
					Shader.GUI.setUniform1f("indexY", (int)((int)s.charAt(i))/16 );
					
					Quad.vertexArray.draw();
					posX += size;
					i++;
				}
				
				posY -= size;
				posX = 0;
			}
			
		}
	}

	public void addChild(GUIElement child)
	{
		rootElement.addChild(child);
	}
	
	public void removeChild(GUIElement child)
	{
		rootElement.removeChild(child);
	}
	
	
}
