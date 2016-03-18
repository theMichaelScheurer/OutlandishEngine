package renderEngine;

import graphics.Billboard;
import graphics.TexturedModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import math.Matrix4f;
import math.Vector3f;
import componentArchitecture.Entity;
import componentArchitecture.PositionComp;
import componentArchitecture.RenderComp;

public class BillboardRenderer
{
	HashMap<Billboard,List<Entity>> billboards = new HashMap<Billboard, List<Entity>>();
	
	public void render()
	{
		Shader.BILLBOARD.enable();
		
		Billboard.vertexArray.bind();
		
		for(Billboard b : billboards.keySet())
		{
			b.bind();
			
			for(Entity e : billboards.get(b))
			{
				PositionComp p = e.getComponent(PositionComp.class);
				Vector3f pos;
				if(p!=null)
				{
					pos = p.getPositionVector();
				}
				else
				{
					pos = new Vector3f(0);
				}
				
				RenderComp r = e.getComponent(RenderComp.class);
				
				Shader.BILLBOARD.setUniform1f("scale", b.getScale());
				Shader.BILLBOARD.setUniformMat4f("transform", Matrix4f.transform(pos, r.rotation, new Vector3f(1)));
				
				b.render();
			}
		}
		Shader.BILLBOARD.disable();
		billboards.clear();
	}
	
	public void addEntity(Billboard b, Entity e)
	{
		List<Entity> batch = billboards.get(b);
		if(batch!=null)
		{
			batch.add(e);
		}
		else
		{
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(e);
			billboards.put(b, newBatch);
		}
	}
}
