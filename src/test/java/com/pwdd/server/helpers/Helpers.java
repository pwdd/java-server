package com.pwdd.server.helpers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public final class Helpers {
  private Helpers() {}

  public static String inputStreamToString(InputStream in) {
    Scanner scan= new Scanner(in).useDelimiter("\\A");
    return scan.hasNext() ? scan.next() : "";
  }

  public static byte[] responseByteArray(InputStream in) throws IOException {
    int bytesRead;
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    byte[] data = new byte[8192];
    while((bytesRead = in.read(data)) != -1) {
      buffer.write(data, 0, bytesRead);
    }
    buffer.flush();
    return buffer.toByteArray();
  }
}
