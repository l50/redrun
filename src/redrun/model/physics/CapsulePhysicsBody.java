package redrun.model.physics;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import com.bulletphysics.collision.dispatch.CollisionFlags;
import com.bulletphysics.collision.shapes.CapsuleShape;

/**
 * CapsulePhysicsBody
 * Its shaped like a pill with a cylander and two semi-spheres on each end
 * Its only aligned to the y-axis
 * @author Jordan Medlock
 * @date 2014-12-06
 */
public class CapsulePhysicsBody extends PhysicsBody
{
  /**
   * Creates a capsule physics body
   * 
   * @param radius in meters
   * @param height
   */
  public CapsulePhysicsBody(Vector3f center, float radius, float mass, float height, int collisionTypes)
  {
    super(mass, new Quat4f(0, 0, 0, 1), center, new CapsuleShape(radius, height), collisionTypes);

    body.setSleepingThresholds(0, 0);
    body.setAngularFactor(0);
    body.setDamping(0.85f, 1);
    body.setFriction(1f);
    body.setGravity(PhysicsTools.openGLToBullet(new Vector3f(0f,-100f,0f)));
    body.setCollisionFlags(body.getCollisionFlags() | CollisionFlags.CUSTOM_MATERIAL_CALLBACK);
  }
  
  /**
   * Creates a capsule physics body
   * 
   * @param radius in meters
   * @param height
   */
  public CapsulePhysicsBody(Vector3f center, float radius, float mass, float height)
  {
    super(mass, new Quat4f(0, 0, 0, 1), center, new CapsuleShape(radius, height), 0);

    body.setSleepingThresholds(0, 0);
    body.setAngularFactor(0);
    body.setDamping(0.85f, 1);
    body.setFriction(1f);
    body.setGravity(PhysicsTools.openGLToBullet(new Vector3f(0f,-100f,0f)));
    body.setCollisionFlags(body.getCollisionFlags() | CollisionFlags.CUSTOM_MATERIAL_CALLBACK);
  }
}
