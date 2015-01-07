package redrun.model.toolkit;

import static org.lwjgl.opengl.GL11.*;

import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import redrun.graphics.camera.Camera;
import redrun.model.constants.CameraType;
import redrun.model.constants.Constants;
import redrun.model.gameobject.player.Player;

/**
 * Creates and manages the HUD
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-11-9
 * 
 */
public class HUD_Manager
{
  private static Texture texture = null;
  private static boolean setUp = true;
  /** The display list IDs */
  private static int heartID = -1, healthOutlineID = -1, hudID = -1, healthID = -1, transBackground1 = -1,
      transBackground2 = -1, transBackground3 = -1;
  public static int transBackgroundUtilGrey = -1;
  public static int transBackgroundUtilRed = -1;

  /*
   * Pass in a player to set up the needed info
   */
  private static void setUp(Player player)
  {
    texture = Tools.loadTexture("heart", "png");

    /** The heart display list */
    heartID = glGenLists(1);
    glNewList(heartID, GL_COMPILE);
    {
      glColor3f(1, 1, 1);
      glBegin(GL_QUADS);
      glTexCoord2f(0, 0);
      glVertex2f(8, 5);
      glTexCoord2f(1, 0);
      glVertex2f(8 + 20, 5);
      glTexCoord2f(1, 1);
      glVertex2f(8 + 20, 5 + 20);
      glTexCoord2f(0, 1);
      glVertex2f(8, 5 + 20);
      glEnd();
    }
    glEndList();

    /** The healthID display list */
    healthID = glGenLists(1);
    glNewList(healthID, GL_COMPILE);
    {
      glBegin(GL_QUADS);
      glVertex2f(11, 65);
      glVertex2f(11 + 1, 65);
      glVertex2f(11 + 1, 65 + 5);
      glVertex2f(11, 65 + 5);
      glEnd();
    }
    glEndList();

    /** The healthOutlineID display list */
    healthOutlineID = glGenLists(1);
    glNewList(healthOutlineID, GL_COMPILE);
    {
      glColor3f(1, 1, 1);
      glBegin(GL_LINE_STRIP);
      glVertex2f(10, 70);
      glVertex2f(10, 70 - 6);
      glVertex2f(10 + player.getHealth() + 1, 70 - 6);
      glVertex2f(10 + player.getHealth() + 1, 70);
      glVertex2f(10, 69);
      glEnd();
    }
    glEndList();

    /** The hudID display list */
    hudID = glGenLists(1);
    glNewList(hudID, GL_COMPILE);
    {
      glColor3f(1, 1, 1);
      glBegin(GL_LINE_STRIP);
      glVertex2f(Constants.DISPLAY_WIDTH / 2 - 40, Constants.DISPLAY_HEIGHT / 2 + 60);
      glVertex2f(Constants.DISPLAY_WIDTH / 2 - 60, Constants.DISPLAY_HEIGHT / 2 + 60);
      glVertex2f(Constants.DISPLAY_WIDTH / 2 - 60, Constants.DISPLAY_HEIGHT / 2 + 40);
      glEnd();
      glBegin(GL_LINE_STRIP);
      glVertex2f(Constants.DISPLAY_WIDTH / 2 + 60, Constants.DISPLAY_HEIGHT / 2 + 40);
      glVertex2f(Constants.DISPLAY_WIDTH / 2 + 60, Constants.DISPLAY_HEIGHT / 2 + 60);
      glVertex2f(Constants.DISPLAY_WIDTH / 2 + 40, Constants.DISPLAY_HEIGHT / 2 + 60);
      glEnd();
      glBegin(GL_LINE_STRIP);
      glVertex2f(Constants.DISPLAY_WIDTH / 2 + 60, Constants.DISPLAY_HEIGHT / 2 - 40);
      glVertex2f(Constants.DISPLAY_WIDTH / 2 + 60, Constants.DISPLAY_HEIGHT / 2 - 60);
      glVertex2f(Constants.DISPLAY_WIDTH / 2 + 40, Constants.DISPLAY_HEIGHT / 2 - 60);
      glEnd();
      glBegin(GL_LINE_STRIP);
      glVertex2f(Constants.DISPLAY_WIDTH / 2 - 40, Constants.DISPLAY_HEIGHT / 2 - 60);
      glVertex2f(Constants.DISPLAY_WIDTH / 2 - 60, Constants.DISPLAY_HEIGHT / 2 - 60);
      glVertex2f(Constants.DISPLAY_WIDTH / 2 - 60, Constants.DISPLAY_HEIGHT / 2 - 40);
      glEnd();
    }
    glEndList();

    /** The transBackground1 display list */
    transBackground1 = glGenLists(1);
    glNewList(transBackground1, GL_COMPILE);
    {
      glColor4f(0.1f, 0.1f, 0.1f, .5f);
      glBegin(GL_QUADS);
      glVertex2f(0, Constants.DISPLAY_HEIGHT);
      glVertex2f(0 + 320, Constants.DISPLAY_HEIGHT);
      glVertex2f(0 + 320, Constants.DISPLAY_HEIGHT - 90);
      glVertex2f(0, Constants.DISPLAY_HEIGHT - 90);
      glEnd();
    }
    glEndList();

    /** The transBackground2 display list */
    transBackground2 = glGenLists(1);
    glNewList(transBackground2, GL_COMPILE);
    {
      glColor4f(0.1f, 0.1f, 0.1f, .5f);
      glBegin(GL_QUADS);
      glVertex2f(0, 0);
      glVertex2f(0 + 130, 0);
      glVertex2f(0 + 130, 0 + 100);
      glVertex2f(0, 0 + 100);
      glEnd();
    }
    glEndList();

    /** The transBackground3 display list */
    transBackground3 = glGenLists(1);
    glNewList(transBackground3, GL_COMPILE);
    {
      glColor4f(0.1f, 0.1f, 0.1f, .5f);
      glBegin(GL_QUADS);
      glVertex2f(0, Constants.DISPLAY_HEIGHT);
      glVertex2f(0 + 330, Constants.DISPLAY_HEIGHT);
      glVertex2f(0 + 330, Constants.DISPLAY_HEIGHT - 50);
      glVertex2f(0, Constants.DISPLAY_HEIGHT - 50);
      glEnd();
    }
    glEndList();

    /** The transBackgroundUtilGrey display list */
    transBackgroundUtilGrey = glGenLists(1);
    glNewList(transBackgroundUtilGrey, GL_COMPILE);
    {
      glColor4f(0.1f, 0.1f, 0.1f, .4f);
      glBegin(GL_QUADS);
      glVertex2f(0, Constants.DISPLAY_HEIGHT);
      glVertex2f(0 + Constants.DISPLAY_WIDTH, Constants.DISPLAY_HEIGHT);
      glVertex2f(0 + Constants.DISPLAY_WIDTH, -Constants.DISPLAY_HEIGHT);
      glVertex2f(0, -Constants.DISPLAY_HEIGHT);
      glEnd();
    }
    glEndList();

    /** The transBackgroundUtilRed display list */
    transBackgroundUtilRed = glGenLists(1);
    glNewList(transBackgroundUtilRed, GL_COMPILE);
    {
      glColor4f(1.0f, 0.0f, 0.0f, .45f);
      glBegin(GL_QUADS);
      glVertex2f(0, Constants.DISPLAY_HEIGHT);
      glVertex2f(0 + Constants.DISPLAY_WIDTH, Constants.DISPLAY_HEIGHT);
      glVertex2f(0 + Constants.DISPLAY_WIDTH, -Constants.DISPLAY_HEIGHT);
      glVertex2f(0, -Constants.DISPLAY_HEIGHT);
      glEnd();
    }
    glEndList();

  }

