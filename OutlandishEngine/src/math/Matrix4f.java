package math;

import java.nio.FloatBuffer;

import utils.BufferUtils;
import static java.lang.Math.*;;

public class Matrix4f
{
	public static final int SIZE = 4*4;
	public float[] elements = new float[SIZE];
	
	public Matrix4f()
	{
		
	}
	
	public static Matrix4f identity()
	{
		Matrix4f result = new Matrix4f();
		
		result.elements[0 + 0 * 4] = 1.0f;
		result.elements[1 + 1 * 4] = 1.0f;
		result.elements[2 + 2 * 4] = 1.0f;
		result.elements[3 + 3 * 4] = 1.0f;
		
		return result;
	}
	
	public Matrix4f multiply(Matrix4f matrix)
	{
		Matrix4f result = new Matrix4f();
		
		for (int y = 0; y < 4; y++)
		{
			for (int x = 0; x < 4; x++)
			{
				float sum = 0.0f;
				for (int e = 0; e < 4; e++)
				{
					sum += this.elements[x + e * 4] * matrix.elements[e + y * 4];
				}
				result.elements[x + y * 4] = sum;
			}
		}
		return result;
	}
	
	public static Matrix4f translate(Vector3f vector)
	{
		Matrix4f result = identity();
		
		result.elements[0 + 3 * 4] = vector.x;
		result.elements[1 + 3 * 4] = vector.y;
		result.elements[2 + 3 * 4] = vector.z;
		
		return result;
	}
	
	public static Matrix4f scale(Vector3f vector)
	{
		Matrix4f result = identity();
		
		result.elements[0 + 0 * 4] = vector.x;
		result.elements[1 + 1 * 4] = vector.y;
		result.elements[2 + 2 * 4] = vector.z;
		
		return result;
	}
	
	public static Matrix4f rotate(float anglex, float angley, float anglez)
	{
		Matrix4f zrot = identity();
		float zr = (float)toRadians(anglez);
		float zcos = (float) cos(zr);
		float zsin = (float) sin(zr);
		
		zrot.elements[0 + 0 * 4] = zcos;
		zrot.elements[1 + 0 * 4] = zsin;
		
		zrot.elements[0 + 1 * 4] = -zsin;
		zrot.elements[1 + 1 * 4] = zcos;
		
		Matrix4f yrot = identity();
		float yr = (float)toRadians(angley);
		float ycos = (float) cos(yr);
		float ysin = (float) sin(yr);
		
		yrot.elements[0 + 0 * 4] = ycos;
		yrot.elements[0 + 2 * 4] = ysin;
		
		yrot.elements[2 + 0 * 4] = -ysin;
		yrot.elements[2 + 2 * 4] = ycos;
		
		Matrix4f xrot = identity();
		float xr = (float)toRadians(anglex);
		float xcos = (float) cos(xr);
		float xsin = (float) sin(xr);
		
		xrot.elements[1 + 1 * 4] = xcos;
		xrot.elements[1 + 2 * 4] = xsin;
		
		xrot.elements[1 + 2 * 4] = -xsin;
		xrot.elements[2 + 2 * 4] = xcos;
		
		Matrix4f result = zrot.multiply(yrot.multiply(xrot));
		
		return result;
	}
	
	public static Matrix4f transform(Vector3f translate, Vector3f rotate, Vector3f scale)
	{
		
		Matrix4f rotMat = rotate(rotate.x, rotate.y, rotate.z);
		Matrix4f transMat = translate(translate);
		Matrix4f scaleMat = scale(scale);
		
		return transMat.multiply(scaleMat.multiply(rotMat));
		
	}
	
