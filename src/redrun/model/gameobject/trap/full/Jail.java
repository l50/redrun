package redrun.model.gameobject.trap.full;

import redrun.model.constants.Direction;
import redrun.model.game.GameData;
import redrun.model.gameobject.trap.Trap;
import redrun.model.gameobject.trap.piece.JailDoor;

/**
 * Creates a jail of two doors looks players inside for 10 seconds.
 * 
 * @author Adam Mitchell
 *
 * @version 1.0
 * @since 2014-11-9
 */
public class Jail extends Trap
{
  /** The jail doors */
  JailDoor door1;
  JailDoor door2;

  /**
   * Constructor
   * 
   * @param x same as map object
   * @param y same as map object
   * @param z same as map object
   * @param same direction as the map object
   * @param just use null
   */
  public Jail(float x, float y, float z, Direction orientation, String textureName)
  {
    super(x, y, z, orientation, null);

    if (orientation == Direction.EAST || orientation == Direction.WEST)
    {
      door1 = new JailDoor(x, y + 25, z - 6.5f, orientation, null);
      door2 = new JailDoor(x, y + 25, z + 6.5f, orientation, null);
    }

    if (orientation == Direction.SOUTH || orientation == Direction.NORTH)
    {
      door1 = new JailDoor(x - 6.5f, y + 25, z, orientation, null);
      door2 = new JailDoor(x + 6.5f, y + 25, z, orientation, null);
    }

    GameData.addGameObject(door1);
    GameData.addGameObject(door2);
  }

  @Override
  public void activate()
  {
    System.out.println("Interacting with the game object: " + this.id);
    door1.activate();
    door2.activate();
  }

  @Override
  public void reset()
  {
  }

  @Override
  public void interact()
  {
  }

  @Override
  public void update()
  {
  }

}
