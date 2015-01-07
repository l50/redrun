package redrun.model.gameobject.map;

import redrun.model.constants.Direction;
import redrun.model.constants.Scale;
import redrun.model.constants.TrapType;
import redrun.model.gameobject.MapObject;
import redrun.model.gameobject.world.RectangularPrism;

/**
 * This class represents a platform.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-22
 * @see MapObject
 */
public class Platform extends MapObject
{
  /**
   * Creates a new oriented platform at the specified location.
   * 
   * @param x the x position of the platform
   * @param y the y position of the platform
   * @param z the z position of the platform
   * @param groundTexture an optional texture to apply to the ground
   * @param wallTexture an optional texture to apply to the walls
   * @param orientation the cardinal direction of the platform
   * @param trap an optional trap to place on the platform
   */
  public Platform(float x, float y, float z, String groundTexture, String wallTexture, Direction orientation, TrapType type)
  {
    super(x, y, z, groundTexture, wallTexture, orientation, type);

    int size = Scale.MAP_SCALE.scale();

    components.add(new RectangularPrism(x, y, z, groundTexture, size - 3.0f, 1.0f, size - 3.0f));
  }

  @Override
  public int compareTo(MapObject o)
  {
    return 0;
  }
}
