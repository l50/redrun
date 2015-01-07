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

import redrun.graphics.camera.Camera;
import redrun.graphics.camera.CameraManager;
import redrun.graphics.selection.Picker;
import redrun.model.constants.CameraType;
import redrun.model.constants.Constants;
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
import redrun.model.gameobject.world.Plane;
import redrun.model.gameobject.world.SkyBox;
import redrun.model.physics.PhysicsWorld;
import redrun.model.toolkit.BufferConverter;
import redrun.model.toolkit.HUD_Manager;
import redrun.model.toolkit.LoadingScreen;
import static org.lwjgl.opengl.GL11.*;

/**
 * This class is for testing OpenGL scenes.
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-11-03
 */
public class GraphicsTestAdam
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
      Display.setDisplayMode(new DisplayMode(Constants.DISPLAY_WIDTH, Constants.DISPLAY_HEIGHT));
      Display.setTitle("RedRun Ice World");
      Display.create();
      Display.setVSyncEnabled(true);
    }
    catch (LWJGLException ex)
    {
      Logger.getLogger(GraphicsTestAdam.class.getName()).log(Level.SEVERE, null, ex);
    }

    player = new Player(0.0f, 1.0f, 0.0f, "Linvala, Keeper of Silence", Team.BLUE);

    Camera spectatorCam = new Camera(70, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000, 0.0f,
        1.0f, 0.0f, CameraType.SPECTATOR);
    Camera playerCam = player.getCamera();

    cameraManager = new CameraManager(spectatorCam, playerCam);

    glEnable(GL_DEPTH_TEST);
    glDisable(GL_COLOR_MATERIAL);
    glEnable(GL_LIGHTING);
    glEnable(GL_LIGHT0);
    glEnable(GL_NORMALIZE);
    glShadeModel(GL_SMOOTH);

    LoadingScreen.loadingScreen();
  }

  /**
   * The main loop where the logic occurs. Stopped when the escape key is
   * pressed or the window is closed.
   */
  private static void gameLoop()
  {
    // Create the map objects...

    // Make the obstacle course...
    GameData.addMapObject(new Start(0.0f, 0.0f, 0.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(0.0f, 0.0f, 15.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(0.0f, 0.0f, 30.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Pit(0.0f, 15.0f, 45.0f, "ground14", "brick8", Direction.EAST,
        TrapType.JAIL)); //
    GameData.addMapObject(new Corridor(0.0f, 0.0f, 60.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(0.0f, 0.0f, 75.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Staircase(0.0f, 0.0f, 90.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(0.0f, 15.0f, 105.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY)); //
    GameData.addMapObject(new Corridor(0.0f, 15.0f, 120.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));
    GameData
        .addMapObject(new Corridor(0.0f, 15.0f, 135.0f, "ground14", "brick8", Direction.EAST, TrapType.SPIKE_FIELD));
    GameData.addMapObject(new Corridor(0.0f, 15.0f, 150.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(0.0f, 15.0f, 165.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Staircase(0.0f, 0.0f, 180.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(0.0f, 0.0f, 195.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(0.0f, 0.0f, 210.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(0.0f, 0.0f, 225.0f, "ground14", "brick8", Direction.EAST,
        TrapType.SPIKE_TRAP_DOOR));
    GameData.addMapObject(new Corridor(0.0f, 0.0f, 240.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(0.0f, 0.0f, 255.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Corner(0.0f, 0.0f, 270.0f, "ground14", "brick8", Direction.SOUTH, TrapType.EMPTY));

    GameData.addMapObject(new Tunnel(15.0f, 0.0f, 270.0f, "ground14", "brick8", Direction.NORTH, TrapType.EMPTY));
    GameData.addMapObject(new Tunnel(30.0f, 0.0f, 270.0f, "ground14", "brick8", Direction.NORTH, TrapType.JAIL));
    GameData.addMapObject(new Corridor(45.0f, 0.0f, 270.0f, "ground14", "brick8", Direction.NORTH, TrapType.EMPTY));
    GameData.addMapObject(new Tunnel(60.0f, 0.0f, 270.0f, "ground14", "brick8", Direction.NORTH, TrapType.POLE_WALL));
    GameData.addMapObject(new Tunnel(75.0f, 0.0f, 270.0f, "ground14", "brick8", Direction.NORTH, TrapType.EMPTY));

    GameData.addMapObject(new Corner(90.0f, 0.0f, 270.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(90.0f, 0.0f, 255.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(90.0f, 0.0f, 240.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(90.0f, 0.0f, 225.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Staircase(90.0f, 0.0f, 210.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(90.0f, 15.0f, 195.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));
    GameData.addMapObject(new Pit(90.0f, 15.0f, 180.0f, "ground14", "brick8", Direction.WEST, TrapType.SPIKE_FIELD));
    GameData.addMapObject(new Corridor(90.0f, 15.0f, 165.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));
    GameData.addMapObject(new Pit(90.0f, 15.0f, 150.0f, "ground14", "brick8", Direction.WEST, TrapType.TRAP_DOOR));
    GameData.addMapObject(new Corridor(90.0f, 15.0f, 135.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));
    GameData.addMapObject(new Pit(90.0f, 15.0f, 120.0f, "ground14", "brick8", Direction.WEST, TrapType.SPIKE_FIELD));
    GameData.addMapObject(new Corridor(90.0f, 15.0f, 105.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Staircase(90.0f, 0.0f, 90.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(90.0f, 0.0f, 75.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(90.0f, 0.0f, 60.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(90.0f, 0.0f, 45.0f, "ground14", "brick8", Direction.WEST, TrapType.SPIKE_FIELD));
    GameData.addMapObject(new Corridor(90.0f, 0.0f, 30.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(90.0f, 0.0f, 15.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Corner(90.0f, 0.0f, 0.0f, "ground14", "brick8", Direction.NORTH, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(75.0f, 0.0f, 0.0f, "ground14", "brick8", Direction.SOUTH, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(60.0f, 0.0f, 0.0f, "ground14", "brick8", Direction.SOUTH, TrapType.EMPTY));

    GameData.addMapObject(new Corner(45.0f, 0.0f, 0.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(45.0f, 0.0f, 15.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Field(45.0f, 0.0f, 45.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(45.0f, 0.0f, 75.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Field(45.0f, 0.0f, 105.0f, "ground14", "brick8", Direction.EAST,
        TrapType.EXPLODING_BOX_FIELD));

    GameData.addMapObject(new Corridor(45.0f, 0.0f, 135.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Field(45.0f, 0.0f, 165.0f, "ground14", "brick8", Direction.EAST, TrapType.ROCK_SMASH));

    GameData.addMapObject(new Corridor(45.0f, 0.0f, 195.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(45.0f, 0.0f, 210.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(45.0f, 0.0f, 225.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new End(45.0f, 0.0f, 240.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));

    // Make the kiosks and paths...
    GameData.addMapObject(new Corner(15.0f, 0.0f, 15.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(15.0f, 0.0f, 30.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Kiosk(15.0f, 0.0f, 45.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY)); // Kiosk

    GameData.addMapObject(new Corridor(15.0f, 0.0f, 60.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(15.0f, 0.0f, 75.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Staircase(15.0f, 0.0f, 90.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Kiosk(15.0f, 15.0f, 105.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY)); // Kiosk
    GameData.addMapObject(new Corridor(15.0f, 15.0f, 120.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Kiosk(15.0f, 15.0f, 135.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY)); // Kiosk
    GameData.addMapObject(new Corridor(15.0f, 15.0f, 150.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Kiosk(15.0f, 15.0f, 165.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY)); // Kiosk

    GameData.addMapObject(new Staircase(15.0f, 0.0f, 180.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(15.0f, 0.0f, 195.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(15.0f, 0.0f, 210.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Kiosk(15.0f, 0.0f, 225.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY)); // Kiosk

    GameData.addMapObject(new Corridor(15.0f, 0.0f, 240.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Corner(15.0f, 0.0f, 255.0f, "ground14", "brick8", Direction.SOUTH, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(30.0f, 0.0f, 255.0f, "ground14", "brick8", Direction.NORTH, TrapType.EMPTY));

    GameData.addMapObject(new Kiosk(45.0f, 0.0f, 255.0f, "ground14", "brick8", Direction.SOUTH, TrapType.EMPTY)); // Kiosk

    GameData.addMapObject(new Corridor(60.0f, 0.0f, 255.0f, "ground14", "brick8", Direction.NORTH, TrapType.EMPTY));

    GameData.addMapObject(new Corner(75.0f, 0.0f, 255.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Kiosk(75.0f, 0.0f, 240.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY)); // Kiosk

    GameData.addMapObject(new Corridor(75.0f, 0.0f, 225.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Staircase(75.0f, 0.0f, 210.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Kiosk(75.0f, 15.0f, 195.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY)); // Kiosk
    GameData.addMapObject(new Corridor(75.0f, 15.0f, 180.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Kiosk(75.0f, 15.0f, 165.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY)); // Kiosk

    GameData.addMapObject(new Corridor(75.0f, 15.0f, 150.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Kiosk(75.0f, 15.0f, 135.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY)); // Kiosk

    GameData.addMapObject(new Corridor(75.0f, 15.0f, 120.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(75.0f, 15.0f, 105.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Staircase(75.0f, 0.0f, 90.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(75.0f, 0.0f, 75.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));
    GameData.addMapObject(new Corridor(75.0f, 0.0f, 60.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Kiosk(75.0f, 0.0f, 45.0f, "ground14", "brick8", Direction.EAST, TrapType.EMPTY)); // Kiosk

    GameData.addMapObject(new Corridor(75.0f, 0.0f, 30.0f, "ground14", "brick8", Direction.WEST, TrapType.EMPTY));

    GameData.addMapObject(new Corner(75.0f, 0.0f, 15.0f, "ground14", "brick8", Direction.NORTH, TrapType.EMPTY));

    GameData.addMapObject(new Staircase(60.0f, 0.0f, 15.0f, "ground14", "brick8", Direction.SOUTH, TrapType.EMPTY));

    GameData.addMapObject(new Corridor(45.0f, 15.0f, 15.0f, "ground14", "brick8", Direction.SOUTH, TrapType.EMPTY));

    GameData.addMapObject(new Staircase(30.0f, 0.0f, 15.0f, "ground14", "brick8", Direction.NORTH, TrapType.EMPTY));

    GameData.bindConnections();

    // Create the game objects...

    // Create the player...

    // Create the skybox...
    SkyBox skybox = new SkyBox(0, 0, 0, "iceflats");

    // Create the floor...
    Plane floor = new Plane(0, -1.0f, 0, "marble", Direction.EAST, 2000);

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

      // glLight(GL_LIGHT0, GL_AMBIENT, lightAmibent);
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

      // pass the camera to the hud
      HUD_Manager.huds(camera, player);

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

    Constants.DX = Mouse.getDX();
    Constants.DY = Mouse.getDY();

    // Camera related input...
    if (Keyboard.isKeyDown(Keyboard.KEY_R))
    {
      cameraManager.chooseNextCamera();
    }

    // Movement related input...
    if (camera.getType() == CameraType.PLAYER)
    {
      player.yaw(Constants.DX * Constants.MOUSE_SENSITIVITY);
      player.pitch(-Constants.DY * Constants.MOUSE_SENSITIVITY);
      if (Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
      {
        player.walkForward(Constants.MOVEMENT_SPEED_PLAYER * 2);
      }
      else if (Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_D)) player
          .walkForwardRight(Constants.MOVEMENT_SPEED_PLAYER);
      else if (Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_A)) player
          .walkForwardLeft(Constants.MOVEMENT_SPEED_PLAYER);
      else if (Keyboard.isKeyDown(Keyboard.KEY_S) && Keyboard.isKeyDown(Keyboard.KEY_D)) player
          .walkBackRight(Constants.MOVEMENT_SPEED_PLAYER);
      else if (Keyboard.isKeyDown(Keyboard.KEY_S) && Keyboard.isKeyDown(Keyboard.KEY_A)) player
          .walkBackLeft(Constants.MOVEMENT_SPEED_PLAYER);

      else if (Keyboard.isKeyDown(Keyboard.KEY_W)) player.walkForward(Constants.MOVEMENT_SPEED_PLAYER);
      else if (Keyboard.isKeyDown(Keyboard.KEY_S)) player.walkBackward(Constants.MOVEMENT_SPEED_PLAYER);
      else if (Keyboard.isKeyDown(Keyboard.KEY_A)) player.walkLeft(Constants.MOVEMENT_SPEED_PLAYER);
      else if (Keyboard.isKeyDown(Keyboard.KEY_D)) player.walkRight(Constants.MOVEMENT_SPEED_PLAYER);
      if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) player.jump();
    }
    else if (camera.getType() == CameraType.SPECTATOR)
    {
      camera.yaw(Constants.DX * Constants.MOUSE_SENSITIVITY);
      camera.pitch(-Constants.DY * Constants.MOUSE_SENSITIVITY);

      if (Keyboard.isKeyDown(Keyboard.KEY_W)) camera.moveForward(Constants.MOVEMENT_SPEED_SPECTATOR);
      if (Keyboard.isKeyDown(Keyboard.KEY_S)) camera.moveBackward(Constants.MOVEMENT_SPEED_SPECTATOR);
      if (Keyboard.isKeyDown(Keyboard.KEY_A)) camera.moveLeft(Constants.MOVEMENT_SPEED_SPECTATOR);
      if (Keyboard.isKeyDown(Keyboard.KEY_D)) camera.moveRight(Constants.MOVEMENT_SPEED_SPECTATOR);
      if (Keyboard.isKeyDown(Keyboard.KEY_UP)) camera.moveUp(Constants.MOVEMENT_SPEED_SPECTATOR);
      if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) camera.moveDown(Constants.MOVEMENT_SPEED_SPECTATOR);
    }
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
    GraphicsTestAdam.createOpenGL();
    GraphicsTestAdam.gameLoop();
    GraphicsTestAdam.destroyOpenGL();
  }
}
