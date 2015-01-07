package redrun.model.constants;

import java.util.Random;

/**
 * Constants Static constants
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-11-9
 * 
 */
public class Constants
{
  // The static info for the game
  public static final Random random = new Random();
  public static final int DISPLAY_WIDTH = 1280;
  public static final int DISPLAY_HEIGHT = 720;
  public static final int MAX_PLAYERS = 4;
  public static final int MIN_PLAYERS = 4;
  
  //Used for controlling the camera with the keyboard and mouse...
  public static float DX = 0.0f;
  public static float DY = 0.0f;

  // Set the mouse sensitivity...
  public final static float MOUSE_SENSITIVITY = 0.08f;
  public final static float MOVEMENT_SPEED_PLAYER = 20f;
  public final static float MOVEMENT_SPEED_SPECTATOR = 0.4f;
}
