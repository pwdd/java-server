package com.pwdd.httpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ConnectionHandler implements Runnable {
  private final Socket socket;
  private final Responder responder;

  ConnectionHandler(Socket _socket, Responder _responder) {
    this.socket = _socket;
    this.responder = _responder;
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
      String response = responder.response(uri);
      sendResponseTo(socket, response);
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
