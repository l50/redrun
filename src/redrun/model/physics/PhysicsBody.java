package redrun.model.physics;

import java.nio.FloatBuffer;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;


import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;

/**
 * Super class for all other physics bodies. creates convenience methods that
 * can be used in the model objects to perform operations on the physics bodies.
 * 
 * @author Jordan Medlock
 * @date 2014-12-06
 */
public class PhysicsBody
{
  /** trans is a instance varaible that is used to speed up getting and setting of x,y,z*/
  private Transform trans = new Transform();
  
  /** The most imprtant variable in this class hold all of the physics information*/
  public RigidBody body;
  public boolean canJump = true;

  /**
   * Creates a simple physics body
   * 
   * @param mass in kg if mass is 0 it is static
   * @param direction that the physics body faces
   * @param center in meters
   * @param collisionShape
   * @param collisionType an int from either CollisionFlags or CollisionTypes and they can be joined
   */
  public PhysicsBody(float mass, Quat4f direction, Vector3f center, CollisionShape collisionShape, int collisionType)
  {
    javax.vecmath.Vector3f fallInertia = PhysicsTools.openGLToBullet(new Vector3f(0, 0, 0));
    if (collisionShape != null && mass != 0.0f)
    {
      collisionShape.calculateLocalInertia(mass, fallInertia);
    }
    body = new RigidBody(mass, new DefaultMotionState(new Transform(new Matrix4f(direction,
        PhysicsTools.openGLToBullet(center), 1))), collisionShape, fallInertia);
    body.setCollisionFlags(body.getCollisionFlags() | collisionType);
    PhysicsWorld.addPhysicsBody(this);
  }

  /**
   * Gets the X value
   * As far as I can tell doesn't quite work
   * @return x value
   */
  public float getX()
  {
    trans = body.getMotionState().getWorldTransform(trans);
    return trans.origin.x;
  }

  /**
   * Gets the Y value
   * As far as I can tell doesn't quite work
   * @return y value
   */
  public float getY()
  {
    trans = body.getMotionState().getWorldTransform(trans);
    return trans.origin.y;
  }

  /**
   * Gets the Z value
   * As far as I can tell doesn't quite work
   * @return z value
   */
  public float getZ()
  {
    trans = body.getMotionState().getWorldTransform(trans);
    return trans.origin.z;
  }
  
  /**
   * Sets the global position of the PhysicsBody
   * 
   * @param x
   * @param y
   * @param z
   */
  public void setPosition(float x, float y, float z)
  {
    trans = body.getMotionState().getWorldTransform(trans);
    trans.origin.x = x;
    trans.origin.y = y;
    trans.origin.z = z;
  }

  /**
   * Gets the mass
   * 
   * @return mass
   */
  public float getMass()
  {
    return 1f / body.getInvMass();
  }

  /**
   * applies a force in the normalised direction this isn't a collision this is
   * like the hand of god pushing the object
   * 
   * @param direction
   */
  public void push(Vector3f direction)
  {
    javax.vecmath.Vector3f vel = PhysicsTools.openGLToBullet(new Vector3f());
    vel = body.getLinearVelocity(vel);
    vel.x = direction.x;
    vel.z = direction.z;
    body.setLinearVelocity(vel);
  }

  /**
   * Jump in the y direction
   * Apply a linear velocity in the positive y direction
   */
  public void jump()
  {
    if (canJump) {
      body.setLinearVelocity(PhysicsTools.openGLToBullet(new Vector3f(0, 50f, 0)));
      canJump = false;
    }
  }

  /**
   * getPitch 
   * @return pitch in degrees
   */
  public float getPitch()
  {
    trans = body.getMotionState().getWorldTransform(trans);
    Quat4f q = PhysicsTools.quatFromMatrix(trans.basis);
    return PhysicsTools.pitchFromQuat(q);
  }

  /**
   * getYaw 
   * @return yaw in degrees
   */
  public float getYaw()
  {
    trans = body.getMotionState().getWorldTransform(trans);
    Quat4f q = PhysicsTools.quatFromMatrix(trans.basis);
    return PhysicsTools.yawFromQuat(q);
  }

  /**
   * getRoll
   * @return roll in degrees
   */
  public float getRoll()
  {
    trans = body.getMotionState().getWorldTransform(trans);
    Quat4f q = PhysicsTools.quatFromMatrix(trans.basis);
    return PhysicsTools.rollFromQuat(q);
  }

  /**
   * moveForward
   * Uses the yaw of the object to move it relative to where the its facing
   * @param speed
   * @param yaw
   */
  public void moveForward(float speed, float yaw)
  {
    float x = (float) (Math.sin(Math.toRadians(yaw)) * speed);
    float z = -(float) (Math.cos(Math.toRadians(yaw)) * speed);
    push(new Vector3f(x, 0, z));
  }

