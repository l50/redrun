package redrun.model.gameobject.map;

import redrun.model.constants.Direction;
import redrun.model.constants.Scale;
import redrun.model.constants.TrapType;
import redrun.model.gameobject.MapObject;
import redrun.model.gameobject.world.EndPlane;
import redrun.model.gameobject.world.InvisibleWall;
import redrun.model.gameobject.world.RectangularPrism;

/**
 * This class represents a end point that denotes the end of the level.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-22
 * @see MapObject
 */
public class End extends MapObject
{
  /**
   * Creates a new oriented end point at the specified location.
   * 
   * @param x the x position of the end point
   * @param y the y position of the end point
   * @param z the z position of the end point
   * @param groundTexture an optional texture to apply to the ground
   * @param wallTexture an optional texture to apply to the walls
   * @param orientation the cardinal direction of the end point
   * @param trap an optional trap to place on the end point
   */
  public End(float x, float y, float z, String groundTexture, String wallTexture, Direction orientation, TrapType type)
  {
    super(x, y, z, groundTexture, wallTexture, orientation, type);
    
    int size = Scale.MAP_SCALE.scale();

    switch (orientation)
    {
      case NORTH:
      {
        components.add(new EndPlane(x, y, z, groundTexture, Direction.NORTH, size));
        components.add(new RectangularPrism(x, y + 1.5f, z + (size / 2), wallTexture, size, 3.0f, 1.0f));
        components.add(new RectangularPrism(x + (size / 2), y + 1.5f, z, wallTexture, 1.0f, 3.0f, size - 2));
        components.add(new RectangularPrism(x, y + 1.5f, z + -(size / 2), wallTexture, size, 3.0f, 1.0f));      
        break;
      }
      case EAST:
      {
        components.add(new EndPlane(x, y, z, groundTexture, Direction.EAST, size));
        components.add(new RectangularPrism(x + -(size / 2), y + 1.5f, z, wallTexture, 1.0f, 3.0f, size));
        components.add(new RectangularPrism(x, y + 1.5f, z + (size / 2), wallTexture, size - 2, 3.0f, 1.0f));
        components.add(new RectangularPrism(x + (size / 2), y + 1.5f, z, wallTexture, 1.0f, 3.0f, size));
        break;
      }
      case SOUTH:
      {
        components.add(new EndPlane(x, y, z, groundTexture, Direction.SOUTH, size));
        components.add(new RectangularPrism(x, y + 1.5f, z + -(size / 2), wallTexture, size, 3.0f, 1.0f));
        components.add(new RectangularPrism(x + -(size / 2), y + 1.5f, z, wallTexture, 1.0f, 3.0f, size - 2));
        components.add(new RectangularPrism(x, y + 1.5f, z + (size / 2), wallTexture, size, 3.0f, 1.0f));
        break;
      }
      case WEST:
      {
        components.add(new EndPlane(x, y, z, groundTexture, Direction.WEST, size));
        components.add(new RectangularPrism(x + (size / 2), y + 1.5f, z, wallTexture, 1.0f, 3.0f, size));
        components.add(new RectangularPrism(x, y + 1.5f, z + -(size / 2), wallTexture, size - 2, 3.0f, 1.0f));
        components.add(new RectangularPrism(x + -(size / 2), y + 1.5f, z, wallTexture, 1.0f, 3.0f, size));
        break;
      }
      default:
      {
        try
        {
          throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException ex)
        {
          ex.printStackTrace();
        }
      }
    }
    
    switch (orientation)
    {
      case NORTH:
      {
        components.add(new InvisibleWall(x, y + 10f, z + (size / 2), null, size, 10.0f, 0.0f));
        components.add(new InvisibleWall(x + (size / 2), y + 10f, z, null, 0.0f, 10.0f, size - 2));
        components.add(new InvisibleWall(x, y + 10f, z + -(size / 2), null, size, 10.0f, 0.0f));      
        break;
      }
      case EAST:
      {
        components.add(new InvisibleWall(x + -(size / 2), y + 10f, z, null, 0.0f, 10.0f, size));
        components.add(new InvisibleWall(x, y + 10f, z + (size / 2), null, size - 2, 10.0f, 0.0f));
        components.add(new InvisibleWall(x + (size / 2), y + 10f, z, null, 0.0f, 10.0f, size));
        break;
      }
      case SOUTH:
      {
        components.add(new InvisibleWall(x, y + 10f, z + -(size / 2), null, size, 10.0f, 0.0f));
        components.add(new InvisibleWall(x + -(size / 2), y + 10f, z, null, 0.0f, 10.0f, size - 2));
        components.add(new InvisibleWall(x, y + 10f, z + (size / 2), null, size, 10.0f, 0.0f));
        break;
      }
      case WEST:
      {
        components.add(new InvisibleWall(x + (size / 2), y + 10f, z, null, 0.0f, 10.0f, size));
        components.add(new InvisibleWall(x, y + 10f, z + -(size / 2), null, size - 2, 10.0f, 0.0f));
        components.add(new InvisibleWall(x + -(size / 2), y + 10f, z, null, 0.0f, 10.0f, size));
        break;
      }
      default:
      {
        try
        {
          throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException ex)
        {
          ex.printStackTrace();
        }
      }
    }
  }

  @Override
  public int compareTo(MapObject o)
  {
    return 0;
  }
}
