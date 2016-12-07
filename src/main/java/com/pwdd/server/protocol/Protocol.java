package com.pwdd.server.protocol;

import com.pwdd.server.responders.IResponder;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface Protocol {
  String version = "HTTP/1.1";
  Map<String, String> statusCodes = new HashMap<String, String>() {{
    put("successGET", "200 OK");
    put("failedGET", "400 Not Found");
  }};

  byte[] processResponse(BufferedReader request) throws IOException;

  IResponder[] responders();
}