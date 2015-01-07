package redrun.test.database;

import redrun.database.Map;
import redrun.database.MapObjectDB;
import redrun.database.RedRunDAO;

/**
 * Testing class for the RedRunDAO class
 * 
 * @author Jayson Grace ( jaysong@unm.edu )
 * @version 1.0
 * @since 2014-11-24
 *
 */
public class RedRunDAOTest
{
  /** Debug mode */
  private static final boolean DEBUG = true;
  /** DAO instance for db functionality */
  private static RedRunDAO dao = new RedRunDAO();

  /**
   * Used to test the map INSERT functionality of the DAO
   * 
   * @return true if all tests pass
   */
  private boolean insertMap()
  {
    assert (RedRunDAO.insertMap("Ice World", "iceflats", "marble", "(-100.0f, 750.0f, 1000.0f)") == true) : "Unable to create new Map";
    boolean found = false;
    for (Map map : RedRunDAO.getAllMaps())
    {
      if (DEBUG) System.out.println(map.toString());
      if (map.toString().contains("Ice World")) found = true;
    }
    assert (found == true) : "Unable to find newly created map in Map table";
    return true;
  }

  /**
   * Used to test the map SELECT functionality of the DAO
   * 
   * @return true if all tests pass
   */
  private boolean selectMap()
  {
    if (DEBUG)
    {
      for (Map map : RedRunDAO.getAllMaps())
      {
        System.out.println(map.toString());
      }
    }
    assert (RedRunDAO.getAllMaps() != null) : "We were unable to retrieve any maps from the Map table";
    return true;
  }

  /**
   * Used to test the map DELETE functionality of the DAO
   * 
   * @return true if all tests pass
   */
  private boolean deleteMap()
  {
    assert (RedRunDAO.deleteMap("Ice World") == true) : "Something went horribly wrong with our deletion method";
    boolean found = false;
    for (Map map : RedRunDAO.getAllMaps())
    {
      if (DEBUG) System.out.println(map.toString());
      if (map.toString().contains("Ice World")) found = true;
    }
    assert (found == false) : "Ice World was not actually deleted from the database.";

    return true;
  }

  /**
   * Used to test the map object INSERT functionality of the DAO
   * 
   * @return true if all tests pass
   */
  private boolean insertMapObject()
  {
    assert (RedRunDAO.insertMapObject("Start", "(0.0f, 0.0f, 0.0f)", "ground14", "brick8", "WEST", "EMPTY", 1)) == true : "Unable to create new MapObject";
    boolean found = false;
    for (MapObjectDB mo : RedRunDAO.getAllMapObjects())
    {
      if (DEBUG) System.out.println(mo.toString());
      if (mo.toString().contains("(0.0f, 0.0f, 0.0f)")) found = true;
    }
    assert (found == true) : "Unable to find newly created game object in MapObjects table";
    return true;
  }

  /**
   * Used to test the map object SELECT functionality of the DAO
   * 
   * @return true if all tests pass
   */
  private boolean selectMapObject()
  {
    if (DEBUG)
    {
      for (MapObjectDB mo : RedRunDAO.getAllMapObjects())
      {
        System.out.println(mo.toString());
      }
    }
    assert (RedRunDAO.getAllMapObjects() != null) : "We were unable to retrieve any map objects from the MapObjects table";
    return true;
  }

  /**
   * Used to test the map object DELETE functionality of the DAO
   * 
   * @return true if all tests pass
   */
  private boolean deleteMapObject()
  {
    assert (RedRunDAO.deleteMapObject("(0.0f, 0.0f, 0.0f)") == true) : "Something went horribly wrong with our deletion method";
    boolean found = false;
    for (MapObjectDB mo : RedRunDAO.getAllMapObjects())
    {
      if (DEBUG) System.out.println(mo.toString());
      if (mo.toString().contains("(0.0f, 0.0f, 0.0f)")) found = true;
    }
    assert (found == false) : "The item with this location: (0.0f, 0.0f, 0.0f) was not actually deleted from the database.";

    return true;
  }

  /**
   * Main statement for test cases
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    RedRunDAOTest redRunDaoTest = new RedRunDAOTest();
    // Map Tests
    if (redRunDaoTest.insertMap() == true) System.out.println("Insert Map test passed.");
    if (redRunDaoTest.selectMap() == true) System.out.println("Select Map test passed.");
    if (redRunDaoTest.deleteMap() == true) System.out.println("Delete Map test passed.");
    // MapObject Tests
    if (redRunDaoTest.insertMapObject() == true) System.out.println("Insert MapObject test passed.");
    if (redRunDaoTest.selectMapObject() == true) System.out.println("Select MapObject test passed.");
    if (redRunDaoTest.deleteMapObject() == true) System.out.println("Delete MapObject test passed.");

    dao.closeConnection();
  }
}
