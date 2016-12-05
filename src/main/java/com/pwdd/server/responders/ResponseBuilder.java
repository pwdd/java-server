package com.pwdd.server.responders;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ResponseBuilder {
  private IResponder[] responders;

  public ResponseBuilder(IResponder[] _responders) {
    this.responders = _responders;
  }

  public byte[] response(String uri) {
    for (IResponder responder : this.responders) {
      if (responder.canRespond(uri)) {
        return buildFrom(responder.header(dateInUTC0()), responder.body(uri));
      }
    }
    return notFound().getBytes();
  }

  byte[] buildFrom(byte[] header, byte[] body) {
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
    return "HTTP/1.1 404 Not Found" + crlf +
        "Date: " + dateInUTC0() + crlf +
        crlf +
        "404 Not Found";
  }
}
