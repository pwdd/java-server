package com.pwdd.server.responders.POST;

import com.pwdd.server.responders.IResponder;

import java.io.File;

public class FormResponder implements IResponder {
  public boolean canRespond(File file) {
    return false;
  }

  public byte[] header(File file, String date) {
    return "".getBytes();
  }

  public byte[] body(File file) {
    return "".getBytes();
  }
}
