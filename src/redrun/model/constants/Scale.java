package redrun.model.constants;

/**
 * This enum represents different scales for map objects.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-27
 */
public enum Scale
{
  MAP_SCALE
  {
    public int scale()
    {
      return 15;
    }
  };

  /**
   * Gets the scale of the map.
   * 
   * @return the scale of the map
   */
  public abstract int scale();
}
