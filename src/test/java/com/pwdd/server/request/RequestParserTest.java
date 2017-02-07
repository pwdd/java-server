package com.pwdd.server.request;

import org.junit.*;
import static org.junit.Assert.assertEquals;

import java.io.*;
import java.util.HashMap;

public class RequestParserTest {
  private final RequestParser requestParser = RequestParser.getInstance();
  @Test
  public void headerTest() throws IOException {
    String request = "GET /foo HTTP/1.1\r\n" +
        "Host: localhost\r\n" +
        "Accept: text/html\r\n" +
        "Keep-Alive: 300\r\n" +
        "ConnectionManager: keep-alive\r\n\r\n" +
        "hello";
    InputStream is = new ByteArrayInputStream(request.getBytes());
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    HashMap<String, String> expected = new HashMap<>();
    expected.put("Method", "GET");
    expected.put("URI", "/foo");
    expected.put("Protocol", "HTTP/1.1");
    expected.put("Host", "localhost");
    expected.put("Accept", "text/html");
    expected.put("Keep-Alive", "300");
    expected.put("ConnectionManager", "keep-alive");

    assertEquals(expected, requestParser.requestMap(br));
  }

  @Test
  public void bodyTest() throws IOException {
    String request = "POST /foo HTTP/1.1\r\n" +
        "Host: localhost\r\n" +
        "Accept: text/html\r\n" +
        "Keep-Alive: 300\r\n" +
        "Content-Length: 5\r\n" +
        "ConnectionManager: keep-alive\r\n\r\n" +
        "hello";
    InputStream is = new ByteArrayInputStream(request.getBytes());
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    HashMap<String, String> expected = new HashMap<>();
    expected.put("Method", "POST");
    expected.put("URI", "/foo");
    expected.put("Protocol", "HTTP/1.1");
    expected.put("Host", "localhost");
    expected.put("Accept", "text/html");
    expected.put("Keep-Alive", "300");
    expected.put("ConnectionManager", "keep-alive");
    expected.put("Content-Length", "5");
    expected.put("Body", "hello");

    assertEquals(expected, requestParser.requestMap(br));
  }
}
