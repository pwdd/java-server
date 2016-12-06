package com.pwdd.server.server;

import com.pwdd.server.responders.HelloWorldResponder;
import com.pwdd.server.responders.IResponder;
import com.pwdd.server.responders.IndexResponder;
import com.pwdd.server.responders.ResponseBuilder;
import org.junit.*;
import static org.junit.Assert.assertEquals;

import com.pwdd.server.mocks.MockSocket;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class ConnectionManagerTest {
  private Server server;
  private final int portNumber = 8080;
  private final IResponder[] handlers = new IResponder[] { new IndexResponder(new File("foo")), new HelloWorldResponder() };
  private final ResponseBuilder response = new ResponseBuilder(handlers);
  private final File rootDirectory = new File(System.getProperty("user.dir"), "src/test/java/com/pwdd/server/mocks/filesystem");

  @Before
  public void setUp() {
    server = new Server(portNumber, response, rootDirectory);
  }

  @After
  public void tearDown() throws Exception  {
    server.stop();
  }

  @Test
  public void acceptsRequest() throws IOException {
    startServer();
    MockSocket mockSocket = new MockSocket();
    ConnectionManager connectionManager = new ConnectionManager(mockSocket, response, rootDirectory);
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
    ConnectionManager connectionManager = new ConnectionManager(mockSocket, response, rootDirectory);
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
