package redrun.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import redrun.database.Map;
import redrun.database.MapObjectDB;

/**
 * Directs the flow of information between a client and the server.
 * 
 * @author Jayson Grace ( jaysong@unm.edu ), Troy Squillaci ( zivia@unm.edu )
 * @version 1.0
 * @since 2014-11-17
 */
public class MailMan extends Thread
{
  /** For sending messages to the client. */
  private PrintWriter clientWriter;

  /** For receiving messages from the client. */
  private BufferedReader clientReader;

  /** Holds player data sent from the client each frame. */
  private String playerData;

  /** Holds trap data send from the client each frame. */
  private String trapData;

  /** Indicates if the worker is ready to broadcast player data. */
  private boolean playerReady = false;

  /** Indicates if the worker is ready to broadcast trap data. */
  private boolean trapReady = false;

  /**
   * Creates a new mailman that handles client-server interactions.
   * 
   * @param client the client socket for communication
   */
  public MailMan(Socket client)
  {
    try
    {
      clientWriter = new PrintWriter(client.getOutputStream(), true);
    }
    catch (IOException e)
    {
      System.err.println("RedRun Worker: Could not open output stream...");
      e.printStackTrace();
    }
    try
    {
      clientReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }
    catch (IOException e)
    {
      System.err.println("RedRun Worker: Could not open input stream...");
      e.printStackTrace();
    }
  }

  /**
   * Sends a message from the server to a client.
   * 
   * @param networkData the message to send to client
   */
  public void send(String networkData)
  {
    clientWriter.println(networkData);
  }

  /**
   * Facilitate responding to client requests from the server.
   */
  public void run()
  {
    Pattern playerData = Pattern
        .compile("===\\sPlayer\\s===\\sLocation:\\[(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?)\\]\\sName:(.*?)\\sTeam\\sName:(\\w+)\\sHealth:(.*?)\\sLives\\sleft:(\\d+)\\sAlive:(\\w+)\\s===");
    Pattern trapData = Pattern.compile("===\\sTrap\\s===\\sID:(\\d+)\\s===");
    Pattern requestDisconnect = Pattern.compile("Disconnect");
    Pattern requestPlayer = Pattern.compile("^Player$");
    Pattern requestMapData = Pattern.compile("Map");
    Pattern requestNumberPlayers = Pattern.compile("^Number Players$");

    while (true)
    {
      try
      {
        String incomingMessage = clientReader.readLine();

        Matcher matchInboundPlayer = playerData.matcher(incomingMessage);
        Matcher matchTrapData = trapData.matcher(incomingMessage);
        Matcher matchRequestDisconnect = requestDisconnect.matcher(incomingMessage);
        Matcher matchRequestPlayer = requestPlayer.matcher(incomingMessage);
        Matcher matchRequestMapData = requestMapData.matcher(incomingMessage);
        Matcher matchRequestNumberPlayers = requestNumberPlayers.matcher(incomingMessage);

        if (matchInboundPlayer.find())
        {
          setPlayerData(incomingMessage);
          playerReady = true;
          Server.checkBroadcast();
        }
        else if (matchTrapData.find())
        {
          setTrapData(incomingMessage);
          trapReady = true;
          Server.checkBroadcast();
        }
        else if (matchRequestPlayer.find())
        {
          send(Server.assignPlayer());
        }
        else if (matchRequestMapData.find())
        {
          for (Map map : Server.mapData)
          {
            send(map.toString());
          }
          for (MapObjectDB mapObject : Server.mapObjectData)
          {
            send(mapObject.toString());
          }
        }
        else if (matchRequestNumberPlayers.find())
        {
          Server.broadcast(Server.numberOfConnections());
        }
        else if (matchRequestDisconnect.find())
        {
          System.out.println("Client Disconnecting!");
          send("Disconnecting client...");
          Server.deleteClientFromList(this);
          Server.broadcast(Server.numberOfConnections());
          break;
        }
        else
        {
          System.out.println("Unknown message from client: " + incomingMessage);
        }
      }
      catch (IOException e)
      {
        System.err.println("RedRun Worker Error: Could send data to client...");
        e.printStackTrace();
      }
    }
  }

  /**
   * Check to see if both player and trap information are prepared for
   * transmission
   * 
   * @return true if both are ready, false otherwise
   */
  protected boolean isPlayerReady()
  {
    return playerReady;
  }

  /**
   * Check to see if both player and trap information are prepared for
   * transmission
   * 
   * @return true if both are ready, false otherwise
   */
  protected boolean isTrapReady()
  {
    return trapReady;
  }

  /**
   * Reset the ready state of player information.
   */
  protected void resetPlayerReady()
  {
    this.playerReady = false;
  }

  /**
   * Reset the ready state of trap information.
   */
  protected void resetTrapReady()
  {
    this.trapReady = false;
  }

  /**
   * Gets the player data associated with this mailman.
   * 
   * @return the playerData
   */
  public String getPlayerData()
  {
    return playerData;
  }

  /**
   * Gets the trap data associated with this mailman.
   * 
   * @return the trapData
   */
  public String getTrapData()
  {
    return trapData;
  }

  /**
   * Sets the player data associated with this mailman.
   * 
   * @param playerData the playerData to set
   */
  public void setPlayerData(String playerData)
  {
    this.playerData = playerData;
  }

  /**
   * Sets the trap data associated with this mailman.
   * 
   * @param trapData the trapData to set
   */
  public void setTrapData(String trapData)
  {
    this.trapData = trapData;
  }
}