  /**
   * moveBackward
   * Uses the yaw of the object to move it relative to where the its facing
   * @param speed
   * @param yaw
   */
  public void moveBackward(float speed, float yaw)
  {
    float x = -(float) (Math.sin(Math.toRadians(yaw)) * speed);
    float z = (float) (Math.cos(Math.toRadians(yaw)) * speed);

    push(new Vector3f(x, 0, z));
  }

  /**
   * moveLeft
   * Uses the yaw of the object to move it relative to where the its facing
   * @param speed
   * @param yaw
   */
  public void moveLeft(float speed, float yaw)
  {
    float x = (float) (Math.sin(Math.toRadians(yaw - 90)) * speed);
    float z = -(float) (Math.cos(Math.toRadians(yaw - 90)) * speed);
    push(new Vector3f(x, 0, z));
  }

  /**
   * moveRight
   * Uses the yaw of the object to move it relative to where the its facing
   * @param speed
   * @param yaw
   */
  public void moveRight(float speed, float yaw)
  {
    float x = (float) (Math.sin(Math.toRadians(yaw + 90)) * speed);
    float z = -(float) (Math.cos(Math.toRadians(yaw + 90)) * speed);
    push(new Vector3f(x, 0, z));
  }
  
  /**
   * moveForwardRight
   * Uses the yaw of the object to move it relative to where the its facing
   * @param speed
   * @param yaw
   */
  public void moveForwardRight(float speed, float yaw)
  {
    float x = (float) (Math.sin(Math.toRadians(yaw + 45)) * speed);
    float z = -(float) (Math.cos(Math.toRadians(yaw + 45)) * speed);
    push(new Vector3f(x, 0, z));
  }
  
  /**
   * moveForwardLeft
   * Uses the yaw of the object to move it relative to where the its facing
   * @param speed
   * @param yaw
   */
  public void moveForwardLeft(float speed, float yaw)
  {
    float x = (float) (Math.sin(Math.toRadians(yaw - 45)) * speed);
    float z = -(float) (Math.cos(Math.toRadians(yaw - 45)) * speed);
    push(new Vector3f(x, 0, z));
  }
  
  /**
   * moveBackRight
   * Uses the yaw of the object to move it relative to where the its facing
   * @param speed
   * @param yaw
   */
  public void moveBackRight(float speed, float yaw)
  {
    float x = -(float) (Math.sin(Math.toRadians(yaw - 45)) * speed);
    float z = (float) (Math.cos(Math.toRadians(yaw - 45)) * speed);
    push(new Vector3f(x, 0, z));
  }
  
  /**
   * moveBackLeft
   * Uses the yaw of the object to move it relative to where the its facing
   * @param speed
   * @param yaw
   */
  public void moveBackLeft(float speed, float yaw)
  {
    float x = -(float) (Math.sin(Math.toRadians(yaw + 45)) * speed);
    float z = (float) (Math.cos(Math.toRadians(yaw + 45)) * speed);
    push(new Vector3f(x, 0, z));
  }

  /**
   * getOpenGLTransformMatrix
   * @return FloatBuffer used to draw the opengl
   */
  public FloatBuffer getOpenGLTransformMatrix()
  {
    float[] m = new float[16];
    trans = body.getWorldTransform(trans);
    trans.getOpenGLMatrix(m);
    FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
    buffer.clear();
    buffer.put(m);
    buffer.flip();
    return buffer;
  }
  
  /**
   * getOpenGLTransformMatrixArray
   * @return float[] 16 element 4x4 matrix holding both x,y,z and rotation
   */
  public float[] getOpenGLTransformMatrixArray()
  {
    float[] m = new float[16];
    trans = body.getWorldTransform(trans);
    trans.getOpenGLMatrix(m);
    return m;
  }
  
  /**
   * setFromOpenGLTransformMatrix
   * Sets the global position and rotation using a 16 element 4x4 matrix
   * @param m
   */
  public void setFromOpenGLTransformMatrix(float[] m)
  {
    trans = body.getWorldTransform(trans);
    trans.setFromOpenGLMatrix(m);
    body.setWorldTransform(trans);
  }

  /**
   * translate
   * Translates the physics body, It sorta teleports the body
   * @param x
   * @param y
   * @param z
   */
  public void translate(float x, float y, float z)
  {
    body.translate(PhysicsTools.openGLToBullet(new Vector3f(x, y, z)));
    body.activate(true);
  }

  /**
   * getCollisionShape
   * @return the bodies collision shape
   */
  public CollisionShape getCollisionShape()
  {
    return body.getCollisionShape();
  }
  
  /**
   * callback
   * gets called every time PhysicsWorld.stepSimulation gets called
   */
  public void callback()
  {
    
  }
  
  /**
   * collidedWith
   * gets called every time it collides with another object
   * must be added to PhysicsWorld.addToWatchlist in order to be called
   * @param other the other body
   */
  public void collidedWith(CollisionObject other)
  {
    canJump = true;
  }
}
