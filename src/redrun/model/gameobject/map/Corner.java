package redrun.model.gameobject.map;

import redrun.model.constants.Direction;
import redrun.model.constants.Scale;
import redrun.model.constants.TrapType;
import redrun.model.gameobject.MapObject;
import redrun.model.gameobject.world.InvisibleWall;
import redrun.model.gameobject.world.Plane;
import redrun.model.gameobject.world.RectangularPrism;

/**
 * This class represents a corner.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-22
 * @see MapObject
 */
public class Corner extends MapObject
{
  /**
   * Creates a new oriented corner at the specified location.
   * 
   * @param x the x position of the corner
   * @param y the y position of the corner
   * @param z the z position of the corner
   * @param groundTexture an optional texture to apply to the ground
   * @param wallTexture an optional texture to apply to the walls
   * @param orientation the cardinal direction of the corner
   * @param trap an optional trap to place on the corner
   */
  public Corner(float x, float y, float z, String groundTexture, String wallTexture, Direction orientation, TrapType type)
  {
    super(x, y, z, groundTexture, wallTexture, orientation, type);
    
    int size = Scale.MAP_SCALE.scale();

    switch (orientation)
    {
      case NORTH:
      {                
        components.add(new Plane(x, y, z, groundTexture, Direction.NORTH, size));
        components.add(new RectangularPrism(x + (size / 2), y + 1.5f, z, wallTexture, 1.0f, 3.0f, size));
        components.add(new InvisibleWall(x + (size / 2), y + 8f, z, null, 0.0f, 10.0f, size));

        components.add(new RectangularPrism(x - 0.5f, y + 1.5f, z + -((size - 1.0f) / 2), wallTexture, size - 1.0f, 3.0f, 1.0f));
        components.add(new InvisibleWall(x - 0.5f, y + 8f, z + -((size - 1.0f) / 2), null, size - 1.0f, 10.0f, 0.0f));

        components.add(new RectangularPrism(x + -(size / 2), y + 1.5f, z + (size / 2), wallTexture, 1.0f, 3.0f, 1.0f));
        components.add(new InvisibleWall(x + -(size / 2), y + 8f, z + (size / 2), null, 0.0f, 10.0f, 0.0f));
        break;
      }
      case EAST:
      {
        components.add(new Plane(x, y, z, groundTexture, Direction.EAST, size));
        components.add(new RectangularPrism(x, y + 1.5f, z + (size / 2), wallTexture, size, 3.0f, 1.0f));
        components.add(new InvisibleWall(x, y + 8f, z + (size / 2), null, size, 10.0f, 0.0f));

        components.add(new RectangularPrism(x + ((size - 1.0f) / 2), y + 1.5f, z - 0.5f, wallTexture, 1.0f, 3.0f, size - 1.0f));
        components.add(new InvisibleWall(x + ((size - 1.0f) / 2), y + 8f, z - 0.5f, null, 0.0f, 10.0f, size - 1.0f));

        components.add(new RectangularPrism(x + -(size / 2), y + 1.5f, z + -(size / 2), wallTexture, 1.0f, 3.0f, 1.0f));
        components.add(new InvisibleWall(x + -(size / 2), y + 8f, z + -(size / 2), null, 0.0f, 10.0f, 0.0f));
        break;
      }
      case SOUTH:
      {
        components.add(new Plane(x, y, z, groundTexture, Direction.SOUTH, size));
        components.add(new RectangularPrism(x + -(size / 2), y + 1.5f, z, wallTexture, 1.0f, 3.0f, size));
        components.add(new InvisibleWall(x + -(size / 2), y + 8f, z, null, 0.0f, 10.0f, size));

        components.add(new RectangularPrism(x + 0.5f, y + 1.5f, z + ((size - 1.0f) / 2.0f), wallTexture, size - 1.0f, 3.0f, 1.0f));
        components.add(new InvisibleWall(x + 0.5f, y + 8f, z + ((size - 1.0f) / 2.0f), null, size - 1.0f, 10.0f, 0.0f));

        components.add(new RectangularPrism(x + (size / 2), y + 1.5f, z + -(size / 2), wallTexture, 1.0f, 3.0f, 1.0f));
        components.add(new InvisibleWall(x + (size / 2), y + 8f, z + -(size / 2), null, 0.0f, 10.0f, 0.0f));
        break;
      }
      case WEST:
      {
        components.add(new Plane(x, y, z, groundTexture, Direction.WEST, size));
        components.add(new RectangularPrism(x + -(size / 2), y + 1.5f, z, wallTexture, 1.0f, 3.0f, size));
        components.add(new InvisibleWall(x + -(size / 2), y + 8f, z, null, 0.0f, 10.0f, size));

        components.add(new RectangularPrism(x + 0.5f, y + 1.5f, z + -((size - 1.0f) / 2), wallTexture, size - 1.0f, 3.0f, 1.0f));
        components.add(new InvisibleWall(x + 0.5f, y + 8f, z + -((size - 1.0f) / 2), null, size - 1.0f, 10.0f, 0.0f));

        components.add(new RectangularPrism(x + (size / 2), y + 1.5f, z + (size / 2), wallTexture, 1.0f, 3.0f, 1.0f));
        components.add(new InvisibleWall(x + (size / 2), y + 8f, z + (size / 2), null, 0.0f, 10.0f, 0.0f));
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
