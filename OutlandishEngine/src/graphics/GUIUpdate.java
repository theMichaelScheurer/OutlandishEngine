package graphics;

import input.MouseInput;

public class GUIUpdate
{
	private GUIElement rootElement;
	private long window;
	
	public GUIUpdate(GUIElement rootElement, long window)
	{
		this.rootElement = rootElement;
		this.window = window;
	}
	
	public void update()
	{
		if(MouseInput.MOUSE_LEFT)
		{
			checkChilds(rootElement);
		}
	}
	
	private void checkChilds(GUIElement rootElement)
	{
		for(GUIElement element : rootElement.childs)
		{
			if(element.isActive())
			{
				if(element.childs.size()>0)
				{
					checkChilds(element);
				}
				
				double[] pos = MouseInput.getMousePositionProjected(window, 5, 5.0f*9.0f/16.0f);
				
//				System.out.println("mpx: " + pos[0]);
//				System.out.println("mpy: " + pos[1]);
				
				if(pos[0]>element.getPosX() && pos[0]<element.getPosX()+element.width && pos[1]>element.getPosY() && pos[1]<element.getPosY()+element.height)
				{
					System.out.println("Inside one");
				}
				
			}
		}
	}
	
}
