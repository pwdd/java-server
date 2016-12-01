package com.pwdd.httpServer.responders;

import com.pwdd.httpServer.utils.URICleaner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IndexResponder implements IResponder {
  private final File directory;

  public IndexResponder(String dirName) {
    this.directory = new File(dirName);
  }

  public boolean canRespond(String uri) {
    return uri.equalsIgnoreCase("/") || (uriToFile(uri).isDirectory());
  }

  public byte[] header(String date) {
    String contentType = "text/html";
    String crlf = "\r\n";
    return ("HTTP/1.1 200 OK" + crlf +
        "Date: " + date + crlf +
        "Content-Type: " + contentType + crlf +
        crlf).getBytes();
  }

  public byte[] body(String uri) {
    return index(uri).getBytes();
  }

  private File uriToFile(String uri) {
    String cleaned = URICleaner.cleanUp(uri);
    return new File(cleaned);
  }

  private String index(String uri) {
    String content = "";
    return content + "<!doctype html>" +
        "<html>" +
        "<head><title>index</title></head>" +
        "<body>" +
        "<h1>Index:</h1>" +
        new File(uri).isFile() +
        linkfyDir(listFilenames(uri)) +
        "</body>" +
        "</html>";
  }

  List<String> listFilenames(String uri) {
    String cleaned = URICleaner.cleanUp(uri);
    File[] listOfFiles;
    List<String> filenames = new ArrayList<>();

    if (cleaned.equals("/")) {
      listOfFiles = directory.listFiles();
    } else {
      listOfFiles = new File(cleaned).listFiles();
    }

    if (listOfFiles != null) {
      for (File file : listOfFiles) {
        if (uri.equals("/")) {
          filenames.add(file.getName());
        } else {
          filenames.add(file.getPath().replace("uri", ""));
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
      listItems.append("<li><a href=\"/");
      listItems.append(filename);
      listItems.append("\">");
      listItems.append(filename);
      listItems.append("</a></li>");
    }
    return startList + listItems + endList;
  }
}
