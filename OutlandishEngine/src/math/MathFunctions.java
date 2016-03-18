package math;

public class MathFunctions
{
	public static float clamp(float value, float lowerLimit, float upperLimit)
	{
		if(value<lowerLimit)
			return lowerLimit;
		else if(value>upperLimit)
			return upperLimit;
		else
			return value;
	}
	
	public static double clamp(double value, double lowerLimit, double upperLimit)
	{
		if(value<lowerLimit)
			return lowerLimit;
		else if(value>upperLimit)
			return upperLimit;
		else
			return value;
	}
	
	public static int clamp(int value, int lowerLimit, int upperLimit)
	{
		if(value<lowerLimit)
			return lowerLimit;
		else if(value>upperLimit)
			return upperLimit;
		else
			return value;
	}
}
