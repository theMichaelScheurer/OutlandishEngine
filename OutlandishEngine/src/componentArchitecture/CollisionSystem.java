package componentArchitecture;

import java.util.HashMap;

import math.Vector3f;

public class CollisionSystem
{
	private EntityManager entityManager;
	
	public CollisionSystem(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}
	
	public void action()
	{
		HashMap<Entity, CollisionBoxComp> storage = entityManager.getEntities(CollisionBoxComp.class);
		
		for(Entity e : storage.keySet())
		{
			CollisionBoxComp eBox = storage.get(e);
			
			PositionComp p = e.getComponent(PositionComp.class);
			Vector3f ePos;
			if(p!=null)
			{
				ePos = p.getPositionVector();
			}
			else
			{
				ePos = new Vector3f(0);
			}
			
			for(Entity f : storage.keySet())
			{
				if(e!=f)
				{
					CollisionBoxComp fBox = storage.get(f);
					
					PositionComp p2 = f.getComponent(PositionComp.class);
					Vector3f fPos;
					if(p2!=null)
					{
						fPos = p2.getPositionVector();
					}
					else
					{
						fPos = new Vector3f(0);
					}
					
					Vector3f el = ePos.add(eBox.position);
					Vector3f fl = fPos.add(fBox.position);
					
					float distance = el.sub(fl).length();
					
					if(distance<eBox.radius+fBox.radius)
					{
						
						if(fl.x>el.x - fBox.width*0.5f  - eBox.width*0.5f &&  fl.x<el.x + fBox.width*0.5f  + eBox.width*0.5f &&  					//Luckly this is not confusing at all!
						   fl.y>el.y - fBox.height*0.5f - eBox.height*0.5f && fl.y<el.y + fBox.height*0.5f + eBox.height*0.5f && 
						   fl.z>el.z - fBox.depth*0.5f  - eBox.depth*0.5f &&  fl.z<el.z + fBox.depth*0.5f  + eBox.depth*0.5f)
						{
							
							Vector3f d = el.sub(fl);
							float x = (fBox.width + eBox.width)*0.5f - Math.abs(d.x);
							float z = (fBox.depth + eBox.depth)*0.5f - Math.abs(d.z);
							
							
							if(eBox.type==CollisionTypes.Soft && fBox.type==CollisionTypes.Soft)
							{
								if(x<=z)
								{
									p.x += 0.5f*x*(d.x/Math.abs(d.x));
									p2.x -= 0.5f*x*(d.x/Math.abs(d.x));
								}
								else if(z<x)
								{
									p.z += 0.5f*z*(d.z/Math.abs(d.z));
									p2.z -= 0.5f*z*(d.z/Math.abs(d.z));
								}
							}
							else if(eBox.type==CollisionTypes.Solid && fBox.type==CollisionTypes.Soft)
							{
								if(x<=z)
								{
									p2.x -= x*(d.x/Math.abs(d.x));
								}
								else if(z<x)
								{
									p2.z -= z*(d.z/Math.abs(d.z));
								}
							}
							else if(eBox.type==CollisionTypes.Soft && fBox.type==CollisionTypes.Solid)
							{
								if(x<=z)
								{
									p.x -= x*(d.x/Math.abs(d.x));
								}
								else if(z<x)
								{
									p.z -= z*(d.z/Math.abs(d.z));
								}
							}
							
							if(eBox.action!=null)
							{
								eBox.action.action(f);
							}
							if(fBox.action!=null)
							{
								fBox.action.action(e);
							}
						}
						
					}
				}
			}
		}
	}
	
	public enum CollisionTypes
	{
		Solid, Soft, Incorporeal;
	}
}
