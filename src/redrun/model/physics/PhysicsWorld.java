package redrun.model.physics;

import java.util.HashSet;

import javax.vecmath.Vector3f;

import com.bulletphysics.BulletGlobals;
import com.bulletphysics.ContactProcessedCallback;
import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.narrowphase.ManifoldPoint;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;

/**
 * PhysicsWorld A set of global static variables and functions that are used to
 * work the physics
 * 
 * @author Jordan Medlock
 * @date 2014-12-06
 */
public class PhysicsWorld
{
  /** Keeps track of everything and does all the calculations */
  public static DiscreteDynamicsWorld world;

  /** Holds a list of all the bodies that are added to the world */
  public static HashSet<PhysicsBody> bodies;

  /** Holds all of the objects that want to be notified when they collide */
  public static HashSet<PhysicsBody> watchList;

  /**
   * Static initialization Loads all of the information needed for physics and
   * collision detection
   */
  static
  {
    BroadphaseInterface broadphase = new DbvtBroadphase();

    // Set up the collision configuration and dispatcher
    DefaultCollisionConfiguration collisionConfiguration = new DefaultCollisionConfiguration();
    CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfiguration);

    // The actual physics solver
    SequentialImpulseConstraintSolver solver = new SequentialImpulseConstraintSolver();

    world = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
    world.setGravity(new Vector3f(0, -10, 0));
    bodies = new HashSet<PhysicsBody>();
    watchList = new HashSet<PhysicsBody>();
    ContactProcessedCallback callback = new ContactProcessedCallback()
    {

      @Override
      public boolean contactProcessed(ManifoldPoint arg0, Object obj1, Object obj2)
      {
        CollisionObject o1 = (CollisionObject) obj1;
        CollisionObject o2 = (CollisionObject) obj2;

        for (PhysicsBody body : watchList)
        {
          if (body.body == o1)
          {

            body.collidedWith(o2);
          }
          else if (body.body == o2)
          {

            body.collidedWith(o1);
          }
        }
        return false;
      }
    };
    BulletGlobals.setContactProcessedCallback(callback);
  }

  /**
   * addPhysicsBody adds a physics body to the world and activates it
   * 
   * @param body body to activate
   */
  public static void addPhysicsBody(PhysicsBody body)
  {
    world.addRigidBody(body.body);
    bodies.add(body);
  }

  /**
   * removePhysicsBody allows you to remove bodies from the world after they
   * have been created
   * 
   * @param body body to remove
   */
  public static void removePhysicsBody(PhysicsBody body)
  {
    world.removeRigidBody(body.body);
    bodies.remove(body);
  }

  /**
   * stepSimulation steps the physics simulation by timeStep seconds
   * 
   * @param timeStep step through the physics
   */
  public static void stepSimulation(float timeStep)
  {
    world.stepSimulation(timeStep, 10);
    for (PhysicsBody body : bodies)
    {
      body.callback();
    }

  }

  /**
   * addToWatchList adds the physics body to a list that will be notified
   * whenever they collide with another object using the collideWith method
   * 
   * @param body body that has collided with another object
   */
  public static void addToWatchList(PhysicsBody body)
  {
    watchList.add(body);
  }
}
