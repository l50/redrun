package redrun.model.gameobject.player;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;

import java.io.File;
import java.util.Arrays;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import com.bulletphysics.collision.dispatch.CollisionFlags;
import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.linearmath.Transform;

import redrun.graphics.camera.Camera;
import redrun.model.constants.CameraType;
import redrun.model.constants.CollisionTypes;
import redrun.model.constants.Team;
import redrun.model.gameobject.GameObject;
import redrun.model.mesh.Face;
import redrun.model.mesh.Model;
import redrun.model.physics.CapsulePhysicsBody;
import redrun.model.physics.PhysicsBody;
import redrun.model.physics.PhysicsTools;
import redrun.model.physics.PhysicsWorld;
import redrun.model.toolkit.OBJLoader;

/**
 * This class represents a player in the game world.
 * 
 * @author Troy Squillaci, J. Jake Nichol
 * @version 1.0
 * @since 2014-11-07
 */
public class Player extends GameObject
{
  /** This player's camera. */
  private Camera camera;

  /** This player's name. */
  private String name;

  /** This player's team. */
  private Team team;

  /** This player's health. */
  private int health;

  /** This player's lives. */
  private int lives;

  /** The state of this player's life. */
  private boolean alive;
  
  /** Has the player won.  -1 = lose, 0 = neither, 1 = win. */
  private int winner = 0;

  /** Models for drawing/animating the red players. */
  private String[] modelRedStrings = new String[] {"standingred", "rightfrontred", "leftfrontred"};
  
  /** Models for drawing/animating the blue players. */
  private String[] modelBlueStrings = new String[] {"standingblue", "rightfrontblue", "leftfrontblue"};
  
  /** The state of the player */
  private boolean killed = false;

  /** The players model */
  private Model[] models;

  /** A list of ids to be drawn at every step */
  private int[] dispIds;

  /** A counter to increment the player graphics */
  private int step = 0;
  
  /** The starting position for when the player dies to be sent back */
  private Transform startPos;

  /** Whether or not the current player is currently exploding */
  private boolean exploding = false;
  
  /** The number of time steps that the player has been exploding for */
  private int explodingCount = 0;
  
  /** The power that the explode had */
  private int explodingPower;

  /**
   * Creates a new player at the specified position.
   * 
   * @param x the x position of the player
   * @param y the y position of the player
   * @param z the z position of the player
   * @param name the name of the player
   * @param textureName the name of the player texture for this player
   * @param team the team this player is on
   */
  public Player(float x, float y, float z, String name, final Team team)
  {
    super(x, y + 10, z, null);
    
    body = new CapsulePhysicsBody(new Vector3f(x, y, z), 2f, 100f, 1.8f, CollisionTypes.PLAYER_COLLISION_TYPE)
    {
      
      public void callback()
      {
        if (exploding)
        {

          if (explodingCount % 2 == 0) hurt();
          explodingCount++;
          if (explodingCount >= explodingPower)
          {
            exploding = false;
          }
        }
      }

      public void collidedWith(CollisionObject other)
      {
        super.collidedWith(other);

        if (exploding) hurt();

        int collisionFlags = other.getCollisionFlags();
        if ((collisionFlags & CollisionTypes.INSTANT_DEATH_COLLISION_TYPE) != 0)
        {
          System.out.println("Instant death!!!!");
          if (!killed)kill();
        }

        if ((collisionFlags & CollisionTypes.MINIMAL_DAMAGE_COLLISION_TYPE) != 0)
        {
          hurt();
        }

        if ((collisionFlags & CollisionTypes.EXPLOSION_COLLISION_TYPE) != 0)
        {

          if (!exploding)
          {
            explodingCount = 0;
            exploding = true;

            float power = 20;
            explodingPower = (int) ((Math.random() * 30) + 10);
            float x = (float) ((Math.random() * power * 2) - power);
            float y = (float) ((Math.random() * power) + power);
            float z = (float) ((Math.random() * power * 2) - power);
            body.setLinearVelocity(PhysicsTools.openGLToBullet(new Vector3f(x, y, z)));

          }
        }
        
        if ((collisionFlags & CollisionTypes.WALL_COLLISION_TYPE) != 0)
        {
          canJump = false;
        }
        
        if (team == Team.BLUE && (collisionFlags & CollisionTypes.END_COLLISION_TYPE) != 0)
        {
          winner = 1;
        }
      }
    };
    body.body.setCollisionFlags(body.body.getCollisionFlags() | CollisionFlags.CUSTOM_MATERIAL_CALLBACK);

    startPos = new Transform();

    startPos = body.body.getWorldTransform(startPos);

    PhysicsWorld.addToWatchList(body);
    camera = new Camera(70, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000f, x, y, z,
        CameraType.PLAYER);

    this.name = name;
    this.team = team;
    this.health = 100;
    this.lives = 5;
    this.alive = true;

    
    if (team == Team.BLUE)
    {
      models = new Model[modelBlueStrings.length];
      dispIds = new int[modelBlueStrings.length];
      for (int i=0; i < modelBlueStrings.length; i++)
      {
        String s = modelBlueStrings[i];
        models[i] = OBJLoader.loadModel(new File("res/models/" + s + ".obj"));
        dispIds[i] = glGenLists(1);
        drawWithModel(models[i], dispIds[i]);
      }
    }
    else
    {
      models = new Model[modelRedStrings.length];
      dispIds = new int[modelRedStrings.length];
      for (int i=0; i < modelRedStrings.length; i++)
      {
        String s = modelRedStrings[i];
        models[i] = OBJLoader.loadModel(new File("res/models/" + s + ".obj"));
        dispIds[i] = glGenLists(1);
        drawWithModel(models[i], dispIds[i]);
      }
    }

    yaw(90);
  }

