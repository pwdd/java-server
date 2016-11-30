package com.pwdd.httpServer;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class Response {
  private final IResponder[] routers;

  Response(IResponder[] routers) {
    this.routers = routers;
  }

  byte[] response(String uri) {
    for (IResponder router : this.routers) {
      if (router.canRespond(uri)) {
        return combineResponse(router.header(dateInUTC0()), router.body(uri));
      }
    }
    return notFound().getBytes();
  }

  private byte[] combineResponse(byte[] header, byte[] body) {
    byte[] combined = new byte[header.length + body.length];
    for (int i = 0; i < combined.length; i++) {
      combined[i] = i < header.length ? header[i] :  body[i - header.length];
    }
    return combined;
  }

  private String dateInUTC0() {
    ZonedDateTime date = ZonedDateTime.now(ZoneOffset.UTC);
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z");
    return dateFormat.format(date);
  }

  private String notFound() {
    String crlf = "\r\n";
    return "HTTP/1.1 404 Not Found" + crlf + "Date: " + dateInUTC0() + crlf + crlf + "404 Not Found";
  }
}
