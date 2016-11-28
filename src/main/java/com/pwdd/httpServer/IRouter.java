package com.pwdd.httpServer;

interface IRouter {
  boolean canRespond(String uri);

  String respond();

  String setContentType();
}
