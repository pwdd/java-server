package com.pwdd.httpServer;

import com.pwdd.httpServer.utils.URICleaner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class DownloadableResponder implements IResponder {

  public boolean canRespond(String uri) {
    return isDownloadable(uriToFile(uri));
  }

  public byte[] header(String date) {
    String contentType = "application/octet-stream";
    String contentDisposition = "attachment";
    String crlf = "\r\n";
    return ("HTTP/1.1 200 OK" + crlf +
        "Date: " + date + crlf +
        "Content-Type: " + contentType + crlf +
        "Content-Disposition: " + contentDisposition + crlf +
        crlf).getBytes();
  }

  public byte[] body(String uri) {
    File file = uriToFile(uri);
    byte[] bodyBytes = new byte[0];
    try {
      bodyBytes = fileToByteArray(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bodyBytes;
  }

  boolean isDownloadable(File file) {
    return file.isFile();
  }

  private File uriToFile(String uri) {
    String cleaned = URICleaner.cleanUp(uri);
    return new File(cleaned);
  }

  private byte[] fileToByteArray(File file) throws IOException {
    Path path = Paths.get(file.getAbsolutePath());
    return Files.readAllBytes(path);
  }
}
