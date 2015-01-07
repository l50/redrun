package redrun.model.game;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import redrun.database.Map;
import redrun.model.constants.Direction;
import redrun.model.constants.NetworkType;
import redrun.model.constants.Team;
import redrun.model.constants.TrapType;
import redrun.model.gameobject.GameObject;
import redrun.model.gameobject.MapObject;
import redrun.model.gameobject.map.Corner;
import redrun.model.gameobject.map.Corridor;
import redrun.model.gameobject.map.End;
import redrun.model.gameobject.map.Field;
import redrun.model.gameobject.map.Kiosk;
import redrun.model.gameobject.map.Pit;
import redrun.model.gameobject.map.Platform;
import redrun.model.gameobject.map.Staircase;
import redrun.model.gameobject.map.Start;
import redrun.model.gameobject.map.Tunnel;
import redrun.model.gameobject.player.Player;
import redrun.model.gameobject.trap.Trap;
import redrun.model.gameobject.world.Plane;
import redrun.model.gameobject.world.SkyBox;

/**
 * This class represents a map object that is used to construct Redrun maps.
 * 
 * @author Troy Squillaci ( zivia@unm.edu ), Jayson Grace ( jaysong@unm.edu )
 * @version 1.0
 * @since 2014-11-22
 */
public class ObjectFromDB
{
  // Regex Patterns...
  /** The map pattern. */
  private static Pattern mapPattern = Pattern
      .compile("(===\\sMap\\s===)\\sID:(\\d+)\\sName:(.*?)\\sSkyBox:(\\w+)\\sFloor:(\\w+)\\sLight Position:(.*?)\\s===");

  /** The map objects pattern. */
  private static Pattern mapObjectPattern = Pattern
      .compile("(===\\sMap\\sObject\\s===)\\sID:(\\d+)\\sName:(\\w+)\\sLocation:(\\d+\\.\\d+f),\\s(\\d+\\.\\d+f),\\s(\\d+\\.\\d+f)\\sGround Texture:(\\w+)\\sWall Texture:(\\w+)\\sDirection:(\\w+)\\sTrap Type:(.*?)\\sMap\\sID:(\\d+)\\s===");

  /** The player pattern. */
  private static Pattern playerPattern = Pattern
      .compile("===\\sPlayer\\s===\\sLocation:\\[(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?)\\]\\sName:(.*?)\\sTeam\\sName:(\\w+)\\sHealth:(.*?)\\sLives\\sleft:(\\d+)\\sAlive:(\\w+)\\s===");

  /** The trap pattern. */
  private static Pattern trapPattern = Pattern.compile("===\\sTrap\\s===\\sID:(\\d+)\\s===");

  /** The number of players in game pattern. */
  private static Pattern numberPlayers = Pattern.compile("^===\\sNumber Players\\s===\\sNumber:(\\d+)\\s===$");

  // Regex Matchers...
  /** The map matcher. */
  private static Matcher mapMatcher = null;

  /** The map objects matcher. */
  private static Matcher mapObjectMatcher = null;

  /** The player matcher. */
  private static Matcher playerMatcher = null;

  /** The trap matcher. */
  private static Matcher trapMatcher = null;

  /** The number of players in game matcher. */
  private static Matcher numberPlayersMatcher = null;

  /**
   * Determines the type of network data that is received by the client from the
   * server.
   * 
   * @param networkData the network data string
   * @return the type of network data
   */
  public static NetworkType parseNetworkType(String networkData)
  {
    mapMatcher = mapPattern.matcher(networkData);
    mapObjectMatcher = mapObjectPattern.matcher(networkData);
    playerMatcher = playerPattern.matcher(networkData);
    trapMatcher = trapPattern.matcher(networkData);
    numberPlayersMatcher = numberPlayers.matcher(networkData);

    if (playerMatcher.find())
    {
      return NetworkType.PLAYER;
    }
    else if (trapMatcher.find())
    {
      return NetworkType.TRAP;
    }
    else if (mapObjectMatcher.find())
    {
      return NetworkType.MAP_OBJECT;
    }
    else if (mapMatcher.find())
    {
      return NetworkType.MAP;
    }
    else if (numberPlayersMatcher.find())
    {
      return NetworkType.NUMBER_PLAYERS;
    }
    else
    {
      return null;
    }
  }

  /**
   * Populates the map fields on the client. This includes everything except map
   * objects.
   * 
   * @param networkItem
   * @return
   */
  public static Map createMap(String networkData)
  {
    mapMatcher = mapPattern.matcher(networkData);

    if (mapMatcher.find()) { return new Map(Integer.parseInt(mapMatcher.group(2)), mapMatcher.group(3),
        mapMatcher.group(4), mapMatcher.group(5), mapMatcher.group(6)); }
    return null;
  }

