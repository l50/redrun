package redrun.model.toolkit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import redrun.database.Map;
import redrun.database.MapObjectDB;
import redrun.database.RedRunDAO;

/**
 * Read in text file with map information, and parse it into the RedRun database
 * 
 * @author Jayson Grace ( jaysong@unm.edu )
 * @version 1.0
 * @since 2014-11-24
 *
 */
public class MapObjectTextToDB
{
  /** Debug mode */
  private static final boolean DEBUG = false;

  /**
   * Generate map
   * 
   * @param br bufferedreader to read map input
   * @return map created
   */
  private static Map generateMap(BufferedReader br)
  {
    Pattern getMap = Pattern.compile("(.*?),\\s(\\w+),\\s(\\w+),\\s\\((.*?)\\)");
    String mapName = null;
    try
    {
      String firstLine = br.readLine();
      Matcher matchMap = getMap.matcher(firstLine);
      if (matchMap.find())
      {
        mapName = matchMap.group(1);
        RedRunDAO.insertMap(matchMap.group(1), matchMap.group(2), matchMap.group(3), matchMap.group(4));
        if (DEBUG)
        {
          for (int i = 1; i <= matchMap.groupCount(); i++)
            System.out.println(i + " " + matchMap.group(i));
        }
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    for (Map map : RedRunDAO.getAllMaps())
    {
      if (DEBUG) System.out.println(map.toString());
      if (map.toString().contains(mapName)) return map;
    }
    return null;
  }

  /**
   * Insert map object into the database
   * 
   * @param br reader
   * @param currentMap current map we're using
   * @throws IOException exception if there's issues reading the input file
   */
  private static void generateMapObjects(BufferedReader br, Map currentMap) throws IOException
  {
    int mapId = currentMap.getId();
    Pattern getMapObject = Pattern.compile("(\\w+),\\s\\((.*?)\\),\\s(\\w+),\\s(\\w+),\\s(\\w+),\\s(\\w+),\\s(\\d+)");

    String line = br.readLine();
    try
    {
      while (null != (line = br.readLine()))
      {
        Matcher matchMapObject = getMapObject.matcher(line);
        if (matchMapObject.find())
        {
          RedRunDAO.insertMapObject(matchMapObject.group(1), matchMapObject.group(2), matchMapObject.group(3),
              matchMapObject.group(4), matchMapObject.group(5), matchMapObject.group(6), mapId);
          if (DEBUG)
          {
            for (int i = 1; i <= matchMapObject.groupCount(); i++)
              System.out.println(i + " " + matchMapObject.group(i));
          }
        }
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    finally
    {
      br.close();
    }
    if (DEBUG)
    {
      for (MapObjectDB mo : RedRunDAO.getAllMapObjects())
      {
        System.out.println(mo.toString());
      }
    }
  }

  /**
   * Main statement
   * 
   * @param args
   */
  public static void main(String[] args) throws IOException
  {
    BufferedReader br = new BufferedReader(new FileReader("res/maps/IceWorld.txt"));

    Map currentMap = generateMap(br);
    generateMapObjects(br, currentMap);
    System.out.println("Database successfully populated!");
  }
}