package com.pwdd.server.responders;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IndexResponder implements IResponder {
  private File rootDirectory;

  public IndexResponder(File _rootDirectory) {
    this.rootDirectory = _rootDirectory;
  }

  public boolean canRespond(String uri) {
    return rootDirectory.exists() && (uri.equals("/") || isValidDirectory(uri));
  }

  private boolean isValidDirectory(String uri) {
    File file = getFile(uri);
    return file.exists() && file.isDirectory();
  }

  private File getFile(String uri) {
    return new File(uri);
  }

  public byte[] header(String date) {
    String responseHeader = "HTTP/1.1 200 OK" +
        CRLF +
        "Content-Type: text/html" +
        CRLF +
        "Date: " +
        date +
        CRLF +
        CRLF;
    return responseHeader.getBytes();
  }

  public byte[] body(String fullURI) {
    return index(fullURI).getBytes();
  }

  private String index(String fullURI) {
    File file = fullURI.equals("/") ? rootDirectory : getFile(fullURI);
    Path absolute = getAbsolutePath(file);
    Path relative = relativizePath(rootDirectory, file);
    return "<!doctype html>" +
        "<html>" +
        "<head><title>index</title></head>" +
        "<body>" +
        "<h1>Absolute path:" + absolute + "</h1>" +
        "<h2>Relative path:" + relative + "</h2>" +
        linkfyDir(file) +
        "</body>" +
        "</html>";
  }

  private String linkfyDir(File directory) {
    File[] files = directory.listFiles();
    String startList = "<ul>";
    String endList = "</ul>";
    StringBuilder listItems = new StringBuilder();

    for (File file : files) {
      listItems.append("<li><a href=\"");
      listItems.append(file.getAbsolutePath());
      listItems.append("\">");
      listItems.append(file.getName());
      listItems.append("</a></li>");
    }
    return startList + listItems + endList;
  }

  private Path getAbsolutePath(File file) {
    return Paths.get(file.getAbsolutePath()).normalize();
  }

  private Path relativizePath(File root, File file) {
    Path rootPath = getAbsolutePath(root);
    Path filePath = Paths.get(file.getAbsolutePath());
    return rootPath.relativize(filePath).normalize();
  }
}
