package redrun.model.physics;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import com.bulletphysics.collision.shapes.BoxShape;

/**
 * A physics body for a plane.
 * @author Jordan Medlock
 * @date 2014-12-06
 */
public class PlanePhysicsBody extends PhysicsBody
{
  /**
   * Creates an plane. This is the simplest and easiest physics body to use
   * and should be used wherever possible.
   * @param center in meters
   * @param normal the vector that is perpendicular to the plane
   * @param mass in kg
   * @param collisionTypes the collision type
   */
  public PlanePhysicsBody(Vector3f center, Vector3f radiuses, float mass, int collisionTypes)
  {
    super(mass, new Quat4f(0,0,0,1), center,  new BoxShape(PhysicsTools.openGLToBullet(radiuses)), collisionTypes);
  }
  
  /**
   * Creates an plane. This is the simplest and easiest physics body to use
   * and should be used wherever possible.
   * @param center in meters
   * @param normal the vector that is perpendicular to the plane
   * @param mass in kg
   */
  public PlanePhysicsBody(Vector3f center, Vector3f radiuses, float mass)
  {
    super(mass, new Quat4f(0,0,0,1), center,  new BoxShape(PhysicsTools.openGLToBullet(radiuses)), 0);
  }
}
