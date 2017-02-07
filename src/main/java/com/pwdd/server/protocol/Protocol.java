package com.pwdd.server.protocol;

import com.pwdd.server.responders.IResponder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public interface Protocol {
  String version = "HTTP/1.1";
  Map<String, String> statusCodes = new HashMap<String, String>() {{
    put("200", "200 OK");
    put("404", "404 Not Found");
    put("400", "400 Bad Request");
  }};

  InputStream processResponse(HashMap<String, String> request, File rootDirectory, IResponder[] responders)
      throws IOException;

  IResponder[] responders();
}