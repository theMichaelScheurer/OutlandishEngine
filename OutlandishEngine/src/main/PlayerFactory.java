package main;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import graphics.Animation;
import input.Action;
import input.KeyInput;
import componentArchitecture.AnimationComp;
import componentArchitecture.AnimationSystem;
import componentArchitecture.Entity;
import componentArchitecture.PositionComp;

public class PlayerFactory
{	//this is not a real comment, it is just a test
	public static void addAnimation(Entity player)
	{
		AnimationComp ac = player.getComponent(AnimationComp.class);
		
		if(ac!=null)
		{
			int[][] walkDownIndices = new int[7][2];
			walkDownIndices[0][0] = 0;
			walkDownIndices[0][1] = 0;
			walkDownIndices[1][0] = 1;
			walkDownIndices[1][1] = 0;
			walkDownIndices[2][0] = 2;
			walkDownIndices[2][1] = 0;
			walkDownIndices[3][0] = 3;
			walkDownIndices[3][1] = 0;
			walkDownIndices[4][0] = 4;
			walkDownIndices[4][1] = 0;
			walkDownIndices[5][0] = 5;
			walkDownIndices[5][1] = 0;
			walkDownIndices[6][0] = 6;
			walkDownIndices[6][1] = 0;
			ac.addAnimation("wDown", new Animation(walkDownIndices, 0.3f, true));
			
			int[][] walkUpIndices = new int[7][2];
			walkUpIndices[0][0] = 0;
			walkUpIndices[0][1] = 1;
			walkUpIndices[1][0] = 1;
			walkUpIndices[1][1] = 1;
			walkUpIndices[2][0] = 2;
			walkUpIndices[2][1] = 1;
			walkUpIndices[3][0] = 3;
			walkUpIndices[3][1] = 1;
			walkUpIndices[4][0] = 4;
			walkUpIndices[4][1] = 1;
			walkUpIndices[5][0] = 5;
			walkUpIndices[5][1] = 1;
			walkUpIndices[6][0] = 6;
			walkUpIndices[6][1] = 1;
			ac.addAnimation("wUp", new Animation(walkUpIndices, 0.3f, true));
			
			int[][] walkRightIndices = new int[7][2];
			walkRightIndices[0][0] = 0;
			walkRightIndices[0][1] = 2;
			walkRightIndices[1][0] = 1;
			walkRightIndices[1][1] = 2;
			walkRightIndices[2][0] = 2;
			walkRightIndices[2][1] = 2;
			walkRightIndices[3][0] = 3;
			walkRightIndices[3][1] = 2;
			walkRightIndices[4][0] = 4;
			walkRightIndices[4][1] = 2;
			walkRightIndices[5][0] = 5;
			walkRightIndices[5][1] = 2;
			walkRightIndices[6][0] = 6;
			walkRightIndices[6][1] = 2;
			ac.addAnimation("wRight", new Animation(walkRightIndices, 0.3f, true));
			
			int[][] walkLeftIndices = new int[7][2];
			walkLeftIndices[0][0] = 0;
			walkLeftIndices[0][1] = 3;
			walkLeftIndices[1][0] = 1;
			walkLeftIndices[1][1] = 3;
			walkLeftIndices[2][0] = 2;
			walkLeftIndices[2][1] = 3;
			walkLeftIndices[3][0] = 3;
			walkLeftIndices[3][1] = 3;
			walkLeftIndices[4][0] = 4;
			walkLeftIndices[4][1] = 3;
			walkLeftIndices[5][0] = 5;
			walkLeftIndices[5][1] = 3;
			walkLeftIndices[6][0] = 6;
			walkLeftIndices[6][1] = 3;
			ac.addAnimation("wLeft", new Animation(walkLeftIndices, 0.3f, true));
			
			int[][] standUpIndices = new int[1][2];
			standUpIndices[0][0] = 3;
			standUpIndices[0][1] = 1;
			ac.addAnimation("sUp", new Animation(standUpIndices, 0.3f, true));
			
			int[][] standDownIndices = new int[1][2];
			standDownIndices[0][0] = 3;
			standDownIndices[0][1] = 0;
			ac.addAnimation("sDown", new Animation(standDownIndices, 0.3f, true));
			
			int[][] standLeftIndices = new int[1][2];
			standLeftIndices[0][0] = 3;
			standLeftIndices[0][1] = 3;
			ac.addAnimation("sLeft", new Animation(standLeftIndices, 0.3f, true));
			
			int[][] standRightIndices = new int[1][2];
			standRightIndices[0][0] = 3;
			standRightIndices[0][1] = 2;
			ac.addAnimation("sRight", new Animation(standRightIndices, 0.3f, true));
			
			ac.currentAnimation = ac.animationList.get("sDown");
		}
		
	}
	
	public static void addControlls(Entity player)
	{
		KeyInput input = new KeyInput();
		
		PositionComp p = player.getComponent(PositionComp.class);
			
		if(p!=null)
		{
			input.addKeyCall(GLFW_KEY_W, GLFW_REPEAT, new Action()
			{
				@Override
				public void action()
				{
					p.z -= 0.05f;
					
					if(!KeyInput.isKey(GLFW_KEY_A) && !KeyInput.isKey(GLFW_KEY_D))
						AnimationSystem.start(player, "wUp");
					
				}
			});
			
			input.addKeyCall(GLFW_KEY_W, GLFW_RELEASE, new Action()
			{
				@Override
				public void action()
				{
					AnimationSystem.start(player, "sUp");
				}
			});
			
			input.addKeyCall(GLFW_KEY_S, GLFW_REPEAT, new Action()
			{
				@Override
				public void action()
				{
					p.z += 0.05f;
					if(!KeyInput.isKey(GLFW_KEY_A) && !KeyInput.isKey(GLFW_KEY_D))
						AnimationSystem.start(player, "wDown");
				}
			});
		
			input.addKeyCall(GLFW_KEY_S, GLFW_RELEASE, new Action()
			{
				@Override
				public void action()
				{
					AnimationSystem.start(player, "sDown");
				}
			});
			
			input.addKeyCall(GLFW_KEY_A, GLFW_REPEAT, new Action()
			{
				@Override
				public void action()
				{
					p.x -= 0.05f;
					AnimationSystem.start(player, "wLeft");
				}
			});
		
			input.addKeyCall(GLFW_KEY_A, GLFW_RELEASE, new Action()
			{
				@Override
				public void action()
				{
					AnimationSystem.start(player, "sLeft");
				}
			});
			
			input.addKeyCall(GLFW_KEY_D, GLFW_REPEAT, new Action()
			{
				@Override
				public void action()
				{
					p.x += 0.05f;
					AnimationSystem.start(player, "wRight");
				}
			});
		
			input.addKeyCall(GLFW_KEY_D, GLFW_RELEASE, new Action()
			{
				@Override
				public void action()
				{
					AnimationSystem.start(player, "sRight");
				}
			});
		}
	}
}
