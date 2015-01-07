package redrun.model.gameobject.world;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.vector.Vector3f;

/**
 * A class for creating a room. A rectangular room of any dimension can be
 * created, including long hallways.
 * 
 * @author J. Jake Nichol
 * @since 11-8-2014
 * @version 1.0
 */
public class Room extends WorldObject
{
  Vector3f dimensions;

  /**
   * Creates a new room object.
   * 
   * @param x the X position of the room
   * @param y the Y position of the room
   * @param z the Z position of the room
   * @param dimensions the 3D dimensions of the room
   */
  public Room(float x, float y, float z, String textureName, Vector3f dimensions)
  {
    super(x, y, z, textureName);
    this.dimensions = dimensions;

    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      glTranslatef(this.getX(), this.getY(), this.getZ());
      
      drawFloor();
      drawCeiling();
      drawLeftWall();
      drawRightWall();
      drawFrontWall();
      drawBackWall();
    }
    glEndList();
  }

  /**
   * Draws the floor.
   */
  private void drawFloor()
  {
    glBegin(GL_QUADS);
    {
      glNormal3f(0.0f, 1.0f, 0.0f);

      glVertex3f(-dimensions.x / 2, -1, 0);
      glVertex3f(-dimensions.x / 2, -1, dimensions.z);
      glVertex3f(dimensions.x / 2, -1, dimensions.z);
      glVertex3f(dimensions.x / 2, -1, 0);
    }
    glEnd();
  }

  /**
   * Draws the ceiling.
   */
  private void drawCeiling()
  {
    glBegin(GL_QUADS);
    {
      glNormal3f(0.0f, -1.0f, 0.0f);

      glVertex3f(-dimensions.x / 2, dimensions.y - 1, 0);
      glVertex3f(-dimensions.x / 2, dimensions.y - 1, dimensions.z);
      glVertex3f(dimensions.x / 2, dimensions.y - 1, dimensions.z);
      glVertex3f(dimensions.x / 2, dimensions.y - 1, 0);
    }
    glEnd();
  }

  /**
   * Draws the left wall.
   */
  private void drawLeftWall()
  {
    glBegin(GL_QUADS);
    {
      glNormal3f(-1.0f, 0.0f, 0.0f);

      glVertex3f(dimensions.x / 2, -1, 0);
      glVertex3f(dimensions.x / 2, -1, dimensions.z);
      glVertex3f(dimensions.x / 2, dimensions.y - 1, dimensions.z);
      glVertex3f(dimensions.x / 2, dimensions.y - 1, 0);
    }
    glEnd();
  }

  /**
   * Draws the right wall.
   */
  private void drawRightWall()
  {
    glBegin(GL_QUADS);
    {
      glNormal3f(1.0f, 0.0f, 0.0f);

      glVertex3f(-dimensions.x / 2, -1, 0);
      glVertex3f(-dimensions.x / 2, -1, dimensions.z);
      glVertex3f(-dimensions.x / 2, dimensions.y - 1, dimensions.z);
      glVertex3f(-dimensions.x / 2, dimensions.y - 1, 0);
    }
    glEnd();
  }

  /**
   * Draws the front wall.
   */
  private void drawFrontWall()
  {
    glBegin(GL_QUADS);
    {
      glNormal3f(0.0f, 0.0f, -1.0f);

      glVertex3f(-dimensions.x / 2, -1, dimensions.z);
      glVertex3f(-dimensions.x / 2, dimensions.y - 1, dimensions.z);
      glVertex3f(dimensions.x / 2, dimensions.y - 1, dimensions.z);
      glVertex3f(dimensions.x / 2, -1, dimensions.z);
    }
    glEnd();
  }

  /**
   * Draws the back wall.
   */
  private void drawBackWall()
  {
    glBegin(GL_QUADS);
    {
      glNormal3f(0.0f, 0.0f, 1.0f);

      glVertex3f(-dimensions.x / 2, -1, 0);
      glVertex3f(-dimensions.x / 2, dimensions.y - 1, 0);
      glVertex3f(dimensions.x / 2, dimensions.y - 1, 0);
      glVertex3f(dimensions.x / 2, -1, 0);
    }
    glEnd();
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
