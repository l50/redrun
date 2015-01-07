package redrun.model.gameobject.trap.full;

import java.awt.Dimension;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.constants.CollisionTypes;
import redrun.model.constants.Direction;
import redrun.model.gameobject.trap.Trap;
import redrun.model.physics.BoxPhysicsBody;
import redrun.model.toolkit.ShaderLoader;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * Creates the spike field trap 
 * 
 * @author Adam Mitchell
 *
 * @version 1.0
 * @since 2014-11-9
 */
public class SpikeField extends Trap
{
  ShaderLoader sl;
  Dimension dim;

  /**
   * Creates the spike field trap 
   * 
   * @param x position
   * @param y position
   * @param z position
   * @param textureName
   * @param dimension of the trap x,z (not x y)
   */
  public SpikeField(float x, float y, float z, String textureName, Direction orientation, Dimension dim, boolean low)
  {
    super(x, y, z, orientation, textureName);

    if (low)
    {
      this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f((float) (dim.width / 2), 1.3f,
          (float) (dim.height / 2) -2), new Quat4f(), 0.0f, CollisionTypes.MINIMAL_DAMAGE_COLLISION_TYPE);
    }

    else if (!low)
    {
      this.body = new BoxPhysicsBody(new Vector3f(x, y - 10, z), new Vector3f((float) (dim.width / 3), 3.0f,
          (float) (dim.height / 3)), new Quat4f(), 0.0f, CollisionTypes.INSTANT_DEATH_COLLISION_TYPE);
    }

    this.dim = dim;

