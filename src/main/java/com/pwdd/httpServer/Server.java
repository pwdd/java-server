package com.pwdd.httpServer;

import java.net.ServerSocket;
import java.net.Socket;

class Server implements Runnable {
  private ServerSocket serverSocket;
  private Socket socket;
  private String rootDirectory;
  private Boolean started = false;
  Responder responder;

  Server(String _rootDirectory) {
    this.rootDirectory = _rootDirectory;
    this.responder = new Responder(rootDirectory);
  }

  void listenAt(int portNumber) throws Exception {
    serverSocket = new ServerSocket(portNumber);
  }

  private void openConnection() throws Exception {
    socket = serverSocket.accept();
  }

  @Override
  public void run() {
    started = true;
    int portNumber = 8080;

    try {
      listenAt(portNumber);
      while(started) {
        openConnection();
        new ConnectionHandler(socket, responder).run();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  void stop() {
    try {
      serverSocket.close();
      started = false;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
