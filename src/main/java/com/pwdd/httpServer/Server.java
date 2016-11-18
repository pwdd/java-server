package com.pwdd.httpServer;

import java.net.ServerSocket;
import java.net.Socket;

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
        System.out.println(connectionHandler.getRequestFrom(socket).readLine());
        connectionHandler.sendResponseTo(socket, "foo");
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
