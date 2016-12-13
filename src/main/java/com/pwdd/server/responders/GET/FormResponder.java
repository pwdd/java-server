package com.pwdd.server.responders.GET;

import com.pwdd.server.protocol.Protocol;
import com.pwdd.server.responders.IResponder;

import java.io.File;

public class FormResponder implements IResponder {
  public boolean canRespond(File file) {
    return file.getPath().toLowerCase().matches(".*^/form/?$.*");
  }

  public byte[] header(File file, String date) {
    String responseHeader = Protocol.version + " " + Protocol.statusCodes.get("200") + CRLF +
        "Content-Type: " + "text/html" + CRLF +
        "Date: " + date + CRLF +
        "Content-Length: " + body(file).length + CRLF +
        CRLF;
    return responseHeader.getBytes();
  }

  public byte[] body(File file) {
    String responseBody = "<!doctype html><html><head><title>Form</title></head><body><h1>Form</h1>" +
        "<form method=\"post\" action=\"/processed-form\" id=\"form\">" +
        "<label>Text: </label>" +
        "<input type=\"text\" name=\"text\"><br>" +
        "<label>Number: </label>" +
        "<input type=\"number\" name=\"number\"><br>" +
        "<select  name=\"select\">" +
        "<option value=\"one\">one</option>" +
        "<option value=\"two\">two</option>" +
        "<option value=\"three\">three</option>" +
        "</select><br>" +
        "<button type=\"submit\" value=\"Submit\">Submit</button>" +
        "</form>" +
        "</body></html>";
    return responseBody.getBytes();
  }
}
