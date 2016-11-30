package com.pwdd.httpServer.utils;

import java.io.BufferedReader;
import java.io.IOException;

public final class InputReader {
  private InputReader() {}

  public static String bufToString(BufferedReader in) throws IOException {
    String crlf = "\r\n";
    StringBuilder builder = new StringBuilder();
    String line;

    while ((line = in.readLine()) != null && !line.equals("")) {
      builder.append(line).append(crlf);
    }
    return builder.toString();
  }
}
