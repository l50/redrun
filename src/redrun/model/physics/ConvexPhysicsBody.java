/**
 * 
 */
package redrun.model.physics;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import com.bulletphysics.collision.shapes.ConvexHullShape;
import com.bulletphysics.util.ObjectArrayList;


/**
 * ConvexPhysicsBody
 * Used to create a custom physics body in any complex convex shape
 * @author Jordan Medlock
 * @date 2014-12-06
 */
public class ConvexPhysicsBody extends PhysicsBody
{
  /**
   * Creates a convex shaped physics body
   * you should define points with respect to center 
   * The way that this was described to me is that you can take a balloon and blow it up arround the points 
   * then let it deflate and that is the shape you will be left with.
   * @param points the points that define the shape in meters with respect to center
   * @param center in meters
   * @param mass in kg
   */
  public ConvexPhysicsBody(Vector3f[] points, Vector3f center, float mass, int collisionTypes)
  {
    super(mass, new Quat4f(0,0,0,1), center,
        new ConvexHullShape(new ObjectArrayList<javax.vecmath.Vector3f>(points.length)), collisionTypes);
    for (Vector3f vec : points)
    {
      ((ConvexHullShape) this.getCollisionShape()).addPoint(PhysicsTools.openGLToBullet(vec));
    }
  }
  
  /**
   * Creates a convex shaped physics body
   * you should define points with respect to center 
   * The way that this was described to me is that you can take a balloon and blow it up arround the points 
   * then let it deflate and that is the shape you will be left with.
   * @param points the points that define the shape in meters with respect to center
   * @param center in meters
   * @param mass in kg
   */
  public ConvexPhysicsBody(Vector3f[] points, Vector3f center, float mass)
  {
    super(mass, new Quat4f(0,0,0,1), center,
        new ConvexHullShape(new ObjectArrayList<javax.vecmath.Vector3f>(points.length)), 0);
    for (Vector3f vec : points)
    {
      ((ConvexHullShape) this.getCollisionShape()).addPoint(PhysicsTools.openGLToBullet(vec));
    }
  }
}
