package com.pwdd.server.responders.GET;

import java.io.*;

public final class FileReader {

  private static FileReader fileReader = new FileReader();

  private FileReader() {}

  public static FileReader getInstance() {
    return fileReader;
  }

  public InputStream body(File file) throws IOException {
    return new FileInputStream(file.getAbsolutePath());
  }

  public String getExtension(File file) {
    String[] split = file.getName().split("\\.");
    return split[split.length - 1];
  }

  int size(File file) {
    return (int) file.length();
  }
}