package com.pwdd.httpServer;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class Responder {
  private final IRouter[] routers;

  Responder(IRouter[] routers) {
    this.routers = routers;
  }

  String response(String uri) {
    return defaultHeader(uri) + bodyForRequested(uri);
  }

  String defaultHeader(String uri) {
    String crlf = "\r\n";
    return "HTTP/1.1 200 OK" + crlf +
        "Date: " + getDateInUTC0() + crlf +
        "Content-Type: " + contentType(uri) + crlf +
        crlf;
  }

  String bodyForRequested(String uri) {
    for (IRouter handler : this.routers) {
      if (handler.canRespond(uri)) {
        return handler.respond();
      }
    }
    return "";
  }

  String contentType(String uri) {
    for (IRouter handler : this.routers) {
      if (handler.canRespond(uri)) {
        return handler.setContentType();
      }
    }
    return "";
  }

  private String getDateInUTC0() {
    ZonedDateTime date = ZonedDateTime.now(ZoneOffset.UTC);
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z");
    return dateFormat.format(date);
  }
}
