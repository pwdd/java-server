package com.pwdd.httpServer;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class Responder {
  private final IHandler[] handlers;

  Responder(String dir) {
    this.handlers = new IHandler[] {
        new HelloWorldHandler(),
        new FileHandler(dir)
    };
  }

  Responder(IHandler[] handlers) {
    this.handlers = handlers;
  }

  String response(String uri) {
    return defaultHeader(contentType(uri)) + bodyForRequested(uri);
  }

  String defaultHeader(String contentType) {
    String crlf = "\r\n";
    return "HTTP/1.1 200 OK" + crlf +
        "Date: " + getDateInUTC0() + crlf +
        "Content-Type: " + contentType + crlf +
        crlf;
  }

  String bodyForRequested(String uri) {
    for (IHandler handler : this.handlers) {
      if (handler.canRespond(uri)) {
        return handler.respond(uri);
      }
    }
    return "";
  }

  String contentType(String uri) {
    if (uri.equals("/")) {
      return "text/html";
    } else {
      return "text/plain";
    }
  }

  private String getDateInUTC0() {
    ZonedDateTime date = ZonedDateTime.now(ZoneOffset.UTC);
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z");
    return dateFormat.format(date);
  }
}
