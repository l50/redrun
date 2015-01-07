package redrun.model.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

import redrun.database.Map;
import redrun.model.constants.GameState;
import redrun.model.gameobject.GameObject;
import redrun.model.gameobject.MapObject;
import redrun.model.gameobject.player.Player;
import redrun.model.gameobject.trap.Trap;
import redrun.model.gameobject.world.Button;
import redrun.sound.SoundManager;

/**
 * Used to maintain data for the game
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-09
 */
public class GameData
{
  // Network related data...
  /** The network data that is to be turned into map objects or game objects. */
  public static volatile CopyOnWriteArrayList<String> networkData = new CopyOnWriteArrayList<String>();

  /** Used to keep track of the current map. */
  public static Map map = null;
  
  /** The number of players connected to the RedRun session. */
  public static int playerCount = -1;

  // GameObject and MapObject related data...
  /** The list of players. */
  public static LinkedList<Player> players = new LinkedList<Player>();

  /** The list of most active map objects. */
  public static LinkedList<MapObject> mapObjects = new LinkedList<MapObject>();

  /** The list of most active game objects. */
  public static LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();

  /** Used for constructing the connections map. Holds buttons. */
  public static ArrayList<Button> buttons = new ArrayList<Button>();

  /** Used for constructing the connections map. Holds traps. */
  public static ArrayList<Trap> traps = new ArrayList<Trap>();

  /** The mapping of buttons to traps. */
  public static HashMap<Button, Trap> connections = new HashMap<Button, Trap>();

  // Sounds related data...
  /** The active sound manager. */
  public static SoundManager soundManager = new SoundManager();

  /** The current game state for this client and its player. */
  public static GameState state = GameState.MAIN_MENU;

  /**
   * Adds a button the list of button.
   * 
   * @param button the button to add
   */
  public static void addButton(Button button)
  {
    buttons.add(button);
  }

  /**
   * Adds a trap the list of traps.
   * 
   * @param trap the trap to add
   */
  public static void addTrap(Trap trap)
  {
    traps.add(trap);
  }

  /**
   * Adds a map object to the list of map objects.
   * 
   * @param mapObject a map object
   */
  public static void addMapObject(MapObject mapObject)
  {
    mapObjects.add(mapObject);
  }

  /**
   * Adds a game object to the list of map objects.
   * 
   * @param gameObject a game object
   */
  public static void addGameObject(GameObject gameObject)
  {
    gameObjects.add(gameObject);
  }

  /**
   * Associates a button and trap.
   * 
   * @param button the button
   * @param trap the trap
   */
  public static void addConnection(Button button, Trap trap)
  {
    connections.put(button, trap);
  }

  // Getter methods...

  /**
   * Gets the list of active map objects.
   * 
   * @return the list of map objects
   */
  public static LinkedList<MapObject> getMapObjects()
  {
    return mapObjects;
  }

  /**
   * Gets the list of active game objects
   * 
   * @return the list of game objects
   */
  public static LinkedList<GameObject> getGameObjects()
  {
    return gameObjects;
  }

  /**
   * Gets the trap associated with the specified button.
   * 
   * @param button the button
   * @return the trap associated with the button
   */
  public static Trap getTrap(Button button)
  {
    return connections.get(button);
  }

  /**
   * Binds all buttons to traps.
   */
  public static void bindConnections()
  {
    System.out.println("Buttons Size: " + buttons.size() + ", Traps Size: " + traps.size());

    if (buttons.size() != traps.size())
    {
      try
      {
        throw new IllegalStateException();
      }
      catch (IllegalStateException ex)
      {
        ex.printStackTrace();
      }
    }
    else
    {
      for (int i = 0; i < buttons.size(); i++)
      {
        connections.put(buttons.get(i), traps.get(i));
      }
    }
  }

  /**
   * Destroys all sounds used.
   */
  public static void destroy()
  {
    soundManager.destroySounds();
  }
}
