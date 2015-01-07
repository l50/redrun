package redrun.model.gameobject.world;

import static org.lwjgl.opengl.GL11.*;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.physics.BoxPhysicsBody;

/**
 * This class represents a tetrahedron that can be drawn in an OpenGL scene.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-03
 */
public class Tetrahedron extends WorldObject
{
  /**
   * Creates a new tetrahedron at the specified position.
   * 
   * @param x the initial x position
   * @param y the initial y position
   * @param z the initial z position
   */
  public Tetrahedron(float x, float y, float z, String textureName)
  {
    super(x, y, z, textureName);
    
    this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(1, 1, 1), new Quat4f(), 1);

    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      glTranslatef(this.getX(), this.getY(), this.getZ());
      
      glBegin(GL_TRIANGLES);
      {
        // Front triangle...
        glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        glTexCoord2f(0.5f, 1.0f); glVertex3f(0.0f, 1.0f, 0.0f); glNormal3f(0.0f, 1.0f, 0.0f);
        glTexCoord2f(0.25f, 0.5f); glVertex3f( -1.0f, -1.0f, 0.0f); glNormal3f( -1.0f, -1.0f, 0.0f);
        glTexCoord2f(0.75f, 0.5f); glVertex3f( 1.0f,  -1.0f, 0.0f); glNormal3f( 1.0f,  -1.0f, 0.0f);

        // Right triangle...
        glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
        glTexCoord2f(0.0f, 0.0f); glVertex3f( 1.0f,  -1.0f, 0.0f); glNormal3f( 1.0f,  -1.0f, 0.0f);
        glTexCoord2f(0.5f, 0.0f); glVertex3f(0.0f, 1.0f, 0.0f); glNormal3f(0.0f, 1.0f, 0.0f);
        glTexCoord2f(0.25f, 0.5f); glVertex3f( 0.0f,  -1.0f, -1.0f); glNormal3f( 0.0f,  -1.0f, -1.0f);

        // Left triangle...
        glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        glTexCoord2f(0.5f, 0.0f); glVertex3f( -1.0f, -1.0f, 0.0f); glNormal3f( -1.0f, -1.0f, 0.0f);
        glTexCoord2f(1.0f, 0.0f); glVertex3f(0.0f, 1.0f, 0.0f); glNormal3f(0.0f, 1.0f, 0.0f);
        glTexCoord2f(0.75f, 0.5f); glVertex3f( 0.0f,  -1.0f, -1.0f); glNormal3f( 0.0f,  -1.0f, -1.0f);

        // Bottom triangle...
        glColor4f(0.0f, 1.0f, 0.0f, 1.0f);
        glTexCoord2f(0.25f, 0.5f); glVertex3f( -1.0f, -1.0f, 0.0f); glNormal3f( -1.0f, -1.0f, 0.0f);
        glTexCoord2f(0.75f, 0.5f); glVertex3f( 1.0f,  -1.0f, 0.0f); glNormal3f( 1.0f,  -1.0f, 0.0f);
        glTexCoord2f(0.5f, 0.0f); glVertex3f( 0.0f,  -1.0f, -1.0f); glNormal3f( 0.0f,  -1.0f, -1.0f);
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
    
  }

  @Override
  public void reset()
  {
    
  }
}