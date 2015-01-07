package redrun.model.gameobject.map;

import redrun.model.constants.Direction;
import redrun.model.constants.Scale;
import redrun.model.constants.TrapType;
import redrun.model.gameobject.MapObject;
import redrun.model.gameobject.world.InvisibleWall;
import redrun.model.gameobject.world.RectangularPrism;

/**
 * This class represents a pit.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-22
 * @see MapObject
 */
public class Pit extends MapObject
{  
  /**
   * Creates a new oriented pit at the specified location.
   * 
   * @param x the x position of the pit
   * @param y the y position of the pit
   * @param z the z position of the pit
   * @param groundTexture an optional texture to apply to the ground
   * @param wallTexture an optional texture to apply to the walls
   * @param orientation the cardinal direction of the pit
   * @param trap an optional trap to place on the pit
   */
  public Pit(float x, float y, float z, String groundTexture, String wallTexture, Direction orientation, TrapType type)
  {
    super(x, y, z, groundTexture, wallTexture, orientation, type);
    
    int size = Scale.MAP_SCALE.scale();
        
    switch (orientation)
    {
      case NORTH:
      {        
        components.add(new RectangularPrism(x, y - ((size + 3.0f) / 2) + 3.0f, z + (size / 2), wallTexture, size, size + 3.0f, 1.0f));
        components.add(new RectangularPrism(x, y - ((size + 3.0f) / 2) + 3.0f, z + -(size / 2), wallTexture, size, size + 3.0f, 1.0f));
        components.add(new RectangularPrism(x + (size / 2), y - (size / 2), z, wallTexture, 1.0f, size - 1.0f, size - 2.0f));
        components.add(new RectangularPrism(x + -(size / 2), y - (size / 2), z, wallTexture, 1.0f, size - 1.0f, size - 2.0f));
        components.add(new RectangularPrism(x, y - size + 0.5f, z, wallTexture, size, 1.0f, size - 2.0f));
        break;
      }
      case EAST:
      {
        components.add(new RectangularPrism(x + (size / 2), y - ((size + 3.0f) / 2) + 3.0f, z, wallTexture, 1.0f, size + 3.0f, size));
        components.add(new RectangularPrism(x + -(size / 2), y - ((size + 3.0f) / 2) + 3.0f, z, wallTexture, 1.0f, size + 3.0f, size));
        components.add(new RectangularPrism(x, y - (size / 2), z + (size / 2), wallTexture, size - 2.0f, size - 1.0f, 1.0f));
        components.add(new RectangularPrism(x, y - (size / 2), z + -(size / 2), wallTexture, size - 2.0f, size - 1.0f, 1.0f));
        components.add(new RectangularPrism(x, y - size + 0.5f, z, wallTexture, size - 2.0f, 1.0f, size));        
        break;
      }
      case SOUTH:
      {
        components.add(new RectangularPrism(x, y - ((size + 3.0f) / 2) + 3.0f, z + (size / 2), wallTexture, size, size + 3.0f, 1.0f));
        components.add(new RectangularPrism(x, y - ((size + 3.0f) / 2) + 3.0f, z + -(size / 2), wallTexture, size, size + 3.0f, 1.0f));
        components.add(new RectangularPrism(x + (size / 2), y - (size / 2), z, wallTexture, 1.0f, size - 1.0f, size - 2.0f));
        components.add(new RectangularPrism(x + -(size / 2), y - (size / 2), z, wallTexture, 1.0f, size - 1.0f, size - 2.0f));
        components.add(new RectangularPrism(x, y - size + 0.5f, z, wallTexture, size, 1.0f, size - 2.0f));
        break;
      }
      case WEST:
      {
        components.add(new RectangularPrism(x + (size / 2), y - ((size + 3.0f) / 2) + 3.0f, z, wallTexture, 1.0f, size + 3.0f, size));
        components.add(new RectangularPrism(x + -(size / 2), y - ((size + 3.0f) / 2) + 3.0f, z, wallTexture, 1.0f, size + 3.0f, size));
        components.add(new RectangularPrism(x, y - (size / 2), z + (size / 2), wallTexture, size - 2.0f, size - 1.0f, 1.0f));
        components.add(new RectangularPrism(x, y - (size / 2), z + -(size / 2), wallTexture, size - 2.0f, size - 1.0f, 1.0f));
        components.add(new RectangularPrism(x, y - size + 0.5f, z, wallTexture, size - 2.0f, 1.0f, size));
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
        components.add(new InvisibleWall(x, y + 10f, z - (size / 2), null, size - 2.0f, 10.0f, 0.0f));
        components.add(new InvisibleWall(x, y + 10f, z + (size / 2), null, size - 2.0f, 10.0f, 0.0f));
        break;
      }
      case EAST:
      {
        components.add(new InvisibleWall(x + -(size / 2), y + 10f, z, null, 1.0f, 10.0f, size - 2.0f));
        components.add(new InvisibleWall(x + (size / 2), y + 10f, z, null, 1.0f, 10.0f, size - 2.0f));    
        break;
      }
      case SOUTH:
      {
        components.add(new InvisibleWall(x, y + 10f, z - (size / 2), null, size - 2.0f, 10.0f, 0.0f));
        components.add(new InvisibleWall(x, y + 10f, z + (size / 2), null, size - 2.0f, 10.0f, 0.0f));       
        break;
      }
      case WEST:
      {
        components.add(new InvisibleWall(x + -(size / 2), y + 10f, z, null, 0.0f, 10.0f, size - 2.0f));
        components.add(new InvisibleWall(x + (size / 2), y + 10f, z, null, 0.0f, 10.0f, size - 2.0f));
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
