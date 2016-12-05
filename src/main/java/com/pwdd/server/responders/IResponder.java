package com.pwdd.server.responders;

public interface IResponder {
  String CRLF = "\r\n";

  boolean canRespond(String fullURI);

  byte[] header(String date);

  byte[] body(String fullURI);
}