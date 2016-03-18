package graphics;

import java.util.ArrayList;
import java.util.List;


public class GUIToggleList extends GUIElement
{
	private List<GUIElement> elementList = new ArrayList<>();
	private GUIElement activeElement = null;
	
	public GUIToggleList()
	{
		super(0, 0, 0, 0, null);
	}
	
	public void addToggleElement(GUIElement element)
	{
		elementList.add(element);
		if(activeElement==null)
		{
			toggle(element);
		}
	}
	
	public void removeToggleElement(GUIElement element)
	{
		elementList.remove(element);
	}
	
	public void toggle(GUIElement element)
	{
		for(GUIElement listElement:elementList)
		{
			if(listElement==element)
			{
				activeElement = element;
				width = listElement.width;
				height = listElement.height;
				setPosX(listElement.posX);
				setPosY(listElement.posY);
				color = listElement.color;
			}
		}
	}
	
}
