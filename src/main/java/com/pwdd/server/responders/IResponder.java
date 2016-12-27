package com.pwdd.server.responders;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface IResponder {
  String CRLF = "\r\n";

  boolean canRespond(File file);

  InputStream header(File file, String date);

  InputStream body(File file) throws IOException;
}