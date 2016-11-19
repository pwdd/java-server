package com.pwdd.httpServer;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

class Server {
  private ServerSocket serverSocket;
  private Socket socket;

  void listenAt(int portNumber) {
    try {
      serverSocket = new ServerSocket(portNumber);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void openConnection() {
    try {
      socket = serverSocket.accept();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  void run() {
    ConnectionHandler connectionHandler;

    int portNumber = 8080;
    listenAt(portNumber);

    while(true) {
      try {
        openConnection();
        connectionHandler = new ConnectionHandler(socket);
        HashMap<String, String> request = RequestParser.header(connectionHandler.getRequestFrom(socket));
        String uri = request.get("URI");
        String response = Response.defaultHeader() + Router.forRequested(uri);
        connectionHandler.sendResponseTo(socket,  response);
        socket.close();
      } catch (Exception e) {
        e.printStackTrace();
        return;
      }
    }
  }

  void stop() {
    try {
      serverSocket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
