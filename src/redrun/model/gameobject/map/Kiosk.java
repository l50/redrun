package redrun.model.gameobject.map;

import redrun.model.constants.Direction;
import redrun.model.constants.Scale;
import redrun.model.constants.TrapType;
import redrun.model.game.GameData;
import redrun.model.gameobject.MapObject;
import redrun.model.gameobject.world.Button;
import redrun.model.gameobject.world.InvisibleWall;
import redrun.model.gameobject.world.Plane;
import redrun.model.gameobject.world.RectangularPrism;

/**
 * This class represents a kiosk that is used for activating traps.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-22
 * @see MapObject
 */
public class Kiosk extends MapObject
{
  /**
   * Creates a new oriented kiosk at the specified location.
   * 
   * @param x the x position of the kiosk
   * @param y the y position of the kiosk
   * @param z the z position of the kiosk
   * @param groundTexture an optional texture to apply to the ground
   * @param wallTexture an optional texture to apply to the walls
   * @param orientation the cardinal direction of the kiosk
   * @param trap an optional trap to place on the kiosk
   */
  public Kiosk(float x, float y, float z, String groundTexture, String wallTexture, Direction orientation, TrapType type)
  {
    super(x, y, z, groundTexture, wallTexture, orientation, type);
    
    int size = Scale.MAP_SCALE.scale();
    
    Button button = null;
        
    switch (orientation)
    {
      case NORTH:
      {
        components.add(new Plane(x, y, z, groundTexture, Direction.NORTH, size));
        components.add(new RectangularPrism(x, y + 1.5f, z + (size / 2), wallTexture, size, 3.0f, 1.0f));
        components.add(new RectangularPrism(x, y + 1.5f, z + -(size / 2), wallTexture, size, 3.0f, 1.0f));  
        button = new Button(x, y + 3.25f, z - (size / 2), "button");
        GameData.addGameObject(button);  
        GameData.addButton(button);
        break;
      }
      case EAST:
      {
        components.add(new Plane(x, y, z, groundTexture, Direction.EAST, size));
        components.add(new RectangularPrism(x + (size / 2), y + 1.5f, z, wallTexture, 1.0f, 3.0f, size));
        components.add(new RectangularPrism(x + -(size / 2), y + 1.5f, z, wallTexture, 1.0f, 3.0f, size));
        button = new Button(x + (size / 2), y + 3.25f, z, "button");  
        GameData.addGameObject(button);  
        GameData.addButton(button);
        break;
      }
      case SOUTH:
      {
        components.add(new Plane(x, y, z, groundTexture, Direction.SOUTH, size));
        components.add(new RectangularPrism(x, y + 1.5f, z + (size / 2), wallTexture, size, 3.0f, 1.0f));
        components.add(new RectangularPrism(x, y + 1.5f, z + -(size / 2), wallTexture, size, 3.0f, 1.0f));
        button = new Button(x, y + 3.25f, z + (size / 2), "button");  
        GameData.addGameObject(button);  
        GameData.addButton(button);
        break;
      }
      case WEST:
      {
        components.add(new Plane(x, y, z, groundTexture, Direction.WEST, size));
        components.add(new RectangularPrism(x + (size / 2), y + 1.5f, z, wallTexture, 1.0f, 3.0f, size));
        components.add(new RectangularPrism(x + -(size / 2), y + 1.5f, z, wallTexture, 1.0f, 3.0f, size));
        button = new Button(x - (size / 2), y + 3.25f, z, "button");  
        GameData.addGameObject(button);  
        GameData.addButton(button);
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
        components.add(new InvisibleWall(x, y + 10f, z + (size / 2), null, size, 10f, 1.0f));
        components.add(new InvisibleWall(x, y + 10f, z + -(size / 2), null, size, 10f, 1.0f));  
        break;
      }
      case EAST:
      {
        components.add(new InvisibleWall(x + (size / 2), y + 10f, z, null, 1.0f, 10f, size));
        components.add(new InvisibleWall(x + -(size / 2), y + 10f, z, null, 1.0f, 10f, size));
        break;
      }
      case SOUTH:
      {
        components.add(new InvisibleWall(x, y + 10f, z + (size / 2), null, size, 10f, 1.0f));
        components.add(new InvisibleWall(x, y + 10f, z + -(size / 2), null, size, 10f, 1.0f));
        break;
      }
      case WEST:
      {
        components.add(new InvisibleWall(x + (size / 2), y + 10f, z, null, 1.0f, 10f, size));
        components.add(new InvisibleWall(x + -(size / 2), y + 10f, z, null, 1.0f, 10f, size));
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
  public int compareTo(MapObject arg0)
  {
    return 0;
  }
}
