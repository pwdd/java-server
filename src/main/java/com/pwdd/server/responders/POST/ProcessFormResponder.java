package com.pwdd.server.responders.POST;

import com.pwdd.server.protocol.Protocol;
import com.pwdd.server.responders.IResponder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ProcessFormResponder implements IResponder {
  private String formData;

  public ProcessFormResponder(String _formData) {
    this.formData = _formData;
  }

  public boolean canRespond(File file) {
    return file.getPath().toLowerCase().matches(".*/processed-form/?$.*");
  }

  public byte[] header(File file, String date) {
    return (Protocol.version + " " + Protocol.statusCodes.get("200") + CRLF + "Date: " + date + CRLF + CRLF).getBytes();
  }

  public byte[] body(File file) {
    StringBuilder html = new StringBuilder();
    html.append("<!doctype html><html><head><title>Form data</title></head><body><h1>Sent data:</h1><ul>").
        append(formatFormData()).append("</ul>").
        append(back()).
        append("</body></html>");
    return html.toString().getBytes();
  }

  private String back() {
    return "<a href=\"/form\"><button>back</button></a>";
  }

  HashMap<String, String> mapFormData() {
    String separatedFormData = formData.replaceAll("&|=", " ");
    String[] split = separatedFormData.split(" ");
    HashMap<String, String> mapData = new HashMap<>();
    for (int i = 0; i < split.length; i++) {
      if (i % 2 == 0) {
        String capitalize = split[i].substring(0, 1).toUpperCase() + split[i].substring(1);
        mapData.put(capitalize, split[i + 1]);
      }
    }
    return mapData;
  }

  private String capitalizeKey(String key) {
    return key.substring(0, 1).toUpperCase() + key.substring(1);
  }

  private String formatFormData() {
    HashMap<String, String> mapped = mapFormData();
    StringBuilder formatted = new StringBuilder();

    for (Map.Entry<String, String> entry : mapped.entrySet()) {
      String key = capitalizeKey(entry.getKey());
      String value = entry.getValue();
      formatted.append("<li>").append(key).append(": ").append(value).append("</li>");
    }
    return formatted.toString();
  }
}
