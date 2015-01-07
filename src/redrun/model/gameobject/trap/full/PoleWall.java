package redrun.model.gameobject.trap.full;

import redrun.model.constants.Direction;
import redrun.model.game.GameData;
import redrun.model.gameobject.trap.Trap;
import redrun.model.gameobject.trap.piece.Spear;


/**
 * Creates the pole wall contains 5 spears
 * 
 * @author Adam Mitchell
 *
 * @version 1.0
 * @since 2014-12-9
 */
public class PoleWall extends Trap
{
  /** The 5 spears */
  Spear spear1;
  Spear spear2;
  Spear spear3;
  Spear spear4;
  Spear spear5;

  /**
   * Creates the pole wall trap at x,y,z
   * @param x position
   * @param y position 
   * @param z position
   * @param orientation
   * @param textureName
   */
  public PoleWall(float x, float y, float z, Direction orientation, String textureName)
  {
    super(x, y, z, orientation, textureName);

    /** orientation of the the pole wall */
    if (orientation == Direction.EAST || orientation == Direction.WEST)
    {
      spear1 = new Spear(x - 35, y + 3, z - 5, orientation, null, 0f, "x");
      spear2 = new Spear(x - 35, y + 3, z + 0, orientation, null, 0f, "x");
      spear3 = new Spear(x - 35, y + 3, z + 5, orientation, null, 0f, "x");
      spear4 = new Spear(x - 35, y + 5, z + 3, orientation, null, 0f, "x");
      spear5 = new Spear(x - 35, y + 5, z - 3, orientation, null, 0f, "x");
    }
    /** orientation of the the pole wall */
    else if (orientation == Direction.SOUTH || orientation == Direction.NORTH)
    {
      spear1 = new Spear(x - 5, y + 3, z + 35, orientation, null, 0f, "z");
      spear2 = new Spear(x + 0, y + 3, z + 35, orientation, null, 0f, "z");
      spear3 = new Spear(x + 5, y + 3, z + 35, orientation, null, 0f, "z");
      spear4 = new Spear(x - 3, y + 5, z + 35, orientation, null, 0f, "z");
      spear5 = new Spear(x + 3, y + 5, z + 35, orientation, null, 0f, "z");
    }
    /** add the spears to the Game data*/
    GameData.addGameObject(spear1);
    GameData.addGameObject(spear2);
    GameData.addGameObject(spear3);
    GameData.addGameObject(spear4);
    GameData.addGameObject(spear5);
  }

  @Override
  public void activate()
  {
    System.out.println("Interacting with the game object: " + this.id);
    spear1.activate();
    spear2.activate();
    spear3.activate();
    spear4.activate();
    spear5.activate();
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
