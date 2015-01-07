package redrun.model.gameobject.trap;

import redrun.model.constants.Direction;
import redrun.model.gameobject.GameObject;

/**
 * This class represents a trap that can be activated by a player.
 * 
 * @author Troy Squillaci, Jake Nichol
 * @version 1.0
 * @since 2014-11-07
 */
public abstract class Trap extends GameObject
{
  /**
   * Creates a new trap at the specified position.
   * 
   * @param x the x position of the trap
   * @param y the y position of the trap
   * @param z the z position of the trap
   */
  public Trap(float x, float y, float z, Direction orientation, String textureName)
  {
    super(x, y, z, textureName);
  }
  
  /**
   * Gets the network string representation of the trap.
   * 
   * @return the network string representation of the trap
   */
  public String getNetworkString()
  {
    return "=== Trap === ID:" + this.id + " ===";
  }
  
  /**
   * Activates the trap.
   */
  public abstract void activate();
  
  /**
   * Resets the trap.
   */
  public abstract void reset();
}
