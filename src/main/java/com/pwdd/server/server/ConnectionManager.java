package com.pwdd.server.server;

import com.pwdd.server.RequestParser;
import com.pwdd.server.responders.ResponseBuilder;
import com.pwdd.server.utils.FileHandler;

import java.io.*;
import java.net.Socket;

class ConnectionManager implements Runnable {
  private File rootDirectory;
  private final Socket socket;
  private final ResponseBuilder responseBuilder;

  ConnectionManager(Socket _socket, ResponseBuilder _responseBuilder, File _rootDirectory) {
    this.socket = _socket;
    this.responseBuilder = _responseBuilder;
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
      String uri = RequestParser.header(getRequestFrom(socket)).get("URI");
      File file = defineFile(uri);
      byte[] response = this.responseBuilder.response(file);
      sendResponseTo(socket, response);
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private File defineFile(String uri) {
    if (uri.toLowerCase().equals("/hello") || uri.toLowerCase().equals("/hello/")) {
      return new File("/hello");
    } else {
      return FileHandler.uriToFile(rootDirectory.getAbsolutePath(), uri);
    }
  }
}
