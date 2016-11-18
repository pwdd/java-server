package com.pwdd.httpServer;

import org.junit.*;
import static org.junit.Assert.*;

import java.net.Socket;

public class ServerTest {
  private int portNumber = 8080;
  private Server server = new Server();

  @Test
  public void acceptsConnection() {
    String hostName = "localhost";

    startServer();
    try (Socket client = new Socket(hostName, portNumber)) {
      assertTrue("Server accepts connection when serverSocket is listening", client.isConnected());
    } catch (Exception e) {
      e.printStackTrace();
    }

    server.stop();

    try (Socket client = new Socket(hostName, portNumber)) {
      fail("Client should be unable to connect when server socket is closed");
    } catch (Exception e) {
      assertEquals("Connection refused", e.getMessage());
    }
  }

  private void startServer() {
    try {
      server.listenAt(portNumber);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
