package math;

public class Vector4f
{
	public float x, y, z, w;
	
	public Vector4f()
	{
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
		w = 0.0f;
	}
	
	public Vector4f(float a)
	{
		x = a;
		y = a;
		z = a;
		w = a;
	}
	
	public Vector4f(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
}
