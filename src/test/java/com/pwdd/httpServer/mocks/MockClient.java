package com.pwdd.httpServer.mocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MockClient {
  public Socket socket;
  public BufferedReader in;
  public PrintWriter out;

  public MockClient(String hostName, int portNumber) throws IOException {
    this.socket = new Socket(hostName, portNumber);
    this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.out = new PrintWriter(socket.getOutputStream(), true);
  }
}