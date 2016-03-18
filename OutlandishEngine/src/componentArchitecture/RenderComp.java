package componentArchitecture;

import math.Vector3f;
import graphics.RenderObject;

/**
 * Component to make an entity renderable. Holds a rotation(vec3), scale and
 * RenderObject
 * 
 * @author Michael
 *
 */
public class RenderComp extends Component {
	public Vector3f rotation;
	public float scale;
	public RenderObject renderObject;
}
