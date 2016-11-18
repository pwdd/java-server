package com.pwdd.httpServer;

import java.net.ServerSocket;

class Server {
  private ServerSocket serverSocket;

  void listenAt(int portNumber) {
    try {
      serverSocket = new ServerSocket(portNumber);
    } catch (Exception e) {
      e.printStackTrace();
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
