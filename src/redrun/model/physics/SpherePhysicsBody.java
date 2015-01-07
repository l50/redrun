package redrun.model.physics;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import com.bulletphysics.collision.shapes.SphereShape;

/**
 * A physics body for a sphere.
 * @author Jordan Medlock
 * @date 2014-12-06
 */
public class SpherePhysicsBody extends PhysicsBody
{
  /**
   * Creates a spherical physics body 
   * @param center in meters
   * @param radius in meters
   * @param mass in kg
   */
  public SpherePhysicsBody(Vector3f center, float radius, float mass, int collisionTypes)
  {
    super(mass, new Quat4f(0,0,0,1), center, new SphereShape(radius), collisionTypes);
  }
  
  /**
   * Creates a spherical physics body 
   * @param center in meters
   * @param radius in meters
   * @param mass in kg
   */
  public SpherePhysicsBody(Vector3f center, float radius, float mass)
  {
    super(mass, new Quat4f(0,0,0,1), center, new SphereShape(radius), 0);
  }
}
