package componentArchitecture;

import renderEngine.Shader;
import main.Main;
import graphics.Animation;

public class AnimationSystem
{
//	starts new animation only when it is a different one
	public static void start(Entity e, String name)
	{
		AnimationComp ac = e.getComponent(AnimationComp.class);
		if(ac!=null)
		{
			Animation a = ac.animationList.get(name);
			
			if(ac.currentAnimation!=a)
			{
				ac.currentAnimation = a;
				ac.time = 0;
			}
		}
	}

//	starts new animation any way
	public static void forcestart(Entity e, String name)
	{
		AnimationComp ac = e.getComponent(AnimationComp.class);
		if(ac!=null)
		{
			Animation a = ac.animationList.get(name);
			ac.currentAnimation = a;
			ac.time = 0;
		}
	}
	
	public static void update(Entity e)
	{
		AnimationComp ac = e.getComponent(AnimationComp.class);
		if(ac!=null)
		{
			
			ac.time += 1/Main.FPS;
			
			if(ac.time<ac.currentAnimation.duration)
			{
			
			int currentFrame = (int)(ac.time/ac.currentAnimation.timePerFrame);
			int currentIndexX = ac.currentAnimation.spriteIndeces[currentFrame][0];
			int currentIndexY = ac.currentAnimation.spriteIndeces[currentFrame][1];
			
			Shader.BILLBOARD.enable();
			Shader.BILLBOARD.setUniform1f("indexX", currentIndexX);
			Shader.BILLBOARD.setUniform1f("indexY", currentIndexY);
			Shader.BILLBOARD.disable();
			
			}
			else if(ac.nextAnimation!=null)
			{
				ac.currentAnimation = ac.nextAnimation;
				ac.nextAnimation = null;
				ac.time = 0;
				update(e);
			}
			else if(ac.currentAnimation.loop)
			{
				ac.time = 0;
			}
		}
	}
}
