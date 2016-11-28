package com.pwdd.httpServer;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

public class ResponderTest {
  private final Responder response = new Responder(
      new IRouter[] { new HelloWorldRouter(), new FileRouter("foo") });
  private final String header = response.defaultHeader("text/plain");
  private final List<String> headerList = Arrays.asList(header.split("\\s"));

  @Test
  public void responseHeaderHasProtocolVersion() {
    assertTrue("Header has HTTP version", headerList.contains("HTTP/1.1"));
  }

  @Test
  public void responseHeaderHasContentType() {
    assertTrue("Header has Content-Type key", headerList.contains("Content-Type:"));
  }

  @Test
  public void responseHeaderHasDate() {
    assertTrue("Header has Date key", headerList.contains("Date:"));
  }

  @Test
  public void bodyForRequestedRoot() {
    String body = response.bodyForRequested("/");
    assertTrue("Body content requested root is an well formatted html file",
        body.matches("(?i:.*<!doctype html>.*)"));
  }

  @Test
  public void bodyForRequestedHello() {
    String body = response.bodyForRequested("/hello");
    assertTrue("Body content requested root is an well formatted html file",
        body.matches("Hello, world"));
  }

  @Test
  public void setContentTypeToHTML() {
    String contentType = response.contentType("/");
    assertEquals("Content-Type is set to text/html for root",
        "text/html", contentType);
  }

  @Test
  public void setContentTypeToPlain() {
    String contentType = response.contentType("/hello");
    assertEquals("Content-Type is set to text/plain for /hello",
        "text/plain", contentType);
  }
}
