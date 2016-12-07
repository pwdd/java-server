package com.pwdd.server.protocol;

import com.pwdd.server.helpers.Helpers;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;

import static org.junit.Assert.assertTrue;

public class GETTest {
  private File rootDirectory = new File(System.getProperty("user.dir"),
      "/test/java/com/pwdd/server/mocks/filesystem");
  private Protocol getResponder = new GET(rootDirectory);

  private BufferedReader bufRequest(String request) {
    return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(request.getBytes())));
  }

  private String response() throws IOException {
    String requestString = "GET /hello HTTP/1.1\r\nHost: localhost\r\n\r\n";
    return Helpers.bytesToString(getResponder.processResponse(bufRequest(requestString)));
  }

  @Test
  public void responseHeaderIsProperlyFormatted() throws IOException {
    assertTrue("Each line of header ends with a CRLF",
        (response().contains("OK\r\n") &&
            response().contains("+0000\r\n") &&
            response().contains("text/plain\r\n")));
    assertTrue("Header ends with two CRLF", response().contains("\r\n\r\n"));
  }

  @Test
  public void responseHasHeaderAndBody() throws IOException {
    assertTrue("Combined responseBuilder has header and body",
        response().contains("HTTP/1.1 200 OK") && response().contains("Hello, world"));
  }

  @Test
  public void responseHeaderHasProtocolVersion() throws IOException {
    assertTrue("Header has HTTP version", response().contains(Protocol.version));
  }

  @Test
  public void responseHeaderHasContentType() throws IOException {
    assertTrue("Header has Content-Type key", response().contains("Content-Type:"));
  }

  @Test
  public void responseHeaderHasDate() throws IOException {
    assertTrue("Header has Date key", response().contains("Date:"));
  }

  @Test
  public void bodyForRequestedHello() throws IOException {
    assertTrue("Body content requested root is an well formatted html file",
        response().contains("Hello, world"));
  }

  @Test
  public void setContentTypeToPlain() throws IOException {
    assertTrue("Content-Type is set to text/plain for /hello",
        response().contains("text/plain"));
  }
}
