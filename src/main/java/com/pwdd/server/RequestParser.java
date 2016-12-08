package com.pwdd.server;

import com.pwdd.server.responders.IResponder;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public final class RequestParser {
  private RequestParser() {}

  public static HashMap<String, String> header(BufferedReader request) throws IOException {
    HashMap<String, String> headerMap = new HashMap<>();
    String requestString = bufToString(request);
    String[] requestArray = stringToStringArray(requestString);
    parseFirstLine(headerMap, requestArray[0]);
    String[] restOfRequest = Arrays.copyOfRange(requestArray, 1, requestArray.length);

    for (String line : restOfRequest) {
      String[] splitLine = line.split(": ");
      headerMap.put(splitLine[0], splitLine[1]);
    }

    return headerMap;
  }

  private static String bufToString(BufferedReader buf) throws IOException {
    int contentLength = 0;
    String contentLengthKey = "Content-Length: ";
    StringBuilder request = new StringBuilder();
    String line;

    while ((line = buf.readLine()) != null && !line.equals("")) {
      request.append(line).append(IResponder.CRLF);

      if (line.contains(contentLengthKey)) {
        contentLength = Integer.parseInt(line.substring(contentLengthKey.length()));
      }
    }
    if (contentLength > 0) {
      getBody(buf, request, contentLength);
    }
    return request.toString();
  }

  private static void getBody(BufferedReader buf, StringBuilder base, int size) throws IOException {
    char[] body = new char[size];
    buf.read(body);
    base.append("Body: ").append(new String(body)).append(IResponder.CRLF);
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