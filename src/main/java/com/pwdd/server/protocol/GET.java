package com.pwdd.server.protocol;

import com.pwdd.server.RequestParser;
import com.pwdd.server.responders.GET.*;
import com.pwdd.server.responders.IResponder;
import com.pwdd.server.utils.FileHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class GET implements Protocol {
  private File rootDirectory;

  public GET(File _rootDirectory) {
    this.rootDirectory = _rootDirectory;
  }

  public IResponder[] responders() {
    return new IResponder[] {
        new DownloadableResponder(),
        new HelloWorldResponder(),
        new ImageResponder(),
        new IndexResponder(rootDirectory)};
  }

  public byte[] processResponse(BufferedReader request) throws IOException {
    String uri = RequestParser.header(request).get("URI");
    File file = defineFile(uri);
    return response(file);
  }

  private byte[] response(File file) {
    for (IResponder responder : responders()) {
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
    return version + " 404 Not Found" + crlf +
        "Date: " + dateInUTC0() + crlf +
        crlf +
        "404 Not Found";
  }

  private File defineFile(String uri) {
    String path = uri.toLowerCase();
    if (path.equals("/hello") || path.equals("/hello/")) {
      return new File("/hello");
    } else {
      return FileHandler.uriToFile(rootDirectory.getAbsolutePath(), uri);
    }
  }
}
