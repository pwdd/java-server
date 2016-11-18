package com.pwdd.httpServer;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerTest {
  private int portNumber = 8080;
  private String hostName = "localhost";
  private Server server;

  @Before
  public void setUp() {
    server = new Server();
  }

  @After
  public void tearDown() {
    server.stop();
  }

  @Test
  public void acceptsConnection() {
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

//  @Test
//  public void runServerTest() throws IOException {
//    server.run();
//    Socket client = new Socket(hostName, portNumber);
//    BufferedReader clientIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
//    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
//    out.print("GET / HTTP/1.1\r\nHost: localhost\r\n\r\n");
//    out.close();
//
//    assertEquals("HTTP/1.1 200 OK", clientIn.readLine());
//  }

  private void startServer() {
    try {
      server.listenAt(portNumber);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
