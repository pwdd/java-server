package com.pwdd.server.connection;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Server implements Runnable {
  private Boolean listening = false;
  private ServerSocket serverSocket;
  private final int portNumber;
  private final File rootDirectory;
  private final ExecutorService pool = Executors.newFixedThreadPool(4);

  Server(int _portNumber, File _rootDirectory) {
    this.portNumber = _portNumber;
    this.rootDirectory = _rootDirectory;
  }

  void listen() throws IOException {
    serverSocket = new ServerSocket(portNumber);
  }

  @Override
  public void run() {
    interceptTermination();
    try {
      listen();
      listening = true;
      while (listening) {
        pool.execute(new ConnectionManager(serverSocket.accept(), rootDirectory));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  void stop() throws IOException {
    serverSocket.close();
    listening = false;
  }

  private void waitTermination() {
    pool.shutdown();
    try {
      if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
        pool.shutdownNow();
      }
    } catch (InterruptedException ie) {
      pool.shutdownNow();
      Thread.currentThread().interrupt();
    }
  }

  private void interceptTermination() {
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        waitTermination();
      }
    });
  }
}
