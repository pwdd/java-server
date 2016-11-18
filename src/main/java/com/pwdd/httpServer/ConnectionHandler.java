package com.pwdd.httpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ConnectionHandler {
  private Socket socket;

  ConnectionHandler(Socket _socket) {
    this.socket = _socket;
  }

  BufferedReader getRequestFrom(Socket socket) throws IOException {
    return new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }

  void sendResponse(Socket socket, String response) throws IOException {
    PrintWriter out = new PrintWriter(socket.getOutputStream());
    out.print(response);
    out.flush();
  }
}
