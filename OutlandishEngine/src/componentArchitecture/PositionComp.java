package componentArchitecture;

import math.Vector3f;

/**
 * Component that holds a position via three float values
 * 
 * @author Michael
 *
 */
public class PositionComp extends Component {
	public float x, y, z;

	public PositionComp() {
	}

	public PositionComp(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3f getPositionVector() {
		return new Vector3f(x, y, z);
	}
}
