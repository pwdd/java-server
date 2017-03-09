package com.pwdd.server.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Request {
  private HashMap<String, String> parsedRequest;

  public Request(HashMap<String, String> parsedRequest) {
    this.parsedRequest = parsedRequest;
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

  public String getProtocol() {
    return parsedRequest.get("Protocol");
  }

  public String getHeader() {
    return getMethod() + " " + getURI() + " " + getProtocol();
  }

  public String getFullHeader() {
    StringBuilder builder = new StringBuilder();
    builder.append(getHeader()).append("\r\n");

    for (Map.Entry<String, String> entry : parsedRequest.entrySet()) {
      String key = entry.getKey();
      if (!Objects.equals(key, "Body") && !isFirstLineHeader(key)) {
        builder.append(key).append(": ");
        builder.append(entry.getValue()).append("\r\n");
      }
    }
    return builder.append("\r\b").toString();
  }

  private boolean isFirstLineHeader(String key) {
    return Objects.equals(key, "Method") || Objects.equals(key, "URI") || Objects.equals(key, "Protocol");
  }
}
