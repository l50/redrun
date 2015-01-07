package redrun.model.toolkit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import redrun.model.mesh.Face;
import redrun.model.mesh.Model;

/**
 * Creates and manages the HUD
 * 
 * @author Adam Mitchell
 * @version 1.0
 * @since 2014-11-9
 * 
 */
public class OBJLoader
{
  /**
   * loadModel static method to load a model
   * 
   * @param File file
   * @return Model to be displayed
   */
  public static Model loadModel(File f)
  {
    BufferedReader reader = null;
    try
    {
      reader = new BufferedReader(new FileReader(f));
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    /** The model */
    Model model = new Model();
    String line;
    Texture currentTexture = null;
    try
    {
      while ((line = reader.readLine()) != null)
      {
        /** find the v */
        if (line.startsWith("v "))
        {
          float x = Float.valueOf(line.split(" ")[1]);
          float y = Float.valueOf(line.split(" ")[2]);
          float z = Float.valueOf(line.split(" ")[3]);
          model.verticies.add(new Vector3f(x, y, z));
        }
        /** find the vn */
        else if (line.startsWith("vn "))
        {
          float x = Float.valueOf(line.split(" ")[1]);
          float y = Float.valueOf(line.split(" ")[2]);
          float z = Float.valueOf(line.split(" ")[3]);
          model.normals.add(new Vector3f(x, y, z));
        }
        /** find the vt */
        else if (line.startsWith("vt "))
        {
          float x = Float.valueOf(line.split(" ")[1]);
          float y = Float.valueOf(line.split(" ")[2]);
          model.texVerticies.add(new Vector2f(x, 1 - y));
        }
        /** find the f */
        else if (line.startsWith("f "))
        {
          Vector3f vertexIndicies = new Vector3f(Float.valueOf(line.split(" ")[1].split("/")[0]) - 1,
              Float.valueOf(line.split(" ")[2].split("/")[0]) - 1, Float.valueOf(line.split(" ")[3].split("/")[0]) - 1);
          Vector3f textureIndicies = new Vector3f(Float.valueOf(line.split(" ")[1].split("/")[1]) - 1,
              Float.valueOf(line.split(" ")[2].split("/")[1]) - 1, Float.valueOf(line.split(" ")[3].split("/")[1]) - 1);
          Vector3f normalIndicies = new Vector3f(Float.valueOf(line.split(" ")[1].split("/")[2]) - 1,
              Float.valueOf(line.split(" ")[2].split("/")[2]) - 1, Float.valueOf(line.split(" ")[3].split("/")[2]) - 1);

          model.faces.add(new Face(vertexIndicies, textureIndicies, normalIndicies, currentTexture.getTextureID()));
        }
        else if (line.startsWith("g "))
        {
          if (line.length() > 2)
          {
            String name = line.split(" ")[1];
            currentTexture = TextureLoader.getTexture("PNG",
                ResourceLoader.getResourceAsStream("res/models/" + name + ".png"));
          }
        }
      }
    }
    catch (NumberFormatException | IOException e)
    {
      e.printStackTrace();
    }
    try
    {
      reader.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return model;
  }
}
