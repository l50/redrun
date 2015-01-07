package redrun.main;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glVertex2f;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import redrun.model.constants.Constants;
import redrun.model.constants.GameState;
import redrun.model.game.GameData;
import redrun.model.toolkit.FontTools;
import redrun.model.toolkit.HUD_Manager;

/**
 * The main menu. Takes input from the keyboard to display information about the
 * game and exit the game.
 * 
 * @author Adam Mitchell, J. Jake Nichol
 * @version 1.0
 * @since 2014-19-10
 */
public class Menu
{
  /** The menu's state. */
  private static MenuState state = MenuState.MAIN_MENU;

  /** DisplayList id. */
  private static int transBackground1 = -1;

  /** The menu's default color. */
  private Color textColor = Color.white;

  /** The menu's selected text color. */
  private Color textSelectedColor = Color.gray;

  /** An array of colors for the menu. */
  private Color[] options = new Color[5];

  /** The menu's default indentation. */
  private int textIndentation = 70;

  /** The number of menu options. */
  private int numMenuOptions = 4;

  /** The current selection number. */
  private int selection = 0;

  /** The number of clients. */
  private int clients = 0;

  /** Debugging flag for printing. */
  private static final boolean DEBUG = false;

  /**
   * Contains all of the possible menu states. Possible states include: OFF,
   * CONTROLS, HOW_TO, EXIT, MAIN_MENU, ERROR;
   * 
   * @author Adam Mitchell, J. Jake Nichol
   * @version 1.0
   * @since 2014-19-10
   */
  public static enum MenuState
  {
    OFF, CONTROLS, HOW_TO, EXIT, MAIN_MENU, ERROR;
  }

