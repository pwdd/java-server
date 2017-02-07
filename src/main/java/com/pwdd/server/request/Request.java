package com.pwdd.server.request;

import java.util.HashMap;

public class Request {
  private HashMap<String, String> parsedRequest;

  public Request(HashMap<String, String> _parsedRequest) {
    this.parsedRequest = _parsedRequest;
  }

  public String getBody() {
    return parsedRequest.get("Body");
  }

  public String getMethod() {
    return parsedRequest.get("Method");
  }

  public String getURI() {
    return parsedRequest.get("URI");
  }
}
