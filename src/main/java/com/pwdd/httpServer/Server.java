package com.pwdd.httpServer;

import java.net.ServerSocket;
import java.net.Socket;

class Server implements Runnable {
  private ServerSocket serverSocket;
  private Socket socket;
  private final int portNumber;
  private Boolean started = false;
  private ConnectionHandler connectionHandler;
  private final Responder responder;

  Server(int _portNumber, Responder _responder) {
    this.portNumber = _portNumber;
    this.responder = _responder;
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

  void stop() throws Exception {
    serverSocket.close();
    started = false;
  }
}
