package com.pwdd.httpServer;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ResponseTest {
  private String response = Response.defaultHeader();
  private List<String> header = Arrays.asList(response.split("\\s"));

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
}
