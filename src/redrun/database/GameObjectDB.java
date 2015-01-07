package redrun.database;

/**
 * Represent a GameObject from the RedRun DB
 * 
 * @author Jayson Grace ( jaysong @ unm.edu )
 * @version 1.0
 * @since 2014-11-24
 */
public class GameObjectDB
{
  /** Primary key */
  private int id;
  /** Name of map associated */
  private String mapName;
  /** Location of skybox image used */
  private String skyBox;
  /** Location of floor image used */
  private String floor;

  /**
   * Instantiate GameObjectDB
   * 
   * @param id database id
   * @param mapName name of map
   * @param skyBox name of skybox image used
   * @param floor name of floor image used
   */
  public GameObjectDB(int id, String mapName, String skyBox, String floor)
  {
    this.id = id;
    this.mapName = mapName;
    this.skyBox = skyBox;
    this.floor = floor;
  }

  /**
   * Convert object to string for network transmission
   */
  public String toString()
  {
    return "=== Game Object === " + "id:" + this.id + " mapName:" + this.mapName + " skyBox:" + this.skyBox + " floor:"
        + this.floor + " ===";
  }
}
