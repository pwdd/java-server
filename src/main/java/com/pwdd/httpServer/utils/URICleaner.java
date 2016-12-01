package com.pwdd.httpServer.utils;

public final class URICleaner {
  private URICleaner() {}

  public static String replaceRootDir(String rootDirectory, String cleanedURI) {
    return cleanedURI.replace(rootDirectory, "");
  }

  public static String cleanUp(String uri) {
    if (uri.equals("/")) {
      return uri;
    } else if (uri.startsWith("/") && !uri.endsWith("/")) {
      return uri.substring(1);
    } else if (!uri.startsWith("/") && uri.endsWith("/")) {
      return uri.substring(0, uri.length() -1);
    } else if (uri.startsWith("/") && uri.endsWith("/")) {
      return uri.substring(1, uri.length() -1);
    } else {
      return uri;
    }
  }
}
