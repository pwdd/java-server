package com.pwdd.server.mocks;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MockSocket extends Socket {
  private String stringRequest;
  public String storedOutput;
  private final List<Byte> listOfBytes = new ArrayList<>();

  public MockSocket () {}

  public InputStream getInputStream() {
    return new ByteArrayInputStream(stringRequest.getBytes());
  }

  public OutputStream getOutputStream() {
    return new OutputStream() {
      @Override
      public void write(int b) throws IOException {
        listOfBytes.add((byte) b);
      }
    };
  }

  public void setRequestString(String request) {
    stringRequest = request;
  }

  public void setStoredOutput() {
    storedOutput = bytesToString(listToByteArray(listOfBytes));
  }

  private byte[] listToByteArray(List<Byte> lob) {
    byte[] byteArray = new byte[lob.size()];
    int index = 0;
    for (byte b : lob) {
      byteArray[index++] = b;
    }
    return byteArray;
  }

  private String bytesToString(byte[] ba ) {
    return new String(ba, StandardCharsets.UTF_8);
  }
}
