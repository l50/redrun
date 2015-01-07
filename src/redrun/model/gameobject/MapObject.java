package redrun.model.gameobject;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import redrun.model.constants.Constants;
import redrun.model.constants.Direction;
import redrun.model.constants.TrapType;
import redrun.model.game.GameData;
import redrun.model.gameobject.map.Pit;
import redrun.model.gameobject.map.Corridor;
import redrun.model.gameobject.map.Field;
import redrun.model.gameobject.trap.Trap;
import redrun.model.gameobject.trap.full.BottomOfPitTrap;
import redrun.model.gameobject.trap.full.ExplodingBoxField;
import redrun.model.gameobject.trap.full.Jail;
import redrun.model.gameobject.trap.full.PoleDance;
import redrun.model.gameobject.trap.full.PoleWall;
import redrun.model.gameobject.trap.full.RockSmash;
import redrun.model.gameobject.trap.full.SpikeField;
import redrun.model.gameobject.trap.full.SpikeTrapDoor;
import redrun.model.gameobject.trap.full.TrapDoor;

/**
 * This class represents a map object that is used to construct Redrun maps.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-22
 */
public abstract class MapObject implements Comparable<MapObject>
{
  /** The X position of the map object. */
  protected final float x;

  /** The Y position of the map object. */
  protected final float y;

  /** The Z position of the map object. */
  protected final float z;

  /**
   * The orientation of the map object in 3D space defined by cardinal
   * directions.
   */
  protected final Direction orientation;

  /** The name of the texture to apply to the ground of the map object. */
  protected String groundTexture = null;

  /** The name of the texture to apply to the walls of the map object. */
  protected String wallTexture = null;

  /** The trap to associate with the map object. */
  protected Trap trap = null;

  /** The list of game object that define the map object. */
  protected List<GameObject> components = new ArrayList<GameObject>();

  /**
   * Creates a new map object.
   * 
   * @param x the x position of the map object
   * @param y the y position of the map object
   * @param z the z position of the map object
   * @param groundTexture the ground texture to apply
   * @param wallTexture the wall texture to apply
   * @param orientation the direction of the map object
   * @param type the type of trap to apply
   */
  public MapObject(float x, float y, float z, String groundTexture, String wallTexture, Direction orientation, TrapType type)
  {
    this.x = x;
    this.y = y;
    this.z = z;
    this.orientation = orientation;

    switch (type)
    {
      case EMPTY:
      {
        break;
      }

      case EXPLODING_BOX_FIELD:
      {
        if (this.getClass() == Pit.class)
        {
          this.trap = new ExplodingBoxField(x + 1, y - 15, z, null, "pit");
        }
        if (this.getClass() == Corridor.class)
        {
          this.trap = new ExplodingBoxField(x + 1, y+1, z, null, "corridor");
        }
        if (this.getClass() == Field.class)
        {
          this.trap = new ExplodingBoxField(x + 2, y+1, z, null, "field");
        }
        break;
      }

      case JAIL:
      {
        this.trap = new Jail(x, y, z, orientation, null);
        break;
      }

      case POLE_DANCE:
      {
        this.trap = new PoleDance(x, y + 0.05f, z, orientation, wallTexture);
        break;
      }

      case POLE_WALL:
      {
        if (orientation == Direction.EAST || orientation == Direction.WEST)
        {
          this.trap = new PoleWall(x + 16, y, z, orientation, wallTexture);
        }
        if (orientation == Direction.NORTH || orientation == Direction.SOUTH)
        {
          this.trap = new PoleWall(x, y, z - 16, orientation, wallTexture);
        }
        break;
      }

      case ROCK_SMASH:
      {
        this.trap = new RockSmash(x, y + 5, z, orientation, "rock" + Constants.random.nextInt(3));
        break;
      }

      case SPIKE_FIELD:
      {
        if (this.getClass() == Pit.class) this.trap = new SpikeField(x, y, z, "ground9", orientation, new Dimension(10,
            15), false);
        else this.trap = new SpikeField(x, y, z, "ground9", orientation, new Dimension(15, 15), true);
        break;
      }

      case SPIKE_TRAP_DOOR:
      {
        if (this.getClass() == Pit.class) this.trap = new SpikeTrapDoor(x, y + 0.8f, z, orientation, groundTexture,
            true);
        else this.trap = new SpikeTrapDoor(x, y + 0.8f, z, orientation, "ground16", false);
        break;
      }

      case TRAP_DOOR:
      {
        this.trap = new TrapDoor(x, y, z, orientation, "ground14");
        break;
      }

      case PIT:
      {
        this.trap = new BottomOfPitTrap(x, y, z, orientation, new Dimension(10, 15));
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

    if (type != TrapType.EMPTY)
    {
      GameData.addGameObject(trap);
      GameData.addTrap(trap);
    }
  }

  /**
   * Gets the X position of the map object.
   * 
   * @return the X position of the map object
   */
  public float getX()
  {
    return x;
  }

  /**
   * Gets the Y position of the map object.
   * 
   * @return the Y position of the map object
   */
  public float getY()
  {
    return y;
  }

  /**
   * Gets the Z position of the map object.
   * 
   * @return the Z position of the map object
   */
  public float getZ()
  {
    return z;
  }

  /**
   * Draws the map object to the OpenGL scene.
   */
  public void draw()
  {
    for (GameObject component : components)
    {
      component.draw();
    }
  }
}
