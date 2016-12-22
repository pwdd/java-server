package com.pwdd.server.responders.GET;

import com.pwdd.server.protocol.Protocol;
import com.pwdd.server.responders.IResponder;
import com.pwdd.server.utils.FileHandler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

public class DownloadableResponder extends FileReader implements IResponder {

  public boolean canRespond(File file) {
    return isDownloadable(file);
  }

  public InputStream header(File file, String date) {
    return new ByteArrayInputStream((Protocol.version + " " + Protocol.statusCodes.get("200") + CRLF +
        "Date: " + date + CRLF +
        "Content-Type: application/octet-stream" + CRLF +
        "Content-Disposition: attachment" + CRLF +
        "Content-Length: " + size(file) + CRLF +
        CRLF).getBytes());
  }

  boolean isDownloadable(File file) {
    return file.isFile() && !FileHandler.isImage(file) && !FileHandler.isPdf(file);
  }
}
