package componentArchitecture;

import graphics.Animation;

import java.util.HashMap;

public class AnimationComp extends Component
{
	public float time=0;
	public Animation currentAnimation;
	public Animation nextAnimation;
	public HashMap<String, Animation> animationList = new HashMap<String, Animation>();;
	
	public void addAnimation(String name, Animation animation)
	{
		animationList.put(name, animation);
	}
	
	public void removeAnimation(String name)
	{
		animationList.remove(name);
	}
}