  /**
   * Sets up the model to be displayed with the dispId
   * 
   * @param model
   * @param dispId
   */
  public void drawWithModel(Model model, int dispId)
  {
    glNewList(dispId, GL_COMPILE);
    {
      GL11.glTranslatef(0, -2f, 0);
      int currentTexture = -1;
      GL11.glEnable(GL11.GL_TEXTURE_2D);
      Face face = null;
      for (int i = 0; i < model.faces.size(); i++)
      {
        face = model.faces.get(i);
        if (face.texture != currentTexture)
        {
          currentTexture = face.texture;
          GL11.glBindTexture(GL11.GL_TEXTURE_2D, currentTexture);
        }
        GL11.glColor3f(1f, 1f, 1f);
        GL11.glBegin(GL11.GL_TRIANGLES);
        Vector3f n1 = model.normals.get((int) face.normal.x);
        GL11.glNormal3f(n1.x, n1.y, n1.z);
        Vector2f t1 = model.texVerticies.get((int) face.textures.x);
        GL11.glTexCoord2f(t1.x, t1.y);
        Vector3f v1 = model.verticies.get((int) face.vertex.x);
        GL11.glVertex3f(v1.x, v1.y, v1.z);
        Vector3f n2 = model.normals.get((int) face.normal.y);
        GL11.glNormal3f(n2.x, n2.y, n2.z);
        Vector2f t2 = model.texVerticies.get((int) face.textures.y);
        GL11.glTexCoord2f(t2.x, t2.y);
        Vector3f v2 = model.verticies.get((int) face.vertex.y);
        GL11.glVertex3f(v2.x, v2.y, v2.z);
        Vector3f n3 = model.normals.get((int) face.normal.z);
        GL11.glNormal3f(n3.x, n3.y, n3.z);
        Vector2f t3 = model.texVerticies.get((int) face.textures.z);
        GL11.glTexCoord2f(t3.x, t3.y);
        Vector3f v3 = model.verticies.get((int) face.vertex.z);
        GL11.glVertex3f(v3.x, v3.y, v3.z);
        GL11.glEnd();
      }
      GL11.glDisable(GL11.GL_TEXTURE_2D);
    }
    GL11.glEndList();
  }

  @Override
  public void draw()
  {
    javax.vecmath.Vector3f vel = new javax.vecmath.Vector3f();
    vel = body.body.getLinearVelocity(vel);
    vel.absolute();
    if (vel.x > 0.0001f)
    {
      int delta = 60 / (dispIds.length - 1);
      if (step % delta == 0)
      {
        displayListId = dispIds[((step / delta) % (dispIds.length - 1)) + 1];
      }
      step++;
    }
    else
    {
      displayListId = dispIds[0];
    }
    super.draw();
  }

  /**
   * Makes the player jump.
   */
  public void jump()
  {
    body.jump();
  }

  
  /**
   * Walk in the specified direction, x and z will be normalized.
   * 
   * @param x
   * @param z
   */
  public void walk(float x, float z)
  {
    Vector3f vec = new Vector3f(x, 0, z);
    body.push(vec);
  }

  /**
   * Increments the pitch 
   * 
   * @param pitch
   */
  public void pitch(float pitch)
  {
    camera.pitch(pitch);
  }

  /**
   * Increments the yaw 
   * 
   * @param yaw
   */
  public void yaw(float yaw)
  {
    Transform trans = new Transform();
    body.body.getWorldTransform(trans);
    trans.basis.rotY(-(float) Math.toRadians(camera.getYaw() + yaw - 180));
    body.body.setWorldTransform(trans);
    camera.yaw(yaw);
  }

  /**
   * Walks the player forward
   * 
   * @param speed
   */
  public void walkForward(float speed)
  {
    body.moveForward(speed, camera.getYaw());
  }

