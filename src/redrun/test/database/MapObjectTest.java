package redrun.test.database;

import redrun.database.MapObjectDB;

/**
 * Testing class for the Map class
 * 
 * @author Jayson Grace ( jaysong@unm.edu )
 * @version 1.0
 * @since 2014-11-24
 *
 */
public class MapObjectTest
{
  /** Debug mode */
  private static final boolean DEBUG = true;

  /**
   * First test for our Map DB class
   * 
   * @return true if all tests pass
   */
  private boolean test()
  {
    MapObjectDB mapObject = new MapObjectDB(1, "Start", "(0.0f, 0.0f, 0.0f)", "ground14", "brick8", "WEST", "EMPTY", 1);
    assert (mapObject.toString() != null) : "Did not genereate MapObject properly, fail sauce";
    assert (mapObject.toString().contains("Start")) : "MapObject name not generated properly.";
    if (DEBUG) System.out.println(mapObject.toString());
    return true;
  }

  /**
   * Main statement for test cases
   * 
   * @param args
   */
  public static void main(String[] args)
  {
    MapObjectTest mapObjectTest = new MapObjectTest();
    if (mapObjectTest.test() == true) System.out.println("Test passed.");
  }
}