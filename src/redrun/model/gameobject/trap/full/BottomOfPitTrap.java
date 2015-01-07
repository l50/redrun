package redrun.model.gameobject.trap.full;

import java.awt.Dimension;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.constants.CollisionTypes;
import redrun.model.constants.Direction;
import redrun.model.gameobject.trap.Trap;
import redrun.model.physics.BoxPhysicsBody;


/**
 * Trap to kill players in an empty pit
 * 
 * @author Adam Michell, Jordan Medlock
 *         
 * @version 1.0
 * @since 2014-12-9
 */
public class BottomOfPitTrap extends Trap
{

  /**
   * BottomOfPitTrap constructor makes a new BottomOfPitTrap at the given location
   * 
   * @param x position 
   * @param y position
   * @param z position 
   * @param orientation
   * @param dimension
   */
  public BottomOfPitTrap(float x, float y, float z, Direction orientation, Dimension dim)
  {
    super(x, y, z, orientation, null);
    this.body = new BoxPhysicsBody(new Vector3f(x, y - 15, z), new Vector3f((float) (dim.width / 3), 3.0f,
        (float) (dim.height / 3)), new Quat4f(), 0.0f, CollisionTypes.INSTANT_DEATH_COLLISION_TYPE);
  }

  @Override
  public void activate()
  {    
  }

  @Override
  public void reset()
  {    
  }

  @Override
  public void interact()
  {    
  }

  @Override
  public void update()
  {    
  }

}
