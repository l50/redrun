package redrun.model.gameobject.trap.full;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.constants.CollisionTypes;
import redrun.model.constants.Direction;
import redrun.model.gameobject.trap.Trap;
import redrun.model.physics.BoxPhysicsBody;
import redrun.model.toolkit.ShaderLoader;

/**
 * Class to make spike trap door. To make a new spike trap door make a new
 * object of this class.
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-11-9
 * 
 */
public class SpikeTrapDoor extends Trap
{
  /** the shader loader */
  ShaderLoader sl;
  /** the whether move or not */
  float occilate = 0;
  float occilate2 = 0;
  float movementSpeed = 0.15f;
  int count = 0;
  boolean down = true;

  /**
   * The spike trap constructor pass in x,y,z
   * 
   * @param x starting coordinate
   * @param y starting coordinate
   * @param z starting coordinate
   * @param low set whether the trap is on a corridor, or a pit
   */
  public SpikeTrapDoor(float x, float y, float z, Direction orientation, String textureName, boolean low)
  {
    super(x, y, z, orientation, null);

    // Physics body
    this.body = new BoxPhysicsBody(new Vector3f(x, y-3f, z), new Vector3f(5, 1, 5), new Quat4f(), 0.0f, CollisionTypes.INSTANT_DEATH_COLLISION_TYPE);
    sl = new ShaderLoader();
    sl.loadShader("bloodf.fs");
    sl.loadShader("bloodv.vs");
    sl.deleteShaders();
    int program = glGetAttribLocation(sl.getShaderProgram(), "atr1");

    displayListId = glGenLists(1);
    glNewList(displayListId, GL_COMPILE);
    {
      glScalef(0.3f, 2.f, 0.2f);
      glColor3f(0.5f, 0.5f, 0.5f);
      for (float z_axis = -10; z_axis < 20; z_axis += 7f)
        for (float x_axis = -8; x_axis < 20; x_axis += 7f)
        {
          glBegin(GL_TRIANGLES);
          {
            // Front triangle...
            glVertexAttrib3f(program, 0.6f, 0f, 0f);
            glVertex3f(0.0f + x_axis, 1.0f, 0.0f + z_axis);
            glNormal3f(0.0f, 1.0f, 0.0f);
            glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
            glVertex3f(-1.0f + x_axis, -1.0f, 0.0f + z_axis);
            glNormal3f(-1.0f, -1.0f, 0.0f);
            glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
            glVertex3f(1.0f + x_axis, -1.0f, 0.0f + z_axis);
            glNormal3f(1.0f, -1.0f, 0.0f);
            // Right triangle...
            glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
            glVertex3f(1.0f + x_axis, -1.0f, 0.0f + z_axis);
            glNormal3f(1.0f, -1.0f, 0.0f);
            glVertexAttrib3f(program, 0.5f, 0f, 0f);
            glVertex3f(0.0f + x_axis, 1.0f, 0.0f + z_axis);
            glNormal3f(0.0f, 1.0f, 0.0f);
            glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
            glVertex3f(0.0f + x_axis, -1.0f, -1.0f + z_axis);
            glNormal3f(0.0f, -1.0f, -1.0f);
            // Left triangle...
            glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
            glVertex3f(-1.0f + x_axis, -1.0f, 0.0f + z_axis);
            glNormal3f(-1.0f, -1.0f, 0.0f);
            glVertexAttrib3f(program, 0.5f, 0f, 0f);
            glVertex3f(0.0f + x_axis, 1.0f, 0.0f + z_axis);
            glNormal3f(0.0f, 1.0f, 0.0f);
            glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
            glVertex3f(0.0f + x_axis, -1.0f, -1.0f + z_axis);
            glNormal3f(0.0f, -1.0f, -1.0f);
          }
          glEnd();
        }
    }
    glEndList();
  }

 
  @Override
  public void draw()
  {
    for (int i = 0; i < 5; i++)
    {
      glPushMatrix();
      {
        glPushName(this.id);
        {
          glUseProgram(sl.getShaderProgram());
          {
            glMultMatrix(body.getOpenGLTransformMatrix()); 
            glCallList(displayListId);
          }
          glUseProgram(0);
        }
        glPopName();
      }
      glPopMatrix();
    }
    update();
  }

  @Override
  public void activate()
  {
    System.out.println("Interacting with the game object: " + this.id);
    this.timer.resume();
  }

  @Override
  public void reset()
  {
    this.timer.reset();
    this.timer.pause();
  }

  @Override
  public void interact()
  {
  }

  @Override
  public void update()
  {
    int time = 40;
    int startTime = 0;
    
    if (this.timer.getTime() > startTime && count < time && down)
    {    
      count++;
      body.translate(0f, 0.1f, 0f);
      if(count == time)
      {
        down=false;
      }
    }
    else if (count > 0 && this.timer.getTime() > startTime && !down)
    {
      count--;
      body.translate(0f, -0.1f, 0f);
      if(count == 0)
      {
        down=true;
        if(this.timer.getTime() > 10)
        {
          this.reset();
        }
      }
    }
  }

}
