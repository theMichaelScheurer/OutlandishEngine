package graphics;

import java.util.ArrayList;
import java.util.List;

import math.Vector4f;

public class GUIElement
{
	public GUIElement parent;
	public List<GUIElement> childs = new ArrayList<>();
	protected float posX, posY;
	public float width, height;
	public float texWidth, texHeight = 0;
	public float indexX, indexY = 0;
	protected boolean active = true;
	public Vector4f color;
	
	public GUIElement(float posX, float posY, float width, float height, Vector4f color)
	{
		setPosX(posX);
		setPosY(posY);
		this.width = width;
		this.height = height;
		this.color = color;
	}
	
	public void addChild(GUIElement child)
	{
		child.parent = this;
		childs.add(child);
	}
	
	public void removeChild(GUIElement child)
	{
		childs.remove(child);
	}
	
	public void activate()
	{
		active = true;
	}
	
	public void deactivate()
	{
		active = false;
	}
	
	public void toggleActivate()
	{
		active = !active;
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	public float getPosX()
	{
		if(parent!=null)
		{
			return posX+parent.getPosX();
		}
		else
		{
			return posX;
		}
	}

	public void setPosX(float posX)
	{
		this.posX = posX;
	}

	public float getPosY()
	{
		if(parent!=null)
		{
			return posY+parent.getPosY();
		}
		else
		{
			return posY;
		}
	}

	public void setPosY(float posY)
	{
		this.posY = posY;
	}
	
}
