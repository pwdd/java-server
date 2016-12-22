package com.pwdd.server.responders.GET;

import java.io.*;

abstract class FileReader {

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

  private byte[] fileToByteArray(File file) throws IOException {
    int chunkSize = 8192;
    byte[] chunks = new byte[chunkSize];
    FileInputStream input = new FileInputStream(file.getAbsolutePath());
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    int bytesRead;
    while ((bytesRead = input.read(chunks)) != -1) {
      output.write(chunks, 0, bytesRead);
    }
    return output.toByteArray();
  }
}