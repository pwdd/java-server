package com.pwdd.httpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

final class RequestParser {
  private static final String crlf = "\r\n";

  private RequestParser() {}

  static HashMap<String, String> header(BufferedReader in) throws IOException {
    HashMap<String, String> headerMap = new HashMap<>();
    String requestString = bufToString(in);
    String[] requestArray = stringToStringArray(requestString);
    parseFirstLine(headerMap, requestArray[0]);

    String[] request = Arrays.copyOfRange(requestArray, 1, requestArray.length);

    for (String line : request) {
      String[] splitLine = line.split(": ");
      headerMap.put(splitLine[0], splitLine[1]);
    }

    return headerMap;
  }

  private static String bufToString(BufferedReader in) throws IOException {
    StringBuilder builder = new StringBuilder();
    String line;

    while ((line = in.readLine()) != null && !line.equals("")) {
      builder.append(line + crlf);
    }
    return builder.toString();
  }

  private static String[] stringToStringArray(String in) throws IOException {
    return in.split("\\r\\n");
  }

  private static HashMap<String, String> parseFirstLine(HashMap<String, String> map, String firstLine) {
    String[] firstLineList = firstLine.split("\\s");

    map.put("Method", firstLineList[0]);
    map.put("URI", firstLineList[1]);
    map.put("Protocol", firstLineList[2]);
    return map;
  }
}