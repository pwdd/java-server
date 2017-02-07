package com.pwdd.server.protocol;

import com.pwdd.server.responders.IResponder;
import com.pwdd.server.responders.POST.ProcessFormResponder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class POST implements Protocol {
  private String requestBody;
  private ResponseBuilder responseBuilder;

  public POST(String _requestBody, ResponseBuilder _responseBuilder) {
    this.requestBody = _requestBody;
    this.responseBuilder = _responseBuilder;
  }

  public InputStream processResponse(
      HashMap<String, String> request,
      File rootDirectory,
      IResponder[] responders) throws IOException {
    return responseBuilder.processResponse(request, rootDirectory, responders, this);
  }

  public IResponder[] responders() {
    return new IResponder[]{
        new ProcessFormResponder(requestBody)
    };
  }
}
