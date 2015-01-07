package redrun.model.gameobject.trap.full;

import redrun.model.constants.Direction;
import redrun.model.game.GameData;
import redrun.model.gameobject.trap.Trap;
import redrun.model.gameobject.trap.piece.Rock;

/**
 * Creates a Rock Smash trap
 * 
 * @author Adam Mitchell
 *
 * @version 1.0
 * @since 2014-11-9
 */
public class RockSmash extends Trap
{
  /** 8 rocks to be displayed*/
  Rock rock1;
  Rock rock2;
  Rock rock3;
  Rock rock4;
  Rock rock5;
  Rock rock6;
  Rock rock7;
  Rock rock8;

  /**
   * 
   * @param x position 
   * @param y position
   * @param z position
   * @param orientation
   * @param textureName
   */
  public RockSmash(float x, float y, float z, Direction orientation, String textureName)
  {
    super(x, y, z, orientation, null);

    /**  Adds 8 rocks */
    rock1 = new Rock(x, y + 25, z - 10, orientation, null, 0f);
    rock2 = new Rock(x + 5, y + 25, z, orientation, null, 1f);
    rock3 = new Rock(x - 15, y + 25, z + 10, orientation, null, 1.5f);
    rock4 = new Rock(x + 10, y + 25, z + 15, orientation, null, 1.7f);
    rock5 = new Rock(x, y + 25, z + 15, orientation, null, 1.7f);
    rock6 = new Rock(x + 15, y + 25, z + 10, orientation, null, 1.7f);
    rock7 = new Rock(x - 10, y + 25, z + 15, orientation, null, 1.3f);
    rock8 = new Rock(x - 15, y + 25, z,  orientation, null, 2f);
    /**  Adds the rocks to the game data*/
    GameData.addGameObject(rock1);
    GameData.addGameObject(rock2);
    GameData.addGameObject(rock3);
    GameData.addGameObject(rock4);
    GameData.addGameObject(rock5);
    GameData.addGameObject(rock6);
    GameData.addGameObject(rock7);
    GameData.addGameObject(rock8);
  }

  @Override
  public void activate()
  {
    System.out.println("Interacting with the game object: " + this.id);
    rock1.activate();
    rock2.activate();
    rock3.activate();
    rock4.activate();
    rock5.activate();
    rock6.activate();
    rock7.activate();
    rock8.activate();
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
