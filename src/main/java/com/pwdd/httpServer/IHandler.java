package com.pwdd.httpServer;

interface IHandler {
  boolean canRespond(String uri);

  String respond(String uri);
}
