package redrun.model.toolkit;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Drawable;
import org.lwjgl.opengl.GLSync;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Adam Mitchell
 *
 * Sets up the necessary locks and mechanisms to share the gl context 
 * with this thread so fonts can be loaded while loading screen is painted in main
 */
public abstract class BackgroundLoader
{
  // CPU synchronization
  private final ReentrantLock lock = new ReentrantLock();
  // GPU synchronization
  @SuppressWarnings("unused")
  private GLSync fence;
  private Drawable drawable;

  private boolean running = true;;

  protected abstract Drawable getDrawable() throws LWJGLException;

  /**
   * starts a drawable gl context and loads the fonts
   * 
   * @throws LWJGLException
   */
  public void start() throws LWJGLException
  {
    // The shared context must be created on the main thread.
    drawable = getDrawable();

    new Thread(new Runnable()
    {
      public void run()
      {
        System.out.println("-- Background Thread started --");
        try
        {
          // Make the shared context current in the worker thread
          drawable.makeCurrent();
        }
        catch (LWJGLException e)
        {
          throw new RuntimeException(e);
        }
        System.out.println("** Context Switched **");

        lock.lock();
        redrun.model.toolkit.FontTools.loadFonts();
        lock.unlock();
        System.out.println("-- Background Thread finished --");
        running = false;
      }
    }).start();
  }

  /**
   * @return true if still running
   */
  public boolean isRunning()
  {
    return running;
  }
}