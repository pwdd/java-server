package com.pwdd.httpServer;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

final class Response {
  private Response() {}

  static String defaultHeader(String contentType) {
    String crlf = "\r\n";
    return "HTTP/1.1 200 OK" + crlf +
        "Date: " + getDateInUTC0() + crlf +
        "Content-Type: " + contentType + crlf +
        crlf;
  }

  private static String getDateInUTC0() {
    ZonedDateTime date = ZonedDateTime.now(ZoneOffset.UTC);
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z");
    return dateFormat.format(date);
  }
}
