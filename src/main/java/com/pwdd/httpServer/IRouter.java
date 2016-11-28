package com.pwdd.httpServer;

interface IRouter {
  boolean canRespond(String uri);

  String header(String date);

  String body();
}
