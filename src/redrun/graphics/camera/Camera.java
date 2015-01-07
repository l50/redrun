package redrun.graphics.camera;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import redrun.model.constants.CameraType;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

/**
 * This class represents a camera that can be used to traverse an OpenGL scene.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-03
 */
public class Camera
{
  /** The type of camera. */
  private final CameraType type;
	
  /** The position of the camera in 3D space. */
  private Vector3f position = null;

  /** The rotation of the camera around the Y axis. */
  private float yaw = 90.0f;

  /** The rotation of the camera around the X axis. */
  private float pitch = 0.0f;

  /** The field of view. */
  private float fov = 0;

  /** The aspect ratio. */
  private float aspectRatio = 0;

  /** The near clipping plane. */
  private float nearClippingPlane = 0;

  /** The far clipping plane. */
  private float farClippingPlane = 0;

  /**
   * Creates a new camera with an initial field of view, aspect ratio, near
   * clipping plane, far clipping place, and position in 3D space.
   * 
   * @param fov the field of view
   * @param aspectRatio the aspect ratio
   * @param nearClippingPlane the near clipping plane
   * @param farClippingPlane the far clipping plane
   * @param x the initial x position
   * @param y the initial y position
   * @param z the initial z position
   */
  public Camera(float fov, float aspectRatio, float nearClippingPlane, float farClippingPlane, float x, float y, float z, CameraType type)
  {
    position = new Vector3f(x, y, z);

    this.fov = fov;
    this.aspectRatio = aspectRatio;
    this.nearClippingPlane = nearClippingPlane;
    this.farClippingPlane = farClippingPlane;
    this.type = type;

    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(fov, aspectRatio, nearClippingPlane, farClippingPlane);
    glMatrixMode(GL_MODELVIEW);
  }

  // Rotation methods...
  /**
   * Change the yaw of the camera.
   * 
   * @param amount the amount to rotate
   */
  public void yaw(float amount)
  {
    yaw = (yaw + amount) % 360;
    
  }

  /**
   * Change the pitch of the camera.
   * 
   * @param amount the amount to rotate
   */
  public void pitch(float amount)
  {
    pitch += amount;
    if (pitch < -90)
    {
      pitch = -90;
    }
    if (pitch > 90)
    {
      pitch = 90;
    }
  }

  // Movement methods...
  /**
   * Updates the camera's position to fit the given parameters. In this project,
   * this will primarily be used for having the camera follow player movement.
   * 
   * @param x the x position
   * @param y the y position
   * @param z the z position
   */
  public void updatePosition(float x, float y, float z, float pitch, float yaw)
  {
    position.x = x;
    position.y = y;
    position.z = z;
  }

  /**
   * Moves the camera forward relative to its current yaw.
   * 
   * @param distance the distance to move
   */
  public void moveForward(float distance)
  {
    position.x += distance * (float) Math.sin(Math.toRadians(yaw));
    position.z -= distance * (float) Math.cos(Math.toRadians(yaw));
  }

  /**
   * Moves the camera backward relative to its current yaw.
   * 
   * @param distance the distance to move
   */
  public void moveBackward(float distance)
  {
    position.x -= distance * (float) Math.sin(Math.toRadians(yaw));
    position.z += distance * (float) Math.cos(Math.toRadians(yaw));
  }

  /**
   * Moves the camera left relative to its current yaw.
   * 
   * @param distance the distance to move
   */
  public void moveLeft(float distance)
  {
    position.x += distance * (float) Math.sin(Math.toRadians(yaw - 90));
    position.z -= distance * (float) Math.cos(Math.toRadians(yaw - 90));
  }

  /**
   * Moves the camera right relative to its current yaw.
   * 
   * @param distance the distance to move
   */
  public void moveRight(float distance)
  {
    position.x += distance * (float) Math.sin(Math.toRadians(yaw + 90));
    position.z -= distance * (float) Math.cos(Math.toRadians(yaw + 90));
  }

  /**
   * Moves the camera up.
   * 
   * @param distance the distance to move
   */
  public void moveUp(float distance)
  {
    position.y += distance;
  }

  /**
   * Moves the camera down.
   * 
   * @param distance the distance to move
   */
  public void moveDown(float distance)
  {
    position.y -= distance;

    if (position.y < 0) position.y = 0;
  }

  // Getter methods...
  /**
   * Gets the X position of the camera.
   * 
   * @return the X position of the camera
   */
  public float getX()
  {
    return position.x;
  }

  /**
   * Gets the Y position of the camera.
   * 
   * @return the Y position of the camera
   */
  public float getY()
  {
    return position.y;
  }

  /**
   * Gets the Z position of the camera.
   * 
   * @return the Z position of the camera
   */
  public float getZ()
  {
    return position.z;
  }

  /**
   * Gets the yaw of the camera.
   * 
   * @return the yaw of the camera
   */
  public float getYaw()
  {
    return yaw;
  }

  /**
   * Gets the pitch of the camera.
   * 
   * @return the pitch of the camera
   */
  public float getPitch()
  {
    return pitch;
  }

  /**
   * Gets the field of view of the camera.
   * 
   * @return the field of view of the camera
   */
  public float getFieldOfView()
  {
    return fov;
  }

  /**
   * Gets the aspect ratio of the camera.
   * 
   * @return the aspect ratio of the camera
   */
  public float getAspectRatio()
  {
    return aspectRatio;
  }

  /**
   * Gets the near clipping plane of the camera.
   * 
   * @return the near clipping plane of the camera
   */
  public float getNearClippingPlane()
  {
    return nearClippingPlane;
  }

  /**
   * Gets the far clipping plane of the camera.
   * 
   * @return the far clipping plane of the camera
   */
  public float getFarClippingPlane()
  {
    return farClippingPlane;
  }
  
  /**
   * Gets the type of the camera.
   * 
   * @return the type of the camera
   */
  public CameraType getType()
  {
  	return type;
  }
  
  /**
   * Sets the yaw of the camera.
   * 
   * @param yaw the new yaw of the camera
   */
  public void setYaw(float yaw)
  {
    this.yaw = yaw;
  }

  /**
   * Adjusts the camera to view the next frame properly. Should be called before
   * the scene is drawn.
   */
  public void lookThrough()
  {
    GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
    GL11.glTranslatef(0.5f, 0, 0.8f);
    GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
    GL11.glTranslatef(-position.x, -position.y, -position.z);
  }
}