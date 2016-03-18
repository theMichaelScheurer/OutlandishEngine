package renderEngine;


import graphics.TexturedModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import math.Matrix4f;
import math.Vector3f;
import componentArchitecture.Entity;
import componentArchitecture.PositionComp;
import componentArchitecture.RenderComp;

public class ModelRenderer
{
	private Map<TexturedModel, List<Entity>> models = new HashMap<TexturedModel, List<Entity>>();
	
	public void render()
	{
		
		Shader.TEXTUREDMODEL.enable();
		for(TexturedModel model: models.keySet())
		{
			model.bind();
			for(Entity e:models.get(model))
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
				
				Shader.TEXTUREDMODEL.setUniformMat4f("transform", Matrix4f.transform(pos, r.rotation, new Vector3f(r.scale)));

				model.render();
				
			}
			
		}
		Shader.TEXTUREDMODEL.disable();
		models.clear();
	}
	
	public void addEntity(TexturedModel model, Entity e)
	{
		List<Entity> batch = models.get(model);
		if(batch!=null)
		{
			batch.add(e);
		}
		else
		{
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(e);
			models.put(model, newBatch);
		}
	}
}
