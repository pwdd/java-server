package com.pwdd.server.utils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileHandler {
  private FileHandler() {}

  public static boolean isImage(File file) {
    Pattern pattern = Pattern.compile("\\.jpeg|\\.jpg|\\.png");
    Matcher matcher = pattern.matcher(file.getName());
    return matcher.find();
  }

  public static boolean isPdf(File file) {
    return file.getName().endsWith(".pdf");
  }
}
