package com.pwdd.httpServer;

interface IResponder {
  boolean canRespond(String uri);

  String header(String date);

  String body();
}
