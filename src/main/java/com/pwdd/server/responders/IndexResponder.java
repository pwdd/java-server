package com.pwdd.server.responders;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IndexResponder implements IResponder {
  private File rootDirectory;

  public IndexResponder(File _rootDirectory) {
    this.rootDirectory = _rootDirectory;
  }

  public boolean canRespond(File file) {
    return isValidDirectory(file);
  }

  private boolean isValidDirectory(File file) {
    return file.exists() && file.isDirectory();
  }

  public byte[] header(File file, String date) {
    String responseHeader = "HTTP/1.1 200 OK" + CRLF +
        "Content-Type: text/html" + CRLF +
        "Date: " + date + CRLF +
        CRLF;
    return responseHeader.getBytes();
  }

  public byte[] body(File file) {
    return index(file).getBytes();
  }

  private String index(File file) {
    Path absolute = normalizeAbsolutePath(file);
    Path relative = relativizePath(rootDirectory, file);
    return "<!doctype html><html><head><title>" +
        relative +
        "</title></head><body><h1>Absolute path:" +
        absolute +
        "</h1><h2>Relative path: /" +
        relative +
        "</h2>" +
        linkfyDir(file) +
        "</body></html>";
  }

  private String linkfyDir(File directory) {
    File[] files = directory.listFiles();
    String startList = "<ul>";
    String endList = "</ul>";
    StringBuilder listItems = new StringBuilder();

    for (File file : files) {
      listItems.append("<li><a href=\"").
          append(relativizePath(directory, file)).
          append("\">").
          append(file.getName()).
          append("</a></li>");
    }
    return startList + listItems + endList;
  }

  private Path normalizeAbsolutePath(File file) {
    return Paths.get(file.getAbsolutePath()).normalize();
  }

  private Path relativizePath(File root, File file) {
    Path rootPath = normalizeAbsolutePath(root);
    Path filePath = Paths.get(file.getAbsolutePath());
    return rootPath.relativize(filePath).normalize();
  }
}
