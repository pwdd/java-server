package com.pwdd.httpServer;

import com.pwdd.httpServer.utils.InputReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public final class RequestParser {
  private RequestParser() {}

  public static HashMap<String, String> header(BufferedReader in) throws IOException {
    HashMap<String, String> headerMap = new HashMap<>();
    String requestString = InputReader.bufToString(in);
    String[] requestArray = stringToStringArray(requestString);
    parseFirstLine(headerMap, requestArray[0]);

    String[] request = Arrays.copyOfRange(requestArray, 1, requestArray.length);

    for (String line : request) {
      String[] splitLine = line.split(": ");
      headerMap.put(splitLine[0], splitLine[1]);
    }

    return headerMap;
  }

  private static String[] stringToStringArray(String in) {
    return in.split("\\r\\n");
  }

  private static void parseFirstLine(HashMap<String, String> map, String firstLine) {
    String[] firstLineList = firstLine.split("\\s");

    map.put("Method", firstLineList[0]);
    map.put("URI", firstLineList[1]);
    map.put("Protocol", firstLineList[2]);
  }
}