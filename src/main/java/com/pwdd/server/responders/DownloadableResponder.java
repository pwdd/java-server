package com.pwdd.server.responders;

import com.pwdd.server.utils.FileHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DownloadableResponder implements IResponder {

  public boolean canRespond(String uri) {
    return isDownloadable(uriToFile(uri));
  }

  public byte[] header(String date) {
    String contentType = "application/octet-stream";
    String contentDisposition = "attachment";
    return ("HTTP/1.1 200 OK" + CRLF +
        "Date: " + date + CRLF +
        "Content-Type: " + contentType + CRLF +
        "Content-Disposition: " + contentDisposition + CRLF +
        CRLF).getBytes();
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
    return file.isFile() && !FileHandler.isImage(file) && !FileHandler.isPdf(file);
  }

  private File uriToFile(String uri) {
    return new File(uri);
  }

  private byte[] fileToByteArray(File file) throws IOException {
    Path path = Paths.get(file.getAbsolutePath());
    return Files.readAllBytes(path);
  }
}
