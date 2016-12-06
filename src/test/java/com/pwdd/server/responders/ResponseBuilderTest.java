package com.pwdd.server.responders;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class ResponseBuilderTest {
  private File rootDirectory = new File(System.getProperty("user.dir") + "/test/java/com/pwdd/server/mocks/filesystem");
  private HelloWorldResponder helloResponder = new HelloWorldResponder();
  private IndexResponder indexResponder = new IndexResponder(rootDirectory);
  private ResponseBuilder responseBuilder = new ResponseBuilder(new IResponder[]{ helloResponder, indexResponder });
  private byte[] header = helloResponder.header(new File("foo"), "Sun 4 December 2016 13:30:30 -000");
  private byte[] body = helloResponder.body(new File("/hello"));

  @Test
  public void responseHeaderIsProperlyFormatted() {
    assertTrue("Each line of header ends with a CRLF",
        (bytesToString(header).contains("OK\r\n") &&
            bytesToString(header).contains("-000\r\n") &&
            bytesToString(header).contains("text/plain\r\n")));
    assertTrue("Header ends with two CRLF", bytesToString(header).contains("\r\n\r\n"));
  }

  @Test
  public void combinedResponseHasHeaderAndBody() {
    String combinedResponse = bytesToString(responseBuilder.buildFrom(header, body));

    assertTrue("Combined responseBuilder has header and body",
        combinedResponse.contains("HTTP/1.1 200 OK") && combinedResponse.contains("Hello, world"));
  }

  @Test
  public void responseHeaderHasProtocolVersion() {
    String responseString = bytesToString(responseBuilder.response(new File("/hello")));
    assertTrue("Header has HTTP version", responseString.contains("HTTP/1.1"));
  }

  @Test
  public void responseHeaderHasContentType() {
    String responseString = bytesToString(responseBuilder.response(new File("/hello")));
    assertTrue("Header has Content-Type key", responseString.contains("Content-Type:"));
  }

  @Test
  public void responseHeaderHasDate() {
    String responseString = bytesToString(responseBuilder.response(new File("/hello")));
    assertTrue("Header has Date key", responseString.contains("Date:"));
  }

  @Test
  public void bodyForRequestedHello() {
    String responseString = bytesToString(responseBuilder.response(new File("/hello")));
    assertTrue("Body content requested root is an well formatted html file",
        responseString.contains("Hello, world"));
  }

  @Test
  public void setContentTypeToPlain() {
    String responseString = bytesToString(responseBuilder.response(new File("/hello")));
    assertTrue("Content-Type is set to text/plain for /hello",
        responseString.contains("text/plain"));
  }

  private String bytesToString(byte[] in) {
    return new String(in, StandardCharsets.UTF_8);
  }
}
