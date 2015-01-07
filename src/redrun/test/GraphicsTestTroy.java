package redrun.test;

import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.Timer;
import org.newdawn.slick.Color;

import redrun.graphics.camera.Camera;
import redrun.graphics.camera.CameraManager;
import redrun.graphics.selection.Picker;
import redrun.model.constants.CameraType;
import redrun.model.constants.Direction;
import redrun.model.constants.Team;
import redrun.model.constants.TrapType;
import redrun.model.game.GameData;
import redrun.model.gameobject.GameObject;
import redrun.model.gameobject.MapObject;
import redrun.model.gameobject.map.Corner;
import redrun.model.gameobject.map.Corridor;
import redrun.model.gameobject.map.End;
import redrun.model.gameobject.map.Field;
import redrun.model.gameobject.map.Kiosk;
import redrun.model.gameobject.map.Pit;
import redrun.model.gameobject.map.Staircase;
import redrun.model.gameobject.map.Start;
import redrun.model.gameobject.map.Tunnel;
import redrun.model.gameobject.player.Player;
import redrun.model.gameobject.world.Ball;
import redrun.model.gameobject.world.Cube;
import redrun.model.gameobject.world.Plane;
import redrun.model.gameobject.world.SkyBox;
import redrun.model.physics.PhysicsWorld;
import redrun.model.toolkit.BufferConverter;
import redrun.model.toolkit.FontTools;
import redrun.model.toolkit.Timing;
import static org.lwjgl.opengl.GL11.*;

/**
 * This class is for testing OpenGL scenes.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-03
 */
public class GraphicsTestTroy
{
  /** The active camera manager. */
  private static CameraManager cameraManager = null;
	
  /** The active camera. */
  private static Camera camera = null;
  
  /** The player associated with the client. */
  private static Player player = null;

