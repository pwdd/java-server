package com.pwdd.server.responders;

import java.io.File;

public interface IResponder {
  String CRLF = "\r\n";

  boolean canRespond(File file);

  byte[] header(File file, String date);

  byte[] body(File file);
}