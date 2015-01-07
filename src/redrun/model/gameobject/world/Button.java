package redrun.model.gameobject.world;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import javax.vecmath.Quat4f;

import org.lwjgl.util.vector.Vector3f;

import redrun.main.Main;
import redrun.model.game.GameData;
import redrun.model.physics.BoxPhysicsBody;
import redrun.model.toolkit.BufferConverter;

/**
 * This class represents a interactable button that can be drawn in an OpenGL scene.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-03
 */
public class Button extends WorldObject
{
  /**
   * Creates a new button at the specified position.
   * 
   * @param x the x position of the button
   * @param y the y position of the button
   * @param z the z position of the button
   * @param textureName the texture to apply to the button
   */
  public Button(float x, float y, float z, String textureName)
  {
    super(x, y, z, textureName);
    
    this.body = new BoxPhysicsBody(new Vector3f(x, y, z), new Vector3f(0.25f, 0.25f, 0.25f), new Quat4f(), 0);

    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      glBegin(GL_QUADS);
      {
        FloatBuffer materialAmbient = BufferConverter.asFloatBuffer(new float[] {0.2f, 0.2f, 0.2f, 1.0f});
        FloatBuffer materialDiffuse = BufferConverter.asFloatBuffer(new float[] {0.8f, 0.8f, 0.8f, 1.0f});
        FloatBuffer materialSpecular = BufferConverter.asFloatBuffer(new float[] {1.0f, 0.75f, 0.75f, 1.0f});
        FloatBuffer materialShininess = BufferConverter.asFloatBuffer(new float[] {30.0f, 0.0f, 0.0f, 0.0f});
        FloatBuffer materialEmission = BufferConverter.asFloatBuffer(new float[] {0.0f, 0.0f, 0.1f, 0.0f});
        
        glMaterial(GL_FRONT, GL_AMBIENT, materialAmbient);
        glMaterial(GL_FRONT, GL_DIFFUSE, materialDiffuse);
        glMaterial(GL_FRONT, GL_SPECULAR, materialSpecular);
        glMaterial(GL_FRONT, GL_SHININESS, materialShininess);
        glMaterial(GL_FRONT, GL_EMISSION, materialEmission);
      	
        // Top face...
        glNormal3f(0.0f, 0.25f, 0.0f);
        glColor3f(0.0f, 0.25f, 0.0f);
        glVertex3f(0.25f, 0.25f, -0.25f);
        glTexCoord2f(0, 0);
        glVertex3f(-0.25f, 0.25f, -0.25f);
        glTexCoord2f(0, 1);
        glVertex3f(-0.25f, 0.25f, 0.25f);
        glTexCoord2f(1, 1);
        glVertex3f(0.25f, 0.25f, 0.25f);
        glTexCoord2f(1, 0);

        // Bottom face...
        glNormal3f(0.0f, -0.25f, 0.0f);
        glColor3f(0.25f, 0.5f, 0.0f);
        glVertex3f(0.25f, -0.25f, 0.25f);
        glTexCoord2f(0, 0);
        glVertex3f(-0.25f, -0.25f, 0.25f);
        glTexCoord2f(0, 1);
        glVertex3f(-0.25f, -0.25f, -0.25f);
        glTexCoord2f(1, 1);
        glVertex3f(0.25f, -0.25f, -0.25f);
        glTexCoord2f(1, 0);

        // Front face...
        glNormal3f(0.0f, 0.0f, -0.25f);
        glColor3f(0.25f, 0.25f, 0.0f);
        glVertex3f(0.25f, -0.25f, -0.25f);
        glTexCoord2f(0, 0);
        glVertex3f(-0.25f, -0.25f, -0.25f);
        glTexCoord2f(0, 1);
        glVertex3f(-0.25f, 0.25f, -0.25f);
        glTexCoord2f(1, 1);
        glVertex3f(0.25f, 0.25f, -0.25f);
        glTexCoord2f(1, 0);

        // Back face...
        glNormal3f(0.0f, 0.0f, 0.25f);
        glColor3f(0.25f, 0.0f, 0.0f);
        glVertex3f(0.25f, 0.25f, 0.25f);
        glTexCoord2f(0, 0);
        glVertex3f(-0.25f, 0.25f, 0.25f);
        glTexCoord2f(0, 1);
        glVertex3f(-0.25f, -0.25f, 0.25f);
        glTexCoord2f(1, 1);
        glVertex3f(0.25f, -0.25f, 0.25f);
        glTexCoord2f(1, 0);

        // Left face...
        glNormal3f(-0.25f, 0.0f, 0.0f);
        glColor3f(0.0f, 0.0f, 0.25f);
        glVertex3f(-0.25f, 0.25f, 0.25f);
        glTexCoord2f(0, 0);
        glVertex3f(-0.25f, 0.25f, -0.25f);
        glTexCoord2f(0, 1);
        glVertex3f(-0.25f, -0.25f, -0.25f);
        glTexCoord2f(1, 1);
        glVertex3f(-0.25f, -0.25f, 0.25f);
        glTexCoord2f(1, 0);

        // Right face...
        glNormal3f(0.25f, 0.0f, 0.0f);
        glColor3f(0.25f, 0.0f, 0.25f);
        glVertex3f(0.25f, 0.25f, -0.25f);
        glTexCoord2f(0, 0);
        glVertex3f(0.25f, 0.25f, 0.25f);
        glTexCoord2f(0, 1);
        glVertex3f(0.25f, -0.25f, 0.25f);
        glTexCoord2f(1, 1);
        glVertex3f(0.25f, -0.25f, -0.25f);
        glTexCoord2f(1, 0);
      }
      glEnd();
    }
    glEndList();
  }

  @Override
  public void interact()
  {
    if (!isActive())
    {
      Main.client.sendTrap(GameData.getTrap(this));
      timer.resume();
    }
  }

  @Override
  public void update()
  {
    if (this.timer.getTime() >= 5.0f) reset();
  }

  @Override
  public void reset()
  {
    System.out.println("Resetting game object: " + this.id);
    this.timer.reset();
    this.timer.pause();
  }
}