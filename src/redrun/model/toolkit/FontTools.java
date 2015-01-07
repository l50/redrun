package redrun.model.toolkit;

import java.awt.Font;
import java.io.InputStream;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

/**
 * This class is a utility class to draw fonts to the screen.
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-11-10
 */
public class FontTools
{
  /** The first font small */
  private static TrueTypeFont fontSmall;
  /** The second font normal sized */
  private static TrueTypeFont fontNormal;
  /** The third font larger*/
  private static TrueTypeFont fontBig;
  /** The really big font*/
  private static TrueTypeFont fontBigger;


  /**
   * Loads the fonts to be used for drawing text. Default fonts are Helvetica, 
   * KGBlankSpaceSolid and size 12, 18, 30, and 44 feel free to come in here and 
   * change to any font supported by java.awt.Font, or add a font to the font res 
   * folder and modify the loadfonts code or just ask me (adam) to do for you :)
   * 
   * Takes a few seconds to load font so account for this when you call this
   * method. Should only be called once while initializing for the first time.
   */
  public static void loadFonts()
  {
    // small test fonts
    Font awtFontSmall = new Font("Helvetica", Font.TRUETYPE_FONT, 12);
    fontSmall = new TrueTypeFont(awtFontSmall, true);
    Font awtFontNormal = new Font("Helvetica", Font.TRUETYPE_FONT, 18);
    fontNormal = new TrueTypeFont(awtFontNormal, true);
    
    try
    {
      // main game fonts
      InputStream inputStream = ResourceLoader.getResourceAsStream("res/fonts/KGBlankSpaceSolid.ttf");
      InputStream inputStream2 = ResourceLoader.getResourceAsStream("res/fonts/KGBlankSpaceSolid.ttf");

      Font awtFontBigger = Font.createFont(Font.TRUETYPE_FONT, inputStream2);
      awtFontBigger = awtFontBigger.deriveFont(30f);
      fontBig = new TrueTypeFont(awtFontBigger, true);
      
      //Color.white.bind();
      Font awtExtraLargeFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
      awtExtraLargeFont = awtExtraLargeFont.deriveFont(44f);
      fontBigger = new TrueTypeFont(awtExtraLargeFont, true);
      Color.white.bind();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Draws a line of text to the screen.
   * 
   * If you want more size and font options let 
   * me know, it will just take more time to 
   * load them so lets try and be conservative.
   * 
   * @param string of the text to be drawn
   * @param x coordinate of screen where text is drawn
   * @param y coordinate of screen where text is drawn
   * @param the color of the font
   * @param font size 0 = 12pt, 1 = 18pt, 2 = 30pt, 3 = 44
   *
   */
  public static void renderText(String text, int x, int y, Color color, int size)
  {
    FontTools.start2DText();
    if (size == 0) fontSmall.drawString(x, y, text, color);
    if (size == 1) fontNormal.drawString(x, y, text, color);
    if (size == 2) fontBig.drawString(x, y, text, color);
    if (size == 3) fontBigger.drawString(x, y, text, color);
    FontTools.stop2DText();
  }

  /**
   * Switches the drawing mode from 3D to 2D
   */
  private static void start2DText()
  {
    glEnable(GL_TEXTURE_2D);
    glShadeModel(GL_SMOOTH);
    glDisable(GL_DEPTH_TEST);
    glDisable(GL_LIGHTING);
    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    glClearDepth(1);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glViewport(0, 0, Display.getWidth(), Display.getHeight());
    glMatrixMode(GL_MODELVIEW);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
  }

  /**
   * Switches the drawing mode from 2D to 3D.
   */
  private static void stop2DText()
  {
    glDisable(GL_TEXTURE_2D);
    glEnable(GL_DEPTH_TEST);
    glDisable(GL_BLEND);
    glEnable(GL_LIGHTING);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(70, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000f);
    glMatrixMode(GL_MODELVIEW);
    glDepthFunc(GL_LEQUAL);
    glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
  }
}
