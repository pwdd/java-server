package com.pwdd.httpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

class ConnectionHandler implements Runnable {
  private Socket socket;
  private Router router;

  ConnectionHandler(Socket _socket, Router _router) {
    this.socket = _socket;
    this.router = _router;
  }

  BufferedReader getRequestFrom(Socket socket) throws IOException {
    return new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }

  void sendResponseTo(Socket socket, String response) throws IOException {
    PrintWriter out = new PrintWriter(socket.getOutputStream());
    out.print(response);
    out.flush();
  }

  @Override
  public void run() {
    try {
      String uri = RequestParser.header(getRequestFrom(socket)).get("URI");
      String response = Response.defaultHeader(router.contentType(uri)) + router.forRequested(uri);
      sendResponseTo(socket, response);
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
