package redrun.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * DAO used to facilitate all database interactions in the RedRun Database
 * 
 * @author Jayson Grace ( jaysong @ unm.edu )
 * @version 1.0
 * @since 2014-11-24
 */
public class RedRunDAO
{
  /** Set DEBUG */
  private static final boolean DEBUG = false;
  /** Connection to Database */
  private static Connection c = RedRunDAO.connectToDB();

  /**
   * Generate a connecting to the database
   * 
   * @return the established connection
   */
  public static Connection connectToDB()
  {
    Connection c = null;
    try
    {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:src/redrun/database/redRun.db");
    }
    catch (Exception e)
    {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    System.out.println("Opened database successfully");
    return c;
  }

  /**
   * Close connection to the database
   */
  public void closeConnection()
  {
    try
    {
      c.close();
    }
    catch (Exception e)
    {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }

  /**
   * Insert new map into Maps table
   * 
   * @param mapName name of map
   * @param skyBox skyBox image
   * @param floor floor image
   * @param lightPosition light position
   * @return true if map created, false otherwise
   */
  public static boolean insertMap(String mapName, String skyBox, String floor, String lightPosition)
  {
    try
    {
      String sql = "INSERT INTO maps(map_name,sky_box,floor,light_position) VALUES (?,?,?,?)";

      /** F**k SQL Injections. */
      PreparedStatement pstmt = c.prepareStatement(sql);
      pstmt.setString(1, mapName);
      pstmt.setString(2, skyBox);
      pstmt.setString(3, floor);
      pstmt.setString(4, lightPosition);
      pstmt.executeUpdate();
      pstmt.close();
      return true;
    }
    catch (SQLException e)
    {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    return false;
  }

  /**
   * Insert new mapObject into Map Objects table
   * 
   * @param objectName name of map object
   * @param location location of map object
   * @param groundTexture texture to use with map object for ground
   * @param wallTexture texture to use with map object for wall
   * @param direction orientation of the map object
   * @param trapType type of trap used
   * @param mapId associated map
   * @return true if mapobject created, otherwise false
   */
  public static boolean insertMapObject(String objectName, String location, String groundTexture, String wallTexture,
      String direction, String trapType, int mapId)
  {
    try
    {
      String sql = "INSERT INTO mapobjects(object_name, location, ground_texture, wall_texture, direction, trap_type, map_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

      /** F**k SQL Injections. */
      PreparedStatement pstmt = c.prepareStatement(sql);
      pstmt.setString(1, objectName);
      pstmt.setString(2, location);
      pstmt.setString(3, groundTexture);
      pstmt.setString(4, wallTexture);
      pstmt.setString(5, direction);
      pstmt.setString(6, trapType);
      pstmt.setInt(7, mapId);
      pstmt.executeUpdate();
      pstmt.close();
      return true;
    }
    catch (SQLException e)
    {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    return false;
  }

  /**
   * Get a list of all maps in the Map table
   * 
   * @param mapId associated map
   * @return list of all items in the Map database
   */
  public static Map getMap(int mapId)
  {
    try
    {
      Statement sqlStatement = c.createStatement();

      ResultSet rs = sqlStatement.executeQuery("SELECT * FROM maps WHERE id = " + mapId + ";");
      int id = rs.getInt("id");
      String mapName = rs.getString("map_name");
      String skyBox = rs.getString("sky_box");
      String floor = rs.getString("floor");
      String lightPosition = rs.getString("light_position");
      Map map = new Map(id, mapName, skyBox, floor, lightPosition);
      if (DEBUG)
      {
        System.out.println(id);
        System.out.println(mapName);
        System.out.println(skyBox);
        System.out.println(floor);
        System.out.println(lightPosition);
      }
      rs.close();
      sqlStatement.close();
      return map;
    }
    catch (SQLException e)
    {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    return null;
  }

  /**
   * Get a map name by its id
   * 
   * @param mapId associated map id
   * @return the map name
   */
  public static String getMapNameById(int mapId)
  {
    try
    {
      Statement sqlStatement = c.createStatement();

      ResultSet rs = sqlStatement.executeQuery("SELECT map_name FROM maps WHERE id = " + mapId + ";");
      String mapName = rs.getString("map_name");
      if (DEBUG) System.out.println(mapName);
      rs.close();
      sqlStatement.close();
      return mapName;
    }
    catch (SQLException e)
    {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    return null;
  }

  /**
   * Get a list of all maps in the Map table
   * 
   * @return list of all items in the Map database
   */
  public static ArrayList<Map> getAllMaps()
  {
    ArrayList<Map> mapList = new ArrayList<Map>();
    try
    {
      Statement sqlStatement = c.createStatement();

      ResultSet rs = sqlStatement.executeQuery("SELECT * FROM maps;");
      while (rs.next())
      {
        int id = rs.getInt("id");
        String mapName = rs.getString("map_name");
        String skyBox = rs.getString("sky_box");
        String floor = rs.getString("floor");
        String lightPosition = rs.getString("light_position");
        Map map = new Map(id, mapName, skyBox, floor, lightPosition);
        mapList.add(map);
        if (DEBUG)
        {
          System.out.println(id);
          System.out.println(mapName);
          System.out.println(skyBox);
          System.out.println(floor);
          System.out.println(lightPosition);
        }
      }
      rs.close();
      sqlStatement.close();
      return mapList;
    }
    catch (SQLException e)
    {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    return null;
  }

  /**
   * Get a list of all map objects in the MapObjects table
   * 
   * @return list of all items in the MapObjects database
   */
  public static ArrayList<MapObjectDB> getAllMapObjects()
  {
    ArrayList<MapObjectDB> mapObjectList = new ArrayList<MapObjectDB>();
    try
    {
      Statement sqlStatement = c.createStatement();

      ResultSet rs = sqlStatement.executeQuery("SELECT * FROM mapobjects;");
      while (rs.next())
      {
        int id = rs.getInt("id");
        String objectName = rs.getString("object_name");
        String location = rs.getString("location");
        String groundTexture = rs.getString("ground_texture");
        String wallTexture = rs.getString("wall_texture");
        String direction = rs.getString("direction");
        String trapType = rs.getString("trap_type");
        int mapId = rs.getInt("map_id");
        MapObjectDB mapObject = new MapObjectDB(id, objectName, location, groundTexture, wallTexture, direction,
            trapType, mapId);

        mapObjectList.add(mapObject);
        if (DEBUG)
        {
          System.out.println(id);
          System.out.println(objectName);
          System.out.println(location);
          System.out.println(groundTexture);
          System.out.println(wallTexture);
          System.out.println(direction);
          System.out.println(trapType);
          System.out.println(mapId);
        }
      }
      rs.close();
      sqlStatement.close();
      return mapObjectList;
    }
    catch (SQLException e)
    {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    return null;
  }

  /**
   * Delete a specified map out of the Map table
   * 
   * @param mapName name of map to delete
   * @return true if successfully deleted, false otherwise
   */
  public static boolean deleteMap(String mapName)
  {
    try
    {
      Statement sqlStatement = c.createStatement();
      String sql = "DELETE FROM maps WHERE map_name = " + "'" + mapName + "'";

      sqlStatement.execute(sql);
      sqlStatement.close();
      return true;
    }
    catch (SQLException e)
    {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    return false;
  }

  /**
   * Delete a specified map object out of the MapObject table
   * 
   * @param location location of the map object to delete
   * @return true if successfully deleted, false otherwise
   */
  public static boolean deleteMapObject(String location)
  {
    try
    {
      Statement sqlStatement = c.createStatement();
      String sql = "DELETE FROM mapobjects WHERE location = " + "'" + location + "'";

      sqlStatement.execute(sql);
      sqlStatement.close();
      return true;
    }
    catch (SQLException e)
    {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    return false;
  }
}