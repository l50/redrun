package redrun.model.physics;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

/**
 * CubePhysicsBody
 * Creates a perfect cube
 * @author Jordan Medlock
 * @date 2014-12-06
 */
public class CubePhysicsBody extends BoxPhysicsBody
{

  /**
   * Constructs a box with all of the sides equal to radius
   * 
   * @param center
   * @param radiuses
   * @param direction
   * @param mass
   * @param collisionType
   */
  public CubePhysicsBody(Vector3f center, float radius, Quat4f direction, float mass, int collisionTypes)
  {
    super(center, new Vector3f(radius,radius,radius), direction, mass, collisionTypes);
  }

  /**
   * Constructs a box with all of the sides equal to radius
   * 
   * @param center
   * @param radiuses
   * @param direction
   * @param mass
   */
  public CubePhysicsBody(Vector3f center, float radius, Quat4f direction, float mass)
  {
    this(center, radius, direction, mass, 0);
  }
}
