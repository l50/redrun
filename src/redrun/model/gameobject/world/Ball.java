package redrun.model.gameobject.world;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;

import redrun.model.physics.SpherePhysicsBody;
import redrun.model.toolkit.BufferConverter;

/**
 * This class represents a ball.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-24
 */
public class Ball extends WorldObject
{  
  /** The sphere used to create the ball. */
  private Sphere sphere;

  /**
   * Creates a new ball.
   * 
   * @param x the x position of the ball
   * @param y the y position of the ball
   * @param z the z position of the ball
   * @param textureName the texture to apply to the ball
   * @param radius the radius of the ball
   */
  public Ball(float x, float y, float z, String textureName, float radius)
  {
    super(x, y, z, textureName);
    
    this.sphere = new Sphere();
    
    this.body = new SpherePhysicsBody(new Vector3f(x, y, z), radius, 1.0f);
    
    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      glBegin(GL_SPHERE_MAP);
      {
        FloatBuffer materialColor = BufferConverter.asFloatBuffer(new float[] {0.7f, 0.7f, 0.7f, 1.0f});
        FloatBuffer materialSpecular = BufferConverter.asFloatBuffer(new float[] {1.0f, 0.75f, 0.75f, 1.0f});
        FloatBuffer materialShininess = BufferConverter.asFloatBuffer(new float[] {30.0f, 0.0f, 0.0f, 0.0f});
        
        glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, materialColor);
        glMaterial(GL_FRONT, GL_SPECULAR, materialSpecular);
        glMaterial(GL_FRONT, GL_SHININESS, materialShininess);
        
        sphere.draw(radius, 40, 40);
      }
      glEnd();
    }
    glEndList();
  }

  @Override
  public void interact()
  {
    
  }

  @Override
  public void update()
  {

  }

  @Override
  public void reset()
  {

  }
}
