package redrun.model.gameobject.world;

import java.nio.FloatBuffer;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.constants.CollisionTypes;
import redrun.model.constants.Direction;
import redrun.model.physics.BoxPhysicsBody;
import redrun.model.toolkit.BufferConverter;
import static org.lwjgl.opengl.GL11.*;

/**
 * This class represents a plane that can be drawn in an OpenGL scene.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-03
 */
public class EndPlane extends WorldObject
{
  /**
   * Creates a new plane of the specified size at the specified location.
   * 
   * @param x the x position of the plane
   * @param y the y position of the plane
   * @param z the z position of the plane
   * @param textureName the texture to apply to the plane
   * @param orientation the orientation of the plane
   * @param size the size of the plane
   */
  public EndPlane(float x, float y, float z, String textureName, Direction orientation, float size)
  {
    super(x, y, z, textureName);
    
    this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(size / 2, 0, size / 2), new Quat4f(), 0, CollisionTypes.END_COLLISION_TYPE);

    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      glBegin(GL_QUADS);
      {
        FloatBuffer materialAmbient = BufferConverter.asFloatBuffer(new float[] {0.2f, 0.2f, 0.2f, 1.0f});
        FloatBuffer materialDiffuse = BufferConverter.asFloatBuffer(new float[] {0.8f, 0.8f, 0.8f, 1.0f});
        FloatBuffer materialSpecular = BufferConverter.asFloatBuffer(new float[] {1.0f, 1.0f, 1.0f, 1.0f});
        FloatBuffer materialShininess = BufferConverter.asFloatBuffer(new float[] {30.0f, 0.0f, 0.0f, 0.0f});
        
        glMaterial(GL_FRONT, GL_AMBIENT, materialAmbient);
        glMaterial(GL_FRONT, GL_DIFFUSE, materialDiffuse);
        glMaterial(GL_FRONT, GL_SPECULAR, materialSpecular);
        glMaterial(GL_FRONT, GL_SHININESS, materialShininess);
        
        glNormal3f(0.0f, 1.0f, 0.0f);
        
        switch (orientation)
        {
          case NORTH:
          {
            glVertex3f(size / 2, 0.0f, size / 2);
            glTexCoord2f(0, 0);
            glVertex3f(-size / 2, 0.0f, size / 2);
            glTexCoord2f(0, 1);
            glVertex3f(-size / 2, 0.0f, -size / 2);
            glTexCoord2f(1, 1);
            glVertex3f(size / 2, 0.0f, -size / 2);
            glTexCoord2f(1, 0);
            break;
          }
          case EAST:
          {
            glVertex3f(size / 2, 0.0f, size / 2);
            glTexCoord2f(0, 0);
            glVertex3f(-size / 2, 0.0f, size / 2);
            glTexCoord2f(0, 1);
            glVertex3f(-size / 2, 0.0f, -size / 2);
            glTexCoord2f(1, 1);
            glVertex3f(size / 2, 0.0f, -size / 2);
            glTexCoord2f(1, 0);
            break;
          }
          case SOUTH:
          {
            glVertex3f(size / 2, 0.0f, size / 2);
            glTexCoord2f(0, 0);
            glVertex3f(-size / 2, 0.0f, size / 2);
            glTexCoord2f(0, 1);
            glVertex3f(-size / 2, 0.0f, -size / 2);
            glTexCoord2f(1, 1);
            glVertex3f(size / 2, 0.0f, -size / 2);
            glTexCoord2f(1, 0);
            break;
          }
          case WEST:
          {
            glVertex3f(size / 2, 0.0f, size / 2);
            glTexCoord2f(0, 0);
            glVertex3f(-size / 2, 0.0f, size / 2);
            glTexCoord2f(0, 1);
            glVertex3f(-size / 2, 0.0f, -size / 2);
            glTexCoord2f(1, 1);
            glVertex3f(size / 2, 0.0f, -size / 2);
            glTexCoord2f(1, 0);
            break;
          }
          default:
          {
          	try
          	{
          		throw new IllegalArgumentException();
          	}
          	catch (IllegalArgumentException ex)
          	{
          		ex.printStackTrace();
          	}
          }
        }
      }
      glEnd();
    }
    glEndList();
  }

  @Override
  public void interact()
  {
    System.out.println("Interacting with the game object: " + this.id);
  }

  @Override
  public void update()
  {
    // TODO Auto-generated method stub
  }

  @Override
  public void reset()
  {
    // TODO Auto-generated method stub
  }
}