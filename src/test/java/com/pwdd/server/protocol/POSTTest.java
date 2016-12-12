package com.pwdd.server.protocol;

import com.pwdd.server.helpers.Helpers;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.*;

public class POSTTest {

  @Test
  public void send400AnswerIfFormIsInvalid() throws IOException {
    File root = new File("foo");
    String data = "text=abc&number=123&select=quatro";
    HashMap<String, String> map = new HashMap<String, String>() {{
      put("Method", "POST");
      put("URI", "/processed-form");
      put("Protocol", "HTTP/1.1");
      put("Body", data);
    }};
    Protocol post = new POST(data);
    String response = Helpers.bytesToString(
        post.processResponse(map, root, post.responders()));
    assertTrue("Status code is 400 for invalid data", response.contains("400 Bad Request"));

  }
}
