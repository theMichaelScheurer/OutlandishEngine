package graphics;

public class Animation
{
	public int[][] spriteIndeces;
	public float duration;
	public float timePerFrame;
	public boolean loop = false;
	
	public Animation(int[][] spriteIndices, float duration)
	{
		this.spriteIndeces = spriteIndices;
		this.duration = duration;
		timePerFrame = duration / spriteIndices.length;
	}
	
	public Animation(int[][] spriteIndices, float duration, boolean loop)
	{
		this.spriteIndeces = spriteIndices;
		this.duration = duration;
		timePerFrame = duration / spriteIndices.length;
		this.loop = loop;
	}
}
