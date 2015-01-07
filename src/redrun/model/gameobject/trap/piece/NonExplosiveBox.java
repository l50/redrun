package redrun.model.gameobject.trap.piece;

import static org.lwjgl.opengl.GL11.*;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.constants.Direction;
import redrun.model.gameobject.trap.Trap;
import redrun.model.physics.BoxPhysicsBody;

/**
 * Creates a Non Explosive Box
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-11-9
 * 
 */
public class NonExplosiveBox extends Trap
{
  /**
   * NonExplosiveBox constructor x,y,z
   * @param x position
   * @param y position
   * @param z position
   * @param orientation
   * @param textureName
   */
  public NonExplosiveBox(float x, float y, float z, Direction orientation, String textureName)
  {
    super(x, y, z, orientation, textureName);

    this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(1, 1, 1), new Quat4f(), 0);

    displayListId = glGenLists(1);
    /** The display list to draw the trap*/
    glNewList(displayListId, GL_COMPILE);
    {
      // A box
      glBegin(GL_QUADS);
      {
        glColor3f(1.0f, 1.0f, 1.0f);
        // Top face...
        glNormal3f(0.0f, 1.0f, 0.0f);
        glVertex3f(1.0f, 1.0f, 1.0f);
        glTexCoord2f(0, 0);
        glVertex3f(1.0f, 1.0f, -1.0f);
        glTexCoord2f(0, 1);
        glVertex3f(-1.0f, 1.0f, -1.0f);
        glTexCoord2f(1, 1);
        glVertex3f(-1.0f, 1.0f, 1.0f);
        glTexCoord2f(1, 0);

        // Bottom face...
        glNormal3f(0.0f, -1.0f, 0.0f);
        glVertex3f(1.0f, -1.0f, 1.0f);
        glTexCoord2f(0, 0);
        glVertex3f(-1.0f, -1.0f, 1.0f);
        glTexCoord2f(0, 1);
        glVertex3f(-1.0f, -1.0f, -1.0f);
        glTexCoord2f(1, 1);
        glVertex3f(1.0f, -1.0f, -1.0f);
        glTexCoord2f(1, 0);

        // Front face...
        glNormal3f(0.0f, 0.0f, -1.0f);
        glVertex3f(1.0f, -1.0f, -1.0f);
        glTexCoord2f(0, 0);
        glVertex3f(-1.0f, -1.0f, -1.0f);
        glTexCoord2f(0, 1);
        glVertex3f(-1.0f, 1.0f, -1.0f);
        glTexCoord2f(1, 1);
        glVertex3f(1.0f, 1.0f, -1.0f);
        glTexCoord2f(1, 0);

        // Back face...
        glNormal3f(0.0f, 0.0f, 1.0f);
        glVertex3f(1.0f, 1.0f, 1.0f);
        glTexCoord2f(0, 0);
        glVertex3f(-1.0f, 1.0f, 1.0f);
        glTexCoord2f(0, 1);
        glVertex3f(-1.0f, -1.0f, 1.0f);
        glTexCoord2f(1, 1);
        glVertex3f(1.0f, -1.0f, 1.0f);
        glTexCoord2f(1, 0);

        // Left face...
        glNormal3f(-1.0f, 0.0f, 0.0f);
        glVertex3f(-1.0f, 1.0f, 1.0f);
        glTexCoord2f(0, 0);
        glVertex3f(-1.0f, 1.0f, -1.0f);
        glTexCoord2f(0, 1);
        glVertex3f(-1.0f, -1.0f, -1.0f);
        glTexCoord2f(1, 1);
        glVertex3f(-1.0f, -1.0f, 1.0f);
        glTexCoord2f(1, 0);

        // Right face...
        glNormal3f(1.0f, 0.0f, 0.0f);
        glVertex3f(1.0f, 1.0f, -1.0f);
        glTexCoord2f(0, 0);
        glVertex3f(1.0f, 1.0f, 1.0f);
        glTexCoord2f(0, 1);
        glVertex3f(1.0f, -1.0f, 1.0f);
        glTexCoord2f(1, 1);
        glVertex3f(1.0f, -1.0f, -1.0f);
        glTexCoord2f(1, 0);
      }
      glEnd();
    }
    glEndList();
  }

  @Override
  public void activate()
  {
  }

  @Override
  public void reset()
  {
  }

  @Override
  public void interact()
  {
  }

  @Override
  public void update()
  {
  }
}