  /**
   * Performs OpenGL initialization.
   */
  private static void createOpenGL()
  {
    try
    {
      Display.setDisplayMode(new DisplayMode(1280, 720));
      Display.setTitle("RedRun Ice World");
      Display.create();
      Display.setVSyncEnabled(true);
    }
    catch (LWJGLException ex)
    {
      Logger.getLogger(GraphicsTestTroy.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    player = new Player(0.0f, 1.0f, 0.0f, "Linvala, Keeper of Silence", Team.BLUE);
    
    Camera spectatorCam = new Camera(70, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000, 0.0f, 1.0f, 0.0f, CameraType.SPECTATOR);
    Camera playerCam = player.getCamera();
    
    cameraManager = new CameraManager(spectatorCam, playerCam);
    
    glEnable(GL_DEPTH_TEST);
    glDisable(GL_COLOR_MATERIAL);
    glEnable(GL_LIGHTING);
    glEnable(GL_LIGHT0);
    glEnable(GL_NORMALIZE);
    glShadeModel(GL_SMOOTH);
    
    FontTools.loadFonts();
  }

  /**
   * The main loop where the logic occurs. Stopped when the escape key is pressed or the window is closed.
   */
  private static void gameLoop()
  {      	
    GameData.addMapObject(new Start(0.0f, 0.0f, 0.0f, "direction", "brick9", Direction.WEST, TrapType.EMPTY));
    
    GameData.addMapObject(new Corridor(0.0f, 0.0f, 15.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(0.0f, 0.0f, 30.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));    
    GameData.addMapObject(new Corridor(0.0f, 0.0f, 45.0f, "ground14", "brick9", Direction.EAST, TrapType.SPIKE_TRAP_DOOR));
    GameData.addMapObject(new Corridor(0.0f, 0.0f, 60.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(0.0f, 0.0f, 75.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));
    
    GameData.addMapObject(new Staircase(0.0f, 0.0f, 90.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));
    
    GameData.addMapObject(new Corridor(0.0f, 15.0f, 105.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(0.0f, 15.0f, 120.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));    
    GameData.addMapObject(new Corridor(0.0f, 15.0f, 135.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(0.0f, 15.0f, 150.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(0.0f, 15.0f, 165.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));
    
    GameData.addMapObject(new Staircase(0.0f, 0.0f, 180.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));
    
    GameData.addMapObject(new Corridor(0.0f, 0.0f, 195.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(0.0f, 0.0f, 210.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));    
    GameData.addMapObject(new Corridor(0.0f, 0.0f, 225.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(0.0f, 0.0f, 240.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(0.0f, 0.0f, 255.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));
    
    GameData.addMapObject(new Corner(0.0f, 0.0f, 270.0f, "ground14", "brick9", Direction.SOUTH, TrapType.EMPTY));
    
    GameData.addMapObject(new Tunnel(15.0f, 0.0f, 270.0f, "ground14", "brick9", Direction.NORTH, TrapType.EMPTY));
    GameData.addMapObject(new Tunnel(30.0f, 0.0f, 270.0f, "ground14", "brick9", Direction.NORTH, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(45.0f, 0.0f, 270.0f, "ground14", "brick9", Direction.NORTH, TrapType.EMPTY));
    GameData.addMapObject(new Tunnel(60.0f, 0.0f, 270.0f, "ground14", "brick9", Direction.NORTH, TrapType.EMPTY));
    GameData.addMapObject(new Tunnel(75.0f, 0.0f, 270.0f, "ground14", "brick9", Direction.NORTH, TrapType.EMPTY));

    GameData.addMapObject(new Corner(90.0f, 0.0f, 270.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(90.0f, 0.0f, 255.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));    
    GameData.addMapObject(new Corridor(90.0f, 0.0f, 240.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));    
    GameData.addMapObject(new Corridor(90.0f, 0.0f, 225.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));    

    GameData.addMapObject(new Staircase(90.0f, 0.0f, 210.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));
    
    GameData.addMapObject(new Corridor(90.0f, 15.0f, 195.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));    
    GameData.addMapObject(new Pit(90.0f, 15.0f, 180.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));    
    GameData.addMapObject(new Corridor(90.0f, 15.0f, 165.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));    
    GameData.addMapObject(new Pit(90.0f, 15.0f, 150.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));    
    GameData.addMapObject(new Corridor(90.0f, 15.0f, 135.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));    
    GameData.addMapObject(new Pit(90.0f, 15.0f, 120.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));    
    GameData.addMapObject(new Corridor(90.0f, 15.0f, 105.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));    

    GameData.addMapObject(new Staircase(90.0f, 0.0f, 90.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(90.0f, 0.0f, 75.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));    
    GameData.addMapObject(new Corridor(90.0f, 0.0f, 60.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));    
    GameData.addMapObject(new Corridor(90.0f, 0.0f, 45.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(90.0f, 0.0f, 30.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));    
    GameData.addMapObject(new Corridor(90.0f, 0.0f, 15.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));    

    GameData.addMapObject(new Corner(90.0f, 0.0f, 0.0f, "ground14", "brick9", Direction.NORTH, TrapType.EMPTY));
    
    GameData.addMapObject(new Corridor(75.0f, 0.0f, 0.0f, "ground14", "brick9", Direction.SOUTH, TrapType.EMPTY));    
    GameData.addMapObject(new Corridor(60.0f, 0.0f, 0.0f, "ground14", "brick9", Direction.SOUTH, TrapType.EMPTY));   
    
    GameData.addMapObject(new Corner(45.0f, 0.0f, 0.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(45.0f, 0.0f, 15.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));   
    
    GameData.addMapObject(new Field(45.0f, 0.0f, 45.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));   

    GameData.addMapObject(new Corridor(45.0f, 0.0f, 75.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));   

    GameData.addMapObject(new Field(45.0f, 0.0f, 105.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));   

    GameData.addMapObject(new Corridor(45.0f, 0.0f, 135.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));   

    GameData.addMapObject(new Field(45.0f, 0.0f, 165.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));   

    GameData.addMapObject(new Corridor(45.0f, 0.0f, 195.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));   
    GameData.addMapObject(new Corridor(45.0f, 0.0f, 210.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));   
    GameData.addMapObject(new Corridor(45.0f, 0.0f, 225.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));   
    
    GameData.addMapObject(new End(45.0f, 0.0f, 240.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));   
    
    GameData.addMapObject(new Corner(15.0f, 0.0f, 15.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(15.0f, 0.0f, 30.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));   

    GameData.addMapObject(new Kiosk(15.0f, 0.0f, 45.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));
    
    GameData.addMapObject(new Corridor(15.0f, 0.0f, 60.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));
    
    GameData.addMapObject(new Corridor(15.0f, 0.0f, 75.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));
    
    GameData.addMapObject(new Staircase(15.0f, 0.0f, 90.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));   

    GameData.addMapObject(new Corridor(15.0f, 15.0f, 105.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(15.0f, 15.0f, 120.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(15.0f, 15.0f, 135.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(15.0f, 15.0f, 150.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));
    
    GameData.addMapObject(new Corridor(15.0f, 15.0f, 165.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY)); 

    GameData.addMapObject(new Staircase(15.0f, 0.0f, 180.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));   
    
    GameData.addMapObject(new Corridor(15.0f, 0.0f, 195.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(15.0f, 0.0f, 210.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(15.0f, 0.0f, 225.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(15.0f, 0.0f, 240.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Corner(15.0f, 0.0f, 255.0f, "ground14", "brick9", Direction.SOUTH, TrapType.EMPTY));
    
    GameData.addMapObject(new Corridor(30.0f, 0.0f, 255.0f, "ground14", "brick9", Direction.NORTH, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(45.0f, 0.0f, 255.0f, "ground14", "brick9", Direction.SOUTH, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(60.0f, 0.0f, 255.0f, "ground14", "brick9", Direction.NORTH, TrapType.EMPTY));

    GameData.addMapObject(new Corner(75.0f, 0.0f, 255.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(75.0f, 0.0f, 240.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(75.0f, 0.0f, 225.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Staircase(75.0f, 0.0f, 210.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));   

    GameData.addMapObject(new Corridor(75.0f, 15.0f, 195.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(75.0f, 15.0f, 180.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(75.0f, 15.0f, 165.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(75.0f, 15.0f, 150.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(75.0f, 15.0f, 135.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(75.0f, 15.0f, 120.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(75.0f, 15.0f, 105.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Staircase(75.0f, 0.0f, 90.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));   

    GameData.addMapObject(new Corridor(75.0f, 0.0f, 75.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(75.0f, 0.0f, 60.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));
    
    GameData.addMapObject(new Corridor(75.0f, 0.0f, 45.0f, "ground14", "brick9", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(75.0f, 0.0f, 30.0f, "ground14", "brick9", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Corner(75.0f, 0.0f, 15.0f, "ground14", "brick9", Direction.NORTH, TrapType.EMPTY));
    
    GameData.addMapObject(new Staircase(60.0f, 0.0f, 15.0f, "ground14", "brick9", Direction.SOUTH, TrapType.EMPTY));   

    GameData.addMapObject(new Corridor(45.0f, 15.0f, 15.0f, "ground14", "brick9", Direction.SOUTH, TrapType.EMPTY));
    
    GameData.addMapObject(new Staircase(30.0f, 0.0f, 15.0f, "ground14", "brick9", Direction.NORTH, TrapType.EMPTY));   
    
    GameData.bindConnections();
        
    // Create the skybox...
    SkyBox skybox = new SkyBox(0, 0, 0, "iceflats");
    
    // Create the floor...
    Plane floor = new Plane(0, -1.0f, 0, "marble", Direction.EAST, 2000);

    // Create cubes above the staircase...
    for (int i = 0; i < 500; i++)
    {
      GameData.addGameObject(new Cube(45.0f, 50.0f + (2 * i), 45.0f, "crate1"));
    }
    
    // Create balls above the staircase...
    for (int i = 0; i < 10; i++)
    {
      GameData.addGameObject(new Ball(45.0f, 50.0f + (5 * i), 15.0f, "crate1", 1.5f));
    }
        
    // Hide the mouse cursor...
    Mouse.setGrabbed(true);
    
    while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
    {
      camera = cameraManager.getActiveCamera();
    	
      // Get input from the user...
      getInput();
      
      // Prepare for rendering...
      glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
      glLoadIdentity();
      
      // Draw the skybox...
      glPushMatrix();
      {
        glDepthMask(false);
        glRotatef(camera.getPitch(), 1.0f, 0.0f, 0.0f);
        glRotatef(camera.getYaw(), 0.0f, 1.0f, 0.0f);
        skybox.draw();
        glDepthMask(true);
      }
      glPopMatrix();
      
      // Orient the camera...
      camera.lookThrough();

      // Global Ambient Light Model...
      FloatBuffer ambientColor = BufferConverter.asFloatBuffer(new float[] { 0.2f, 0.2f, 0.2f, 1.0f });
      glLightModel(GL_LIGHT_MODEL_AMBIENT, ambientColor);
      
      // Local Viewport Model...
      glLightModeli(GL_LIGHT_MODEL_LOCAL_VIEWER, GL_TRUE);
      
      // Add positional light...
      FloatBuffer lightDiffuse = BufferConverter.asFloatBuffer(new float[] { 1.0f, 1.0f, 1.0f, 1.0f });
      FloatBuffer lightSpecular = BufferConverter.asFloatBuffer(new float[] { 1.0f, 1.0f, 1.0f, 1.0f });
      FloatBuffer lightPosition = BufferConverter.asFloatBuffer(new float[] { -100.0f, 750.0f, 1000.0f, 1.0f });
      
      //glLight(GL_LIGHT0, GL_AMBIENT, lightAmibent);
      glLight(GL_LIGHT0, GL_DIFFUSE, lightDiffuse);
      glLight(GL_LIGHT0, GL_SPECULAR, lightSpecular);
      glLight(GL_LIGHT0, GL_POSITION, lightPosition);
      
      // Picking code for 3D selection of game objects...
      if (Keyboard.isKeyDown(Keyboard.KEY_F))
      {
        Picker.startPicking();
        {
          // Draw the game objects...
          for (GameObject gameObject : GameData.getGameObjects())
          {
          	glPushName(gameObject.id);
          	{
            	gameObject.draw();
          	}
          	glPopName();
          }
        }
        Picker.stopPicking();
      }
      
      // Draw the player...
      player.draw();
      
      // Draw the floor...
      floor.draw();
      
      // Draw the map objects...
      for (MapObject mapObject : GameData.getMapObjects())
      {
        mapObject.draw();
      }
      
      // Draw the game objects...
      for (GameObject gameObject : GameData.getGameObjects())
      {
      	gameObject.draw();
      }
            
      // Draw text to the screen...
      if (camera.getType() == CameraType.SPECTATOR)
      {
        FontTools.renderText("Spectator Camera: (" + camera.getX() + ", " + camera.getY() + ", " + camera.getZ() + ")", 10, 10, Color.black, 1);
      }
      else
      {
      	FontTools.renderText("Player: " + player.getName(), 10, 10, Color.black, 1);
      	FontTools.renderText("Team: " + player.getTeam(), 10, 30, Color.black, 1);
      	FontTools.renderText("Lives: " + player.getLives(), 10, 50, Color.black, 1);
        FontTools.renderText("Player Camera: (" + player.getCamera().getX() + ", " + player.getCamera().getY() + ", " + player.getCamera().getZ() + ")", 10, 70, Color.black, 1);
      }
      
      // Update...
      cameraManager.update();
      PhysicsWorld.stepSimulation(1 / 60.0f);
      Timer.tick();
      Display.update();
      Display.sync(60);
    }
  }
  
  /**
   * Gets user input from the keyboard and mouse.
   */
  private static void getInput()
  {
    // Used for controlling the camera with the keyboard and mouse...
    float dx = 0.0f;
    float dy = 0.0f;
    float dt = 0.0f;
    
    // Set the mouse sensitivity...
    float mouseSensitivity = 0.08f;
    float movementSpeed = 0.02f;
        
    dx = Mouse.getDX();
    dy = Mouse.getDY();
    dt = Timing.getDelta();
    
    camera.yaw(dx * mouseSensitivity);
    camera.pitch(-dy * mouseSensitivity);
    
    // Camera related input...
    if (Keyboard.isKeyDown(Keyboard.KEY_R))
    {
    	cameraManager.chooseNextCamera();
    }
    
    // Movement related input...
    if (Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
    {
    	camera.moveForward(movementSpeed * dt * 2);
    }
    else if (Keyboard.isKeyDown(Keyboard.KEY_W)) camera.moveForward(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_S)) camera.moveBackward(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_A)) camera.moveLeft(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_D)) camera.moveRight(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_UP)) camera.moveUp(movementSpeed * dt);
    if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) camera.moveDown(movementSpeed * dt);
  }
  
  /**
   * Cleans up resources.
   */
  private static void destroyOpenGL()
  {
    Display.destroy();
  }

  public static void main(String[] args)
  {
    GraphicsTestTroy.createOpenGL();
    GraphicsTestTroy.gameLoop();
    GraphicsTestTroy.destroyOpenGL();
  }
}