  /**
   * Walks the player backward
   * 
   * @param speed
   */
  public void walkBackward(float speed)
  {
    body.moveBackward(speed, camera.getYaw());
  }

  /**
   * Walks the player left
   * 
   * @param speed
   */
  public void walkLeft(float speed)
  {
    body.moveLeft(speed, camera.getYaw());
  }

  /**
   * Walks the player right
   * 
   * @param speed
   */
  public void walkRight(float speed)
  {
    body.moveRight(speed, camera.getYaw());
  }

  /**
   * Walks the player forward - right
   * 
   * @param speed
   */
  public void walkForwardRight(float speed)
  {
    body.moveForwardRight(speed, camera.getYaw());
  }

  /**
   * Walks the player forward - left
   * 
   * @param speed
   */
  public void walkForwardLeft(float speed)
  {
    body.moveForwardLeft(speed, camera.getYaw());
  }

  /**
   * Walks the player back - right
   * 
   * @param speed
   */
  public void walkBackRight(float speed)
  {
    body.moveBackRight(speed, camera.getYaw());
  }

  /**
   * Walks the player back - left
   * 
   * @param speed
   */
  public void walkBackLeft(float speed)
  {
    body.moveBackLeft(speed, camera.getYaw());
  }

  /**
   * Allows the main loop to ask the player to setup the camera
   * 
   */
  public void lookThrough()
  {
    camera.lookThrough();
  }

  @Override
  public void interact()
  {
  }

  @Override
  public void update()
  {
    camera.updatePosition(this.getX(), this.getY() + 1.5f, this.getZ(), body.getPitch(), body.getYaw());
    playerStatus();
  }

  /**
   * Sets the players status
   * 
   */
  private void playerStatus()
  {
    if (killed)
    {
      setDead();
    }
  }

  @Override
  public void reset()
  {

  }

  /**
   * Gets the state of this player's life.
   * 
   * @return the state of this player's life
   */
  public boolean isAlive()
  {
    return this.alive;
  }
  
  /**
   * Gets the state of this player's victory.
   * 
   * @return the state of this player's victory
   */
  public int isWinner()
  {
    return this.winner;
  }
  
  /**
   * Sets the state of this player's victory.
   * 
   * @param the state of this player's victory
   */
  public void setWinner(int value)
  {
    this.winner = value;
  }

  /**
   * Gets the name of this player.
   * 
   * @return this player's name
   */
  public String getName()
  {
    return this.name;
  }

  /**
   * Gets this player's health.
   * 
   * @return the health of this player
   */
  public int getHealth()
  {
    return this.health;
  }

  /**
   * Gets this player's team.
   * 
   * @return the team of this player
   */
  public Team getTeam()
  {
    return this.team;
  }

  /**
   * Gets this player's lives.
   * 
   * @return the number of lives this player has
   */
  public int getLives()
  {
    return this.lives;
  }

  /**
   * Gets the camera object on this player's head.
   * 
   * @return this player's camera
   */
  public Camera getCamera()
  {
    return this.camera;
  }

  /**
   * Gets the player's physics body.
   */
  public PhysicsBody getBody()
  {
    return body;
  }

  /**
   * Sets the state of this player's life.
   * 
   * @param alive whether or not this player is alive
   */
  public void setAlive(boolean alive)
  {
    this.alive = alive;
  }

  /**
   * Sets this player's health.
   * 
   * @param health this player's health
   */
  public void setHealth(int health)
  {
    this.health = health;
  }

  /**
   * Sets this player's team.
   * 
   * @param team this player's team
   */
  public void setTeam(Team team)
  {
    this.team = team;
  }

  /**
   * Sets this player's lives.
   * 
   * @param lives the number of lives to give this player
   */
  public void setLives(int lives)
  {
    this.lives = lives;
  }

  /**
   * Sets the players dead state
   */
  private void setDead()
  {
    {
      lives--;
      body.body.setWorldTransform(startPos);
      body.body.setLinearVelocity(PhysicsTools.openGLToBullet(new Vector3f(0, 0, 0)));
      body.body.activate(true);
      health = 100;
      killed = false;
      if (lives <= 0)
      {
        alive = false;
      }
      exploding = false;
    }
  }

  /**
   * Kills the player
   */
  public void kill()
  {
    killed = true;
  }

  /**
   * Hurts the player
   */
  public void hurt()
  {
    health--;
    if (health <= 0)
    {
      kill();
    }
  }

  @Override
  public String toString()
  {
    // @formatter:off
    return "=== Player === " + "Location:" + Arrays.toString(body.getOpenGLTransformMatrixArray()) + " Name:"
        + this.name + " Team Name:" + this.team + " Health:" + this.health + " Lives left:" + this.lives + " Alive:"
        + this.alive + " ===";
    // @formatter:on
  }

}
