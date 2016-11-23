package com.pwdd.httpServer;

import java.net.ServerSocket;
import java.net.Socket;

class Server implements Runnable {
  private ServerSocket serverSocket;
  private Socket socket;
  private String rootDirectory;
  private int portNumber;
  private Boolean started = false;
  private ConnectionHandler connectionHandler;
  Responder responder;

  Server(String _rootDirectory, int _portNumber) {
    this.rootDirectory = _rootDirectory;
    this.portNumber = _portNumber;
    this.responder = new Responder(rootDirectory);
  }

  Server() {
    this.rootDirectory = "foo";
    this.portNumber = 8080;
    this.responder = new Responder(rootDirectory);
  }

  void listenAt(int portNumber) throws Exception {
    serverSocket = new ServerSocket(portNumber);
  }

  private void openConnection() throws Exception {
    socket = serverSocket.accept();
  }

  private void startConnectionHandler() {
    connectionHandler = new ConnectionHandler(socket, responder);
  }

  @Override
  public void run() {
    started = true;

    try {
      listenAt(portNumber);
      while(started) {
        openConnection();
        startConnectionHandler();
        connectionHandler.run();
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
