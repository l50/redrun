package redrun.model.toolkit;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.Drawable;
import org.lwjgl.opengl.SharedDrawable;

import redrun.model.constants.Constants;
import redrun.model.gameobject.GameObject;

/**
 * Loading Screen class 
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-19-10
 * 
 * Game object the draws loading screen text.
 */
public class LoadingScreen extends GameObject
{
  private static BackgroundLoader backgroundLoader;


  /**
   * The loading screen 
   * @param texture not used.
   */
  public LoadingScreen(String texture)
  {
    super(0, 0, 0, texture);
    displayListId = glGenLists(1);

    glNewList(displayListId, GL_COMPILE);
    {
      glBegin(GL_QUADS);
      glTexCoord2f(0, 0);
      glVertex2i(-2, -2); // Upper-left
      glTexCoord2f(1, 0);
      glVertex2i(Constants.DISPLAY_WIDTH, 0); // Upper-right
      glTexCoord2f(1, 1);
      glVertex2i(Constants.DISPLAY_WIDTH, Constants.DISPLAY_HEIGHT); // Bottom-right
      glTexCoord2f(0, 1);
      glVertex2i(0, Constants.DISPLAY_HEIGHT); // Bottom-left
      glEnd();
    }
    glEndList();
  }

  @Override
  public void draw()
  {
    glPushMatrix();
    {
      glEnable(GL_TEXTURE_2D);
      texture.bind();
      glCallList(displayListId);
      glDisable(GL_TEXTURE_2D);
    }
    glPopMatrix();
  }

  /***
   * Sets up the loading screen
   */
  public static void loadingScreen()
  {
    backgroundLoader = new BackgroundLoader()
    {
      protected Drawable getDrawable() throws LWJGLException
      {
        return new SharedDrawable(Display.getDrawable());
      }
    };
    try
    {
      backgroundLoader.start();
    }
    catch (LWJGLException e)
    {
    }
    // set up the loading screen texture
    LoadingScreen spashScreen = new LoadingScreen("loading2");
    HUD_Manager.make2D();
    while (backgroundLoader.isRunning())
    {
      spashScreen.draw();
      Display.sync(65);
      Display.update();
    }
    HUD_Manager.make3D();
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
