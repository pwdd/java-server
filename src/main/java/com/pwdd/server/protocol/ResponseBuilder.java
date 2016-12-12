package com.pwdd.server.protocol;

import com.pwdd.server.responders.IResponder;
import com.pwdd.server.utils.FileHandler;

import java.io.File;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

abstract class ResponseBuilder {

  public byte[] processResponse(HashMap<String, String> request, File rootDirectory, IResponder[] responders) throws IOException {
    String uri = request.get("URI");
    File file = defineFile(rootDirectory, uri);
    return response(file, responders);
  }

  private byte[] response(File file, IResponder[] responders) {
    for (IResponder responder : responders) {
      if (responder.canRespond(file)) {
        return buildFrom(responder.header(file, dateInUTC0()), responder.body(file));
      }
    }
    return notFound().getBytes();
  }

  private byte[] buildFrom(byte[] header, byte[] body) {
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
    return Protocol.version + " " + Protocol.statusCodes.get("404") + crlf +
        "Date: " + dateInUTC0() + crlf +
        crlf +
        Protocol.statusCodes.get("404");
  }

  private File defineFile(File rootDirectory, String uri) {
    String path = uri.toLowerCase();
    if (path.equals("/hello") || path.equals("/hello/")) {
      return new File("/hello");
    } else if (path.equals("/form") || path.equals("/form/")) {
      return new File("/form");
    } else if (path.equals("/processed-form") || path.equals("/processed-form/")) {
      return new File("/processed-form");
    } else {
      return FileHandler.uriToFile(rootDirectory.getAbsolutePath(), uri);
    }
  }
}
