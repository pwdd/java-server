package com.pwdd.httpServer.connection;

import com.pwdd.httpServer.Response;

import java.net.ServerSocket;
import java.net.Socket;

class Server implements Runnable {
  private ServerSocket serverSocket;
  private Socket socket;
  private final int portNumber;
  private Boolean listening = false;
  private ConnectionManager connectionManager;
  private final Response response;

  Server(int _portNumber, Response _response) {
    this.portNumber = _portNumber;
    this.response = _response;
  }

  void listenAt(int portNumber) throws Exception {
    serverSocket = new ServerSocket(portNumber);
  }

  private void openConnection() throws Exception {
    socket = serverSocket.accept();
  }

  private void startConnectionHandler() {
    connectionManager = new ConnectionManager(socket, response);
  }

  @Override
  public void run() {
    listening = true;

    try {
      listenAt(portNumber);
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
