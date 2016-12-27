package com.pwdd.server.responders.GET;

import com.pwdd.server.protocol.Protocol;
import com.pwdd.server.responders.IResponder;
import com.pwdd.server.utils.FileHandler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

public class ImageResponder extends FileReader implements IResponder {

  public boolean canRespond(File file) {
    return file.exists() && (FileHandler.isImage(file) || FileHandler.isPdf(file));
  }

  public InputStream header(File file, String date) {
    return new ByteArrayInputStream((Protocol.version + " " + Protocol.statusCodes.get("200") + CRLF +
        "Content-Type: " + contentTypeFor(file) + CRLF +
        "Date: " + date + CRLF +
        "Content-Length: " + size(file) + CRLF +
        CRLF).getBytes());
  }

  private String contentTypeFor(File file) {
    String extension = getExtension(file);
    return extension.equals("pdf") ? "application/pdf" : "image/" + imageExtension(extension);
  }

  private String imageExtension(String extension) {
    return extension.equals("png") ? extension : "jpeg";
  }
}