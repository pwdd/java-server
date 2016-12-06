package com.pwdd.server.responders;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    return file.isFile() && !isImage(file) && !isPdf(file);
  }

  boolean isImage(File file) {
    Pattern pattern = Pattern.compile("\\.jpeg|\\.jpg|\\.png|\\.tiff|\\.gif");
    Matcher matcher = pattern.matcher(file.getName());
    return matcher.find();
  }

  boolean isPdf(File file) {
    return file.getName().endsWith(".pdf");
  }

  private File uriToFile(String uri) {
    return new File(uri);
  }

  private byte[] fileToByteArray(File file) throws IOException {
    Path path = Paths.get(file.getAbsolutePath());
    return Files.readAllBytes(path);
  }
}
