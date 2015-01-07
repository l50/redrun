package redrun.sound;

import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.alBufferData;
import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.AL10.alGenBuffers;
import static org.lwjgl.openal.AL10.alGenSources;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourcei;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.lwjgl.util.WaveData;

import redrun.model.game.GameData;

/**
 * This class represents a sound that can be played in RedRun.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-12-07
 */
public class Sound
{
  /** The sound data. */
  private WaveData data = null;
  
  /** The buffer. */
  int buffer = 0;

  /** The source buffer. */
  private int source = 0;
  
  /**
   * Creates a new sound with the specified sound file.
   * 
   * @param soundName the location of the sound file.
   */
  public Sound(String soundName)
  {
    try
    {
      FileInputStream input = new FileInputStream("res/sounds/" + soundName + ".wav");
      data = WaveData.create(new BufferedInputStream(input));
      buffer = alGenBuffers();
      alBufferData(buffer, data.format, data.data, data.samplerate);
      data.dispose();
      source = alGenSources();
      alSourcei(source, AL_BUFFER, buffer);    
      GameData.soundManager.addSound(this);
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
  }
  
  /**
   * Plays the sound.
   */
  public void play()
  {
    alSourcePlay(source);
  }
  
  /**
   * Destroys this sound.
   */
  public void destroy()
  {
    alDeleteBuffers(buffer);
  }
}
