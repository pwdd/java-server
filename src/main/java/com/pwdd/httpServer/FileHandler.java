package com.pwdd.httpServer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class FileHandler implements IHandler {
  private final File directory;

  FileHandler(String dirName) {
    this.directory = new File(dirName);
  }

  public String respond(String uri) {
    return index();
  }

  public boolean canRespond(String uri) {
    return !uri.equalsIgnoreCase("/hello");
  }

  private String index() {
    String content = "";
    return content + "<!doctype html>" +
        "<html>" +
        "<head><title>index</title></head>" +
        "<body>" +
        "<h1>Index:</h1>" +
        linkfyDir(listFilenames()) +
        "</body>" +
        "</html>";
  }

  List<String> listFilenames() {
    File[] listOfFiles = directory.listFiles();
    List<String> filenames = new ArrayList<>();

    if (listOfFiles != null) {
      for (File file : listOfFiles) {
        filenames.add(file.getName());
      }
    }
    return filenames;
  }

  private String linkfyDir(List<String> filenames) {
    String startList = "<ul>";
    String endList = "</ul>";
    StringBuilder listItems = new StringBuilder();

    for (String filename : filenames) {
      listItems.append("<li><a href=\"./");
      listItems.append(filename);
      listItems.append("\">");
      listItems.append(filename);
      listItems.append("</a></li>");
    }
    return startList + listItems + endList;
  }
}
