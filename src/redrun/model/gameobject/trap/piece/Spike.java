package redrun.model.gameobject.trap.piece;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glVertexAttrib3f;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.model.constants.Direction;
import redrun.model.gameobject.trap.Trap;
import redrun.model.gameobject.trap.full.TrapDoor;
import redrun.model.physics.BoxPhysicsBody;
import redrun.model.toolkit.ShaderLoader;

/**
 * Class to make a Spike
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-11-9
 * 
 */
public class Spike extends Trap
{
  ShaderLoader sl;
  TrapDoor td1;
  TrapDoor td2;
  float occilate = 0;
  float occilate2 = 0;
  float movementSpeed = 0.15f;

  /**
   * Spike trap
   * 
   * @param x position
   * @param y position
   * @param z position
   * @param orientation
   * @param textureName
   */
  public Spike(float x, float y, float z, Direction orientation, String textureName)
  {
    super(x, y, z, orientation, null);

    // Physics body...
    this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(1, 1, 1), new Quat4f(), 1);

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

      /** Make the spike triangles */
      glBegin(GL_TRIANGLES);
      {
        // Front triangle...
        glVertexAttrib3f(program, 0.6f, 0f, 0f);
        glVertex3f(0.0f, 1.0f, 0.0f);
        glNormal3f(0.0f, 1.0f, 0.0f);
        glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
        glVertex3f(-1.0f, -1.0f, 0.0f);
        glNormal3f(-1.0f, -1.0f, 0.0f);
        glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
        glVertex3f(1.0f, -1.0f, 0.0f);
        glNormal3f(1.0f, -1.0f, 0.0f);
        // Right triangle...
        glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
        glVertex3f(1.0f, -1.0f, 0.0f);
        glNormal3f(1.0f, -1.0f, 0.0f);
        glVertexAttrib3f(program, 0.5f, 0f, 0f);
        glVertex3f(0.0f, 1.0f, 0.0f);
        glNormal3f(0.0f, 1.0f, 0.0f);
        glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
        glVertex3f(0.0f, -1.0f, -1.0f);
        glNormal3f(0.0f, -1.0f, -1.0f);
        // Left triangle...
        glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
        glVertex3f(-1.0f, -1.0f, 0.0f);
        glNormal3f(-1.0f, -1.0f, 0.0f);
        glVertexAttrib3f(program, 0.5f, 0f, 0f);
        glVertex3f(0.0f, 1.0f, 0.0f);
        glNormal3f(0.0f, 1.0f, 0.0f);
        glVertexAttrib3f(program, 0.0f, 0.0f, 0.0f);
        glVertex3f(0.0f, -1.0f, -1.0f);
        glNormal3f(0.0f, -1.0f, -1.0f);
      }
      glEnd();
    }
    glEndList();
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
