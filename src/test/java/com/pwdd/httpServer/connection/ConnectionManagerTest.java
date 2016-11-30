package com.pwdd.httpServer.connection;

import com.pwdd.httpServer.Response;
import com.pwdd.httpServer.responders.HelloWorldResponder;
import com.pwdd.httpServer.responders.IResponder;
import com.pwdd.httpServer.responders.IndexResponder;
import com.pwdd.httpServer.utils.InputReader;
import org.junit.*;
import static org.junit.Assert.assertEquals;

import com.pwdd.httpServer.mocks.MockSocket;

import java.io.BufferedReader;
import java.io.IOException;

public class ConnectionManagerTest {
  private Server server;
  private final int portNumber = 8080;
  private final IResponder[] handlers = new IResponder[] { new IndexResponder("foo"), new HelloWorldResponder() };
  private final Response response = new Response(handlers);

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
    String expected = request.trim();
    mockSocket.setRequestString(request);
    String requested = InputReader.bufToString(connectionManager.getRequestFrom(mockSocket));
    assertEquals(expected, requested.trim());
  }

  @Test
  public void sendsAResponse() throws IOException {
    startServer();

    MockSocket mockSocket = new MockSocket();
    ConnectionManager connectionManager = new ConnectionManager(mockSocket, response);
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
}
