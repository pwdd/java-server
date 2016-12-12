package com.pwdd.server.server;

import com.pwdd.server.RequestParser;
import com.pwdd.server.protocol.GET;
import com.pwdd.server.protocol.POST;
import com.pwdd.server.protocol.Protocol;
import com.pwdd.server.responders.IResponder;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

class ConnectionManager implements Runnable {
  private final File rootDirectory;
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
      HashMap<String, String> request = RequestParser.requestMap(getRequestFrom(socket));
      Protocol method = getProtocol(request);
      IResponder[] responders = method.responders();
      byte[] response =  getProtocol(request).processResponse(request, rootDirectory, responders);
      sendResponseTo(socket, response);
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Protocol getProtocol(HashMap<String, String> request) throws IOException {
    return request.get("Method").equalsIgnoreCase("POST") ?
        new POST(rootDirectory, request.get("Body")) :
        new GET(rootDirectory);
  }
}
