package com.pwdd.httpServer.connection;

import com.pwdd.httpServer.RequestParser;
import com.pwdd.httpServer.Response;

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

  void sendResponseTo(Socket socket, byte[] response) throws IOException {
    OutputStream out = socket.getOutputStream();
    out.write(response);
    out.flush();
  }

  @Override
  public void run() {
    try {
      String uri = RequestParser.header(getRequestFrom(socket)).get("URI");
      byte[] response = this.response.response(uri);
      sendResponseTo(socket, response);
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
