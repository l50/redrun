package redrun.model.gameobject.trap.piece;

import static org.lwjgl.opengl.GL11.*;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import com.bulletphysics.collision.dispatch.CollisionObject;

import redrun.model.constants.CollisionTypes;
import redrun.model.constants.Direction;
import redrun.model.gameobject.trap.Trap;
import redrun.model.gameobject.trap.full.ExplodingBoxField;
import redrun.model.physics.BoxPhysicsBody;
import redrun.model.physics.PhysicsWorld;

/**
 * Class to make an explosive box
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-11-9
 * 
 */
public class ExplosiveBox extends Trap
{
  // Don't explode the whole box row
  private boolean exploded = false;

  /**
   * Creates a single spike
   * 
   * @param x position
   * @param y position
   * @param z position
   * @param orientation
   * @param textureName
   * @param boxField
   */
  public ExplosiveBox(float x, float y, float z, Direction orientation, String textureName,
      final ExplodingBoxField boxField)
  {
    super(x, y + 20, z, orientation, textureName);

    this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(1, 1, 1), new Quat4f(), 0,
        CollisionTypes.EXPLOSION_COLLISION_TYPE)
    {
      public void collidedWith(CollisionObject other)
      {
        int collisionFlags = other.getCollisionFlags();
        if ((collisionFlags & CollisionTypes.PLAYER_COLLISION_TYPE) != 0)
        {
          exploded = true;
          PhysicsWorld.removePhysicsBody(this);
        }
      }
    };
    // Add the body to physics world
    PhysicsWorld.addToWatchList(this.body);

    displayListId = glGenLists(1);
    // the display list for the box
    glNewList(displayListId, GL_COMPILE);
    {
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

  /**
   * Explode the box set to true
   */
  public void explode()
  {
    exploded = true;
  }

  @Override
  public void draw()
  {
    if (!exploded) super.draw();
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