package main;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import graphics.Billboard;
import graphics.GUIElement;
import graphics.GUIUpdate;
import graphics.Texture;
import graphics.TexturedModel;
import graphics.VertexArray;
import input.Action;
import input.KeyInput;
import input.MouseInput;

import java.nio.ByteBuffer;

import math.Camera;
import math.MathFunctions;
import math.Matrix4f;
import math.Vector3f;
import math.Vector4f;

import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;

import renderEngine.BaseRenderer;
import renderEngine.GUIRenderer;
import renderEngine.Shader;
import componentArchitecture.AnimationComp;
import componentArchitecture.CollisionBoxComp;
import componentArchitecture.CollisionSystem;
import componentArchitecture.CollisionSystem.CollisionTypes;
import componentArchitecture.CollisionAction;
import componentArchitecture.Entity;
import componentArchitecture.EntityManager;
import componentArchitecture.PlayerComp;
import componentArchitecture.PositionComp;
import componentArchitecture.RenderComp;
import componentArchitecture.UpdateComp;
import componentArchitecture.UpdateSystem;

public class Main implements Runnable
{
	public static final float FPS = 60;
	public static int width = 1280;
	public static int height = 720;
	
	private Thread thread;
	private boolean running = false;
	private KeyInput keyinput;
	private MouseInput mouseinput;
	private boolean fullscreen = false;
	private long window;
	
	EntityManager entityManager;
	BaseRenderer renderSystem;
	GUIRenderer guirenderer;
	GUIUpdate guiupdate;
	UpdateSystem updateSystem;
	CollisionSystem collisionSystem;
	Entity link;
	Camera camera;
	
	public void start()
	{
		thread = new Thread(this, "LWJGL Test");	//yes
		thread.start();
		running = true;
	}
	
