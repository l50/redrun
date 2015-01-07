package redrun.database;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Convert start location from string to floats that can be used as coordinates
 * 
 * @author Jayson Grace ( jaysong @ unm.edu )
 * @version 1.0
 * @since 2014-11-24
 */
public class StartLocation
{
  /** Location specified by x, y, and z for a starting location */
  private float x, y, z;

  /**
   * Instantiate a Start location
   * 
   * @param startLocation input location to be translated to StartLocation
   *          object
   */
  public StartLocation(String startLocation)
  {
    Pattern parsed = Pattern.compile("(\\d+\\.\\d+),(\\d+\\.\\d+),(\\d+\\.\\d+)");
    Matcher matchParsed = parsed.matcher(startLocation);
    if (matchParsed.find())
    {
      this.x = Float.parseFloat(matchParsed.group(1));
      this.y = Float.parseFloat(matchParsed.group(2));
      this.z = Float.parseFloat(matchParsed.group(3));
    }
  }

  /**
   * Getter for x
   * 
   * @return x value
   */
  public float getX()
  {
    return x;
  }

  /**
   * Getter for y
   * 
   * @return y value
   */
  public float getY()
  {
    return y;
  }

  /**
   * Getter for z
   * 
   * @return z value
   */
  public float getZ()
  {
    return z;
  }
}