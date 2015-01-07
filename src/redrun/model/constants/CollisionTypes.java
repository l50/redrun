package redrun.model.constants;

/**
 * Utility class to provide information to the collision detector
 * The constants in this class are placed into the existing RigidBody objects
 * so that bullet will hold the information and pass it to the collision detector
 * @author jem
 * @date 141208
 */
public class CollisionTypes
{
  /** Default -- when we dont care about the collision */
  public static final int NO_COLLISION_TYPE             = 0;
  /** The collision type held by the player */
  public static final int PLAYER_COLLISION_TYPE         = 1 << 6; 
  /** Tells the player that if it collides with this object to kill the player*/
  public static final int INSTANT_DEATH_COLLISION_TYPE  = 1 << 7;
  /** Tells the player to explode */
  public static final int EXPLOSION_COLLISION_TYPE      = 1 << 8;
  /** Only hurts the player */
  public static final int MINIMAL_DAMAGE_COLLISION_TYPE = 1 << 9;
  /** Tells the player its run into a wall and not to jump up it */
  public static final int WALL_COLLISION_TYPE           = 1 << 10;
  /** Tells the player its reached the end */
  public static final int END_COLLISION_TYPE            = 1 << 11;
}
