package math;



public class Vector3f
{
	
	public float x, y, z;
	
	public Vector3f()
	{
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
	}
	
	public Vector3f(float a)
	{
		this.x = a;
		this.y = a;
		this.z = a;
	}
	
	public Vector3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3f sub(Vector3f vector)
	{
		float rx = x - vector.x;
		float ry = y - vector.y;
		float rz = z - vector.z;
		
		return new Vector3f(rx, ry, rz);
	}
	
	public Vector3f add(Vector3f vector)
	{
		float rx = x + vector.x;
		float ry = y + vector.y;
		float rz = z + vector.z;
		
		return new Vector3f(rx, ry, rz);
	}
	
	public Vector3f normal()
	{
		
		return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
		
	}
	
	public float dot(Vector3f vector)
	{
		float rx = x * vector.x;
		float ry = y * vector.y;
		float rz = z * vector.z;
		
		return rx + ry + rz;
	}
	
	public Vector3f cross(Vector3f vector)
	{
		float rx = y * vector.z - z * vector.y;
		float ry = z * vector.x - x * vector.z;
		float rz = x * vector.y - y * vector.x;
		
		return new Vector3f(rx, ry, rz);
	}
	
	public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }
    
    public static float distance(Vector3f start, Vector3f end) {
        return (float) Math.sqrt((end.x - start.x) * (end.x - start.x)
                + (end.y - start.y) * (end.y - start.y)
                + (end.z - start.z) * (end.z - start.z));
    }
	
}
