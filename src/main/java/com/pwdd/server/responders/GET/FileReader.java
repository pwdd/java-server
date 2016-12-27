package com.pwdd.server.responders.GET;

import java.io.*;

class FileReader {

  public InputStream body(File file) throws IOException {
    return new FileInputStream(file.getAbsolutePath());
  }

  String getExtension(File file) {
    String[] split = file.getName().split("\\.");
    return split[split.length - 1];
  }

  int size(File file) {
    return (int) file.length();
  }
}