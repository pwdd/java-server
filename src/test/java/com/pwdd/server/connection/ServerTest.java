package com.pwdd.server.connection;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.*;
import java.net.Socket;

public class ServerTest {
  private final File rootDirectory = new File(System.getProperty("user.dir"),
      "src/test/java/com/pwdd/server/mocks/filesystem");
  private final int portNumber = 8080;
  private final String hostName = "localhost";
  private Server server;
  private Thread thread;

  @Before
  public void setUp() {
    server = new Server(portNumber, rootDirectory);
    thread = new Thread(server);
    thread.start();
  }

  @After
  public void tearDown() throws Exception {
    server.stop();
    thread.join();
  }

  @Test
  public void acceptsConnection() throws Exception  {
    try (Socket client = new Socket(hostName, portNumber)) {
      assertTrue("Server accepts connection when serverSocket is listening", client.isConnected());
    } catch (IOException e) {
      e.printStackTrace();
    }

    server.stop();

    try (Socket client = new Socket(hostName, portNumber)) {
      fail("Client should be unable to connect when server socket is closed");
    } catch (IOException e) {
      String message = e.getMessage().toLowerCase();
      assertTrue(message.contains("connection refused"));
    }
  }

  @Test
  public void runServerTest() throws Exception {
    Socket client = new Socket(hostName, portNumber);
    BufferedReader clientIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
    PrintWriter out = new PrintWriter(client.getOutputStream());
    out.print("GET / HTTP/1.1\r\nHost: localhost\r\n\r\n");
    out.flush();
    client.setSoTimeout(500);
    String in = clientIn.readLine();
    out.close();
    assertEquals("HTTP/1.1 200 OK", in);
  }
}
