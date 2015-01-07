package redrun.model.toolkit;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;

/**
 * This class provides conversion method that convert primitive data arrays into their respective buffer form.
 * 
 * @author Troy Squillaci
 * @version 1.0
 * @since 2014-11-08
 */
public class BufferConverter
{
  /**
   * Converts a byte array to a ByteBuffer.
   * 
   * @param values the byte array to convert into a ByteBuffer
   * @return the converted ByteBuffer
   */
  public static ByteBuffer asByteBuffer(byte[] values)
  {
    ByteBuffer buffer = BufferUtils.createByteBuffer(values.length);
    buffer.put(values);
    buffer.flip();
    return buffer;
  }

  /**
   * Converts a int array to a IntBuffer.
   * 
   * @param values the int array to convert into a IntBuffer
   * @return the converted IntBuffer
   */
  public static IntBuffer asIntBuffer(int[] values)
  {
    IntBuffer buffer = BufferUtils.createIntBuffer(values.length);
    buffer.put(values);
    buffer.flip();
    return buffer;
  }

  /**
   * Converts a float array to a FloatBuffer.
   * 
   * @param values the float array to convert into a FloatBuffer
   * @return the converted FloatBuffer
   */
  public static FloatBuffer asFloatBuffer(float[] values)
  {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
    buffer.put(values);
    buffer.flip();
    return buffer;
  }

  /**
   * Converts a double array to a DoubleBuffer.
   * 
   * @param values the double array to convert into a DoubleBuffer
   * @return the converted DoubleBuffer
   */
  public static DoubleBuffer asDoubleBuffer(double[] values)
  {
    DoubleBuffer buffer = BufferUtils.createDoubleBuffer(values.length);
    buffer.put(values);
    buffer.flip();
    return buffer;
  }

  /**
   * Converts a float array to a flipped FloatBuffer.
   * 
   * @param values the float values that are to be turned into a FloatBuffer
   * @return a FloatBuffer
   */
  public static FloatBuffer asFlippedFloatBuffer(float... values)
  {
    FloatBuffer buffer = BufferUtils.createFloatBuffer(values.length);
    buffer.put(values);
    buffer.flip();
    return buffer;
  }

  /**
   * Creates a pre-flipped float buffer of the specified size.
   * 
   * @param size the size of the buffer
   * @return the new reversed float buffer
   */
  public static FloatBuffer reserveFloatData(int size)
  {
    return BufferUtils.createFloatBuffer(size);
  }
}
