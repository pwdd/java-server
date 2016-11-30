package com.pwdd.httpServer;

import org.junit.*;
import static org.junit.Assert.assertEquals;

import com.pwdd.httpServer.mocks.MockSocket;

import java.io.BufferedReader;
import java.io.IOException;

public class ConnectionManagerTest {
  private Server server;
  private int portNumber = 8080;
  private IResponder[] handlers = new IResponder[] { new IndexResponder("foo"), new HelloWorldResponder() };
  private Response response = new Response(handlers);

  @Before
  public void setUp() {
    server = new Server(portNumber, response);
  }

  @After
  public void tearDown() throws Exception  {
    server.stop();
  }

  @Test
  public void acceptsRequest() throws IOException {
    startServer();
    MockSocket mockSocket = new MockSocket();
    ConnectionManager connectionManager = new ConnectionManager(mockSocket, response);
    String request = "GET / HTTP/1.1\r\nHost: localhost\r\n\r\n";
    String expected = request.replace("\r\n", "");
    mockSocket.setRequestString(request);
    String requested = bufToString(connectionManager.getRequestFrom(mockSocket));
    assertEquals(expected, requested);
  }

  @Test
  public void sendsAResponse() throws IOException {
    startServer();

    MockSocket mockSocket = new MockSocket();
    ConnectionManager connectionManager = new ConnectionManager(mockSocket, response);
    connectionManager.sendResponseTo(mockSocket, "foo");
    mockSocket.setStoredOutput();
    assertEquals("foo", mockSocket.storedOutput);
  }

  private void startServer() {
    try {
      server.listenAt(portNumber);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private String bufToString(BufferedReader toRead) throws IOException {
    String line;
    StringBuilder requestString = new StringBuilder();
    while ((line = toRead.readLine()) != null) {
      requestString.append(line);
    }
    return requestString.toString();
  }
}
