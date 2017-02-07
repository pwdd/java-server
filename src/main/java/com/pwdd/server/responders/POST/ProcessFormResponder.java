package com.pwdd.server.responders.POST;

import com.pwdd.server.protocol.Protocol;
import com.pwdd.server.responders.IResponder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ProcessFormResponder implements IResponder {
  private final String formData;

  public ProcessFormResponder(String _formData) {
    this.formData = _formData;
  }

  public boolean canRespond(File file) {
    return validateForm(mapFormData()) && isValidPath(file.getPath());
  }

  private boolean isValidPath(String path) {
    return path.toLowerCase().matches(".*/processed-form/?$.*");
  }

  public InputStream header(File file, String date) {
    return new ByteArrayInputStream((Protocol.version + " " + Protocol.statusCodes.get("200") + CRLF + "Date: " + date + CRLF + CRLF).getBytes());
  }

  public InputStream body(File file) {
    String back = "<a href=\"/form\"><button>back</button></a>";

    String html = "<!doctype html><html><head><title>Form data</title></head><body><h1>Sent data:</h1><ul>" +
        formatFormData() + "</ul>" +
        back +
        "</body></html>";

    return new ByteArrayInputStream(html.getBytes());
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

  private boolean validateForm(HashMap<String, String> data) {
    return data.size() == 3 &&
        checkKeyExistence(data, "Text") &&
        checkKeyExistence(data, "Number") &&
        checkKeyExistence(data, "Select") &&
        validateNumber(data.get("Number")) &&
        validateSelect(data.get("Select"));
  }

  private boolean checkKeyExistence(HashMap<String, String> data, String key) {
    return data.get(key) != null;
  }

  private boolean validateNumber(String number) {
    return number.matches("\\d+");
  }

  private boolean validateSelect(String select) {
    return select.equalsIgnoreCase("one") ||
        select.equalsIgnoreCase("two") ||
        select.equalsIgnoreCase("three");
  }
}
