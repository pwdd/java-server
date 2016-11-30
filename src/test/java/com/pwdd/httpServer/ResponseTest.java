package com.pwdd.httpServer;

import com.pwdd.httpServer.responders.HelloWorldResponder;
import com.pwdd.httpServer.responders.IResponder;
import com.pwdd.httpServer.responders.IndexResponder;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class ResponseTest {
  private final Response response = new Response(
      new IResponder[] { new HelloWorldResponder(), new IndexResponder("foo") });

  @Test
  public void responseHeaderHasProtocolVersion() {
    String response = getResponseString("/");
    assertTrue("Header has HTTP version", response.contains("HTTP/1.1"));
  }

  @Test
  public void responseHeaderHasContentType() {
    String response = getResponseString("/");
    assertTrue("Header has Content-Type key", response.contains("Content-Type:"));
  }

  @Test
  public void responseHeaderHasDate() {
    String response = getResponseString("/");
    assertTrue("Header has Date key", response.contains("Date:"));
  }

  @Test
  public void bodyForRequestedRoot() {
    String response = getResponseString("/");
    assertTrue("Body content requested root is an well formatted html file",
        response.toLowerCase().contains("<!doctype html>".toLowerCase()));
  }

  @Test
  public void bodyForRequestedHello() {
    String response = getResponseString("/hello");
    assertTrue("Body content requested root is an well formatted html file",
        response.contains("Hello, world"));
  }

  @Test
  public void setContentTypeToHTML() {
    String response = getResponseString("/");
    assertTrue("Content-Type is set to text/html for root",
        response.contains("text/html"));
  }

  @Test
  public void setContentTypeToPlain() {
    String response = getResponseString("/hello");
    assertTrue("Content-Type is set to text/plain for /hello",
        response.contains("text/plain"));
  }

  private String getResponseString(String uri) {
    byte[] response = this.response.response(uri);
    return new String(response, StandardCharsets.UTF_8);
  }
}
