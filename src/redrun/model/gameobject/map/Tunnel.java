package redrun.model.gameobject.map;

import redrun.model.constants.Direction;
import redrun.model.constants.Scale;
import redrun.model.constants.TrapType;
import redrun.model.gameobject.GameObject;
import redrun.model.gameobject.MapObject;
import redrun.model.gameobject.world.Plane;
import redrun.model.gameobject.world.RectangularPrism;

/**
 * This class represents a tunnel.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-22
 * @see MapObject
 */
public class Tunnel extends MapObject
{
  /**
   * Creates a new oriented tunnel at the specified location.
   * 
   * @param x the x position of the tunnel
   * @param y the y position of the tunnel
   * @param z the z position of the tunnel
   * @param groundTexture an optional texture to apply to the ground
   * @param wallTexture an optional texture to apply to the walls
   * @param orientation the cardinal direction of the tunnel
   * @param trap an optional trap to place on the tunnel
   */
  public Tunnel(float x, float y, float z, String groundTexture, String wallTexture, Direction orientation, TrapType type)
  {
    super(x, y, z, groundTexture, wallTexture, orientation, type);
    
    int size = Scale.MAP_SCALE.scale();
        
    switch (orientation)
    {
      case NORTH:
      {   
        components.add(new Plane(x, y, z, groundTexture, Direction.NORTH, size));
        components.add(new RectangularPrism(x, y + (size / 2) + 0.5f, z + (size / 2), wallTexture, size, size, 1.0f));
        components.add(new RectangularPrism(x, y + (size / 2) + 0.5f, z + -(size / 2), wallTexture, size, size, 1.0f));
        components.add(new RectangularPrism(x, y + size - 0.5f, z, wallTexture, size, 1.0f, size - 2.0f));
        break;
      }
      case EAST:
      {
        components.add(new Plane(x, y, z, groundTexture, Direction.EAST, size));
        components.add(new RectangularPrism(x + (size / 2), y + (size / 2) + 0.5f, z, wallTexture, 1.0f, size, size));
        components.add(new RectangularPrism(x + -(size / 2), y + (size / 2) + 0.5f, z, wallTexture, 1.0f, size, size));
        components.add(new RectangularPrism(x, y + size - 0.5f, z, wallTexture, size - 2.0f, 1.0f, size));        
        break;
      }
      case SOUTH:
      {
        components.add(new Plane(x, y, z, groundTexture, Direction.SOUTH, size));
        components.add(new RectangularPrism(x, y + + (size / 2) + 0.5f, z + (size / 2), wallTexture, size, size, 1.0f));
        components.add(new RectangularPrism(x, y + + (size / 2) + 0.5f, z + -(size / 2), wallTexture, size, size, 1.0f));
        components.add(new RectangularPrism(x, y + size - 0.5f, z, wallTexture, size, 1.0f, size - 2.0f));
        break;
      }
      case WEST:
      {
        components.add(new Plane(x, y, z, groundTexture, Direction.WEST, size));
        components.add(new RectangularPrism(x + (size / 2), y + (size / 2) + 0.5f, z, wallTexture, 1.0f, size, size));
        components.add(new RectangularPrism(x + -(size / 2), y + (size / 2) + 0.5f, z, wallTexture, 1.0f, size, size));
        components.add(new RectangularPrism(x, y + size - 0.5f, z, wallTexture, size - 2.0f, 1.0f, size));        
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
    for (GameObject go : components)
    {
      go.getBody().body.setFriction(1);
    }
  }

  @Override
  public int compareTo(MapObject o)
  {
    return 0;
  }
}
