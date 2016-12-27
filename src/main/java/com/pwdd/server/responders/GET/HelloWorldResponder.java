package com.pwdd.server.responders.GET;

import com.pwdd.server.protocol.Protocol;
import com.pwdd.server.responders.IResponder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

public class HelloWorldResponder implements IResponder {

  public boolean canRespond(File file) {
    return file.getPath().toLowerCase().matches(".*^/hello/?$.*");
  }

  public InputStream header(File file, String date) {
    return new ByteArrayInputStream((Protocol.version + " " + Protocol.statusCodes.get("200") + CRLF +
        "Content-Type: text/plain" + CRLF +
        "Date: " + date + CRLF +
        "Content-Length: " + size() + CRLF +
        CRLF).getBytes());
  }

  public InputStream body(File file) {
    return new ByteArrayInputStream("Hello, world".getBytes());
  }

  private int size() {
    return "Hello, world".getBytes().length;
  }
}