  /**
   * Creates and returns a new map object from the string representation of the
   * database object.
   * 
   * @param networkData the database representation of the map object
   * @return the new map object
   */
  public static MapObject createMapObject(String networkData)
  {
    mapObjectMatcher = mapObjectPattern.matcher(networkData);

    if (mapObjectMatcher.find())
    {
      // The position of the map object...
      float x = Float.parseFloat(mapObjectMatcher.group(4));
      float y = Float.parseFloat(mapObjectMatcher.group(5));
      float z = Float.parseFloat(mapObjectMatcher.group(6));

      // The textures to apply to the map object...
      final String groundTexture = mapObjectMatcher.group(7);
      final String wallTexture = mapObjectMatcher.group(8);

      // The orientation of the map object...
      Direction orientation = null;
      switch (mapObjectMatcher.group(9))
      {
        case "NORTH":
        {
          orientation = Direction.NORTH;
          break;
        }
        case "EAST":
        {
          orientation = Direction.EAST;
          break;
        }
        case "SOUTH":
        {
          orientation = Direction.SOUTH;
          break;
        }
        case "WEST":
        {
          orientation = Direction.WEST;
          break;
        }
        default:
        {
          try
          {
            throw new IllegalArgumentException();
          }
          catch (IllegalArgumentException e)
          {
            e.printStackTrace();
          }
        }
      }

      // The trap placed on the map object...
      TrapType type = null;
      switch (mapObjectMatcher.group(10))
      {
        case "PIT":
        {
          type = TrapType.PIT;
          break;
        }
        case "EXPLODING_BOX_FIELD":
        {
          type = TrapType.EXPLODING_BOX_FIELD;
          break;
        }
        case "JAIL":
        {
          type = TrapType.JAIL;
          break;
        }
        case "SPIKE_FIELD":
        {
          type = TrapType.SPIKE_FIELD;
          break;
        }
        case "SPIKE_TRAP_DOOR":
        {
          type = TrapType.SPIKE_TRAP_DOOR;
          break;
        }
        case "POLE_WALL":
        {
          type = TrapType.POLE_WALL;
          break;
        }
        case "POLE_DANCE":
        {
          type = TrapType.POLE_DANCE;
          break;
        }
        case "ROCK_SMASH":
        {
          type = TrapType.ROCK_SMASH;
          break;
        }
        case "TRAP_DOOR":
        {
          type = TrapType.TRAP_DOOR;
          break;
        }
        case "EMPTY":
        {
          type = TrapType.EMPTY;
          break;
        }
        default:
        {
          try
          {
            throw new IllegalArgumentException();
          }
          catch (IllegalArgumentException e)
          {
            e.printStackTrace();
          }
        }
      }

      // Make and return the map object...
      switch (mapObjectMatcher.group(3))
      {
        case "Corner":
        {
          return new Corner(x, y, z, groundTexture, wallTexture, orientation, type);
        }
        case "Corridor":
        {
          return new Corridor(x, y, z, groundTexture, wallTexture, orientation, type);
        }
        case "End":
        {
          return new End(x, y, z, groundTexture, wallTexture, orientation, type);
        }
        case "Field":
        {
          return new Field(x, y, z, groundTexture, wallTexture, orientation, type);
        }
        case "Kiosk":
        {
          return new Kiosk(x, y, z, groundTexture, wallTexture, orientation, type);
        }
        case "Pit":
        {
          return new Pit(x, y, z, groundTexture, wallTexture, orientation, type);
        }
        case "Platform":
        {
          return new Platform(x, y, z, groundTexture, wallTexture, orientation, type);
        }
        case "Staircase":
        {
          return new Staircase(x, y, z, groundTexture, wallTexture, orientation, type);
        }
        case "Start":
        {
          return new Start(x, y, z, groundTexture, wallTexture, orientation, type);
        }
        case "Tunnel":
        {
          return new Tunnel(x, y, z, groundTexture, wallTexture, orientation, type);
        }
        default:
        {
          try
          {
            throw new IllegalArgumentException();
          }
          catch (IllegalArgumentException e)
          {
            e.printStackTrace();
          }
        }
      }
    }
    return null;
  }

  /**
   * Updates traps on the client end from network data.
   * 
   * @param networkData the networkData from the server
   */
  public static void updateTrap(String networkData)
  {
    trapMatcher = trapPattern.matcher(networkData);

    if (trapMatcher.find())
    {
      int id = Integer.parseInt(trapMatcher.group(1));

      GameObject object = GameObject.getGameObject(id);

      if (object instanceof Trap)
      {
        Trap trap = (Trap) object;

        System.out.println("Activating.........");
        trap.activate();
      }
      else
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
    }
  }