  /**
   * Creates a new menu.
   */
  public Menu()
  {
    for (int i = 0; i < options.length; i++)
    {
      options[i] = textColor;
    }
    options[0] = textSelectedColor;
    selection = 0;

    transBackground1 = glGenLists(1);
    glNewList(transBackground1, GL_COMPILE);
    {
      glColor4f(0.1f, 0.1f, 0.1f, .3f);
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
   * Draws a grey transparent background over the scene.
   */
  private static void drawTransparentBackground()
  {
    HUD_Manager.make2D();
    glPushMatrix();
    {
      glCallList(transBackground1);
    }
    glPopMatrix();
    HUD_Manager.make3D();
  }

  /**
   * Checks for keyboard input and handles it appropriately.
   */
  private void checkMenuInput()
  {
    while (Keyboard.next())
    {
      if (Keyboard.getEventKey() == Keyboard.KEY_DOWN && Keyboard.getEventKeyState() && selection < numMenuOptions - 1)
      {
        ++selection;
        options[selection - 1] = textColor;
        options[selection] = textSelectedColor;
      }
      else if (Keyboard.getEventKey() == Keyboard.KEY_UP && Keyboard.getEventKeyState() && selection > 0)
      {
        --selection;
        options[selection + 1] = textColor;
        options[selection] = textSelectedColor;
      }
      else if (Keyboard.getEventKey() == Keyboard.KEY_RETURN && Keyboard.getEventKeyState())
      {
        state = MenuState.values()[selection];
        if (DEBUG) System.out.println(state);
      }
    }
  }

  /**
   * Allows users to press enter to return to the menu.
   */
  private void backToMenuControls()
  {
    while (Keyboard.next())
    {
      if (Keyboard.getEventKey() == Keyboard.KEY_RETURN && Keyboard.getEventKeyState())
      {
        state = MenuState.valueOf("MAIN_MENU");
        if (DEBUG) System.out.println(state);
      }
    }
  }

  /**
   * Controls the overall menu state.
   * 
   * Use static field state and set it to a game state in the following way.
   */
  public void stateControl()
  {
    switch (state)
    {
      case MAIN_MENU:
        checkMenuInput();
        menuText();
        break;
      case OFF:
        GameData.state = GameState.PLAY;
        break;
      case CONTROLS:
        backToMenuControls();
        controlsText();
        break;
      case HOW_TO:
        backToMenuControls();
        howToText();
        break;
      case EXIT:
        Main.requestClose();
        break;
      case ERROR:
        backToMenuControls();
        errorText();
        break;
      default:
        break;
    }
  }

  /**
   * Prints the main menu text.
   */
  private void menuText()
  {
    drawTransparentBackground();

    FontTools.renderText("Welcome to Red Run", textIndentation, 110, textColor, 3);
    FontTools.renderText("Clients Connected: " + GameData.playerCount, textIndentation, 170, textColor, 1);

    FontTools.renderText("Back to Game", textIndentation, 220, options[0], 2);
    FontTools.renderText("Controls", textIndentation, 260, options[1], 2);
    FontTools.renderText("How to Play", textIndentation, 300, options[2], 2);
    FontTools.renderText("Exit", textIndentation, 340, options[3], 2);

    FontTools.renderText("Use the arrow keys to select an option then press enter", textIndentation, 540, textColor, 1);
    FontTools.renderText("Number of clients must match selection", textIndentation, 560, textColor, 1);
  }

  /**
   * Prints text about controlling the game.
   */
  private void controlsText()
  {
    drawTransparentBackground();

    FontTools.renderText("Controls", textIndentation, 110, textColor, 3);
    FontTools.renderText("Use WASD controls to move around the map", textIndentation, 170, textColor, 2);
    FontTools.renderText("Press SPACE to jump", textIndentation, 220, textColor, 2);
    FontTools.renderText("In Spectator Mode, use SPACE to move upward and Left Control", textIndentation, 270,
        textColor, 2);
    FontTools.renderText("to move downward", textIndentation, 310, textColor, 2);
    FontTools.renderText("Press F to interact with buttons", textIndentation, 360, textColor, 2);
    FontTools.renderText("Press R to switch cameras", textIndentation, 410, textColor, 2);
    FontTools.renderText("Press ESC to open the menu", textIndentation, 460, textColor, 2);

    FontTools.renderText("Press enter to return the main menu", textIndentation, 540, textColor, 1);
  }

  /**
   * Prints information about how to play the game.
   */
  private void howToText()
  {
    drawTransparentBackground();

    FontTools.renderText("How to Play", textIndentation, 110, textColor, 3);
    FontTools.renderText(
        "Players on the BLUE team try to get from the beginning of the obstacle course to the end without dying.",
        textIndentation, 210, textColor, 1);
    FontTools.renderText("Players on the RED team try to spring traps which will kill the BLUE team players.",
        textIndentation, 250, textColor, 1);
    FontTools.renderText(
        "For the BLUE team to win, at least one BLUE player must reach the end of the obstacle course.",
        textIndentation, 290, textColor, 1);
    FontTools.renderText("For the RED team to win, all of the BLUE players must be dead and without lives.",
        textIndentation, 330, textColor, 1);

    FontTools.renderText("Press enter to return the main menu", textIndentation, 540, textColor, 1);
  }

  /**
   * Prints error text.
   */
  private void errorText()
  {
    drawTransparentBackground();

    FontTools.renderText("I'm sorry there are only " + clients + " clients connected.", 20, 110, textColor, 2);
    FontTools.renderText("You need " + (Constants.MAX_PLAYERS - clients) + " more users connect to play a", 20, 150,
        textColor, 2);
    FontTools.renderText("Feel free to test out the controls in the", 20, 240, textColor, 2);
    FontTools.renderText("Play Ground", 20, 280, textColor, 2);

    FontTools.renderText("Press enter to return the main menu", textIndentation, 540, textColor, 1);
  }

  /**
   * Gets the current state of the menu.
   * 
   * @return the menu's current state
   */
  public MenuState getState()
  {
    return state;
  }

  /**
   * Sets the menu to the MAIN_MENU state.
   */
  public void setState()
  {
    state = MenuState.MAIN_MENU;
  }
}
