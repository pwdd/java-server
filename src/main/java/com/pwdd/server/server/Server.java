package com.pwdd.server.server;

import com.pwdd.server.responders.ResponseBuilder;

import java.net.ServerSocket;
import java.net.Socket;

class Server implements Runnable {
  private ServerSocket serverSocket;
  private Socket socket;
  private final int portNumber;
  private Boolean listening = false;
  private ConnectionManager connectionManager;
  private final ResponseBuilder responseBuilder;

  Server(int _portNumber, ResponseBuilder _responseBuilder) {
    this.portNumber = _portNumber;
    this.responseBuilder = _responseBuilder;
  }

  void listen() throws Exception {
    serverSocket = new ServerSocket(portNumber);
  }

  private void openConnection() throws Exception {
    socket = serverSocket.accept();
  }

  private void startConnectionHandler() {
    connectionManager = new ConnectionManager(socket, responseBuilder);
  }

  @Override
  public void run() {
    listening = true;

    try {
      listen();
      while(listening) {
        openConnection();
        startConnectionHandler();
        connectionManager.run();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  void stop() throws Exception {
    serverSocket.close();
    listening = false;
  }
}
