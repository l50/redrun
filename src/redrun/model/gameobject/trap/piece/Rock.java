package redrun.model.gameobject.trap.piece;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;

import java.io.File;

import javax.vecmath.Quat4f;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import redrun.model.constants.CollisionTypes;
import redrun.model.constants.Constants;
import redrun.model.constants.Direction;
import redrun.model.gameobject.trap.Trap;
import redrun.model.mesh.Face;
import redrun.model.mesh.Model;
import redrun.model.physics.BoxPhysicsBody;
import redrun.model.toolkit.OBJLoader;

/**
 * Creates a Rock trap
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-11-9
 * 
 */
public class Rock extends Trap
{
  private Model model = null;
  private float startTime;
  private int count = 0;
  private boolean down = true;

  /**
   * A rock smashing traps at x,y,z
   * 
   * @param x position
   * @param y position
   * @param z position
   * @param model name
   * @param start time
   */
  public Rock(float x, float y, float z, Direction orientation, String modelName, float startTime)
  {
    super(x, y, z, orientation, null);

    this.startTime = startTime;
    // this.body = new SpherePhysicsBody(new Vector3f(x, y, z), 4.5f, 10.0f);
    this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(2, 2, 2.5f), new Quat4f(), 0,
        CollisionTypes.INSTANT_DEATH_COLLISION_TYPE);

    // load in model
    model = OBJLoader.loadModel(new File("res/models/" + "rock" + Constants.random.nextInt(2) + ".obj"));

    displayListId = glGenLists(1);
    glNewList(displayListId, GL_COMPILE);
    {
      int currentTexture = -1;
      GL11.glEnable(GL11.GL_TEXTURE_2D);
      Face face = null;
      GL11.glScalef(0.5f, 0.5f, 0.5f);
      for (int i = 0; i < model.faces.size(); i++)
      {
        face = model.faces.get(i);
        if (face.texture != currentTexture)
        {
          currentTexture = face.texture;
          GL11.glBindTexture(GL11.GL_TEXTURE_2D, currentTexture);
        }
        GL11.glColor3f(1f, 1f, 1f);
        GL11.glBegin(GL11.GL_TRIANGLES);
        Vector3f n1 = model.normals.get((int) face.normal.x);
        GL11.glNormal3f(n1.x, n1.y, n1.z);
        Vector2f t1 = model.texVerticies.get((int) face.textures.x);
        GL11.glTexCoord2f(t1.x, t1.y);
        Vector3f v1 = model.verticies.get((int) face.vertex.x);
        GL11.glVertex3f(v1.x, v1.y, v1.z);
        Vector3f n2 = model.normals.get((int) face.normal.y);
        GL11.glNormal3f(n2.x, n2.y, n2.z);
        Vector2f t2 = model.texVerticies.get((int) face.textures.y);
        GL11.glTexCoord2f(t2.x, t2.y);
        Vector3f v2 = model.verticies.get((int) face.vertex.y);
        GL11.glVertex3f(v2.x, v2.y, v2.z);
        Vector3f n3 = model.normals.get((int) face.normal.z);
        GL11.glNormal3f(n3.x, n3.y, n3.z);
        Vector2f t3 = model.texVerticies.get((int) face.textures.z);
        GL11.glTexCoord2f(t3.x, t3.y);
        Vector3f v3 = model.verticies.get((int) face.vertex.z);
        GL11.glVertex3f(v3.x, v3.y, v3.z);
        GL11.glEnd();
      }
      GL11.glDisable(GL11.GL_TEXTURE_2D);
    }
    GL11.glEndList();
  }

  @Override
  public void activate()
  {
    this.timer.resume();
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
    body.getY();
    // System.out.println("body.getY: " + body.getOpenGLTransformMatrix());
    int time = 60;
    if (this.timer.getTime() > startTime && count < time && down)
    {
      count++;
      body.translate(0f, -0.5f, 0f);
      if (count == time)
      {
        down = false;
      }
    }
    else if (count > 0 && this.timer.getTime() > startTime && !down)
    {
      count--;
      body.translate(0f, 0.5f, 0f);
      if (count == 0)
      {
        down = true;
        if (this.timer.getTime() > 10)
        {
          this.reset();
        }
      }
    }
  }
}
