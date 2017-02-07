package com.pwdd.server.protocol;

import com.pwdd.server.responders.IResponder;
import com.pwdd.server.utils.FileHandler;

import java.io.*;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public final class ResponseBuilder {

  private static ResponseBuilder responseBuilder = new ResponseBuilder();

  private ResponseBuilder() {}

  public static ResponseBuilder getInstance() {
    return responseBuilder;
  }

  InputStream processResponse(
      HashMap<String, String> request,
      File rootDirectory,
      IResponder[] responders,
      Protocol protocol) throws IOException {
    String uri = request.get("URI");
    File file = defineFile(rootDirectory, uri);
    return response(file, responders, protocol);
  }

  private static InputStream response(File file, IResponder[] responders, Protocol protocol) throws IOException {
    for (IResponder responder : responders) {
      if (responder.canRespond(file)) {
        return buildFrom(responder.header(file, dateInUTC0()), responder.body(file));
      }
    }
    return new ByteArrayInputStream(errorMessage(protocol).getBytes());
  }

  private static InputStream buildFrom(InputStream header, InputStream body) {
    return new SequenceInputStream(header, body);
  }

  private static String dateInUTC0() {
    ZonedDateTime date = ZonedDateTime.now(ZoneOffset.UTC);
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z");
    return dateFormat.format(date);
  }

  private static String errorMessage(Protocol protocol) {
    String crlf = "\r\n";
    String error = protocol instanceof GET ? Protocol.statusCodes.get("404") : Protocol.statusCodes.get("400");
    return Protocol.version + " " + error + crlf +
        "Date: " + dateInUTC0() + crlf +
        crlf +
        error;
  }

  private static File defineFile(File rootDirectory, String uri) {
    String path = uri.toLowerCase();
    switch (path) {
      case "/hello":
      case "/hello/":
        return new File("/hello");
      case "/form":
      case "/form/":
        return new File("/form");
      case "/processed-form":
      case "/processed-form/":
        return new File("/processed-form");
      default:
        return FileHandler.uriToFile(rootDirectory.getAbsolutePath(), uri);
    }
  }
}
