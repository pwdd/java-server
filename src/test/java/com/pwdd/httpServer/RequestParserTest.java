package com.pwdd.httpServer;

import org.junit.*;
import static org.junit.Assert.*;

import java.io.*;
import java.util.HashMap;

public class RequestParserTest {
  @Test
  public void headerTest() throws IOException {
    String request = "GET / HTTP/1.1\r\n" +
        "Host: localhost\r\n" +
        "Accept: text/html\r\n" +
        "Keep-Alive: 300\r\n" +
        "ConnectionManager: keep-alive\r\n\r\n";
    InputStream is = new ByteArrayInputStream(request.getBytes());
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    HashMap<String, String> expected = new HashMap();
    expected.put("Method", "GET");
    expected.put("URI", "/");
    expected.put("Protocol", "HTTP/1.1");
    expected.put("Host", "localhost");
    expected.put("Accept", "text/html");
    expected.put("Keep-Alive", "300");
    expected.put("ConnectionManager", "keep-alive");

    assertEquals(expected, RequestParser.header(br));
  }
}
