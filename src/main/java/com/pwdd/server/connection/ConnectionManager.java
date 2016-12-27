package com.pwdd.server.connection;

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

  void sendResponseTo(Socket socket, InputStream response) throws IOException {
    byte[] buf = new byte[1024];
    int bytesRead = 0;
    BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
    while ((bytesRead = response.read(buf)) != -1) {
      out.write(buf, 0, bytesRead);
    }
    response.close();
    out.flush();
    out.close();
  }

  @Override
  public void run() {
    try {
      HashMap<String, String> request = RequestParser.requestMap(getRequestFrom(socket));
      Protocol method = getProtocol(request);
      IResponder[] responders = method.responders();
      sendResponseTo(socket, method.processResponse(request, rootDirectory, responders));
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Protocol getProtocol(HashMap<String, String> request) throws IOException {
    return request.get("Method").equalsIgnoreCase("POST") ?
        new POST(request.get("Body")) :
        new GET(rootDirectory);
  }
}
