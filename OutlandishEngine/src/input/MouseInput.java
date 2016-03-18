package input;

import java.awt.Point;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;

import main.Main;
import math.Camera;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

public class MouseInput extends GLFWMouseButtonCallback
{
	public static boolean MOUSE_LEFT;
	public static boolean MOUSE_RIGHT;
	public static boolean MOUSE_MIDDLE;
	private static double scrollOffset = 0;
	
	private static DoubleBuffer b1 = ByteBuffer.allocateDirect(8).order(ByteOrder.nativeOrder()).asDoubleBuffer();
	private static DoubleBuffer b2 = ByteBuffer.allocateDirect(8).order(ByteOrder.nativeOrder()).asDoubleBuffer();

	public GLFWScrollCallback scrollCallback = new GLFWScrollCallback()
	{
		
		@Override
		public void invoke(long window, double xoffset, double yoffset)
		{
			scrollOffset = yoffset;
		}
	};
	
	@Override
	public void invoke(long window, int button, int action, int mods)
	{
		if(button==GLFW.GLFW_MOUSE_BUTTON_1)
		{
			MOUSE_LEFT = action!=GLFW.GLFW_RELEASE;
		}
		else if(button==GLFW.GLFW_MOUSE_BUTTON_2)
		{
			MOUSE_RIGHT = action!=GLFW.GLFW_RELEASE;
		}
		else if(button==GLFW.GLFW_MOUSE_BUTTON_3)
		{
			MOUSE_MIDDLE = action!=GLFW.GLFW_RELEASE;
		}
	}
	
	public static double[] getMousePosition(long window)
	{
		GLFW.glfwGetCursorPos(window, b1, b2);
		double[] ret = { b1.get(0), b2.get(0) };
		return ret;
	}
	
	public static double[] getMousePositionProjected(long window, float rightBound, float upperBound)
	{
		GLFW.glfwGetCursorPos(window, b1, b2);
		double[] ret = { b1.get(0), b2.get(0) };
		ret[0] = ( ret[0] / (Main.width*0.5f) - 1 ) * rightBound;
		ret[1] = upperBound - ( ret[1] / (Main.height*0.5f) ) * upperBound;
		return ret;
	}

	public static double getScrollOffset()
	{
		double ret = scrollOffset;
		scrollOffset = 0;
		return ret;
	}

	public GLFWScrollCallback getScrollCallback()
	{
		return scrollCallback;
	}
	
	
	
}
