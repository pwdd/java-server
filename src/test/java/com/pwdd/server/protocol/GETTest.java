package com.pwdd.server.protocol;

import com.pwdd.server.helpers.Helpers;
import org.junit.*;

import java.io.*;
import java.util.HashMap;

import static org.junit.Assert.*;

public class GETTest {
  private final File rootDirectory = new File(System.getProperty("user.dir"),
      "src/test/java/com/pwdd/server/mocks/filesystem");
  private final Protocol getResponder = new GET(rootDirectory);

  private HashMap<String, String> mapRequest(String uri) {
    return new HashMap<String, String>() {{
      put("Method", "GET");
      put("URI", uri);
      put("Host", "localhost");}};
  }

  private String responseFor(String uri) throws IOException {
    return Helpers.inputStreamToString(getResponder.processResponse(mapRequest(uri),
        rootDirectory,
        getResponder.responders()));
  }

  @Test
  public void responseHeaderIsProperlyFormatted() throws IOException {
    String response = responseFor("/hello");
    assertTrue("Each line of requestMap ends with a CRLF",
        (response.contains("OK\r\n") &&
            response.contains("+0000\r\n") &&
            response.contains("text/plain\r\n")));
    assertTrue("Header ends with two CRLF", response.contains("\r\n\r\n"));
  }

  @Test
  public void responseHasHeaderAndBody() throws IOException {
    String response = responseFor("/hello");
    assertTrue("Combined responseBuilder has requestMap and body",
        response.contains("HTTP/1.1 200 OK") && response.contains("Hello, world"));
  }

  @Test
  public void responseHeaderHasProtocolVersion() throws IOException {
    String response = responseFor("/hello");
    assertTrue("Header has HTTP version", response.contains(Protocol.version));
  }

  @Test
  public void responseHeaderHasContentType() throws IOException {
    String response = responseFor("/hello");
    assertTrue("Header has Content-Type key", response.contains("Content-Type:"));
  }

  @Test
  public void responseHeaderHasDate() throws IOException {
    String response = responseFor("/hello");
    assertTrue("Header has Date key", response.contains("Date:"));
  }

  @Test
  public void bodyForRequestedHello() throws IOException {
    String response = responseFor("/hello");
    assertTrue("Body content requested /hello has hello world",
        response.contains("Hello, world"));
  }

  @Test
  public void setContentTypeToHello() throws IOException {
    String response = responseFor("/hello");
    assertTrue("Content-Type is set to text/plain for /hello",
        response.contains("text/plain"));
  }

  @Test
  public void bodyForRequestedForm() throws IOException {
    String response = responseFor("/form");
    assertTrue("Body content requested /form has a form tag",
        response.contains("</form>"));
  }

  @Test
  public void setContentTypeToForm() throws IOException {
    String response = responseFor("/form");
    assertTrue("Content-Type is set to text/html for /form",
        response.contains("text/html"));
  }

  @Test
  public void bodyForRequestedDownload() throws IOException {
    String response = responseFor("/a.txt");
    assertTrue("Body content requested file has the file content",
        response.contains("abc\ndef"));
  }

  @Test
  public void setContentTypeToDownload() throws IOException {
    String response = responseFor("/a.txt");
    assertTrue("Content-Type is set to application/octet-stream",
        response.contains("Content-Type: application/octet-stream"));
  }

  @Test
  public void headerForDownloadHasContentDisposition() throws IOException {
    String response = responseFor("/a.txt");
    assertTrue("Content-Disposition is set on header",
        response.contains("Content-Disposition: attachment"));
  }

  @Test
  public void setContentTypeToImage() throws IOException {
    String response = responseFor("/battle.png");
    assertTrue("Content-Type is image/png",
        response.contains("Content-Type: image/png"));
  }

  @Test
  public void respond404IfInvalidURI() throws IOException {
    String response = responseFor("/abc.txt");
    assertTrue("Respond with 404 error if resource is not found", response.contains("404 Not Found"));
  }
}
