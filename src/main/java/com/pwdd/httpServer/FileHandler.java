package com.pwdd.httpServer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class FileHandler {
  private String dirName;
  private String absolutePath = "src/main/java/com/pwdd/httpServer/";

  FileHandler(String _dirName) {
    this.dirName = absolutePath + _dirName;
  }

  private List<String> listFilenames() {
    File dir = new File(dirName);
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
    StringBuilder links = new StringBuilder();

    for (String filename : filenames) {
      links.append("<p><a href=\"./" + filename + "\">" + filename + "</a></p>");
    }
    return links.toString();
  }

  String index() {
    StringBuilder content = new StringBuilder();
    content.append("<!doctype html>");
    content.append("<html>");
    content.append("<head><title>index</title></head>");
    content.append("<body>");
    content.append(linkfyDir(listFilenames()));
    content.append("</body>");
    content.append("</html>");
    return content.toString();
  }

//  void createIndex() {
//    try (Writer writer = new BufferedWriter(
//        new OutputStreamWriter(
//            new FileOutputStream(absolutePath + "index.html"), "utf-8"))) {
//      writer.write(index());
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }
}
