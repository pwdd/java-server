package com.pwdd.server.responders.POST;

import org.junit.*;

import java.util.HashMap;

import static org.junit.Assert.*;

public class ProcessFormResponderTest {
  private String data = "text=abc&number=123&select=one";
  private ProcessFormResponder responder = new ProcessFormResponder(data);
  @Test
  public void mapFormatDataTest() {
    HashMap<String, String> mapped = new HashMap<String, String>() {{
      put("Text", "abc");
      put("Number", "123");
      put("Select", "one");
    }};
    assertEquals(mapped, responder.mapFormData());
  }
}
