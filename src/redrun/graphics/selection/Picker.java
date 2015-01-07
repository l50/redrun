package redrun.graphics.selection;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;

import redrun.model.gameobject.GameObject;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

/**
 * This class allows selection of objects from an 3D OpenGL scene.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-08
 */
public class Picker
{
  /** The mode of selection. */
  private static boolean picking;

  /** Contains selection data. */
  private static IntBuffer selectBuf = BufferUtils.createIntBuffer(1024);

  /** The number of objects hit in 3D space. */
  private static int hits;

  /**
   * Begins picking from an OpenGL scene.
   */
  public static void startPicking()
  {
    IntBuffer viewport = BufferUtils.createIntBuffer(16);

    glSelectBuffer(selectBuf);

    glGetInteger(GL_VIEWPORT, viewport);

    glRenderMode(GL_SELECT);

    glInitNames();

    glMatrixMode(GL_PROJECTION);
    glPushMatrix();
    glLoadIdentity();

    gluPickMatrix(Display.getWidth() / 2, viewport.get(3) - (Display.getHeight() / 2), 5, 5, viewport);
    gluPerspective(70.0f, (float) Display.getWidth() / (float) Display.getHeight(), 0.3f, 1000.0f);
    glMatrixMode(GL_MODELVIEW);
  }

  /**
   * Processes all hits from picking and executes the interact method of the closest game object.
   * 
   * @param hits the total number of hits
   * @param buffer the selection buffer where the results from picking were placed
   */
  public static void processHits(int hits, IntBuffer buffer)
  {
    for (int i = 0; i < hits * 4; i++)
      System.out.println("Buffer: " + buffer.get(i));
    
    int numberOfNames = 0;
    int names, minZ;
    int index = 0;
    int indexNames = 0;

    minZ = Integer.MAX_VALUE;
    for (int i = 0; i < hits; i++)
    {
      names = buffer.get(index);
      index++;
      if (buffer.get(index) < minZ)
      {
        numberOfNames = names;
        minZ = buffer.get(index);
        indexNames = index + 2;
      }

      index += names + 2;
    }
    
    GameObject selected = null; 

    if (numberOfNames > 0)
    {
      System.out.print("You selected a ");

      index = indexNames;
      
      selected = GameObject.getGameObject(buffer.get(index));
      
      System.out.print(selected.getClass().getName() + " with unique ID: " + buffer.get(index));

      for (int j = 0; j < numberOfNames; j++, index++)
      {
        System.out.println(buffer.get(index));
      }
    }
    else System.out.println("No game object selected...");
    
    selected.interact();
  }

  /**
   * Stops picking and processes the results.
   */
  public static void stopPicking()
  {
    glMatrixMode(GL_PROJECTION);
    glPopMatrix();
    glMatrixMode(GL_MODELVIEW);
    glFlush();
    hits = glRenderMode(GL_RENDER);
    System.out.println("================================================");
    System.out.println("Number of hits: " + hits);
    if (hits != 0) processHits(hits, selectBuf);
    System.out.println("================================================\n");
    selectBuf.clear();
    
    // Prevent picking from occurring on future passes...
    picking = false;
  }
  
  /**
   * Toggles picking.
   */
  public static void pick()
  {
    picking = true;
  }
  
  /*
   * Indicates if the picker is currently is currently picking.
   */
  public static boolean isPicking()
  {
    return picking;
  }
}
