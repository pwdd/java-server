package com.pwdd.server.responders.GET;

import com.pwdd.server.protocol.Protocol;
import com.pwdd.server.responders.IResponder;

import java.io.File;

public class HelloWorldResponder implements IResponder {

  public boolean canRespond(File file) {
    return file.getPath().toLowerCase().matches(".*^/hello/?$.*");
  }

  public byte[] header(File file, String date) {
    String responseHeader = Protocol.version + " " + Protocol.statusCodes.get("successGET") + CRLF +
        "Content-Type: text/plain" + CRLF +
        "Date: " + date + CRLF +
        CRLF;
    return responseHeader.getBytes();
  }

  public byte[] body(File file) {
    return "Hello, world".getBytes();
  }
}