  /**
   * Renders the HUDs player and spectator
   * @param cam the camera being used
   * @param player the current player
   */
  public static void huds(Camera cam, Player player)
  {
    if (setUp == true)
    {
      setUp = false;
      setUp(player);
    }
    // Draw spectator text
    if (cam.getType() == CameraType.SPECTATOR)
    {
      // draw spectator HUD
      make2D();
      drawSpectatorHUD();
      make3D();

      // draw spectator text
      FontTools.renderText(
          "Spectator Camera: (" + (float) Math.round(cam.getX() * 10) / 10 + ", " + (float) Math.round(cam.getY() * 10)
              / 10 + ", " + (float) Math.round(cam.getZ() * 10) / 10 + ")", 10, 10, Color.white, 1);
    }
    else if (cam.getType() == CameraType.PLAYER)
    {
      // draw player HUD
      make2D();
      drawPlayerHUD(player);
      make3D();

      // Draw player text
      FontTools.renderText("Player: " + player.getName(), 10, 10, Color.white, 1);
      FontTools.renderText("Team: " + player.getTeam(), 10, 30, Color.white, 1);
      FontTools.renderText("Lives: " + player.getLives(), 10, Constants.DISPLAY_HEIGHT - 53, Color.white, 1);
      FontTools.renderText(
          "Player Camera: (" + (float) Math.round(cam.getX() * 10) / 10 + ", " + (float) Math.round(cam.getY() * 10)
              / 10 + ", " + (float) Math.round(cam.getZ() * 10) / 10 + ")", 10, 50, Color.white, 1);
      FontTools.renderText("Health: " + player.getHealth(), 10, Constants.DISPLAY_HEIGHT - 100, Color.white, 1);
    }
  }

