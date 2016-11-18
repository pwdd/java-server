package com.pwdd.httpServer;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ResponseTest {
  private Response response = new Response(hashResponse());
  private List<String> header = Arrays.asList(response.stringResponse().split("\\s"));

  @Test
  public void responseHeaderHasProtocolVersion() {
    assertTrue("Header has HTTP version", header.contains("HTTP/1.1"));
  }

  @Test
  public void responseHeaderHasContentType() {
    assertTrue("Header has Content-Type key", header.contains("Content-Type:"));
  }

  @Test
  public void responseHeaderHasDate() {
    assertTrue("Header has Date key", header.contains("Date:"));
  }

  @Test
  public void responseHeaderRemovesNullValues() {
    String contentLength = response.contentLength;
    if (contentLength == null) {
      assertTrue("Null body is not in response", !header.contains("Content-Length:"));
    } else {
      assertTrue("Null body is not in response", header.contains("Content-Length:"));
    }
  }

  private HashMap<String, String> hashResponse() {
    HashMap<String, String> result = new HashMap<>();
    result.put("statusCode", "200");
    result.put("statusMessage", "OK");
    result.put("date", "some date");
    result.put("contentType", "text/plain");

    return result;
  }


}
