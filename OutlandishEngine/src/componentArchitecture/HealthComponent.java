package componentArchitecture;

import math.MathFunctions;

/**
 * Holds the current and maximal health
 * 
 * @author Michael
 *
 */
public class HealthComponent {
	public float maxhealth;
	public float health;

	public void increaseHealth(float amount) {
		health += amount;
		health = MathFunctions.clamp(health, 0, maxhealth);
	}

	public void decreaseHealth(float amount) {
		health -= amount;
		health = MathFunctions.clamp(health, 0, maxhealth);
	}

	public void fillHealth() {
		health = maxhealth;
	}
}
