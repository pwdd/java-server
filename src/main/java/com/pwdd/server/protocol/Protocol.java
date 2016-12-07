package com.pwdd.server.protocol;

import com.pwdd.server.responders.IResponder;

import java.io.BufferedReader;
import java.io.IOException;

public interface Protocol {
  String version = "HTTP/1.1";

  byte[] processResponse(BufferedReader request) throws IOException;

  IResponder[] responders();
}