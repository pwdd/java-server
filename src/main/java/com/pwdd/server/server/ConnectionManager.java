package com.pwdd.server.server;

import com.pwdd.server.RequestParser;
import com.pwdd.server.responders.ResponseBuilder;

import java.io.*;
import java.net.Socket;

class ConnectionManager implements Runnable {
  private final Socket socket;
  private final ResponseBuilder responseBuilder;

  ConnectionManager(Socket _socket, ResponseBuilder _responseBuilder) {
    this.socket = _socket;
    this.responseBuilder = _responseBuilder;
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
      byte[] response = this.responseBuilder.response(uri);
      sendResponseTo(socket, response);
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