  /**
   * Draws player health and lives remaining
   * @param Player player
   */
  private static void drawPlayerHUD(Player player)
  {

    glPushMatrix();
    {
      glCallList(transBackground2);
      glCallList(transBackground1);
      glCallList(healthOutlineID);
    }
    glPopMatrix();

    for (int i = 0; i < player.getLives(); i++)
    {
      glPushMatrix();
      {
        glEnable(GL_TEXTURE_2D);
        texture.bind();
        glTranslatef(25f * i, 0f, 0f);
        glCallList(heartID);
        glDisable(GL_TEXTURE_2D);
      }
      glPopMatrix();
    }

    for (int i = 0; i < player.getHealth() / 2; i++)
    {
      glPushMatrix();
      {
        if (player.getHealth() < 20)
        {
          glColor3f(1, 0, 0);
        }
        else if (player.getHealth() < 50)
        {
          glColor3f(1, 1, 0);
        }
        else
        {
          glColor3f(0, 1, 0);
        }
        glTranslatef(2f * i, 0f, 0f);
        glCallList(healthID);
      }
      glPopMatrix();
    }
    // draw the cross hairs
    drawHUD();
  }

  /**
   * drawSpectatorHUD
   */
  private static void drawSpectatorHUD()
  {
    glPushMatrix();
    {
      glCallList(transBackground3);
    }
    glPopMatrix();
    // draw the cross hairs
    drawHUD();
  }

  /**
   * Draws the center screen hud i.e. the cross hairs
   */
  private static void drawHUD()
  {
    glPushMatrix();
    {
      glCallList(hudID);
    }
    glPopMatrix();
  }

  /**
   * drawBackGroundRed
   */
  public static void drawBackGroundRed()
  {
    glPushMatrix();
    {
      glCallList(HUD_Manager.transBackgroundUtilRed);
    }
    glPopMatrix();
  }

  /**
   * drawBackGroundGrey
   */
  public static void drawBackGroundGrey()
  {
    glPushMatrix();
    {
      glCallList(HUD_Manager.transBackgroundUtilGrey);
    }
    glPopMatrix();
  }

  /**
   * displayGameOver 
   * @param cam the camera 
   * @param player player
   */
  public static void displayGameOver(Camera cam, Player player)
  {
    if (cam.getType() == CameraType.PLAYER)
    {
      // TODO give player a winner boolean
      if (player.isWinner() == 1)
      {
        displayYouWin();
      }
      else if (player.isWinner() == -1)
      {
        displayYouLose();
      }
    }
  }

  /**
   * displayYouDied
   * 
   * @param the camera 
   */
  public static void displayYouDied(Camera cam)
  {
    if (cam.getType() == CameraType.PLAYER)
    {
      make2D();
      drawBackGroundRed();
      make3D();

      FontTools.renderText("Wasted", Constants.DISPLAY_WIDTH / 4, Constants.DISPLAY_HEIGHT / 2, Color.red, 3);
    }
  }

  /**
   * displayYouWin
   */
  public static void displayYouWin()
  {
    make2D();
    drawBackGroundGrey();
    make3D();

    FontTools.renderText("Congratulations Your Team Won", Constants.DISPLAY_WIDTH / 4, Constants.DISPLAY_HEIGHT / 2,
        Color.red, 3);

  }

  /**
   * displayYouLose
   */
  public static void displayYouLose()
  {
    make2D();
    drawBackGroundGrey();
    make3D();

    FontTools.renderText("Your Team Lost.", Constants.DISPLAY_WIDTH / 2, Constants.DISPLAY_HEIGHT / 2, Color.red, 3);
  }

  /**
   * makes the screen 2D
   */
  public static void make2D()
  {
    // Remove the Z axis
    glDisable(GL_LIGHTING);
    glDisable(GL_DEPTH_TEST);
    glMatrixMode(GL_PROJECTION);
    glPushMatrix();
    glLoadIdentity();
    glOrtho(0, Constants.DISPLAY_WIDTH, 0, Constants.DISPLAY_HEIGHT, -1, 1);
    glMatrixMode(GL_MODELVIEW);
    glPushMatrix();
    glLoadIdentity();
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
  }

  /**
   * makes the screen 3D
   */
  public static void make3D()
  {
    // Restore the Z axis
    glMatrixMode(GL_PROJECTION);
    glPopMatrix();
    glMatrixMode(GL_MODELVIEW);
    glPopMatrix();
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_LIGHTING);
    glDisable(GL_BLEND);
  }
}
