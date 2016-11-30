package com.pwdd.httpServer;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class Response {
  private final IResponder[] routers;

  Response(IResponder[] routers) {
    this.routers = routers;
  }

  String response(String uri) {
    for (IResponder router : this.routers) {
      if (router.canRespond(uri)) {
        return router.header(dateInUTC0()) + router.body();
      }
    }
    return notFound();
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
