package com.pwdd.server.responders.GET;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

abstract  class FileReader {

  public byte[] body(File file) {
    byte[] bodyBytes = new byte[0];
    try {
      bodyBytes = fileToByteArray(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bodyBytes;
  }

  public String getExtension(File file) {
    String[] split = file.getName().split("\\.");
    return split[split.length - 1];
  }

  private byte[] fileToByteArray(File file) throws IOException {
    Path path = Paths.get(file.getAbsolutePath());
    return Files.readAllBytes(path);
  }
}