	public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far)
	{
		Matrix4f result = identity();
		
		result.elements[0 + 0 * 4] = 2.0f / (right - left);
		result.elements[1 + 1 * 4] = 2.0f / (top - bottom);
		result.elements[2 + 2 * 4] = 2.0f / (near - far);
		
		result.elements[0 + 3 * 4] = (left + right) / (left - right);
		result.elements[1 + 3 * 4] = (bottom + top) / (bottom - top);
		result.elements[2 + 3 * 4] = (far + near) / (far - near);
		
		return result;
	}
	
	public static Matrix4f perspective(float fov, float aspect, float near, float far)
	{
		Matrix4f result = new Matrix4f();
		
		float tanfovover2 = (float) tan(0.5f * toRadians(fov));
		
		result.elements[0 + 0*4] = 1 / (aspect * tanfovover2);
		result.elements[1 + 1*4] = 1 / tanfovover2;
		result.elements[2 + 2*4] = -(far + near) / (far - near);
		result.elements[2 + 3*4] = -2 * near * far / (far - near);
		result.elements[3 + 2*4] = -1;
		
		return result;
	} 
	
	public static Matrix4f createViewMatrix(Camera camera)
	{
		
		Matrix4f rotMat = rotate(camera.getPitch(), camera.getYaw(), camera.getRoll());
		Matrix4f transMat = translate(new Vector3f(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z));
		Matrix4f scaleMat = scale(new Vector3f(1, 1, 1));
		return rotMat.multiply(scaleMat.multiply(transMat));
	}
	
	public static Matrix4f getViewMatrix(Vector3f position, Vector3f lookAt, Vector3f up)
	{
		Matrix4f result = identity();
		
		float dirX, dirY, dirZ;
        dirX = Math.abs(lookAt.x - position.x);
        dirY = Math.abs(lookAt.y - position.y);
        dirZ = Math.abs(lookAt.z - position.z);
        // Normalize direction
        float dirLength = Vector3f.distance(position, lookAt);
        dirX /= dirLength;
        dirY /= dirLength;
        dirZ /= dirLength;
        // Normalize up
        float upX, upY, upZ;
        upX = up.x;
        upY = up.y;
        upZ = up.z;
        float upLength = up.length();
        upX /= upLength;
        upY /= upLength;
        upZ /= upLength;
        // right = direction x up
        float rightX, rightY, rightZ;
        rightX = dirY * upZ - dirZ * upY;
        rightY = dirZ * upX - dirX * upZ;
        rightZ = dirX * upY - dirY * upX;
        // up = right x direction
        upX = rightY * dirZ - rightZ * dirY;
        upY = rightZ * dirX - rightX * dirZ;
        upZ = rightX * dirY - rightY * dirX;
        // Set matrix elements
        result.elements[0 + 4 * 0] = rightX;
        result.elements[1 + 4 * 0] = rightY;
        result.elements[2 + 4 * 0] = rightZ;
        result.elements[3 + 4 * 0] = -rightX * position.x - rightY * position.y - rightZ * position.z;
        result.elements[0 + 4 * 1] = upX;
        result.elements[1 + 4 * 1] = upY;
        result.elements[2 + 4 * 1] = upZ;
        result.elements[3 + 4 * 1] = -upX * position.x - upY * position.y - upZ * position.z;
        result.elements[0 + 4 * 2] = -dirX;
        result.elements[1 + 4 * 2] = -dirY;
        result.elements[2 + 4 * 2] = -dirZ;
        result.elements[3 + 4 * 2] = dirX * position.x + dirY * position.y + dirZ * position.z;
        result.elements[0 + 4 * 3] = 0.0f;
        result.elements[1 + 4 * 3] = 0.0f;
        result.elements[2 + 4 * 3] = 0.0f;
        result.elements[3 + 4 * 3] = 1.0f;
        
        return result;
	}
	
	public void printMat()
	{
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				System.out.print(elements[i + j * 4] + " \t\t");
			}
			System.out.println();	
		}
	}
	
	public FloatBuffer toFloatBuffer()
	{
		return BufferUtils.createFloatBuffer(elements);
	}
	
}
