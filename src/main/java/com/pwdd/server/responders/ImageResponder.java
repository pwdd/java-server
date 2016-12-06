package com.pwdd.server.responders;

import com.pwdd.server.utils.FileHandler;

import java.io.File;

public class ImageResponder extends FileReader implements IResponder {

  public boolean canRespond(File file) {
    return file.exists() && (FileHandler.isImage(file) || FileHandler.isPdf(file));
  }

  public byte[] header(File file, String date) {
    String responseHeader = "HTTP/1.1 200 OK" + CRLF +
        "Content-Type: " + contentTypeFor(file) + CRLF +
        "Date: " + date + CRLF +
        CRLF;
    return responseHeader.getBytes();
  }

  private String contentTypeFor(File file) {
    String extension = getExtension(file);
    return extension.equals("pdf") ? "application/pdf" : "image/" + imageExtension(extension);
  }

  private String imageExtension(String extension) {
    return extension.equals("png") ? extension : "jpeg";
  }
}