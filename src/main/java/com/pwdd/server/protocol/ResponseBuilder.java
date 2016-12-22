package com.pwdd.server.protocol;

import com.pwdd.server.responders.IResponder;
import com.pwdd.server.utils.FileHandler;

import java.io.*;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

abstract class ResponseBuilder {

  public InputStream processResponse(HashMap<String, String> request, File rootDirectory, IResponder[] responders) throws IOException {
    String uri = request.get("URI");
    File file = defineFile(rootDirectory, uri);
    return response(file, responders);
  }

  private InputStream response(File file, IResponder[] responders) throws IOException {
    for (IResponder responder : responders) {
      if (responder.canRespond(file)) {
        return buildFrom(responder.header(file, dateInUTC0()), responder.body(file));
      }
    }
    return new ByteArrayInputStream(errorMessage().getBytes());
  }

  private InputStream buildFrom(InputStream header, InputStream body) {
    return new SequenceInputStream(header, body);
  }

  String dateInUTC0() {
    ZonedDateTime date = ZonedDateTime.now(ZoneOffset.UTC);
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z");
    return dateFormat.format(date);
  }

  String errorMessage() {
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
