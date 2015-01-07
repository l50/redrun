package redrun.graphics.camera;

import java.util.ArrayList;

import org.lwjgl.util.Timer;

/**
 * Used for managing the list of cameras in an OpenGL scene.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-29
 */
public class CameraManager
{
  /** The list of all camera in the scene. */
  private ArrayList<Camera> cameras = new ArrayList<Camera>();

  /** The index of camera that is currently being used to look through. */
  private int active = 0;

  /** Indicates if another camera can be selected. */
  private boolean selectable = true;

  /** Used for timed selection. */
  private Timer timer = new Timer();

  /**
   * Creates a camera manager that keeps track of the two most common cameras -
   * the spectator camera and the player camera.
   * 
   * @param spectator the spectator camera
   * @param player the player camera
   */
  public CameraManager(Camera spectator, Camera player)
  {
    cameras.add(player);
    cameras.add(spectator);
  }

  /**
   * Switches to the next camera in the list.
   */
  public void chooseNextCamera()
  {
    if (selectable)
    {
      active++;
      if (active == cameras.size()) active = 0;
      timer.resume();
      selectable = false;
    }
  }

  // Getter methods...
  /**
   * Gets the active camera from the list of all camera in the scene.
   * 
   * @return the active camera
   */
  public Camera getActiveCamera()
  {
    return cameras.get(active);
  }

  /**
   * Updates the timer.
   */
  public void update()
  {
    if (timer.getTime() >= 0.5f)
    {
      timer.reset();
      timer.pause();
      selectable = true;
    }
  }
}
