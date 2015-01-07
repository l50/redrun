package redrun.model.physics;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import com.bulletphysics.collision.shapes.BoxShape;

/**
 * A physics body in the shape of a box/ rectangular prism.
 * @author Jordan Medlock
 * @date 2014-12-06
 */
public class BoxPhysicsBody extends PhysicsBody
{
  /**
   * Creates a new box shaped physics body
   * @param center in meters
   * @param radiuses in meters
   * @param direction a quaternion
   * @param mass in kg
   * @param collisionTypes
   */
  public BoxPhysicsBody(Vector3f center, Vector3f radiuses, Quat4f direction, float mass, int collisionTypes)
  {
    super(mass, direction, center, new BoxShape(PhysicsTools.openGLToBullet(radiuses)), collisionTypes);
  }
  
  /**
   * Creates a new box shaped physics body
   * @param center in meters
   * @param radiuses in meters
   * @param direction a quaternion
   * @param mass in kg
   */
  public BoxPhysicsBody(Vector3f center, Vector3f radiuses, Quat4f direction, float mass)
  {
    super(mass, direction, center, new BoxShape(PhysicsTools.openGLToBullet(radiuses)), 0);
  }
}
