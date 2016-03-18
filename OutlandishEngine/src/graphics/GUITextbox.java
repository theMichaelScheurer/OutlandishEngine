package graphics;

import math.Vector4f;

public class GUITextbox extends GUIElement
{
	public String text;
	public float fontSize;
	
	public GUITextbox(float posX, float posY, float width, float height,
			Vector4f color, String text, float fontSize)
	{
		super(posX, posY, width, height, color);
		this.text = text;
		this.fontSize = fontSize;
	}

}
