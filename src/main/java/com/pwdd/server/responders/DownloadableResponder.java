package com.pwdd.server.responders;

import com.pwdd.server.utils.FileHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DownloadableResponder extends FileReader implements IResponder {

  public boolean canRespond(File file) {
    return isDownloadable(file);
  }

  public byte[] header(File file, String date) {
    return ("HTTP/1.1 200 OK" + CRLF +
        "Date: " + date + CRLF +
        "Content-Type: application/octet-stream" + CRLF +
        "Content-Disposition: attachment" + CRLF +
        CRLF).getBytes();
  }

  boolean isDownloadable(File file) {
    return file.isFile() && !FileHandler.isImage(file) && !FileHandler.isPdf(file);
  }
}
