package redrun.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import redrun.database.Map;
import redrun.database.MapObjectDB;
import redrun.database.RedRunDAO;

/**
 * Facilitate server functionality
 * 
 * @author Jayson Grace ( jaysong@unm.edu ), Troy Squillaci ( zivia@unm.edu )
 * @version 1.0
 * @since 2014-11-17
 */
public class Server
{
  /** Server IP address. */
  public static final String HOST = "127.0.0.1";

  /** Server listening port. */
  public static final int PORT = 7777;

  /** Used to listen for in-bound client connections. */
  private ServerSocket serverSocket;

  /** Used to assign unique players to each connected client. */
  private static int playerCounter = 0;

  /** List of all connections to server. */
  private static LinkedList<MailMan> allConnections = new LinkedList<MailMan>();

  /** Keeps track of data associated with the map. */
  public static ArrayList<Map> mapData = new ArrayList<Map>();

  /** Keeps track of data associated with items that make up the map. */
  public static ArrayList<MapObjectDB> mapObjectData = new ArrayList<MapObjectDB>();

  /**
   * Creates a new RedRun server on the specified port.
   * 
   * @param portNumber port number for clients to connect to
   */
  public Server(int portNumber)
  {
    try
    {
      serverSocket = new ServerSocket(portNumber);
    }
    catch (IOException e)
    {
      System.err.println("Server error: Opening socket failed.");
      e.printStackTrace();
      System.exit(-1);
    }

    mapData = RedRunDAO.getAllMaps();
    mapObjectData = RedRunDAO.getAllMapObjects();

    waitForConnection();
  }

  /**
   * Waits for client to connect.
   */
  public void waitForConnection()
  {
    while (true)
    {
      System.out.println("RedRun Server: Waiting for a client...");
      try
      {
        Socket client = serverSocket.accept();
        MailMan worker = new MailMan(client);
        worker.start();
        System.out.println("RedRun Server: A new client has connected!");
        allConnections.add(worker);
      }
      catch (IOException e)
      {
        System.err.println("RedRun Server Error: Failed to connect to client...");
        e.printStackTrace();
      }
    }
  }

  /**
   * Assign a player to each connected client
   * 
   * @return player to assign
   */
  public static String assignPlayer()
  {
    String[] players = {
        "=== Player === Location:[1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 15.0, 2.0, 15.0] Name:Balthazar Team Name:RED Health:100 Lives left:5 Alive:true ===",
        "=== Player === Location:[1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 4.0, 2.0, -4.0] Name:Avacyn Team Name:BLUE Health:100 Lives left:5 Alive:true ===",
        "=== Player === Location:[1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, -4.0, 2.0, 4.0] Name:Archimedes Team Name:BLUE Health:100 Lives left:5 Alive:true ===",
        "=== Player === Location:[1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, -4.0, 2.0, -4.0] Name:Leeroy Jenkins Team Name:BLUE Health:100 Lives left:5 Alive:true ===",
        "=== Player === Location:[1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 4.0, 2.0, 4.0] Name:Linvala Team Name:BLUE Health:100 Lives left:5 Alive:true ==="
    };
    return players[playerCounter++];
  }

  /**
   * Clear all connections.
   */
  public void cleanConnectionList()
  {
    allConnections.clear();
  }

  /**
   * Deletes a single client from the list of connections.
   */
  public static void deleteClientFromList(MailMan worker)
  {
    allConnections.remove(worker);
  }

  /**
   * Checks to see if all workers are ready to broadcast, if they are ready a
   * broadcast will occur.
   */
  public static void checkBroadcast()
  {
    boolean isPlayerReady = true;

    for (MailMan workers : allConnections)
    {
      if (!workers.isPlayerReady())
      {
        isPlayerReady = false;
      }
    }

    if (isPlayerReady)
    {
      for (MailMan workers : allConnections)
      {
        broadcast(workers.getPlayerData());
        workers.resetPlayerReady();
      }
    }

    for (MailMan workers : allConnections)
    {      
      if (workers.isTrapReady())
      {
        broadcast(workers.getTrapData());
        workers.resetTrapReady();
      }
    }
  }

  /**
   * Broadcasts a message to all connected clients.
   * 
   * @param networkData the message to send to all clients
   */
  public static void broadcast(String networkData)
  {
    for (MailMan workers : allConnections)
    {
      workers.send(networkData);
    }
  }

  /**
   * Get the number of connected clients to the server
   * 
   * @return get number of clients
   */
  public static String numberOfConnections()
  {
    Integer connections = allConnections.size();
    return "=== Number Players === Number:" + connections.toString() + " ===";
  }

  /**
   * Entry point of the program.
   * 
   * @param args the command line arguments
   */
  public static void main(String args[])
  {
    new Server(PORT);
  }
}