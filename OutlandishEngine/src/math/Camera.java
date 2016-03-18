package math;


import input.MouseInput;

public class Camera
{
	private Vector3f position = new Vector3f(0, 1, 4);
	private float pitch = 0;
	private float yaw = 0;
	private float roll = 0;
	private float zoom = 1;
	
	private long window;
	private double[] oldPos;
	private boolean mousemiddlepressed;
	private boolean mouseleftpressed;

	
	public float getZoom()
	{
		return zoom;
	}

	public Camera(long window)
	{
		this.window = window;
		
		pitch = 20;
		yaw = -5;
	}
	
	public void move()
	{
		
		zoom += MouseInput.getScrollOffset()*0.075f;
		
		
		if(MouseInput.MOUSE_LEFT)
		{
			if(!mouseleftpressed)
			{
				oldPos = MouseInput.getMousePosition(window);
				mouseleftpressed = true;
			}
			double[] pos = MouseInput.getMousePosition(window);
			double x = pos[0] - oldPos[0];
			double y = pos[1] - oldPos[1];
			
			float xfactor = 0.0078f;
			float yfactor = 0.0078f;
			
			position.y += y*yfactor;
			
			position.x -= x*xfactor;
			
			oldPos = pos;
			
		}
		else
		{
			mouseleftpressed = false;
		}
		
		if(MouseInput.MOUSE_RIGHT)
		{
			if(!mousemiddlepressed)
			{
				oldPos = MouseInput.getMousePosition(window);
				mousemiddlepressed = true;
			}
			double[] pos = MouseInput.getMousePosition(window);
			double x = pos[0] - oldPos[0];
			double y = pos[1] - oldPos[1];
			
			float xfactor = 0.05f;
			float yfactor = 0.05f;
			
			pitch += y*yfactor;
			yaw += x*xfactor;
			
			float pitchclamp = 55 - (40f/25f) * Math.abs(yaw);
			pitch = MathFunctions.clamp(pitch, 15, pitchclamp);
			yaw = MathFunctions.clamp(yaw, -25, 25);
			
			oldPos = pos;
			
		}
		else
		{
			mousemiddlepressed = false;
		}
	}
	
	public Vector3f getPosition()
	{
		return position;
	}
	public float getPitch()
	{
		return pitch;
	}
	public float getYaw()
	{
		return yaw;
	}
	public float getRoll()
	{
		return roll;
	}
	
	
}
