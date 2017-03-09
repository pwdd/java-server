package com.pwdd.server.request;

import org.junit.*;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RequestTest {
  private HashMap<String, String> parsedRequest = getParsedRequest();

  private Request request = new Request(parsedRequest);

  private HashMap<String, String> getParsedRequest() {
    HashMap<String, String> parsedRequest = new HashMap<>();
    parsedRequest.put("Method", "GET");
    parsedRequest.put("URI", "/foo");
    parsedRequest.put("Protocol", "HTTP/1.1");
    parsedRequest.put("Host", "localhost");
    parsedRequest.put("Accept", "text/html");
    parsedRequest.put("Keep-Alive", "300");
    parsedRequest.put("ConnectionManager", "keep-alive");
    parsedRequest.put("Body", "hello");
    return parsedRequest;
  }

  @Test
  public void getBodyTest() {
    assertEquals("hello", request.getBody());
  }

  @Test
  public void getMethodTest() {
    assertEquals("GET", request.getMethod());
  }

  @Test
  public void getURITest() {
    assertEquals("/foo", request.getURI());
  }

  @Test
  public void getProtocolTest() {
    assertEquals("HTTP/1.1", request.getProtocol());
  }

  @Test
  public void getHeaderTest() {
    assertEquals("GET /foo HTTP/1.1", request.getHeader());
  }

  @Test
  public void getFullHeaderHasOnlyHeaderTest() {
    String fullHeader = request.getFullHeader();
    assertTrue(fullHeader.contains(request.getHeader()));
    assertTrue(fullHeader.contains("Host: localhost"));
    assertTrue(fullHeader.contains("Keep-Alive: 300"));
    assertTrue(fullHeader.contains("Accept: text/html"));
    assertTrue(fullHeader.contains("ConnectionManager: keep-alive"));
  }

  @Test
  public void getFullHeaderDoesNotHaveBodyTest() {
    String fullHeader = request.getFullHeader();
    assertTrue(!fullHeader.contains(request.getBody()));
  }
}
