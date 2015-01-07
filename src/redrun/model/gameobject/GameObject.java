package redrun.model.gameobject;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.vecmath.Quat4f;

import org.lwjgl.util.Timer;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import redrun.model.physics.PhysicsBody;
import redrun.model.toolkit.Tools;
import static org.lwjgl.opengl.GL11.*;

/**
 * This abstract class represents a game object. Every object in the 3D scene
 * will extend this class.
 * 
 * @author Troy Squillaci, Jake Nichol
 * @version 1.0
 * @since 2014-11-07
 */
public abstract class GameObject
{
  // Identification related fields...
  /** The ID of the game object. */
  public final int id;

  /** The ID counter. */
  private static int counter = 0;

  /** All game objects in existence. */
  private static HashMap<Integer, GameObject> gameObjects = new HashMap<Integer, GameObject>();

  // OpenGL related fields...
  /** A timer associated with this game object. */
  protected Timer timer = null;

  /** A texture associated with this game object. */
  protected Texture texture = null;

  /** The display list for the game object. */
  protected int displayListId = -1;

  // Physics related fields...
  /**
   * The variable that holds all of the information needed for the physics
   * calculations.
   */
  protected PhysicsBody body = null;

  /**
   * Creates a new game object at the specified position.
   * 
   * @param x the x position of the game object
   * @param y the y position of the game object
   * @param z the z position of the game object
   * @param textureName the name of the texture to apply to the game object
   */
  public GameObject(float x, float y, float z, String textureName)
  {
    body = new PhysicsBody(0, new Quat4f(0, 0, 0, 1), new Vector3f(x, y, z), null, 0);

    if (textureName != null)
    {
      texture = Tools.loadTexture(textureName, "png");
    }

    timer = new Timer();
    timer.pause();

    id = counter++;

    if (gameObjects.containsKey(id))
    {
      try
      {
        throw new IllegalArgumentException();
      }
      catch (IllegalArgumentException ex)
      {
        Logger.getLogger(GameObject.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    gameObjects.put(id, this);
  }

  // OpenGL related methods...
  /**
   * Draws the game object to the OpenGL scene.
   */
  public void draw()
  {
    if (texture != null)
    {
      glPushMatrix();
      {
        glEnable(GL_TEXTURE_2D);
        texture.bind();
        glMultMatrix(body.getOpenGLTransformMatrix()); 
        glCallList(displayListId);
        glDisable(GL_TEXTURE_2D);
      }
      glPopMatrix();
    }
    else
    {
      glPushMatrix();
      {
        glMultMatrix(body.getOpenGLTransformMatrix());
        glCallList(displayListId);
      }
      glPopMatrix();
    }

    update();
  }

  /**
   * Interacts with the game object.
   */
  public abstract void interact();

  /**
   * Updates the game object to reflect the state of the timer.
   */
  public abstract void update();

  /**
   * Reset the game object.
   */
  public abstract void reset();

  // Getter methods...
  /**
   * Gets an active game object with the specified ID. Returns null if no such
   * game object is associated with the specified ID.
   * 
   * @param id the ID of the game object
   * @return the game object with the specified ID
   */
  public static GameObject getGameObject(int id)
  {
    return gameObjects.get(id);
  }
  
  /**
   * Prints all active game objects.
   */
  public static void printAll()
  {
  	for (GameObject gameObject : GameObject.gameObjects.values())
  	{
  		System.out.println(gameObject);
  	}
  }

  /**
   * Gets the X position of the game object.
   * 
   * @return the X position of the game object
   */
  public float getX()
  {
    return body.getX();
  }

  /**
   * Gets the Y position of the game object.
   * 
   * @return the Y position of the game object
   */
  public float getY()
  {
    return body.getY();
  }

  /**
   * Gets the Z position of the game object.
   * 
   * @return the Z position of the game object
   */
  public float getZ()
  {
    return body.getZ();
  }

  /**
   * Gets the physics rigid body.
   * 
   * @return the the physics rigid body
   */
  public PhysicsBody getBody()
  {
    return body;
  }
  
  /**
   * Indicates if the game object is active.
   * 
   * @return a indicator if the game object is active
   */
  public boolean isActive()
  {
    return timer.getTime() > 0 ? true : false; 
  }

  // Overridden methods from Object...
  @Override
  public boolean equals(Object obj)
  {
    GameObject other = (GameObject) obj;

    return id == other.id;
  }

  @Override
  public int hashCode()
  {
    return id;
  }

  @Override
  public String toString()
  {
    // @formatter:off
    return "=== Game Object ===\n" + "ID: " + id + "\n" + "Position: (" + body.getX() + ", " + body.getY() + ", "
        + body.getZ() + ")\n" + "Physics: " + body.toString() + "\n" + "Type: " + this.getClass().getName() + "\n"
        + "===================\n";
    // @formatter:on
  }
}
