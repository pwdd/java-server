package com.pwdd.httpServer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class FileHandler {
  private String directory;

  FileHandler(String relativePath, String dirName) {
    this.directory = relativePath + "/" + dirName;
  }

  FileHandler(String dirName) {
    this.directory = "src/main/java/com/pwdd/httpServer/" + dirName;
  }

  List<String> listFilenames() {
    File dir = new File(directory);
    File[] listOfFiles = dir.listFiles();
    List<String> filenames = new ArrayList<>();

    if (listOfFiles != null) {
      for (File file : listOfFiles) {
        if (file.isFile()) {
          filenames.add(file.getName());
        }
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

  String index() {
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
}
