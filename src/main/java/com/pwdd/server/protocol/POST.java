package com.pwdd.server.protocol;

import com.pwdd.server.responders.IResponder;
import com.pwdd.server.responders.POST.ProcessFormResponder;

import java.io.File;

public class POST extends ResponseBuilder implements Protocol {
  private File rootDirectory;
  private String requestBody;
  private IResponder[] responders;

  public POST(File _rootDirectory, String _requestBody) {
    this.requestBody = _requestBody;
    this.responders = responders();
    this.rootDirectory = _rootDirectory;
  }

  public IResponder[] responders() {
    return new IResponder[]{
        new ProcessFormResponder(requestBody)
    };
  }

  @Override
  public String errorMessage() {
    return Protocol.version + " " + Protocol.statusCodes.get("400") + IResponder.CRLF;
  }
}
