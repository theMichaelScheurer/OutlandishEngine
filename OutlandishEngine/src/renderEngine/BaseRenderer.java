package renderEngine;

import graphics.Billboard;
import graphics.RenderObject;
import graphics.TexturedModel;

import java.util.HashMap;

import componentArchitecture.Component;
import componentArchitecture.Entity;
import componentArchitecture.EntityManager;
import componentArchitecture.PositionComp;
import componentArchitecture.RenderComp;
import math.Matrix4f;
import math.Vector3f;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;


public class BaseRenderer
{
	private EntityManager entityManager;
	private ModelRenderer modelRenderer;
	private BillboardRenderer billboardRenderer;
	
	public BaseRenderer(EntityManager entityManager)
	{
		this.entityManager = entityManager;
		modelRenderer = new ModelRenderer();
		billboardRenderer = new BillboardRenderer();
	}
	
	public void render()
	{
		
		HashMap<Entity, RenderComp> storage = entityManager.getEntities(RenderComp.class);
			
		for (Entity e : storage.keySet())
		{
			RenderComp r = storage.get(e);
			RenderObject ro = r.renderObject;
			
			if(ro.getClass()==Billboard.class)
			{
				Billboard b = (Billboard)ro;
				
				billboardRenderer.addEntity(b, e);
			}
			else if(ro.getClass()==TexturedModel.class)
			{
				TexturedModel t = (TexturedModel)ro;
				
				modelRenderer.addEntity(t, e);
				
			}
		}
		
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		modelRenderer.render();
		billboardRenderer.render();
		
		glBindTexture(GL_TEXTURE_2D, 0);
		glBindVertexArray(0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
}
