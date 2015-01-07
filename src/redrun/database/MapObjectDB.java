package redrun.database;

/**
 * Represent a MapObject from the RedRun DB
 * 
 * @author Jayson Grace ( jaysong @ unm.edu )
 * @version 1.0
 * @since 2014-11-25
 */
public class MapObjectDB
{
  /** Primary key */
  private int id;
  /** Object name */
  private String objectName;
  /** Location of object in map */
  private String location;
  /** Location of image used for groundTexture */
  private String groundTexture;
  /** Location of image used for wallTextrue */
  private String wallTexture;
  /** Orientation of object */
  private String direction;
  /** Type of trap associated with object */
  private String trapType;
  /** Associated map */
  private int mapId;

  /**
   * MapObject instantiation
   * 
   * @param id database id
   * @param objectName name of the map object
   * @param location location of the map object
   * @param groundTexture texture to use with the map object for the ground
   * @param wallTexture texture to use with the map object for the wall
   * @param direction orientation of the map object
   * @param trapType type of trap to use with the map object
   * @param mapId associated map
   */
  public MapObjectDB(int id, String objectName, String location, String groundTexture, String wallTexture,
      String direction, String trapType, int mapId)
  {
    this.id = id;
    this.objectName = objectName;
    this.location = location;
    this.groundTexture = groundTexture;
    this.wallTexture = wallTexture;
    this.direction = direction;
    this.trapType = trapType;
    this.mapId = mapId;
  }

  /**
   * Convert object to string for network transmission
   */
  public String toString()
  {
    return "=== Map Object === " + "ID:" + this.id + " Name:" + this.objectName + " Location:" + this.location
        + " Ground Texture:" + this.groundTexture + " Wall Texture:" + this.wallTexture + " Direction:"
        + this.direction + " Trap Type:" + this.trapType + " Map ID:" + this.mapId + " ===";
  }
}