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
    int portNumber = 8080;
    listenAt(portNumber);

    while(true) {
      try {
        openConnection();
        new Thread(new ConnectionHandler(socket)).start();
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
