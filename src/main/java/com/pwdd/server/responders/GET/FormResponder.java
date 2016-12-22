package com.pwdd.server.responders.GET;

import com.pwdd.server.protocol.Protocol;
import com.pwdd.server.responders.IResponder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

public class FormResponder implements IResponder {
  public boolean canRespond(File file) {
    return file.getPath().toLowerCase().matches(".*^/form/?$.*");
  }

  public InputStream header(File file, String date) {
    return new ByteArrayInputStream((Protocol.version + " " + Protocol.statusCodes.get("200") + CRLF +
        "Content-Type: " + "text/html" + CRLF +
        "Date: " + date + CRLF +
        "Content-Length: " + body(file) + CRLF +
        CRLF).getBytes());
  }

  public InputStream body(File file) {
    return new ByteArrayInputStream(("<!doctype html><html><head><title>Form</title></head><body><h1>Form</h1>" +
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
        "</body></html>").getBytes());
  }
}
