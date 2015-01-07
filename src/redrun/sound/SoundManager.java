package redrun.sound;

import java.util.ArrayList;

/**
 * This class manages sound files for RedRun.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-12-07
 */
public class SoundManager
{
  /** The list of all sounds currently loaded. */
  private ArrayList<Sound> sounds = new ArrayList<Sound>();
  
  /**
   * Adds a sound to the list of active sounds.
   * 
   * @param sound the sound to add
   */
  public void addSound(Sound sound)
  {
    sounds.add(sound);
  }
  
  /**
   * Destroys all sound files. Should be called before OpenAL is destroyed.
   */
  public void destroySounds()
  {
    for (Sound sound : sounds)
    {
      sound.destroy();
    }
  }
}
