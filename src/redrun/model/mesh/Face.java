package redrun.model.mesh;

import org.lwjgl.util.vector.Vector3f;

/**
 * The face of model
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-11-9
 * 
 */
public class Face
{
  /** the faces vertex */
  public Vector3f vertex = new Vector3f();
  /** the faces normal vectors */
  public Vector3f normal = new Vector3f();
  /** the faces textures */
  public Vector3f textures = new Vector3f();
  public int texture;

  /**
   * Just sets the vertex, textures, normal, and texture
   * @param vertex
   * @param textures
   * @param normal
   * @param texture
   */
  public Face(Vector3f vertex, Vector3f textures, Vector3f normal, int texture)
  {
    this.vertex = vertex;
    this.normal = normal;
    this.textures = textures;
    this.texture = texture;
  }
}
