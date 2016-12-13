package com.pwdd.server.server;

import com.pwdd.server.protocol.GET;
import com.pwdd.server.protocol.POST;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.pwdd.server.mocks.MockSocket;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class ConnectionManagerTest {
  private Server server;
  private final File rootDirectory = new File(System.getProperty("user.dir"), "src/test/java/com/pwdd/server/mocks/filesystem");

  @Before
  public void setUp() {
    final int portNumber = 8080;
    server = new Server(portNumber, rootDirectory);
  }

  @After
  public void tearDown() throws Exception  {
    server.stop();
  }

  @Test
  public void acceptsRequest() throws IOException {
    startServer();
    MockSocket mockSocket = new MockSocket();
    ConnectionManager connectionManager = new ConnectionManager(mockSocket, rootDirectory);
    String request = "GET / HTTP/1.1\r\nHost: localhost\r\n\r\n";
    String expected = request.trim();
    mockSocket.setRequestString(request);
    String requested = bufToString(connectionManager.getRequestFrom(mockSocket));
    assertEquals(expected, requested.trim());
  }

  @Test
  public void sendsAResponse() throws IOException {
    startServer();

    MockSocket mockSocket = new MockSocket();
    ConnectionManager connectionManager = new ConnectionManager(mockSocket, rootDirectory);
    connectionManager.sendResponseTo(mockSocket, "foo".getBytes());
    mockSocket.setStoredOutput();
    assertEquals("foo", mockSocket.storedOutput);
  }

  private void startServer() {
    try {
      server.listen();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String bufToString(BufferedReader in) throws IOException {
    String crlf = "\r\n";
    StringBuilder builder = new StringBuilder();
    String line;

    while ((line = in.readLine()) != null && !line.equals("")) {
      builder.append(line).append(crlf);
    }
    return builder.toString();
  }
}
