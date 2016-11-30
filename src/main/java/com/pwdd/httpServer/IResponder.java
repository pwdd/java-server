package com.pwdd.httpServer;

interface IResponder {
  boolean canRespond(String uri);

  byte[] header(String date);

  byte[] body();
}
