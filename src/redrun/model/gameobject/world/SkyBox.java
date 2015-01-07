package redrun.model.gameobject.world;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL12;
import org.newdawn.slick.opengl.Texture;
import redrun.model.toolkit.Tools;

/**
 * This class represents a skybox for an OpenGl scene.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-12
 */
public class SkyBox extends WorldObject
{    
  /** The list of textures. */
  private ArrayList<Texture> textures = new ArrayList<Texture>();;

  /**
   * Creates a new skybox at the specified location and uses the textures located in
   * the specified directory.
   * 
   * @param x the x position of the skybox
   * @param y the y position of the skybox
   * @param z the z position of the skybox
   * @param skybox the name of the directory containing the skybox textures
   */
  public SkyBox(float x, float y, float z, String skybox)
  {
    super(x, y, z, null);
    
    // Load the skybox textures...
    textures.add(Tools.loadTexture(skybox + "/" + skybox + "512_front", "jpg"));
    textures.add(Tools.loadTexture(skybox + "/" + skybox + "512_right", "jpg"));
    textures.add(Tools.loadTexture(skybox + "/" + skybox + "512_back", "jpg"));
    textures.add(Tools.loadTexture(skybox + "/" + skybox + "512_left", "jpg"));
    textures.add(Tools.loadTexture(skybox + "/" + skybox + "512_top", "jpg"));

    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      glPushMatrix();
      {          
        glPushAttrib(GL_ENABLE_BIT);
        {
          glDisable(GL_DEPTH_TEST);
          glDisable(GL_LIGHTING);
          glDisable(GL_BLEND);
          glEnable(GL_TEXTURE_2D);
          
          // Set the color to white to indicate an error...
          glColor4f(1, 1, 1, 1);

          // Render the front quad...
          clampToEdge();
          textures.get(0).bind();
          glBegin(GL_QUADS);
          {
            glTexCoord2f(1, 1);
            glVertex3f(1.0f, -1.0f, -1.0f);
            glTexCoord2f(0, 1);
            glVertex3f(-1.0f, -1.0f, -1.0f);
            glTexCoord2f(0, 0);
            glVertex3f(-1.0f, 1.0f, -1.0f);
            glTexCoord2f(1, 0);
            glVertex3f(1.0f, 1.0f, -1.0f);
          }
          glEnd();

          // Render the right quad...
          clampToEdge();
          textures.get(1).bind();
          glBegin(GL_QUADS);
          {
            glTexCoord2f(1, 1);
            glVertex3f(1.0f, -1.0f, 1.0f);
            glTexCoord2f(0, 1);
            glVertex3f(1.0f, -1.0f, -1.0f);
            glTexCoord2f(0, 0);
            glVertex3f(1.0f, 1.0f, -1.0f);
            glTexCoord2f(1, 0);
            glVertex3f(1.0f, 1.0f, 1.0f); 
          }
          glEnd();

          // Render the back quad...
          clampToEdge();
          textures.get(2).bind();
          glBegin(GL_QUADS);
          {
            glTexCoord2f(1, 1);
            glVertex3f(-1.0f, -1.0f, 1.0f);
            glTexCoord2f(0, 1);
            glVertex3f(1.0f, -1.0f, 1.0f);
            glTexCoord2f(0, 0);
            glVertex3f(1.0f, 1.0f, 1.0f);
            glTexCoord2f(1, 0);
            glVertex3f(-1.0f, 1.0f, 1.0f);
          }
          glEnd();

          // Render the left quad...
          clampToEdge();
          textures.get(3).bind();
          glBegin(GL_QUADS);
          {
            glTexCoord2f(1, 1);
            glVertex3f(-1.0f, -1.0f, -1.0f);
            glTexCoord2f(0, 1);
            glVertex3f(-1.0f, -1.0f, 1.0f);
            glTexCoord2f(0, 0);
            glVertex3f(-1.0f, 1.0f, 1.0f);
            glTexCoord2f(1, 0);
            glVertex3f(-1.0f, 1.0f, -1.0f);
          }
          glEnd();

          // Render the top quad
          clampToEdge();
          textures.get(4).bind();
          glBegin(GL_QUADS);
          {
            glTexCoord2f(1, 1);
            glVertex3f(1.0f, 1.0f, -1.0f);
            glTexCoord2f(0, 1);
            glVertex3f(-1.0f, 1.0f, -1.0f);
            glTexCoord2f(0, 0);
            glVertex3f(-1.0f, 1.0f, 1.0f);
            glTexCoord2f(1, 0);
            glVertex3f(1.0f, 1.0f, 1.0f);
          }
          glEnd();
          
          glEnable(GL_DEPTH_TEST);
          glEnable(GL_LIGHTING);
          glEnable(GL_BLEND);
          glDisable(GL_TEXTURE_2D);
        }
        glPopAttrib();
      }
      glPopMatrix();
    }
    glEndList();
  }

  /**
   * Aligns textures to edges correctly. Helper method...
   */
  private void clampToEdge()
  {
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
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
