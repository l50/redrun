package redrun.model.constants;

/**
 * This enum represents different cardinal directions of map objects.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-27
 */
public enum Direction
{
  NORTH
  {
    public int deltaX()
    {
      return 0;
    }

    public int deltaY()
    {
      return -1;
    }
  },
  EAST
  {
    public int deltaX()
    {
      return 1;
    }

    public int deltaY()
    {
      return 0;
    }
  },
  SOUTH
  {
    public int deltaX()
    {
      return 0;
    }

    public int deltaY()
    {
      return 1;
    }
  },
  WEST
  {
    public int deltaX()
    {
      return -1;
    }

    public int deltaY()
    {
      return 0;
    }
  };

  /**
   * Gets the change in X position.
   * 
   * @return the change in X position
   */
  public abstract int deltaX();

  /**
   * Gets the change in Y position.
   * 
   * @return the change in Y position
   */
  public abstract int deltaY();

  /** The number of directions. */
  public static final int SIZE = values().length;

  /**
   * Gets the direction to the left of the current.
   * 
   * @param dir the current direction
   * @return the direction to the left of the current direction
   */
  public static final Direction getLeftDir(Direction dir)
  {
    return values()[(dir.ordinal() + SIZE - 1) % SIZE];
  }

  /**
   * Gets the direction to the right of the current.
   * 
   * @param dir the current direction
   * @return the direction to the right of the current direction
   */
  public static final Direction getRightDir(Direction dir)
  {
    return values()[(dir.ordinal() + 1) % SIZE];
  }
}