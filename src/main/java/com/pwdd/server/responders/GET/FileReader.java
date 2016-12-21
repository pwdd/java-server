package com.pwdd.server.responders.GET;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

abstract class FileReader {

  public byte[] body(File file) {
    byte[] bodyBytes = new byte[0];
    try {
      bodyBytes = fileToByteArray(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bodyBytes;
  }

  String getExtension(File file) {
    String[] split = file.getName().split("\\.");
    return split[split.length - 1];
  }

  private byte[] fileToByteArray(File file) throws IOException {
    int chunkSize = 8192;
    byte[] chunks = new byte[chunkSize];
    FileInputStream input = new FileInputStream(file.getAbsolutePath());
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int bytesRead;
    while ((bytesRead = input.read(chunks)) != -1) {
      outputStream.write(chunks, 0, bytesRead);
    }
    return outputStream.toByteArray();
  }
}
