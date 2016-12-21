package com.pwdd.server.responders.GET;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

abstract class FileReader {

  public byte[] body(File file) {
    byte[] bodyBytes = new byte[1024];
    try {
      bodyBytes = fileToByteArray(file);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bodyBytes;
  }

  String getExtension(File file) {
    String[] split = file.getName().split("\\.");
    return split[split.length - 1];
  }

  private byte[] fileToByteArray(File file) throws IOException {
    int chunkSize = 8192;
    byte[] chunks = new byte[chunkSize];
    FileInputStream input = new FileInputStream(file.getAbsolutePath());
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int bytesRead;
    while ((bytesRead = input.read(chunks)) != -1) {
      outputStream.write(chunks, 0, bytesRead);
    }
    return outputStream.toByteArray();
  }
}







//  private byte[] fileToByteArray(File file) throws IOException {
//    int chunkSize = 15 * 1024;
//    FileChannel inChannel = new FileInputStream(file.getAbsolutePath()).getChannel();
//    ByteBuffer buffer = ByteBuffer.allocate((int) inChannel.size());
//    byte[] chunk = new byte[chunkSize];
//    byte[] fileData = new byte[0];
//    int bytesRead, bytesGet;
//    while ((bytesRead = inChannel.read(buffer)) != -1) {
//      if (bytesRead == 0) {
//        continue;
//      }
//      buffer.position(0);
//      buffer.limit(bytesRead);
//      fileData = new byte[buffer.remaining()];
//      buffer.get(fileData);
//      while (buffer.hasRemaining()) {
//        bytesGet = Math.min(buffer.remaining(), chunkSize);
//        buffer.get(chunk, 0, bytesGet);
//      }
//      buffer.clear();
//    }
//    inChannel.close();
//    return fileData;
//  }