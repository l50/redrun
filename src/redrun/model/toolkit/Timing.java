package redrun.model.toolkit;

import org.lwjgl.Sys;

/**
 * This class manages timing data for movement and mouse controls.
 * 
 * @author Troy Squillaci
 * @author Jake Nichol
 * @version 1.0
 * @since 2014-10-23
 */
public class Timing
{
  /** Time at last frame */
  public static long lastFrame;

  /** Frames per second */
  public static int fps;

  /** Last FPS time */
  public static long lastFPS;

  /**
   * Get the accurate system time for LWJGL.
   * 
   * @return The system time in milliseconds
   */
  public static long getTime()
  {
    return (Sys.getTime() * 1000) / Sys.getTimerResolution();
  }

  /**
   * Calculate how many milliseconds have passed since last frame.
   * 
   * @return milliseconds passed since last frame
   */
  public static int getDelta()
  {
    long time = getTime();
    int delta = (int) (time - lastFrame);
    lastFrame = time;
    return delta;
  }
}