    // shaders to color the spikes red
    sl = new ShaderLoader();
    sl.loadShader("bloodf.fs");
    sl.loadShader("bloodv.vs");
    sl.deleteShaders();
    // the shader program 
    int program = glGetAttribLocation(sl.getShaderProgram(), "atr1");
    // set up the display list
    displayListId = glGenLists(1);
    glNewList(displayListId, GL_COMPILE);
    {
      // if low is set make them smaller 
      if (low)
      {
        glTranslatef(-(float) (dim.width / 2), 1.0f, -(float) (dim.height / 2));
        glBegin(GL_QUADS);
        glNormal3f(0.0f, 1.0f, 0.0f);
        for (int width = 0; width < dim.width - 1; width++)
          for (int height = 0; height < dim.height - 1; height++)
          {
            glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            glVertex3d(width, -0.999f, height);
            glTexCoord2f(0, 0);
            glVertex3d(width + 1, -0.999f, height);
            glTexCoord2f(0, 1);
            glVertex3d(width + 1, -0.999f, height + 1);
            glTexCoord2f(1, 1);
            glVertex3d(width, -0.999f, height + 1);
            glTexCoord2f(1, 0);
          }
        glEnd();

        glScalef(0.05f, 1.5f, 0.05f);
        glTranslatef(0, 0.5f, 0);
        for (float z_axis = 2f; z_axis < dim.height * 20; z_axis += 25f)
        {
          for (float x_axis = 2f; x_axis < dim.width * 20; x_axis += 25f)
          {
            glUseProgram(sl.getShaderProgram());

            glBegin(GL_TRIANGLES);
            // Front
            glVertexAttrib3f(program, 0.6f, 0f, 0f);
            glVertex3f(0.0f + x_axis, 0.1f, 0.0f + z_axis);
            glVertexAttrib3f(program, 0f, 0f, 0f);
            glVertex3f(-1.0f + x_axis, -1.0f, 1.0f + z_axis);
            glVertex3f(1.0f + x_axis, -1.0f, 1.0f + z_axis);
            // Right
            glVertexAttrib3f(program, 0.6f, 0f, 0f);
            glVertex3f(0.0f + x_axis, 0.1f, 0.0f + z_axis);
            glVertexAttrib3f(program, 0f, 0f, 0f);
            glVertex3f(1.0f + x_axis, -1.0f, 1.0f + z_axis);
            glVertex3f(1.0f + x_axis, -1.0f, -1.0f + z_axis);
            // Back
            glVertexAttrib3f(program, 0.6f, 0f, 0f);
            glVertex3f(0.0f + x_axis, 0.1f, 0.0f + z_axis);
            glVertexAttrib3f(program, 0f, 0f, 0f);
            glVertex3f(1.0f + x_axis, -1.0f, -1.0f + z_axis);
            glVertex3f(-1.0f + x_axis, -1.0f, -1.0f + z_axis);
            // Left
            glVertexAttrib3f(program, 0.6f, 0f, 0f);
            glVertex3f(0.0f + x_axis, 0.1f, 0.0f + z_axis);
            glVertexAttrib3f(program, 0f, 0f, 0f);
            glVertex3f(-1.0f + x_axis, -1.0f, -1.0f + z_axis);
            glVertex3f(-1.0f + x_axis, -1.0f, 1.0f + z_axis);
            glEnd();
            glUseProgram(0);
          }
        }
      }
      // if !low is set make them larger 
      else if (!low)
      {
        glTranslatef(-(float) (dim.width / 2), 1.51f, -(float) (dim.height / 2));
        glBegin(GL_QUADS);
        glNormal3f(0.0f, 1.0f, 0.0f);
        for (int width = 0; width < dim.width - 1; width++)
          for (int height = 0; height < dim.height - 1; height++)
          {
            glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            glVertex3d(width, -5.5, height);
            glTexCoord2f(0, 0);
            glVertex3d(width + 1, -5.5, height);
            glTexCoord2f(0, 1);
            glVertex3d(width + 1, -5.5, height + 1);
            glTexCoord2f(1, 1);
            glVertex3d(width, -5.5f, height + 1);
            glTexCoord2f(1, 0);
          }
        glEnd();
        glScalef(0.5f, 7f, 0.5f);
        // Makes the spike parts
        for (float z_axis = 2f; z_axis < dim.height * 1.5; z_axis += 6f)
        {
          for (float x_axis = 2f; x_axis < dim.width * 1.5; x_axis += 6f)
          {
            glUseProgram(sl.getShaderProgram());

            glBegin(GL_TRIANGLES);
            // Front
            glVertexAttrib3f(program, 0.6f, 0f, 0f);
            glVertex3f(0.0f + x_axis, 0.1f, 0.0f + z_axis);
            glVertexAttrib3f(program, 0f, 0f, 0f);
            glVertex3f(-1.0f + x_axis, -1.0f, 1.0f + z_axis);
            glVertex3f(1.0f + x_axis, -1.0f, 1.0f + z_axis);
            // Right
            glVertexAttrib3f(program, 0.6f, 0f, 0f);
            glVertex3f(0.0f + x_axis, 0.1f, 0.0f + z_axis);
            glVertexAttrib3f(program, 0f, 0f, 0f);
            glVertex3f(1.0f + x_axis, -1.0f, 1.0f + z_axis);
            glVertex3f(1.0f + x_axis, -1.0f, -1.0f + z_axis);
            // Back
            glVertexAttrib3f(program, 0.6f, 0f, 0f);
            glVertex3f(0.0f + x_axis, 0.1f, 0.0f + z_axis);
            glVertexAttrib3f(program, 0f, 0f, 0f);
            glVertex3f(1.0f + x_axis, -1.0f, -1.0f + z_axis);
            glVertex3f(-1.0f + x_axis, -1.0f, -1.0f + z_axis);
            // Left
            glVertexAttrib3f(program, 0.6f, 0f, 0f);
            glVertex3f(0.0f + x_axis, 0.1f, 0.0f + z_axis);
            glVertexAttrib3f(program, 0f, 0f, 0f);
            glVertex3f(-1.0f + x_axis, -1.0f, -1.0f + z_axis);
            glVertex3f(-1.0f + x_axis, -1.0f, 1.0f + z_axis);
            glEnd();
            glUseProgram(0);
          }
        }
      }
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
