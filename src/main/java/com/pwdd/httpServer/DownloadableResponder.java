package com.pwdd.httpServer;

import com.pwdd.httpServer.utils.URICleaner;

import java.io.File;

class DownloadableResponder implements IResponder {

  public boolean canRespond(String uri) {
    return isDownloadable(uriToFile(uri));
  }

  public String header(String date) {
    String contentType = "application/octet-stream";
    String contentDisposition = "attachment";
    String crlf = "\r\n";
    return "HTTP/1.1 200 OK" + crlf +
        "Date: " + date + crlf +
        "Content-Type: " + contentType + crlf +
        "Content-Disposition: " + contentDisposition + crlf +
        crlf;
  }

  public String body() {
    return "downloadable";
  }

  boolean isDownloadable(File file) {
    return file.isFile();
  }

  private File uriToFile(String uri) {
    String cleaned = URICleaner.cleanUp(uri);
    return new File(cleaned);
  }
}
