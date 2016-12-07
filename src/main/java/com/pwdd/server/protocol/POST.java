package com.pwdd.server.protocol;

import com.pwdd.server.responders.IResponder;

import java.io.BufferedReader;
import java.io.IOException;

public class POST implements Protocol {

  public IResponder[] responders() {
    return new IResponder[]{};
  }

  public byte[] processResponse(BufferedReader request) throws IOException {
    return "".getBytes();
  }
}
