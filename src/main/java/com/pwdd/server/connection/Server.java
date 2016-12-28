package com.pwdd.server.connection;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Server implements Runnable {
  private final int NUMBEROFTHREADS = 4;
  private ServerSocket serverSocket;
  private final int portNumber;
  private Boolean listening = false;
  private final File rootDirectory;
  private final ExecutorService pool;

  Server(int _portNumber, File _rootDirectory) {
    this.portNumber = _portNumber;
    this.rootDirectory = _rootDirectory;
    this.pool = Executors.newFixedThreadPool(NUMBEROFTHREADS);
  }

  void listen() throws Exception {
    serverSocket = new ServerSocket(portNumber);
  }

  @Override
  public void run() {
    listening = true;

    try {
      listen();
      while(listening) {
        pool.execute(new ConnectionManager(serverSocket.accept(), rootDirectory));
      }
    } catch (Exception e) {
      pool.shutdown();
    }
  }

  void stop() throws Exception {
    serverSocket.close();
    listening = false;
  }
}
