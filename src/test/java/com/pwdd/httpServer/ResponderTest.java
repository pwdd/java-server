package com.pwdd.httpServer;

import org.junit.Test;
import static org.junit.Assert.*;

public class ResponderTest {
  private final Responder responder = new Responder(
      new IRouter[] { new HelloWorldRouter(), new IndexRouter("foo") });

  @Test
  public void responseHeaderHasProtocolVersion() {
    String response = responder.response("/");
    assertTrue("Header has HTTP version", response.contains("HTTP/1.1"));
  }

  @Test
  public void responseHeaderHasContentType() {
    String response = responder.response("/");
    assertTrue("Header has Content-Type key", response.contains("Content-Type:"));
  }

  @Test
  public void responseHeaderHasDate() {
    String response = responder.response("/");
    assertTrue("Header has Date key", response.contains("Date:"));
  }

  @Test
  public void bodyForRequestedRoot() {
    String response = responder.response("/");
    assertTrue("Body content requested root is an well formatted html file",
        response.toLowerCase().contains("<!doctype html>".toLowerCase()));
  }

  @Test
  public void bodyForRequestedHello() {
    String response = responder.response("/hello");
    assertTrue("Body content requested root is an well formatted html file",
        response.contains("Hello, world"));
  }

  @Test
  public void setContentTypeToHTML() {
    String response = responder.response("/");
    assertTrue("Content-Type is set to text/html for root",
        response.contains("text/html"));
  }

  @Test
  public void setContentTypeToPlain() {
    String response = responder.response("/hello");
    assertTrue("Content-Type is set to text/plain for /hello",
        response.contains("text/plain"));
  }
}