	public void init()
	{
		if(glfwInit() == GL_FALSE)
		{
			// TODO: handle that shit
			return;
		}
		
		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		long monitor = fullscreen ? glfwGetPrimaryMonitor() : NULL;
		window = glfwCreateWindow(width, height, "Link in SS1", monitor, NULL);
		if(window==NULL)
		{
			// TODO: handling
			return;
		}
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - width) / 2, (GLFWvidmode.height(vidmode) - height) / 2);
		
		keyinput = new KeyInput();
		glfwSetKeyCallback(window, keyinput);
		mouseinput = new MouseInput();
		glfwSetMouseButtonCallback(window, mouseinput);
		glfwSetScrollCallback(window, mouseinput.getScrollCallback());
		
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		GLContext.createFromCurrent();
		glViewport(0, 0, width, height);
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glFrontFace(GL_CCW);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glActiveTexture(GL_TEXTURE1);
		glClearColor(0, 0, 0, 1);
		
		System.out.println("GL Version: " + glGetString(GL_VERSION));

		System.out.println(glGetInteger(GL_MAJOR_VERSION));
		System.out.println(glGetInteger(GL_MINOR_VERSION));
		
		Shader.loadAll();
		
		Shader.TEXTUREDMODEL.enable();
		Shader.TEXTUREDMODEL.setUniform1i("tex", 1);
		Shader.TEXTUREDMODEL.setUniformMat4f("projection", Matrix4f.orthographic(-5.0f, 5.0f, -5.0f * 9.0f / 16.0f, 5.0f * 9.0f / 16.0f, -500.0f, 500.0f));
		Shader.TEXTUREDMODEL.disable();
		Shader.BILLBOARD.enable();
		Shader.BILLBOARD.setUniform1i("tex", 1);
		Shader.BILLBOARD.setUniformMat4f("projection", Matrix4f.orthographic(-5.0f, 5.0f, -5.0f * 9.0f / 16.0f, 5.0f * 9.0f / 16.0f, -500.0f, 500.0f));
		Shader.BILLBOARD.disable();
		Shader.GUI.enable();
		Shader.GUI.setUniform1i("tex", 1);
		Shader.GUI.setUniformMat4f("projection", Matrix4f.orthographic(-5.0f, 5.0f, -5.0f * 9.0f / 16.0f, 5.0f * 9.0f / 16.0f, -500.0f, 500.0f));
		Shader.GUI.disable();
		
		entityManager = new EntityManager();
		
		float[] cubevertices = {			
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,0.5f,-0.5f,		
				
				-0.5f,0.5f,0.5f,	
				-0.5f,-0.5f,0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				0.5f,0.5f,-0.5f,	
				0.5f,-0.5f,-0.5f,	
				0.5f,-0.5f,0.5f,	
				0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,-0.5f,	
				-0.5f,-0.5f,-0.5f,	
				-0.5f,-0.5f,0.5f,	
				-0.5f,0.5f,0.5f,
				
				-0.5f,0.5f,0.5f,
				-0.5f,0.5f,-0.5f,
				0.5f,0.5f,-0.5f,
				0.5f,0.5f,0.5f,
				
				-0.5f,-0.5f,0.5f,
				-0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,-0.5f,
				0.5f,-0.5f,0.5f
				
		};
		
		float[] cubetextureCoords = {
				
				0,0,
				0,1,
				1,1,
				1,0,			
				0,0,
				0,1,
				1,1,
				1,0,			
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0,
				0,0,
				0,1,
				1,1,
				1,0

				
		};
		
		int[] cubeindices = {
				0,3,1,	
				3,2,1,	
				4,5,7,
				7,5,6,
				8,11,9,
				11,10,9,
				12,13,15,
				15,13,14,	
				16,19,17,
				19,18,17,
				20,21,23,
				23,21,22

		};
		
		VertexArray cubeVertexArray = new VertexArray(cubevertices, cubeindices, cubetextureCoords);
		Texture cubeTexture = new Texture("res/floor1.bmp");
		TexturedModel cubeModel = new TexturedModel(cubeTexture, cubeVertexArray);
		
		Entity cube = entityManager.generateEntity();
		entityManager.addComponent(cube, new PositionComp(0,0,0));
		RenderComp cubeRender = new RenderComp();
		cubeRender.renderObject = cubeModel;
		cubeRender.scale = 3;
		cubeRender.rotation = new Vector3f(0, 0, 0);
		entityManager.addComponent(cube, cubeRender);
		entityManager.addComponent(cube, new CollisionBoxComp(new Vector3f(0), 3, 3, 3, CollisionTypes.Solid));
		
		Entity cube2 = entityManager.generateEntity();
		entityManager.addComponent(cube2, new PositionComp(3,0.25f,3));
		RenderComp cube2Render = new RenderComp();
		cube2Render.renderObject = cubeModel;
		cube2Render.scale = 0.5f;
		cube2Render.rotation = new Vector3f(0, 0, 0);
		entityManager.addComponent(cube2, cube2Render);
		entityManager.addComponent(cube2, new CollisionBoxComp(new Vector3f(0), 0.5f, 0.5f, 0.5f, CollisionTypes.Soft));
		
		Entity cube3 = entityManager.generateEntity();
		entityManager.addComponent(cube3, new PositionComp(-3,0.25f,3));
		RenderComp cube3Render = new RenderComp();
		cube3Render.renderObject = cubeModel;
		cube3Render.scale = 0.25f;
		cube3Render.rotation = new Vector3f(0, 0, 0);
		entityManager.addComponent(cube3, cube3Render);
		CollisionBoxComp cube3Box = new CollisionBoxComp(new Vector3f(0), 0.5f, 0.5f, 0.5f, CollisionTypes.Incorporeal);
		cube3Box.action = new CollisionAction()
		{
			
			@Override
			public void action(Entity e)
			{
				if(e==cube2)
				{
					cube.getComponent(CollisionBoxComp.class).type=CollisionTypes.Incorporeal;
				}
				else
				{
					cube.getComponent(CollisionBoxComp.class).type=CollisionTypes.Solid;
				}
				
			}
		};
		entityManager.addComponent(cube3,cube3Box);
		
		Entity cube4 = entityManager.generateEntity();
		entityManager.addComponent(cube4, new PositionComp(-6,0.25f,3));
		RenderComp cube4Render = new RenderComp();
		cube4Render.renderObject = cubeModel;
		cube4Render.scale = 0.5f;
		cube4Render.rotation = new Vector3f(0, 0, 0);
		entityManager.addComponent(cube4, cube4Render);
		entityManager.addComponent(cube4, new CollisionBoxComp(new Vector3f(0), 0.5f, 0.5f, 0.5f, CollisionTypes.Incorporeal));
		
		
		float[] floorvertices = {
				1,0,1,
				-1,0,1,
				-1,0,-1,
				1,0,-1
		};
		float[] floortexcoords = {
			1,1,
			0,1,
			0,0,
			1,0
		};
		
		int[] floorindices = {
				0,3,1,
				2,1,3
		};
		
		VertexArray floorVertexArray = new VertexArray(floorvertices, floorindices, floortexcoords);
		Texture floorTexture = new Texture("res/floor2.bmp");
		TexturedModel floorModel = new TexturedModel(floorTexture, floorVertexArray);
		
		Entity floor = entityManager.generateEntity();
		entityManager.addComponent(floor, new PositionComp(0,0,0));
		RenderComp floorRender = new RenderComp();
		floorRender.renderObject = floorModel;
		floorRender.scale = 10;
		floorRender.rotation = new Vector3f(0, 0, 0);
		entityManager.addComponent(floor, floorRender);
		
		
		
		Billboard linksprite = new Billboard("res/spritesheetlink.png", 0.5f);
		link = entityManager.generateEntity();
		entityManager.addComponent(link, new PositionComp(0, 0, 4));
		RenderComp linkrender = new RenderComp();
		linkrender.rotation = new Vector3f(0, 0, 0);
		linkrender.scale = 0.5f;
		linkrender.renderObject = linksprite;
		entityManager.addComponent(link, linkrender);
		entityManager.addComponent(link, new UpdateComp());
		entityManager.addComponent(link, new AnimationComp());
		entityManager.addComponent(link, new PlayerComp());
		entityManager.addComponent(link, new CollisionBoxComp(new Vector3f(0, 0, 0), 0.3f, 0.5f, 0.3f, CollisionTypes.Soft));
		PlayerFactory.addControlls(link);
		PlayerFactory.addAnimation(link);
		
		renderSystem = new BaseRenderer(entityManager);
		updateSystem = new UpdateSystem(entityManager);	
		collisionSystem = new CollisionSystem(entityManager);
		
		camera = new Camera(window);
		
		GUIElement root = new GUIElement(0, 0, 0, 0, null);
		guirenderer = new GUIRenderer(root);
		guiupdate = new GUIUpdate(root, window);
		
		GUIElement p = new GUIElement(-5, 1.81f, 0, 0, new Vector4f(0f, 0, 0, 1f));
		GUIElement c1 = new GUIElement(0.78f, 0.35f, 2.05f, 0.27f, new Vector4f(0, 1, 0, 1));
		GUIElement c2 = new GUIElement(0.0f, 0.0f, 3f, 1f, new Vector4f(0, 0, 0, 0));
		c2.texHeight = 1;
		c2.texWidth = 3;
		c2.indexX = 0;
		c2.indexY = 1;
		p.addChild(c1);
		p.addChild(c2);
		guirenderer.addChild(p);
		
		keyinput.addKeyCall(GLFW_KEY_H, GLFW_PRESS, new Action()
		{
			@Override
			public void action()
			{
				p.toggleActivate();
			}
		});
		
		keyinput.addKeyCall(GLFW_KEY_Q, GLFW_PRESS, new Action()
		{
			@Override
			public void action()
			{
				c1.width -= 0.1f;
				c1.width = MathFunctions.clamp(c1.width, 0, 2);
			}
		});
		keyinput.addKeyCall(GLFW_KEY_E, GLFW_PRESS, new Action()
		{
			@Override
			public void action()
			{
				c1.width += 0.1f;
				c1.width = MathFunctions.clamp(c1.width, 0, 2.05f);
			}
		});
		
	}
	
	private void update()
	{
		
		glfwPollEvents();
		KeyInput.updateRepeatKeyCalls();
		
		camera.move();
		
		updateSystem.action();
		collisionSystem.action();
		guiupdate.update();
		
		
		Vector3f zoom = new Vector3f(camera.getZoom());
		Shader.TEXTUREDMODEL.setUniformMat4f("view", Matrix4f.createViewMatrix(camera).multiply(Matrix4f.scale(zoom)));
		Shader.BILLBOARD.setUniform1f("zoom", camera.getZoom());
		Shader.BILLBOARD.setUniformMat4f("view", Matrix4f.createViewMatrix(camera).multiply(Matrix4f.scale(zoom)));
	}
	
	private void rendering()
	{
		renderSystem.render();
		
		guirenderer.render();
		
		int t = glGetError();
		if(t != GL_NO_ERROR)
			System.out.println("Error: "+t);
		
		glfwSwapBuffers(window);
	}
	
	public void run()
	{
		init();
		
		long lastTime = System.nanoTime();
		double ns = 1000000000.0 / FPS;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while(running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if(delta>=1.0)
			{
				update();
				updates++;
				delta--;
			}
			
			rendering();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				System.out.println(updates + " " + frames);
				frames = 0;
				updates = 0;
			}
			
			if(glfwWindowShouldClose(window) == GL_TRUE)
			{
				running = false;
			}
		}
		keyinput.release();
		glfwDestroyWindow(window);
		glfwTerminate();
	}
	
	public static void main(String[] args)
	{
		new Main().start();
	}
}
