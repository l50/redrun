package redrun.model.gameobject.trap.piece;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.constants.CollisionTypes;
import redrun.model.constants.Direction;
import redrun.model.gameobject.trap.Trap;
import redrun.model.physics.BoxPhysicsBody;
import redrun.model.toolkit.ShaderLoader;
import redrun.model.toolkit.Timing;

/**
 * Class to make a Spear
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-11-9
 * 
 */
public class Spear extends Trap
{
  private ShaderLoader sl;
  private float startTime;
  private int count = 0;
  private boolean down = false;
  private long time = 0;
  Direction orientation;
  String xyz;

  /**
   * 
   * @param x position 
   * @param y position
   * @param z position
   * @param orientation
   * @param textureName
   * @param startTime
   * @param xyz one either x, y, or z
   */
  public Spear(float x, float y, float z, Direction orientation, String textureName, float startTime, String xyz)
  {
    super(x, y, z, orientation, textureName);
    float height = 15f;
    float radius = .3f;
    float resolution = .1f;
    this.startTime = startTime;
    this.orientation = orientation;
    this.xyz = xyz;

    if (xyz.equals("x"))
      this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(height / 2, 0.3f, 0.3f), new Quat4f(), 0,
          CollisionTypes.INSTANT_DEATH_COLLISION_TYPE);
    if (xyz.equals("y"))
      this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(0.3f, height / 2, 0.3f), new Quat4f(), 0,
          CollisionTypes.INSTANT_DEATH_COLLISION_TYPE);
    if (xyz.equals("z"))
      this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(0.3f, 0.3f, height / 2), new Quat4f(), 0,
          CollisionTypes.INSTANT_DEATH_COLLISION_TYPE);

    /** Load the shaders*/
    sl = new ShaderLoader();
    sl.loadShader("bloodf.fs");
    sl.loadShader("bloodv.vs");
    sl.deleteShaders();
    int program = glGetAttribLocation(sl.getShaderProgram(), "atr1");

    /** Set and compile the display list */
    displayListId = glGenLists(1);
    glNewList(displayListId, GL_COMPILE);
    {
      /** Apply the transformations */
      if (xyz.equals("x")) glRotatef(90, 0, 0, -1);
      if (xyz.equals("z")) glRotatef(90, -1, 0, 0);

      glTranslatef(0, -7.5f, 0);
      glUseProgram(sl.getShaderProgram());
      glBegin(GL_TRIANGLE_FAN);
      {
        glColor3f(.1f, .1f, .1f);
        glVertexAttrib3f(program, 0.05f, 0.05f, 0.05f);
        glVertex3f(0, height, 0); /* center */
        for (float i = 0; i <= 2 * Math.PI; i += resolution)
        {
          glVertexAttrib3f(program, 0.05f, 0.05f, 0.05f);
          glVertex3f((float) (radius * Math.cos(i)), (float) height, (float) (radius * Math.sin(i)));
        }
      }
      glEnd();
      glBegin(GL_TRIANGLE_FAN);
      {
        glColor3f(.1f, .1f, .1f);
        glVertexAttrib3f(program, 0.05f, 0.05f, 0.05f);
        glVertex3f(0, 0, 0); /* center */
        for (float i = (float) (2 * Math.PI); i >= 0; i -= resolution)
        {
          glVertexAttrib3f(program, 0.05f, 0.05f, 0.05f);
          glVertex3f((float) (radius * Math.cos(i)), 0, (float) (radius * Math.sin(i)));
        }
        glVertex3f(radius, height, 0);
      }
      glEnd();
      /* middle tube */
      glBegin(GL_QUAD_STRIP);
      {
        for (float i = 0; i <= 2 * Math.PI; i += resolution)
        {
          glColor3f(.1f, .1f, .1f);
          glVertexAttrib3f(program, 0.05f, 0.05f, 0.05f);
          glVertex3f((float) (radius * Math.cos(i)), 0, (float) (radius * Math.sin(i)));
          glColor3f(.1f, .1f, .1f);
          glVertexAttrib3f(program, 0.05f, 0.05f, 0.05f);
          glVertex3f((float) (radius * Math.cos(i)), height, (float) (radius * Math.sin(i)));
        }
        glColor3f(.1f, .1f, .1f);
        glVertexAttrib3f(program, 0.05f, 0.05f, 0.05f);
        glVertex3f(radius, 0, 0);
        glVertexAttrib3f(program, 0.05f, 0.05f, 0.05f);
        glVertex3f(radius, height, 0);
      }
      glEnd();
      glUseProgram(0);

      glPushMatrix();
      glScalef(0.3f, 3f, 0.3f);
      glTranslatef(0.0f, 6f, 0.0f);
      glUseProgram(sl.getShaderProgram());

      glBegin(GL_TRIANGLES);
      // Front
      glVertexAttrib3f(program, 0.6f, 0.6f, 0.6f);
      glVertex3f(0.0f, 0.1f, 0.0f);
      glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
      glVertex3f(-1.0f, -1.0f, 1.0f);
      glVertexAttrib3f(program, .1f, .1f, .1f);
      glVertex3f(1.0f, -1.0f, 1.0f);
      // Right
      glVertexAttrib3f(program, 0.6f, 0.6f, 0.6f);
      glVertex3f(0.0f, 0.1f, 0.0f);
      glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
      glVertex3f(1.0f, -1.0f, 1.0f);
      glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
      glVertex3f(1.0f, -1.0f, -1.0f);
      // Back
      glVertexAttrib3f(program, 0.6f, 0.6f, 0.6f);
      glVertex3f(0.0f, 0.1f, 0.0f);
      glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
      glVertex3f(1.0f, -1.0f, -1.0f);
      glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
      glVertex3f(-1.0f, -1.0f, -1.0f);
      // Left
      glVertexAttrib3f(program, 0.6f, 0.6f, 0.6f);
      glVertex3f(0.0f, 0.1f, 0.0f);
      glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
      glVertex3f(-1.0f, -1.0f, -1.0f);
      glVertexAttrib3f(program, 0.1f, 0.1f, 0.1f);
      glVertex3f(-1.0f, -1.0f, 1.0f);
      glEnd();
      glUseProgram(0);
      glPopMatrix();
    }
    glEndList();
  }

  @Override
  public void activate()
  {
    this.timer.resume();
    time = Timing.getTime();
  }

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

    float speed = 0.4f;
    int limit = 40;
    if (this.timer.getTime() > startTime && count < limit && !down)
    {
      count++;
      if (xyz.equals("x")) body.translate(speed, 0f, 0f);
      if (xyz.equals("y")) body.translate(0f, speed, 0f);
      if (xyz.equals("z")) body.translate(0f, 0f, -speed);

      if (count == limit)
      {
        down = true;
      }
    }

    if (count > 0 && this.timer.getTime() > startTime && down)
    {
      count--;
      if (xyz.equals("x")) body.translate(-speed, 0f, 0f);
      if (xyz.equals("y")) body.translate(0f, -speed, 0f);
      if (xyz.equals("z")) body.translate(0f, 0f, speed);

      if (count == 0)
      {
        down = false;
        if (Timing.getTime() > time + 7000)
        {
          this.reset();
        }
      }
    }
  }
}
