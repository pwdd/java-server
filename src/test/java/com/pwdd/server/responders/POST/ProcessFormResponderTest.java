package com.pwdd.server.responders.POST;

import org.junit.*;

import java.io.File;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ProcessFormResponderTest {

  @Test
  public void canRespondToProcessedFormTest() {
    String data = "text=abc&number=123&select=one";
    ProcessFormResponder responder = new ProcessFormResponder(data);
    assertTrue("Respond to /processed-form if form data is valid",
        responder.canRespond(new File("/processed-form")));
  }

  @Test
  public void cannotRespondOtherURITest() {
    String data = "text=abc&number=123&select=one";
    ProcessFormResponder responder = new ProcessFormResponder(data);
    assertFalse("Cannot respond to other uri",
        responder.canRespond(new File("/form")));
  }

  @Test
  public void cannotRespondInvalidFormTest() {
    String data = "text=abc&number=123&select=quatro";
    ProcessFormResponder responder = new ProcessFormResponder(data);
    assertFalse("Cannot respond to invalid form data",
        responder.canRespond(new File("/processed-form")));
  }

  @Test
  public void mapFormatDataTest() {
    String data = "text=abc&number=123&select=one";
    ProcessFormResponder responder = new ProcessFormResponder(data);
    HashMap<String, String> mapped = new HashMap<String, String>() {{
      put("Text", "abc");
      put("Number", "123");
      put("Select", "one");
    }};
    assertEquals(mapped, responder.mapFormData());
  }
}
