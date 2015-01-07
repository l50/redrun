package redrun.model.gameobject.trap.full;

import java.util.ArrayList;

import redrun.model.constants.Constants;
import redrun.model.game.GameData;
import redrun.model.gameobject.trap.Trap;
import redrun.model.gameobject.trap.piece.ExplosiveBox;
import redrun.model.gameobject.trap.piece.NonExplosiveBox;
import redrun.model.physics.PhysicsWorld;

/**
 * The container to make the box field
 * 
 * @author Adam Michell
 * 
 * @version 1.0
 * @since 2014-12-9
 */
public class ExplodingBoxField extends Trap
{
  /** explosiveBoxes array */
  private ArrayList<ExplosiveBox> explosiveBoxes = new ArrayList<ExplosiveBox>(220);
  /** size of field */
  int size = 0;
  /** height of pit */
  int pit_height = 1;

  /**
   * Makes a new Exploding Box Field at x,y,z
   * 
   * @param x position
   * @param y position
   * @param z position 
   * @param textureName
   * @param type of map object to be placed in "pit", "corridor", "field"
   */
  public ExplodingBoxField(float x, float y, float z, String textureName, String type)
  {
    super(x, y, z, null, textureName);

    if (type.equals("pit"))
    {
      size = 3;
      pit_height = 7;
    }
    else if (type.equals("corridor"))
    {
      size = 3;
    }
    else if (type.equals("field"))
    {
      size = 10;
    }

    for (int y_axis = 0; y_axis < pit_height; y_axis++)
    {
      for (int x_axis = -size; x_axis < size; x_axis++)
      {
        for (int z_axis = -size; z_axis < size; z_axis++)
        {
          int rand = Constants.random.nextInt(3);
          if (rand == 0)
          {
            ExplosiveBox box1 = new ExplosiveBox(x + 2 * x_axis, y + 2 * y_axis, z + 2 * z_axis, null, "crate1", this);
            explosiveBoxes.add(box1);
            GameData.addGameObject(box1);
          }
          else
          {
            NonExplosiveBox box2 = new NonExplosiveBox(x + 2 * x_axis, y + 2 * y_axis, z + 2 * z_axis, null, "crate1");
            GameData.addGameObject(box2);
          }
        }
      }
    }
  }

  /**
   * Explodes a box or set of boxes
   */
  public void explode()
  {
    for (ExplosiveBox box : explosiveBoxes)
    {
      PhysicsWorld.removePhysicsBody(box.getBody());
      box.explode();
    }
  }

  @Override
  public void activate()
  {
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
