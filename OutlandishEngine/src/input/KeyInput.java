package input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class KeyInput extends GLFWKeyCallback
{
	
	public static boolean[] keys = new boolean[65536];
	
	private static HashMap<Integer, List<Action>> presscalls = new HashMap<Integer, List<Action>>();
	private static HashMap<Integer, List<Action>> releasecalls = new HashMap<Integer, List<Action>>();
	private static HashMap<Integer, List<Action>> repeatcalls = new HashMap<Integer, List<Action>>();
	private static List<Action> activePressCalls = new ArrayList<Action>();

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods)
	{
		keys[key] = action != GLFW.GLFW_RELEASE;
		
		if(action==GLFW.GLFW_PRESS)
		{
			List<Action> keycalllist = presscalls.get(key);
			if(keycalllist!=null)
			{
				for(int i = 0; i < keycalllist.size(); i++)
				{
					keycalllist.get(i).action();
				}
			}
			
			keycalllist = repeatcalls.get(key);
			if(keycalllist!=null)
			{
				for (int i = 0; i < keycalllist.size(); i++)
				{
					activePressCalls.add(keycalllist.get(i));
				}
			}
		}
		else if(action==GLFW.GLFW_RELEASE)
		{
			List<Action> keycalllist = releasecalls.get(key);
			if(keycalllist!=null)
			{
				for(int i = 0; i < keycalllist.size(); i++)
				{
					keycalllist.get(i).action();
				}
			}
			
			keycalllist = repeatcalls.get(key);
			if(keycalllist!=null)
			{
				for (int i = 0; i < keycalllist.size(); i++)
				{
					activePressCalls.remove(keycalllist.get(i));
				}
			}
		}
		
	}
	
//	Has to be called in the gameloop to work!
	public static void updateRepeatKeyCalls()
	{
		for(int i = 0; i < activePressCalls.size(); i++)
		{
			activePressCalls.get(i).action();
		}
	}

	public static boolean isKey(int keycode)
	{
		return keys[keycode];
	}
	
	public void addKeyCall(int keycode, int action, Action keycall)
	{
		if(action==GLFW.GLFW_REPEAT)
		{
			List<Action> keycalllist = repeatcalls.get(keycode);
			if(keycalllist!=null)
			{
				keycalllist.add(keycall);
			}
			else
			{
				keycalllist = new ArrayList<Action>();
				keycalllist.add(keycall);
				repeatcalls.put(keycode, keycalllist);
			}
		}
		else if(action==GLFW.GLFW_RELEASE)
		{
			List<Action> keycalllist = releasecalls.get(keycode);
			if(keycalllist!=null)
			{
				keycalllist.add(keycall);
			}
			else
			{
				keycalllist = new ArrayList<Action>();
				keycalllist.add(keycall);
				releasecalls.put(keycode, keycalllist);
			}
		}
		else if(action==GLFW.GLFW_PRESS)
		{
			List<Action> keycalllist = presscalls.get(keycode);
			if(keycalllist!=null)
			{
				keycalllist.add(keycall);
			}
			else
			{
				keycalllist = new ArrayList<Action>();
				keycalllist.add(keycall);
				presscalls.put(keycode, keycalllist);
			}
		}
				
	}
	
}
