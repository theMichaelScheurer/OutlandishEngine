package componentArchitecture;

import componentArchitecture.CollisionSystem.CollisionTypes;
import math.Vector3f;
/**
 * Gives an entity a CollisionBox
 * 
 * @author Michael
 *
 */
public class CollisionBoxComp extends Component
{
	public Vector3f position;
	public float width, height, depth;
	public float radius;
	public CollisionTypes type;
	public CollisionAction action = null;
	
	public CollisionBoxComp(Vector3f position, float width, float height, float depth, CollisionTypes type)
	{
		this.position = position;
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.type = type;
		radius = (float)Math.sqrt(width*width+height*height+depth*depth);
	}
}
