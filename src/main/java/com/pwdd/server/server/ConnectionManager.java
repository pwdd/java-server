package com.pwdd.server.server;

import com.pwdd.server.protocol.GET;

import java.io.*;
import java.net.Socket;

class ConnectionManager implements Runnable {
  private File rootDirectory;
  private final Socket socket;

  ConnectionManager(Socket _socket, File _rootDirectory) {
    this.socket = _socket;
    this.rootDirectory = _rootDirectory;
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
      byte[] response = new GET(rootDirectory).processResponse(getRequestFrom(socket));
      sendResponseTo(socket, response);
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
