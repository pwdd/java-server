package com.pwdd.httpServer.responders;

public interface IResponder {
  boolean canRespond(String uri);

  byte[] header(String date);

  byte[] body(String uri);
}
