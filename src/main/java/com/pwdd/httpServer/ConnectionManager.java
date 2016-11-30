package com.pwdd.httpServer;

import java.io.*;
import java.net.Socket;

class ConnectionManager implements Runnable {
  private final Socket socket;
  private final Response response;

  ConnectionManager(Socket _socket, Response _response) {
    this.socket = _socket;
    this.response = _response;
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
      String response = this.response.response(uri);
      sendResponseTo(socket, response);
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