  /**
   * Updates players on the client end from network data.
   * 
   * @param networkData the networkData from the server
   */
  public static void updatePlayer(String networkData)
  {
    playerMatcher = playerPattern.matcher(networkData);

    if (playerMatcher.find())
    {
      float buf1 = Float.parseFloat(playerMatcher.group(1));
      float buf2 = Float.parseFloat(playerMatcher.group(2));
      float buf3 = Float.parseFloat(playerMatcher.group(3));
      float buf4 = Float.parseFloat(playerMatcher.group(4));
      float buf5 = Float.parseFloat(playerMatcher.group(5));
      float buf6 = Float.parseFloat(playerMatcher.group(6));
      float buf7 = Float.parseFloat(playerMatcher.group(7));
      float buf8 = Float.parseFloat(playerMatcher.group(8));
      float buf9 = Float.parseFloat(playerMatcher.group(9));
      float buf10 = Float.parseFloat(playerMatcher.group(10));
      float buf11 = Float.parseFloat(playerMatcher.group(11));
      float buf12 = Float.parseFloat(playerMatcher.group(12));
      float buf13 = Float.parseFloat(playerMatcher.group(13));
      float buf14 = Float.parseFloat(playerMatcher.group(14));
      float buf15 = Float.parseFloat(playerMatcher.group(15));
      float buf16 = Float.parseFloat(playerMatcher.group(16));

      float[] buffer = { buf1, buf2, buf3, buf4, buf5, buf6, buf7, buf8, buf9, buf10, buf11, buf12, buf13, buf14,
          buf15, buf16 };

      String name = playerMatcher.group(17);

      int health = Integer.parseInt(playerMatcher.group(19));
      int lives = Integer.parseInt(playerMatcher.group(20));
      boolean alive = Boolean.parseBoolean(playerMatcher.group(21));

      boolean isFound = false;

      for (Player player : GameData.players)
      {
        if (name.equals(player.getName()))
        {
          if (!name.equals(GameData.players.get(0).getName()))
          {
            player.getBody().setFromOpenGLTransformMatrix(buffer);
            player.setHealth(health);
            player.setLives(lives);
            player.setAlive(alive);
          }
          isFound = true;
        }
      }

      if (!isFound)
      {
        GameData.players.add(createPlayer(networkData));
      }
    }
  }

  /**
   * Creates a new player from the network data.
   * 
   * @param networkData the networkData from the server
   */
  public static Player createPlayer(String networkData)
  {
    playerMatcher = playerPattern.matcher(networkData);

    if (playerMatcher.find())
    {
      float x = Float.parseFloat(playerMatcher.group(14));
      float y = Float.parseFloat(playerMatcher.group(15));
      float z = Float.parseFloat(playerMatcher.group(16));

      String name = playerMatcher.group(17);

      Team team = null;

      switch (playerMatcher.group(18))
      {
        case "RED":
        {
          team = Team.RED;
          break;
        }
        case "BLUE":
        {
          team = Team.BLUE;
          break;
        }
        default:
        {
          try
          {
            throw new IllegalArgumentException();
          }
          catch (IllegalArgumentException e)
          {
            e.printStackTrace();
          }
        }
      }

      return new Player(x, y, z, name, team);
    }

    return null;
  }

  /**
   * Creates and returns a new skybox with the specified texture from the
   * database.
   * 
   * @param skyboxTexture the texture to apply to the skybox, received from the
   *          database
   * @return a new skybox
   */
  public static SkyBox createSkybox(String skyboxTexture)
  {
    return new SkyBox(0, 0, 0, skyboxTexture);
  }

  /**
   * Creates and returns a new plane with the specified texture from the
   * database.
   * 
   * @param groundTexture the texture to apply to the floor, received from the
   *          database
   * @return a new plane
   */
  public static Plane createFloor(String groundTexture)
  {
    return new Plane(0, -0.5f, 0, groundTexture, Direction.NORTH, 1000);
  }

  /**
   * Get the title from the map.
   * 
   * @param map name of map
   * @param mapID id for map
   * @return title of map
   */
  public static String getTitle(String map, int mapID)
  {
    Pattern getGameObject = Pattern
        .compile("(===\\sMap\\s===)\\sID:(\\d+)\\sName:(.*?)\\sSkyBox:(\\w+)\\sFloor:(\\w+)\\sLight Position:(.*?)\\s===");

    Matcher matchGameObject = getGameObject.matcher(map);

    if (matchGameObject.find()) return matchGameObject.group(3);
    else return null;
  }

  /**
   * Return the number of players
   * 
   * @param networkData data with the number of players
   * @return data parsed from input data in integer format
   */
  public static int returnPlayerNumber(String networkData)
  {
    numberPlayersMatcher = numberPlayers.matcher(networkData);
    if (numberPlayersMatcher.find()) { return Integer.parseInt(numberPlayersMatcher.group(1)); }
    return 0;
  }
}
