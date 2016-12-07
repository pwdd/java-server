package com.pwdd.server.responders.GET;

import com.pwdd.server.protocol.Protocol;
import com.pwdd.server.responders.IResponder;
import com.pwdd.server.utils.FileHandler;

import java.io.File;

public class DownloadableResponder extends FileReader implements IResponder {

  public boolean canRespond(File file) {
    return isDownloadable(file);
  }

  public byte[] header(File file, String date) {
    return (Protocol.version + " 200 OK" + CRLF +
        "Date: " + date + CRLF +
        "Content-Type: application/octet-stream" + CRLF +
        "Content-Disposition: attachment" + CRLF +
        CRLF).getBytes();
  }

  boolean isDownloadable(File file) {
    return file.isFile() && !FileHandler.isImage(file) && !FileHandler.isPdf(file);
  }
}
