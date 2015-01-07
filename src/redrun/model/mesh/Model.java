package redrun.model.mesh;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

/**
 * The Model of model
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-11-9
 * 
 */
public class Model
{
  /** the verticies */
  public List<Vector3f> verticies = new ArrayList<Vector3f>();
  /** the normals */
  public List<Vector3f> normals = new ArrayList<Vector3f>();
  /** the faces */
  public List<Face> faces = new ArrayList<Face>();
  /** the texVerticies */
  public List<Vector2f> texVerticies = new ArrayList<Vector2f>();
  /** the textures */
  public List<Texture> textures = new ArrayList<Texture>();
}
