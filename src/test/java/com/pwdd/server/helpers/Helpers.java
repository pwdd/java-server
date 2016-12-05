package com.pwdd.server.helpers;

import java.nio.charset.StandardCharsets;

public final class Helpers {
  private Helpers() {}

  public static String bytesToString(byte[] in) {
    return new String(in, StandardCharsets.UTF_8);
  }
}
