package com.pwdd.httpServer;

import org.junit.*;
import static org.junit.Assert.assertEquals;

import com.pwdd.httpServer.mocks.MockSocket;

import java.io.BufferedReader;
import java.io.IOException;

public class ConnectionHandlerTest {
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
  public void acceptsRequest() throws IOException {
    startServer();
    MockSocket mockSocket = new MockSocket();
    ConnectionHandler connectionHandler = new ConnectionHandler(mockSocket);
    String request = "GET / HTTP/1.1\r\nHost: localhost\r\n\r\n";
    String expected = request.replace("\r\n", "");
    mockSocket.setRequestString(request);
    String requested = bufToString(connectionHandler.getRequestFrom(mockSocket));
    assertEquals(expected, requested);
  }

  @Test
  public void sendsAResponse() throws IOException {
    startServer();

    MockSocket mockSocket = new MockSocket();
    ConnectionHandler connectionHandler = new ConnectionHandler(mockSocket);
    connectionHandler.sendResponseTo(mockSocket, "foo");
    mockSocket.setStoredOutput();
    assertEquals("foo", mockSocket.storedOutput);
  }

  private String bufToString(BufferedReader toRead) throws IOException {
    String line;
    StringBuilder requestString = new StringBuilder();
    while ((line = toRead.readLine()) != null) {
      requestString.append(line);
    }
    return requestString.toString();
  }

  private void startServer() {
    int portNumber = 8080;
    try {
      server.listenAt(portNumber);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
