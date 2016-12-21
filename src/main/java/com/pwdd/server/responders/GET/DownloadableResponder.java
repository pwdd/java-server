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
    return (Protocol.version + " " + Protocol.statusCodes.get("200") + CRLF +
        "Date: " + date + CRLF +
        "Content-Type: application/octet-stream" + CRLF +
        "Content-Disposition: attachment" + CRLF +
        "Content-Length: " + body(file).length + CRLF +
        CRLF).getBytes();
  }

  boolean isDownloadable(File file) {
    return file.isFile() && !FileHandler.isImage(file) && !FileHandler.isPdf(file);
  }
}