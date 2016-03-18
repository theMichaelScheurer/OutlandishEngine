package componentArchitecture;


import java.util.HashMap;

import math.Vector3f;

public class UpdateSystem
{
	private EntityManager entityManager;
	
	public UpdateSystem(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}
	
	public void action()
	{
		HashMap<Entity, UpdateComp> storage = entityManager.getEntities(UpdateComp.class);
		
		for (Entity e : storage.keySet())
		{
				AnimationSystem.update(e);
				
		}
	}
}
