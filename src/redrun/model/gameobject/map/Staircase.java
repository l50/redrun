package redrun.model.gameobject.map;

import redrun.model.constants.Direction;
import redrun.model.constants.Scale;
import redrun.model.constants.TrapType;
import redrun.model.gameobject.GameObject;
import redrun.model.gameobject.MapObject;
import redrun.model.gameobject.world.InvisibleWall;
import redrun.model.gameobject.world.RectangularPrism;

/**
 * This class represents a staircase.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-22
 * @see MapObject
 */
public class Staircase extends MapObject
{  
  /**
   * Creates a new oriented staircase at the specified location.
   * 
   * @param x the x position of the staircase
   * @param y the y position of the staircase
   * @param z the z position of the staircase
   * @param groundTexture an optional texture to apply to the ground
   * @param wallTexture an optional texture to apply to the walls
   * @param orientation the cardinal direction of the staircase
   * @param trap an optional trap to place on the staircase
   */
  public Staircase(float x, float y, float z, String groundTexture, String wallTexture, Direction orientation, TrapType type)
  {
    super(x, y, z, groundTexture, wallTexture, orientation, type);
    
    int size = Scale.MAP_SCALE.scale();
            
    switch (orientation)
    {
      case NORTH:
      {
        components.add(new RectangularPrism(x, y + (size / 2) + 2.0f, z + (size / 2), wallTexture, size, size + 3.0f, 1.0f));
        components.add(new InvisibleWall(x, y + (size / 2) + 18.0f, z + (size / 2), null, size + 20, 10.0f, 1.0f));

        components.add(new RectangularPrism(x, y + (size / 2) + 2.0f, z + -(size / 2), wallTexture, size, size + 3.0f, 1.0f));
        components.add(new InvisibleWall(x, y + (size / 2) + 18.0f, z + -(size / 2), null, size + 20, 10.0f, 1.0f));
        for (int i = 0; i < size; i++)
        {
          components.add(new RectangularPrism((x - (size / 2)) + i, (y - (size / 2)) + i + (size / 2) + 0.5f, z, groundTexture, 1.0f, 1.0f, size - 2));
        }  
        break;
      }
      case EAST:
      {
        components.add(new RectangularPrism(x + (size / 2), y + (size / 2) + 2.0f, z, wallTexture, 1.0f, size + 3.0f, size));
        components.add(new InvisibleWall(x + (size / 2), y + (size / 2) + 18.0f, z, null, 1.0f, 10.0f, size + 20));

        components.add(new RectangularPrism(x + -(size / 2), y + (size / 2) + 2.0f, z, wallTexture, 1.0f, size + 3.0f, size));
        components.add(new InvisibleWall(x + -(size / 2), y + (size / 2) + 18.0f, z, null, 1.0f, 10.0f, size + 20));
        for (int i = 0; i < size; i++)
        {
          components.add(new RectangularPrism(x, (y - (size / 2)) + i + (size / 2) + 0.5f, (z - (size / 2)) + i, groundTexture, size - 2, 1.0f, 1.0f));
        } 
        break;
      }
      case SOUTH:
      {
        components.add(new RectangularPrism(x, y + (size / 2) + 2.0f, z + (size / 2), wallTexture, size, size + 3.0f, 1.0f));
        components.add(new InvisibleWall(x, y + (size / 2) + 18.0f, z + (size / 2), null, size + 20, 10.0f, 1.0f));
        
        components.add(new RectangularPrism(x, y + (size / 2) + 2.0f, z + -(size / 2), wallTexture, size, size + 3.0f, 1.0f));
        components.add(new InvisibleWall(x, y + (size / 2) + 18.0f, z + -(size / 2), null, size + 20, 10.0f, 1.0f));
        for (int i = 0; i < size; i++)
        {
          components.add(new RectangularPrism((x + (size / 2)) - i, (y - (size / 2)) + i + (size / 2) + 0.5f, z, groundTexture, 1.0f, 1.0f, size - 2));
        } 
        break;
      }
      case WEST:
      {
        components.add(new RectangularPrism(x + (size / 2), y + (size / 2) + 2.0f, z, wallTexture, 1.0f, size + 3.0f, size));
        components.add(new InvisibleWall(x + (size / 2), y + (size / 2) + 18.0f, z, null, 1.0f, 10.0f, size + 20));

        components.add(new RectangularPrism(x + -(size / 2), y + (size / 2) + 2.0f, z, wallTexture, 1.0f, size + 3.0f, size));
        components.add(new InvisibleWall(x + -(size / 2), y + (size / 2) + 18.0f, z, null, 1.0f, 10.0f, size + 20));
        for (int i = 0; i < size; i++)
        {
          components.add(new RectangularPrism(x, (y - (size / 2)) + i + (size / 2) + 0.5f, (z + (size / 2)) - i, groundTexture, size - 2, 1.0f, 1.0f));
        } 
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
      go.getBody().body.setFriction(0.02f);
    }
  }

  @Override
  public int compareTo(MapObject o)
  {
    return 0;
  }
}
