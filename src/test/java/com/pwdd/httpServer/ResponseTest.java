package com.pwdd.httpServer;

import org.junit.Test;
import static org.junit.Assert.*;

public class ResponseTest {
  private final Response response = new Response(
      new IResponder[] { new HelloWorldResponder(), new IndexResponder("foo") });

  @Test
  public void responseHeaderHasProtocolVersion() {
    String response = this.response.response("/");
    assertTrue("Header has HTTP version", response.contains("HTTP/1.1"));
  }

  @Test
  public void responseHeaderHasContentType() {
    String response = this.response.response("/");
    assertTrue("Header has Content-Type key", response.contains("Content-Type:"));
  }

  @Test
  public void responseHeaderHasDate() {
    String response = this.response.response("/");
    assertTrue("Header has Date key", response.contains("Date:"));
  }

  @Test
  public void bodyForRequestedRoot() {
    String response = this.response.response("/");
    assertTrue("Body content requested root is an well formatted html file",
        response.toLowerCase().contains("<!doctype html>".toLowerCase()));
  }

  @Test
  public void bodyForRequestedHello() {
    String response = this.response.response("/hello");
    assertTrue("Body content requested root is an well formatted html file",
        response.contains("Hello, world"));
  }

  @Test
  public void setContentTypeToHTML() {
    String response = this.response.response("/");
    assertTrue("Content-Type is set to text/html for root",
        response.contains("text/html"));
  }

  @Test
  public void setContentTypeToPlain() {
    String response = this.response.response("/hello");
    assertTrue("Content-Type is set to text/plain for /hello",
        response.contains("text/plain"));
  }
}
