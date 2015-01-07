package redrun.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import redrun.model.game.GameData;
import redrun.model.gameobject.player.Player;
import redrun.model.gameobject.trap.Trap;

/**
 * Facilitate client interaction with the server
 * 
 * @author Jayson Grace ( jaysong@unm.edu ), Troy Squillaci ( zivia@unm.edu )
 * @version 1.0
 * @since 2014-11-22
 */
public class Client
{
  /** The socket connection to the server. */
  private Socket clientSocket;

  /** For sending messages to the server. */
  private PrintWriter write;

  /** For receiving messages from the server. */
  private BufferedReader reader;

  /** Listens for messages from the server. */
  private ClientListener listener;

  /**
   * Creates a new client that connects to the specified host and port.
   * 
   * @param host the IP address or FQDN of the machine to connect to
   * @param portNumber the port number to connect to
   */
  public Client(String host, int portNumber)
  {
    // Try to connect until a connection is established...
    while (!openConnection(host, portNumber))
      ;

    // Start listening thread...
    listener = new ClientListener();
    System.out.println("RedRun Client Listener: " + listener);
    listener.start();
  }

  /**
   * Close all streams gracefully and exits worker threads.
   */
  public void closeAll()
  {
    System.out.println("RedRun Client: Closing Resources...");

    listener.destroyThread();

    try
    {
      listener.join();
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }

    if (write != null) write.close();
    if (reader != null)
    {
      try
      {
        reader.close();
        clientSocket.close();
      }
      catch (IOException e)
      {
        System.err.println("RedRun Client Error: Could not close...");
        e.printStackTrace();
      }
    }
  }

  /**
   * Open connection to the specified host and port.
   * 
   * @param host the server address to connect to
   * @param portNumber the port number to connect to on the server
   * @return a boolean indicating if the client is connected to the server
   */
  private boolean openConnection(String host, int portNumber)
  {
    try
    {
      clientSocket = new Socket(host, portNumber);
    }
    catch (UnknownHostException e)
    {
      System.err.println("RedRun Client Error: Unknown Host " + host);
      e.printStackTrace();
      return false;
    }
    catch (IOException e)
    {
      System.err.println("RedRun Client Error: Could not open connection to " + host + " on port " + portNumber);
      e.printStackTrace();
      return false;
    }

    try
    {
      write = new PrintWriter(clientSocket.getOutputStream(), true);
    }
    catch (IOException e)
    {
      System.err.println("RedRun Client Error: Could not open output stream...");
      e.printStackTrace();
      return false;
    }
    try
    {
      reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }
    catch (IOException e)
    {
      System.err.println("RedRun Client Error: Could not open input stream...");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Requests map objects from the server.
   */
  public void requestMapObjects()
  {
    this.write.println("Map");
  }

  /**
   * Requests to be disconnected from the server.
   */
  public void requestDisconnect()
  {
    this.write.println("Disconnect");
  }

  /**
   * Requests the player data to make the player.
   */
  public void requestPlayer()
  {
    this.write.println("Player");
  }

  /**
   * Get the number of players connected to the server
   */
  public void requestNumberPlayers()
  {
    this.write.println("Number Players");
  }

  /**
   * Sends the current player to the server.
   * 
   * @param player the current player
   */
  public void sendPlayer(Player player)
  {
    this.write.println(player.toString());
  }

  /**
   * Sends activated trap data to the server.
   * 
   * @param trap the activated trap
   */
  public void sendTrap(Trap trap)
  {
    this.write.println(trap.getNetworkString());
  }

  /**
   * This class listens for and handles messages received from the server.
   * 
   * @author Jayson Grace ( jaysong@unm.edu )
   * @version 1.0
   * @since 2014-11-17
   */
  class ClientListener extends Thread
  {
    /** For closing the thread gracefully. */
    private volatile boolean destroy = false;

    /**
     * Gracefully closes the thread.
     */
    public void destroyThread()
    {
      this.destroy = true;
    }

    /**
     * Listen for incoming data from the server.
     */
    @Override
    public void run()
    {
      while (!destroy)
      {
        Pattern getMap = Pattern
            .compile("(===\\sMap\\s===)\\sID:(\\d+)\\sName:(.*?)\\sSkyBox:(\\w+)\\sFloor:(\\w+)\\sLight Position:(.*?)\\s===");
        Pattern getMapObject = Pattern
            .compile("(===\\sMap\\sObject\\s===)\\sID:(\\d+)\\sName:(\\w+)\\sLocation:(\\d+\\.\\d+f),\\s(\\d+\\.\\d+f),\\s(\\d+\\.\\d+f)\\sGround Texture:(\\w+)\\sWall Texture:(\\w+)\\sDirection:(\\w+)\\sTrap Type:(.*?)\\sMap\\sID:(\\d+)\\s===");
        Pattern inboundPlayerData = Pattern
            .compile("===\\sPlayer\\s===\\sLocation:\\[(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?),\\s(.*?)\\]\\sName:(.*?)\\sTeam\\sName:(\\w+)\\sHealth:(.*?)\\sLives\\sleft:(\\d+)\\sAlive:(\\w+)\\s===");
        Pattern quitGame = Pattern.compile("Disconnecting client...");
        Pattern requestTrapData = Pattern.compile("===\\sTrap\\s===\\sID:(\\d+)\\s===");
        Pattern numberPlayers = Pattern.compile("^===\\sNumber Players\\s===\\sNumber:(\\d+)\\s===$");

        try
        {
          // Wait for the next message from the server...
          String msg = reader.readLine();

          Matcher matchMap = getMap.matcher(msg);
          Matcher matchMapObject = getMapObject.matcher(msg);
          Matcher matchQuitGame = quitGame.matcher(msg);
          Matcher matchInboundPlayerData = inboundPlayerData.matcher(msg);
          Matcher matchTrapData = requestTrapData.matcher(msg);
          Matcher matchNumberPlayers = numberPlayers.matcher(msg);

          if (matchMap.find() || matchMapObject.find() || matchInboundPlayerData.find() || matchTrapData.find()
              || matchNumberPlayers.find())
          {
            GameData.networkData.add(msg);
          }
          else if (matchQuitGame.find())
          {
            write.println(msg);
            break;
          }
          else
          {
            System.out.println("Unrecognized message " + msg + " sent, error!");
          }
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    }
  }
}