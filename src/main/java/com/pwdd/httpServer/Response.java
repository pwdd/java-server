package com.pwdd.httpServer;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class Response {
  private String protocolVersion = "HTTP/1.1";
  private String statusCode;
  private String statusMessage;
  private String contentType;
  private String contentDisposition;
  String contentLength;
  private String body;
  private String date;
  private String crlf = "\r\n";

  Response(HashMap<String, String> responseArgs) {
    this.statusCode = responseArgs.get("statusCode");
    this.statusMessage = responseArgs.get("statusMessage");
    this.date = getDateInUTC0();
    this.contentType = responseArgs.get("contentType");
    this.contentDisposition = responseArgs.get("contentDisposition");
    this.contentLength = responseArgs.get("contentLength");
    this.body = responseArgs.get("body");
  }

  String stringResponse() {
    return firstResponseLine() + crlf + joinedResponseArray(responseArray()).trim() + crlf + crlf + existingBody();
  }

  private String existingBody() {
    if (body != null) {
      return body;
    } else {
      return "";
    }
  }

  private String firstResponseLine() {
    return protocolVersion + " " + statusCode + " " + statusMessage;
  }

  private String joinedResponseArray(List<String> responseArray) {
    String str = "";
    for (String s : responseArray) {
      str += s + crlf;
    }
    return str;
  }

  private List<String> responseArray() {
    return new ArrayList<>(Arrays.asList(
        filterOutNullValues("Date: ", date),
        filterOutNullValues("Content-Type: ", contentType),
        filterOutNullValues("Content-Disposition: ", contentDisposition),
        filterOutNullValues("Content-Length: ", contentLength)));
  }

  private String filterOutNullValues(String item, String value) {
    String composed = "";
    if (value != null) {
      composed = item + value;
    }
    return composed;
  }

  private String getDateInUTC0() {
    ZonedDateTime date = ZonedDateTime.now(ZoneOffset.UTC);
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z");
    return dateFormat.format(date);
  }
}
