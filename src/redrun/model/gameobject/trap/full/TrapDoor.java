package redrun.model.gameobject.trap.full;

import java.awt.Dimension;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.constants.Direction;
import redrun.model.game.GameData;
import redrun.model.gameobject.trap.Trap;
import redrun.model.physics.BoxPhysicsBody;
import static org.lwjgl.opengl.GL11.*;

/**
 * Class to make trap doors. To make a new trap door make a new object of this
 * class.
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-11-9
 * 
 */
public class TrapDoor extends Trap
{
  Direction orientation;
  private int count = 0;
  private boolean down = true;

  /**
   * Constructor to make a new trap door give the starting position
   * 
   * @param x position
   * @param y position
   * @param z position
   * @param textureName
   */
  public TrapDoor(float x, float y, float z, Direction orientation, String textureName)
  {
    super(x, y+.02f, z, orientation, textureName);

    // Box physics body with a flat side the floor
    this.body = new BoxPhysicsBody(new Vector3f(x, y+.02f, z), new Vector3f(7, 0f, 7), new Quat4f(), 0.0f);

    this.orientation = orientation;

    BottomOfPitTrap bottomTrap = new BottomOfPitTrap(x, y, z, orientation, new Dimension(10, 15)); 
    GameData.addGameObject(bottomTrap);
    displayListId = glGenLists(1);

    // The display list 
    glNewList(displayListId, GL_COMPILE);
    {
      glBegin(GL_QUADS);
      {
        glNormal3f(0.0f, 1.0f, 0.0f);
        glVertex3f(1.0f, 0.0f, -1.0f);
        glTexCoord2f(0, 0);
        glVertex3f(-1.0f, 0.0f, -1.0f);
        glTexCoord2f(0, 1);
        glVertex3f(-1.0f, 0.0f, 1.0f);
        glTexCoord2f(1, 1);
        glVertex3f(1.0f, 0.0f, 1.0f);
        glTexCoord2f(1, 0);
      }
      glEnd();
    }
    glEndList();
  }

  /**
   * override draw to apply the scale
   */
  @Override
  public void draw()
  {
    glPushMatrix();
    {
      glMultMatrix(body.getOpenGLTransformMatrix());
      glScalef(7f, 1f, 7f);
      glEnable(GL_TEXTURE_2D);
      texture.bind();
      glCallList(displayListId);
      glDisable(GL_TEXTURE_2D);
    }
    glPopMatrix();
    update();
  }
  /** 
   *Turn on the trap 
   **/
  @Override
  public void activate()
  {
    this.timer.resume();
  }

  /**
   * Reset the trap
   */
  @Override
  public void reset()
  {
    this.timer.pause();
    this.timer.reset();
  }

  @Override
  public void interact()
  {
  }

  @Override
  public void update()
  {
    // the trap animation open sequence 
    if (this.timer.getTime() > 0 && count < 10 && down)
    {
      // number of times to run the trap
      count++;
      if (orientation == Direction.NORTH || orientation == Direction.SOUTH)
      {
        body.translate(1f, 0f, 0f);
      }
      if (orientation == Direction.EAST || orientation == Direction.WEST)
      {
        body.translate(0f, 0f, -1f);
      }

      if (count == 10)
      {
        down = false;
      }
    }
    // go back to the close position
    else if (count > 0 && !down && this.timer.getTime() > 6)
    {
      count--;
      if (orientation == Direction.NORTH || orientation == Direction.SOUTH)
      {
        body.translate(-1f, 0f, 0f);
      }
      if (orientation == Direction.EAST || orientation == Direction.WEST)
      {
        body.translate(0f, 0f, 1f);
      }
      if (count == 0)
      {
        down = true;
      }
    }
    if (count == 0 && this.timer.getTime() > 0)
    {
      this.reset();
    }
  }
}
